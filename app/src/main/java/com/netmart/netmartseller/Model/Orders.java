package com.netmart.netmartseller.Model;

public class Orders {

    private String name, username, items_name, phone, houseNo, street, houseArea, city, postcode, state, sent, received, date, discount, time, totalAmount, orderId, uid, pid, delivererId, delivererName, timeorder, timearrival, orderPickupTime, orderTrackingId;
    private int item_num;
    //private int orderId, uid, pid, delivererId, timeorder, timearrival;

    public Orders(){

    }

    public Orders(String name, String username, String items_name, String phone, String houseNo, String street, String houseArea, String city, String postcode, String state, String sent, String received, String date, String discount, String time, String totalAmount, String orderId, String uid, String pid, String delivererId, String delivererName, String timeorder, String timearrival, String orderPickupTime, String orderTrackingId, int item_num) {
        this.name = name;
        this.username = username;
        this.items_name = items_name;
        this.phone = phone;
        this.houseNo = houseNo;
        this.street = street;
        this.houseArea = houseArea;
        this.city = city;
        this.postcode = postcode;
        this.state = state;
        this.sent = sent;
        this.received = received;
        this.date = date;
        this.discount = discount;
        this.time = time;
        this.totalAmount = totalAmount;
        this.orderId = orderId;
        this.uid = uid;
        this.pid = pid;
        this.delivererId = delivererId;
        this.delivererName = delivererName;
        this.timeorder = timeorder;
        this.timearrival = timearrival;
        this.orderPickupTime = orderPickupTime;
        this.orderTrackingId = orderTrackingId;
        this.item_num = item_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getItems() {
        return items_name;
    }

    public void setItems(String items) {
        this.items_name = items;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(String houseArea) {
        this.houseArea = houseArea;
    }

    public String getCity() {
        return city;
    }

    public String getItems_name() {
        return items_name;
    }

    public void setItems_name(String items_name) {
        this.items_name = items_name;
    }

    public int getItem_num() {
        return item_num;
    }

    public void setItem_num(int item_num) {
        this.item_num = item_num;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDelivererId() {
        return delivererId;
    }

    public void setDelivererId(String delivererId) {
        this.delivererId = delivererId;
    }

    public String getDelivererName() {
        return delivererName;
    }

    public void setDelivererName(String delivererName) {
        this.delivererName = delivererName;
    }

    public String getTimeorder() {
        return timeorder;
    }

    public void setTimeorder(String timeorder) {
        this.timeorder = timeorder;
    }

    public String getTimearrival() {
        return timearrival;
    }

    public void setTimearrival(String timearrival) {
        this.timearrival = timearrival;
    }

    public String getOrderPickupTime() {
        return orderPickupTime;
    }

    public void setOrderPickupTime(String orderPickupTime) {
        this.orderPickupTime = orderPickupTime;
    }

    public String getOrderTrackingId() {
        return orderTrackingId;
    }

    public void setOrderTrackingId(String orderTrackingId) {
        this.orderTrackingId = orderTrackingId;
    }
}

