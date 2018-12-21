package com.example.demo.chen;

public class UserGetPackageGood {
    String body;
    String head;

    UserGetPackageGood(){
        this.head = Main.bean2Str(new HeadBase(100011));
        this.body = Main.bean2Str(new Body());
    }

    class Body{
    	Long distributor_id = 3L ;
    	Integer type = 5;
    	String openid="c685f4a911c7e49abcd5df2122c43e46";
    	String token= "976324b97982938954ef8be29c934a1f";
    }
}
