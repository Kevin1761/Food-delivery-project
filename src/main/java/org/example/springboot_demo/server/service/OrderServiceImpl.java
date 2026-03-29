package org.example.springboot_demo.server.service;

import org.example.springboot_demo.POJO.OrdersSubmitDTO;
import org.example.springboot_demo.server.OrderService;
import org.example.springboot_demo.server.Orders;
import org.example.springboot_demo.server.mapper.OrderDetailMapper;
import org.example.springboot_demo.server.mapper.OrderMapper;
import org.example.springboot_demo.POJO.OrderSubmitVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO){
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO,orders);
        orders.setUserId(1L);
        orders.setDeliveryTime(LocalDateTime.now());
        orders.setPayStatus(Orders.UN_PAID);
        orders.setNumber(String.valueOf(System.currentTimeMillis()));
        // Insert data into the database
        orderMapper.insert(orders);
        // Insert n order detail records (extend as needed)
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(orders.getId())
                .orderNumber(orders.getNumber())
                .money(orders.getAmount())
                .orderTime(orders.getOrderTime())
                .build();

        return orderSubmitVO;
    }

}
