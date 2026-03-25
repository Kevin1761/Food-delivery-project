package org.example.springboot_demo.POJO;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private int code;// 发送0代表 用户  发送1 代表商家
    private String email;
    private String password;

}
