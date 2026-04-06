package org.example.springboot_demo.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.springboot_demo.server.Orders;

import java.util.List;

@Mapper
public interface OrderMapper {

    public void insert(Orders orders); // Insert data into database

    // Select all orders
    @Select("SELECT * FROM orders ORDER BY id DESC")
    List<Orders> selectAll();

    // Select order by ID
    @Select("SELECT * FROM orders WHERE id = #{id}")
    Orders selectById(Long id);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
