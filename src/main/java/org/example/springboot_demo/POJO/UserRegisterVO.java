package org.example.springboot_demo.POJO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterVO {
    private int role;   // 0 represents user, 1 represents merchant
    private long id;
    private String email;
}
