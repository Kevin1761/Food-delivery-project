package org.example.springboot_demo.POJO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrdersSubmitDTO implements Serializable {
    private Long addressBookId;
    private String address;
    private String phone;
    private int payMethod;
    private String remark;
    private String orderTime;
    private String estimatedDeliveryTime;
    private Integer deliveryStatus;
    private Integer tablewareNumber;
    private Integer tablewareStatus;
    private Integer packAmount;
    private BigDecimal amount;
    private List<OrderDetail> orderDetails;

    public OrdersSubmitDTO() {
    }

    public OrdersSubmitDTO(Long addressBookId, String address, String phone, int payMethod, String remark,
                           String orderTime, String estimatedDeliveryTime, Integer deliveryStatus,
                           Integer tablewareNumber, Integer tablewareStatus, Integer packAmount, BigDecimal amount,
                           List<OrderDetail> orderDetails) {
        this.addressBookId = addressBookId;
        this.address = address;
        this.phone = phone;
        this.payMethod = payMethod;
        this.remark = remark;
        this.orderTime = orderTime;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.deliveryStatus = deliveryStatus;
        this.tablewareNumber = tablewareNumber;
        this.tablewareStatus = tablewareStatus;
        this.packAmount = packAmount;
        this.amount = amount;
        this.orderDetails = orderDetails;
    }

    public Long getAddressBookId() { return addressBookId; }
    public void setAddressBookId(Long addressBookId) { this.addressBookId = addressBookId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getPayMethod() { return payMethod; }
    public void setPayMethod(int payMethod) { this.payMethod = payMethod; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public String getOrderTime() { return orderTime; }
    public void setOrderTime(String orderTime) { this.orderTime = orderTime; }

    public String getEstimatedDeliveryTime() { return estimatedDeliveryTime; }
    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) { this.estimatedDeliveryTime = estimatedDeliveryTime; }

    public Integer getDeliveryStatus() { return deliveryStatus; }
    public void setDeliveryStatus(Integer deliveryStatus) { this.deliveryStatus = deliveryStatus; }

    public Integer getTablewareNumber() { return tablewareNumber; }
    public void setTablewareNumber(Integer tablewareNumber) { this.tablewareNumber = tablewareNumber; }

    public Integer getTablewareStatus() { return tablewareStatus; }
    public void setTablewareStatus(Integer tablewareStatus) { this.tablewareStatus = tablewareStatus; }

    public Integer getPackAmount() { return packAmount; }
    public void setPackAmount(Integer packAmount) { this.packAmount = packAmount; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public List<OrderDetail> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<OrderDetail> orderDetails) { this.orderDetails = orderDetails; }

    public static class OrderDetail implements Serializable {
        private String name;
        private Integer quantity;
        private BigDecimal price;

        public OrderDetail() {
        }

        public OrderDetail(String name, Integer quantity, BigDecimal price) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }

        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
    }
}
