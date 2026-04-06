package org.example.springboot_demo.POJO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class MerchantOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String number;
    private BigDecimal amount;
    private Integer status;
    private Integer payStatus;
    private String phone;
    private String address;
    private LocalDateTime orderTime;
    private LocalDateTime checkoutTime;
    private List<OrderDetailVO> items;

    public MerchantOrderVO() {
    }

    public MerchantOrderVO(Long id, String number, BigDecimal amount, Integer status, Integer payStatus,
                          String phone, String address, LocalDateTime orderTime, LocalDateTime checkoutTime, List<OrderDetailVO> items) {
        this.id = id;
        this.number = number;
        this.amount = amount;
        this.status = status;
        this.payStatus = payStatus;
        this.phone = phone;
        this.address = address;
        this.orderTime = orderTime;
        this.checkoutTime = checkoutTime;
        this.items = items;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Integer getPayStatus() { return payStatus; }
    public void setPayStatus(Integer payStatus) { this.payStatus = payStatus; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalDateTime getOrderTime() { return orderTime; }
    public void setOrderTime(LocalDateTime orderTime) { this.orderTime = orderTime; }

    public LocalDateTime getCheckoutTime() { return checkoutTime; }
    public void setCheckoutTime(LocalDateTime checkoutTime) { this.checkoutTime = checkoutTime; }

    public List<OrderDetailVO> getItems() { return items; }
    public void setItems(List<OrderDetailVO> items) { this.items = items; }

    public static class OrderDetailVO implements Serializable {
        private Long id;
        private String dishName;
        private Integer quantity;
        private BigDecimal price;

        public OrderDetailVO() {
        }

        public OrderDetailVO(Long id, String dishName, Integer quantity, BigDecimal price) {
            this.id = id;
            this.dishName = dishName;
            this.quantity = quantity;
            this.price = price;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getDishName() { return dishName; }
        public void setDishName(String dishName) { this.dishName = dishName; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }

        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
    }
}
