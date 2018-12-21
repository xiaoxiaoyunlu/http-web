package com.example.demo.chen;

public class UploadSKu {
    String body;
    String head;

    UploadSKu(){
        this.head = Main.bean2Str(new HeadBase(100085));
        this.body = Main.bean2Str(new Body());
    }

    class Body{
    	
    	String openid="c685f4a911c7e49abcd5df2122c43e46";
    	String token= "976324b97982938954ef8be29c934a1f";
    	String sku="测试室sku";
    	Double price = 12.6;
    	Double weight=1.0;
    	Long good_id=24L;
    	Long shop_id=5L;
    }
}
