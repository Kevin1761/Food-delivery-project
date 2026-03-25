package org.example.springboot_demo.POJO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterVO {
    private int role;//0 代表用户 1 代表商家
    private long id;
    private String email;
}
