package org.example.springboot_demo.POJO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginVO {
    private int role;
    private long id;
    private String email;

}
