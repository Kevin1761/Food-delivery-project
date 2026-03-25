package org.example.springboot_demo.server.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.example.springboot_demo.POJO.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface LoginMapper {

    @Select("select id, email, password, role from user where email = #{email}")
    User getEmail(String email);

    @Insert("insert into user (email, password, role) values (#{email},#{password},#{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void setUser(User user);
}
