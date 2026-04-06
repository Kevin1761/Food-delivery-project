package org.example.springboot_demo.server.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    // Insert order detail record
    @Insert("INSERT INTO order_details (order_id, dish_name, quantity, price, create_time) " +
            "VALUES (#{orderId}, #{dishName}, #{quantity}, #{price}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(org.example.springboot_demo.server.OrderDetail orderDetail);

    // Select order details by order ID with column mapping
    @Select("SELECT id, order_id, dish_name, quantity, price, create_time FROM order_details WHERE order_id = #{orderId}")
    @Results({
            @Result(column = "order_id", property = "orderId"),
            @Result(column = "dish_name", property = "dishName"),
            @Result(column = "create_time", property = "createTime")
    })
    List<org.example.springboot_demo.server.OrderDetail> selectByOrderId(Long orderId);
}
