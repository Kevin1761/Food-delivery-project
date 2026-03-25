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

    private long id;// 订单号
    private String orderNumber;//订单名字
    private BigDecimal money;//订单金额
    private LocalDateTime orderTime; //订单时间
}
