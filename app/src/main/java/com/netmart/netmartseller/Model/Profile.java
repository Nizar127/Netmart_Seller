package com.netmart.netmartseller.Model;

public class Profile {

    private int Id, bankAcct, bizHour;
    private String Username, sellername, store_desc, store_location, website, email, bankAcctName, receiptName, receiptTime;

    public Profile(){

    }

    public Profile(int id, int bankAcct, int bizHour, String username, String sellername, String store_desc, String store_location, String website, String email, String bankAcctName, String receiptName, String receiptTime) {
        Id = id;
        this.bankAcct = bankAcct;
        this.bizHour = bizHour;
        Username = username;
        this.sellername = sellername;
        this.store_desc = store_desc;
        this.store_location = store_location;
        this.website = website;
        this.email = email;
        this.bankAcctName = bankAcctName;
        this.receiptName = receiptName;
        this.receiptTime = receiptTime;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getBankAcct() {
        return bankAcct;
    }

    public void setBankAcct(int bankAcct) {
        this.bankAcct = bankAcct;
    }

    public int getBizHour() {
        return bizHour;
    }

    public void setBizHour(int bizHour) {
        this.bizHour = bizHour;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getSellername() {
        return sellername;
    }

    public void setSellername(String sellername) {
        this.sellername = sellername;
    }

    public String getStore_desc() {
        return store_desc;
    }

    public void setStore_desc(String store_desc) {
        this.store_desc = store_desc;
    }

    public String getStore_location() {
        return store_location;
    }

    public void setStore_location(String store_location) {
        this.store_location = store_location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankAcctName() {
        return bankAcctName;
    }

    public void setBankAcctName(String bankAcctName) {
        this.bankAcctName = bankAcctName;
    }

    public String getReceiptName() {
        return receiptName;
    }

    public void setReceiptName(String receiptName) {
        this.receiptName = receiptName;
    }

    public String getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(String receiptTime) {
        this.receiptTime = receiptTime;
    }
}
