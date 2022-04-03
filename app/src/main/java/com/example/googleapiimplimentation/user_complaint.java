package com.example.googleapiimplimentation;

public class user_complaint {
    String name;
    String mobile;
    String address;
    String Image;
    String status;
    String pincode;
  String date;
    public user_complaint() {

    }
    public user_complaint(String name, String mobile, String address,String Image,String status,String pincode,String date) {
    this.name=name;
    this.mobile=mobile;
    this.address=address;
     this.Image=Image;
     this.status=status;
     this.pincode=pincode;
     this.date=date;

    }

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

    public String getMobile() {

        return mobile;

    }

    public void setMobile(String mobile) {

        this.mobile = mobile;

    }

    public String getAddress() {

        return address;

    }

    public void setAddress(String address) {

        this.address = address;

    }
    public String getPincode() {

        return pincode;

    }

    public void setpincode(String pincode) {

        this.pincode = pincode;

    }
    public String getdate() {

        return date;

    }

    public void setdate(String date) {

        this.date = date;

    }
    public void setImage(String Image) {

        this.Image = Image;

    }
    public String getImage() {

        return Image;

    }

}

