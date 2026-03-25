package org.example.springboot_demo.POJO;

import lombok.Data;

@Data
public class UserloginDTO {
    // 0 代表商家 1代表用户
    private String email;
    private String password;
}
