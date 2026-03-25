package org.example.springboot_demo.server;

import org.example.springboot_demo.POJO.OrdersSubmitDTO;
import org.example.springboot_demo.POJO.OrderSubmitVO;

public interface OrderService {

    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);
}
