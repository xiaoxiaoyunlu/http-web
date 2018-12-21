package com.example.demo.chen;

public class Register {
    String body;
    String head;

    Register(){
        this.head = Main.bean2Str(new HeadBase(100002));
        this.body = Main.bean2Str(new Body());
    }

    class Body{
    	String phone = "11111111111" ;
    	String pwd = "12345678";
    }
}
