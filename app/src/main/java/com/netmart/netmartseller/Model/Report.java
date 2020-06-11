package com.netmart.netmartseller.Model;

public class Report {
    String item_name, item_qty, item_num, item_price, date_order, time_order;
    int orderId;

    public Report(){}

    public Report(String item_name, String item_qty, String item_num, String item_price, String date_order, String time_order, int orderId) {
        this.item_name = item_name;
        this.item_qty = item_qty;
        this.item_num = item_num;
        this.item_price = item_price;
        this.date_order = date_order;
        this.time_order = time_order;
        this.orderId = orderId;
    }

    public String getTime_order() {
        return time_order;
    }

    public void setTime_order(String time_order) {
        this.time_order = time_order;
    }

    public String getDate_order() {
        return date_order;
    }

    public void setDate_order(String date_order) {
        this.date_order = date_order;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_qty() {
        return item_qty;
    }

    public void setItem_qty(String item_qty) {
        this.item_qty = item_qty;
    }

    public String getItem_num() {
        return item_num;
    }

    public void setItem_num(String item_num) {
        this.item_num = item_num;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
