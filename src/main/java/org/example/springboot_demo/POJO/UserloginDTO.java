package org.example.springboot_demo.POJO;

import lombok.Data;

@Data
public class UserloginDTO {
    // 0 represents merchant, 1 represents user
    private String email;
    private String password;
}
