package org.example.springboot_demo.Controller;

import org.example.springboot_demo.POJO.UserLoginVO;
import org.example.springboot_demo.POJO.UserRegisterDTO;
import org.example.springboot_demo.POJO.UserRegisterVO;
import org.example.springboot_demo.POJO.UserloginDTO;
import org.example.springboot_demo.server.Result;
import org.example.springboot_demo.server.service.LoginService;
import org.example.springboot_demo.server.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("register")
@RequestMapping("/user")
public class registerController {
    @Autowired
    RegisterService registerService;
    @PostMapping("/register")
    public Result<UserRegisterVO> login(@RequestBody UserRegisterDTO userRegisterDTO){
        UserRegisterVO userRegisterVO = registerService.register(userRegisterDTO);
        return Result.success(userRegisterVO);
    }
}
