package com.example.chatapplication.Model;

public class disp_msgModel {
    String sender,msg,receiver;
    public disp_msgModel(){}

    public disp_msgModel(String sender, String msg,String receiver) {
        this.sender = sender;
        this.msg = msg;
        this.receiver=receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
