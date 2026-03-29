package org.example.springboot_demo.server.service;

import org.example.springboot_demo.POJO.Entity.User;
import org.example.springboot_demo.POJO.OrderSubmitVO;
import org.example.springboot_demo.POJO.UserLoginVO;
import org.example.springboot_demo.POJO.UserloginDTO;
import org.example.springboot_demo.server.mapper.LoginMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    LoginMapper loginMapper;
    public UserLoginVO login(UserloginDTO userloginDTO){
        User user = new User();
        String email = userloginDTO.getEmail();
        String password = userloginDTO.getPassword();
        user= loginMapper.getEmail(email);
        if(user==null){
            throw new RuntimeException("account does not exist");
        }
        if(!password.equals(user.getPassword())){
            throw new RuntimeException("password is wrong");
        }
        UserLoginVO userLoginVO= UserLoginVO.builder()
                .email(user.getEmail())
                .id(user.getId())
                .role(user.getRole())
                .build();
        return userLoginVO;
    }

}
