package org.example.springboot_demo.Controller;


import org.example.springboot_demo.POJO.UserLoginVO;
import org.example.springboot_demo.POJO.UserloginDTO;
import org.example.springboot_demo.server.Result;
import org.example.springboot_demo.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("login")
@RequestMapping("/user")
public class loginController {
    @Autowired
    LoginService loginService;
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserloginDTO userloginDTO){
        UserLoginVO userLoginVO1 = loginService.login(userloginDTO);
        return Result.success(userLoginVO1);
    }

}
