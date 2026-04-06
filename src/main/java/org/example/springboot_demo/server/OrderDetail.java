package org.example.springboot_demo.server;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long orderId;
    private String dishName;
    private Integer quantity;
    private BigDecimal price;
    private LocalDateTime createTime;

    public OrderDetail() {
    }

    public OrderDetail(Long id, Long orderId, String dishName, Integer quantity, BigDecimal price, LocalDateTime createTime) {
        this.id = id;
        this.orderId = orderId;
        this.dishName = dishName;
        this.quantity = quantity;
        this.price = price;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", dishName='" + dishName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", createTime=" + createTime +
                '}';
    }
}
