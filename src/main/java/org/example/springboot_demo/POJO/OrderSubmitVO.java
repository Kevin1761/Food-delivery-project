package org.example.springboot_demo.POJO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderSubmitVO {

    private long id;               // Order id
    private String orderNumber;    // Order number
    private BigDecimal money;      // Order amount
    private LocalDateTime orderTime; // Order time
}
