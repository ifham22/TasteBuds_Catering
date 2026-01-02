package com.tastebuds.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {
    @XmlElement(required = true)
    private String orderNo;

    @XmlElement(required = true)
    private String customerId;

    @XmlElement(required = true)
    private String items;

    @XmlElement
    private double billAmount;

    @XmlElement
    private double finalBill;

    @XmlElement
    private String status;

    @XmlElement
    private int queuePosition;

    @XmlElement
    private OrderCategory category;

    @XmlElement
    private String assignedChefs;

    @XmlElement
    private int estimatedTime;

    @XmlElement
    private String driverId;

    @XmlElement
    private String vehicle;

    public Order() {}

    public Order(String orderNo, String customerId, String items,
                 double billAmount, double finalBill, int queuePosition) {
        this.orderNo = orderNo;
        this.customerId = customerId;
        this.items = items;
        this.billAmount = billAmount;
        this.finalBill = finalBill;
        this.status = "PLACED";
        this.queuePosition = queuePosition;
        this.category = OrderCategory.NORMAL;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public double getFinalBill() {
        return finalBill;
    }

    public void setFinalBill(double finalBill) {
        this.finalBill = finalBill;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQueuePosition() {
        return queuePosition;
    }

    public void setQueuePosition(int queuePosition) {
        this.queuePosition = queuePosition;
    }

    public OrderCategory getCategory() {
        return category;
    }

    public void setCategory(OrderCategory category) {
        this.category = category;
    }

    public String getAssignedChefs() {
        return assignedChefs;
    }

    public void setAssignedChefs(String assignedChefs) {
        this.assignedChefs = assignedChefs;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    // Business methods
    public void markAsPreparing(OrderCategory category, String chefs, int time) {
        this.category = category;
        this.assignedChefs = chefs;
        this.estimatedTime = time;
        this.status = "PREPARING";
    }

    public void markAsReady() {
        this.status = "READY";
    }

    public void assignDelivery(String driverId, String vehicle) {
        this.driverId = driverId;
        this.vehicle = vehicle;
        this.status = "OUT_FOR_DELIVERY";
    }

    public void markAsDelivered() {
        this.status = "DELIVERED";
    }

    @Override
    public String toString() {
        return "Order{orderNo='" + orderNo + "', customerId='" + customerId +
                "', status='" + status + "', category=" + category + "}";
    }
}