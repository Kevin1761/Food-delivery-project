package org.example.springboot_demo.POJO;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private int code;// send 0 for user, 1 for merchant
    private String email;
    private String password;

}
