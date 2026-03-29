package org.example.springboot_demo.POJO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrdersSubmitDTO implements Serializable {
    // Address book id
    private Long addressBookId;
    // Payment method
    private int payMethod;
    // Remark
    private String remark;
    // Estimated delivery time
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedDeliveryTime;
    // Delivery status: 1 = send immediately, 0 = choose specific time
    private Integer deliveryStatus;
    // Tableware quantity
    private Integer tablewareNumber;
    // Tableware status: 1 = according to number of meals, 0 = choose specific quantity
    private Integer tablewareStatus;
    // Packaging fee
    private Integer packAmount;
    // Total amount
    private BigDecimal amount;
}
