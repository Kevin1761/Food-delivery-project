package org.example.springboot_demo.Controller;


import org.example.springboot_demo.POJO.OrderSubmitVO;
import org.example.springboot_demo.server.OrderService;
import org.example.springboot_demo.POJO.OrdersSubmitDTO;
import org.example.springboot_demo.server.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userOrderController")
@RequestMapping("/user/order")

public class orderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/submit")
    // @ApiOperation("User places an order")
    public Result<OrderSubmitVO> submitVOResult(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        OrderSubmitVO orderSubmitVO1 = orderService.submit(ordersSubmitDTO);
        return Result.success(orderSubmitVO1);

    }



}
