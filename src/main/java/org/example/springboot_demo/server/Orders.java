package org.example.springboot_demo.server;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Order entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {

    /**
     * Order status: 1 = pending payment, 2 = to be accepted,
     * 3 = accepted, 4 = in delivery, 5 = completed, 6 = cancelled.
     */
    public static final Integer PENDING_PAYMENT = 1;
    public static final Integer TO_BE_CONFIRMED = 2;
    public static final Integer CONFIRMED = 3;
    public static final Integer DELIVERY_IN_PROGRESS = 4;
    public static final Integer COMPLETED = 5;
    public static final Integer CANCELLED = 6;

    /**
     * Payment status: 0 = unpaid, 1 = paid, 2 = refunded.
     */
    public static final Integer UN_PAID = 0;
    public static final Integer PAID = 1;
    public static final Integer REFUND = 2;

    private static final long serialVersionUID = 1L;

    private Long id;

    // Order number
    private String number;

    // Order status: 1 = pending payment, 2 = to be accepted,
    // 3 = accepted, 4 = in delivery, 5 = completed, 6 = cancelled, 7 = refunded
    private Integer status;

    // User id who placed the order
    private Long userId;

    // Address book id
    private Long addressBookId;

    // Order time
    private LocalDateTime orderTime;

    // Checkout time
    private LocalDateTime checkoutTime;

    // Payment method: 1 = WeChat, 2 = Alipay
    private Integer payMethod;

    // Payment status: 0 = unpaid, 1 = paid, 2 = refunded
    private Integer payStatus;

    // Actual received amount
    private BigDecimal amount;

    // Remark
    private String remark;

    // User name
    private String userName;

    // Phone number
    private String phone;

    // Address
    private String address;

    // Consignee
    private String consignee;

    // Reason for order cancellation
    private String cancelReason;

    // Reason for order rejection
    private String rejectionReason;

    // Order cancellation time
    private LocalDateTime cancelTime;

    // Estimated delivery time
    private LocalDateTime estimatedDeliveryTime;

    // Delivery status: 1 = send immediately, 0 = choose specific time
    private Integer deliveryStatus;

    // Actual delivery time
    private LocalDateTime deliveryTime;

    // Packaging fee
    private int packAmount;

    // Tableware quantity
    private int tablewareNumber;

    // Tableware status: 1 = according to number of meals, 0 = choose specific quantity
    private Integer tablewareStatus;
}
