package com.example.chatapplication.Model;

public class userModel {
    String email,status,pimageUrl;

    public userModel(){}

    public userModel(String email, String status,String pimageUrl) {
        this.email = email;
        this.status = status;
        this.pimageUrl= pimageUrl;
    }

    public userModel(String email, String status) {
        this.email = email;
        this.status = status;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPimageUrl() {
        return pimageUrl;
    }

    public void setPimageUrl(String pimageUrl) {
        this.pimageUrl = pimageUrl;
    }
}
