package org.example.springboot_demo.POJO.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int role;
    private long id;
    private String email;
    private String password;
}
