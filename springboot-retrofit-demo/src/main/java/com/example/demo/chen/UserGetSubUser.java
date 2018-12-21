package com.example.demo.chen;

public class UserGetSubUser {
    String body;
    String head;

    UserGetSubUser(){
        this.head = Main.bean2Str(new HeadBase(100012));
        this.body = Main.bean2Str(new Body());
    }

    class Body{
    	Long distributor_id = 3L ;
    	Integer type = 2;
    	String openid="cc80474ae24441878cc1d019e2320714";
    	String token= "82206fe5e3a55af95c0a7132518cebed";
    }
}
