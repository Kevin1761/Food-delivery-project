package org.example.springboot_demo.server.service;

import org.example.springboot_demo.POJO.MerchantOrderVO;
import org.example.springboot_demo.POJO.OrdersSubmitDTO;
import org.example.springboot_demo.server.OrderService;
import org.example.springboot_demo.server.Orders;
import org.example.springboot_demo.server.OrderDetail;
import org.example.springboot_demo.server.mapper.OrderDetailMapper;
import org.example.springboot_demo.server.mapper.OrderMapper;
import org.example.springboot_demo.POJO.OrderSubmitVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final DateTimeFormatter SPACE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Queue: stores pending order ids in arrival order for merchant processing.
    private final Queue<Long> pendingOrderQueue = new ConcurrentLinkedQueue<>();

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO){
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, orders, "orderTime", "estimatedDeliveryTime", "orderDetails");
        orders.setOrderTime(parseDateTime(ordersSubmitDTO.getOrderTime()));
        orders.setEstimatedDeliveryTime(parseDateTime(ordersSubmitDTO.getEstimatedDeliveryTime()));
        
        // Set required fields that must not be null
        orders.setUserId(1L);                                    // Current user ID
        orders.setStatus(Orders.TO_BE_CONFIRMED);                // Order status: pending confirmation
        orders.setNumber(String.valueOf(System.currentTimeMillis())); // Order number: timestamp
        orders.setPayStatus(Orders.UN_PAID);                     // Payment status: unpaid
        orders.setCheckoutTime(LocalDateTime.now());             // Checkout time
        orders.setDeliveryTime(LocalDateTime.now());             // Delivery time
        
        // Set default values for fields that may be missing
        if (orders.getAddressBookId() == null) {
            orders.setAddressBookId(0L);  // Default address book ID
        }
        if (orders.getTablewareStatus() == null) {
            orders.setTablewareStatus(1);  // Default tableware status
        }
        
        // packAmount and tablewareNumber are primitive int, so they default to 0
        // No need to check for null
        
        // Insert order into database
        orderMapper.insert(orders);
        
        // Insert order details (dishes)
        if (ordersSubmitDTO.getOrderDetails() != null && 
            !ordersSubmitDTO.getOrderDetails().isEmpty()) {
            for (OrdersSubmitDTO.OrderDetail detail : ordersSubmitDTO.getOrderDetails()) {
                OrderDetail od = new OrderDetail();
                od.setOrderId(orders.getId());
                od.setDishName(detail.getName());
                od.setQuantity(detail.getQuantity());
                od.setPrice(detail.getPrice());
                od.setCreateTime(LocalDateTime.now());
                orderDetailMapper.insert(od);
            }
        }

        pendingOrderQueue.offer(orders.getId());
        
        // Build response
        OrderSubmitVO orderSubmitVO = new OrderSubmitVO();
        orderSubmitVO.setId(orders.getId());
        orderSubmitVO.setOrderNumber(orders.getNumber());
        orderSubmitVO.setMoney(orders.getAmount());
        orderSubmitVO.setOrderTime(orders.getOrderTime());

        return orderSubmitVO;
    }

    @Override
    public List<MerchantOrderVO> getAllOrdersWithDetails() {
        // Get all orders
        List<Orders> orders = orderMapper.selectAll();
        
        // Convert to MerchantOrderVO and fetch details
        List<MerchantOrderVO> result = new ArrayList<>();
        for (Orders order : orders) {
            MerchantOrderVO vo = new MerchantOrderVO();
            vo.setId(order.getId());
            vo.setNumber(order.getNumber());
            vo.setAmount(order.getAmount());
            vo.setStatus(order.getStatus());
            vo.setPayStatus(order.getPayStatus());
            vo.setPhone(order.getPhone());
            vo.setAddress(order.getAddress());
            vo.setOrderTime(order.getOrderTime());
            vo.setCheckoutTime(order.getCheckoutTime());
            
            // Get order details
            List<OrderDetail> details = orderDetailMapper.selectByOrderId(order.getId());// list to store order details for current order
            List<MerchantOrderVO.OrderDetailVO> itemList = details.stream()
                    .map(d -> {
                        MerchantOrderVO.OrderDetailVO item = new MerchantOrderVO.OrderDetailVO();
                        item.setId(d.getId());
                        item.setDishName(d.getDishName());
                        item.setQuantity(d.getQuantity());
                        item.setPrice(d.getPrice());
                        return item;
                    })
                    .collect(Collectors.toList());
            
            vo.setItems(itemList);
            result.add(vo);
        }
        
        return result;
    }

    @Override
    public boolean updateOrderStatus(Long orderId, Integer status) {
        Orders order = orderMapper.selectById(orderId);
        if (order == null) {
            return false;
        }
        boolean updated = orderMapper.updateStatus(orderId, status) > 0;
        if (updated && status != Orders.TO_BE_CONFIRMED) {
            pendingOrderQueue.remove(orderId);
        }
        return updated;
    }

    @Override
    public List<Long> getPendingOrderQueue() {
        return new ArrayList<>(pendingOrderQueue);
    }

    @Override
    public Map<String, Integer> getDishOrderCounts() {
        List<Orders> orders = orderMapper.selectAll();
        Map<String, Integer> dishCounts = new HashMap<>();

        for (Orders order : orders) {
            List<OrderDetail> details = orderDetailMapper.selectByOrderId(order.getId());
            for (OrderDetail detail : details) {
                String dishName = detail.getDishName();
                int quantity = detail.getQuantity() == null ? 0 : detail.getQuantity();
                dishCounts.put(dishName, dishCounts.getOrDefault(dishName, 0) + quantity);
            }
        }

        return dishCounts.entrySet().stream()
                .sorted((left, right) -> Integer.compare(right.getValue(), left.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (left, right) -> left,
                        LinkedHashMap::new
                ));
    }

    private LocalDateTime parseDateTime(String value) {
        if (value == null || value.isBlank()) {
            return LocalDateTime.now();
        }

        try {
            return LocalDateTime.parse(value, ISO_FORMATTER);
        } catch (DateTimeParseException ignored) {
        }

        try {
            return LocalDateTime.parse(value, SPACE_FORMATTER);
        } catch (DateTimeParseException ignored) {
        }

        return LocalDateTime.parse(value);
    }

}
