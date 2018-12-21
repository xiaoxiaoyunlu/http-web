package com.example.demo.chen;

public class UserandUpdate {
    String body;
    String head;

    UserandUpdate(){
        this.head = Main.bean2Str(new HeadBase(100004));
        this.body = Main.bean2Str(new Body());
    }

    class Body{
    	String phone = "18621986579" ;
    	Integer is_consumer = 0;
    	String openid="c685f4a911c7e49abcd5df2122c43e46";
    	String token= "976324b97982938954ef8be29c934a1f";
    	String username="18621986579";
    }
}
