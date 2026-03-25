package org.example.springboot_demo.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.springboot_demo.server.Orders;

@Mapper
public interface OrderMapper {

    public void insert(Orders orders);// 数据库插入数据

}
