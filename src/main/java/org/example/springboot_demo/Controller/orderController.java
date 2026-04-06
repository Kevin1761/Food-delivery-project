package org.example.springboot_demo.Controller;


import org.example.springboot_demo.POJO.MerchantOrderVO;
import org.example.springboot_demo.POJO.OrderSubmitVO;
import org.example.springboot_demo.server.OrderService;
import org.example.springboot_demo.POJO.OrdersSubmitDTO;
import org.example.springboot_demo.server.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/merchant/list")
    public Result<List<MerchantOrderVO>> getMerchantOrders(){
        List<MerchantOrderVO> orders = orderService.getAllOrdersWithDetails();
        return Result.success(orders);
    }

    @GetMapping("/merchant/pending/queue")
    public Result<List<Long>> getPendingOrderQueue() {
        return Result.success(orderService.getPendingOrderQueue());
    }

    @GetMapping("/merchant/dish-counts")
    public Result<Map<String, Integer>> getDishOrderCounts() {
        return Result.success(orderService.getDishOrderCounts());
    }

    @PostMapping("/merchant/{orderId}/accept")
    public Result<String> acceptOrder(@PathVariable Long orderId) {
        boolean updated = orderService.updateOrderStatus(orderId, org.example.springboot_demo.server.Orders.CONFIRMED);
        if (!updated) {
            return Result.error("Failed to accept order");
        }
        return Result.success("accepted");
    }

    @PostMapping("/merchant/{orderId}/reject")
    public Result<String> rejectOrder(@PathVariable Long orderId) {
        boolean updated = orderService.updateOrderStatus(orderId, org.example.springboot_demo.server.Orders.CANCELLED);
        if (!updated) {
            return Result.error("Failed to reject order");
        }
        return Result.success("rejected");
    }

}
