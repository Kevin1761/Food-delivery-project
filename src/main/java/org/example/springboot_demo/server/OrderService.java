package org.example.springboot_demo.server;

import org.example.springboot_demo.POJO.MerchantOrderVO;
import org.example.springboot_demo.POJO.OrdersSubmitDTO;
import org.example.springboot_demo.POJO.OrderSubmitVO;

import java.util.List;
import java.util.Map;

public interface OrderService {

    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    // Get all orders with details for merchant
    List<MerchantOrderVO> getAllOrdersWithDetails();

    boolean updateOrderStatus(Long orderId, Integer status);

    List<Long> getPendingOrderQueue();

    Map<String, Integer> getDishOrderCounts();
}
