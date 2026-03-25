package org.example.springboot_demo.server.service;

import org.example.springboot_demo.POJO.Entity.User;
import org.example.springboot_demo.POJO.UserRegisterDTO;
import org.example.springboot_demo.POJO.UserRegisterVO;
import org.example.springboot_demo.server.Result;
import org.example.springboot_demo.server.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    LoginMapper loginMapper;

    public UserRegisterVO register(UserRegisterDTO userRegisterDTO){
        int role = userRegisterDTO.getCode();
        String email =userRegisterDTO.getEmail();
        String password = userRegisterDTO.getPassword();

        User user = loginMapper.getEmail(email);// 从数据查询email 并封装成user
        if(user!=null&&user.getEmail() != null){//说明 用户存在
            throw new RuntimeException("user exists");
        }
        User user1 = new User();
        user1.setEmail(email);
        user1.setPassword(password);
        user1.setRole(role);
        loginMapper.setUser(user1);
        UserRegisterVO userRegisterVO = UserRegisterVO.builder()
                .email(user1.getEmail())
                .role(user1.getRole())
                .id(user1.getId())
                .build();
        return userRegisterVO;
    }
}
