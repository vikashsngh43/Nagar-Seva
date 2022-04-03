package com.example.googleapiimplimentation;

public class MainModel {
    String address,image,name,status,date,pincode;
    //String mobile;

    public MainModel(){}

    public MainModel(String address, String image,/*String mobile*/ String name, String Status,String date,String pincode) {
        this.address = address;
        this.image = image;
       // this.mobile = mobile;
        this.name = name;
        this.status = Status;
        this.pincode = pincode;
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /*public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
