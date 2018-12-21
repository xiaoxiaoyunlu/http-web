package com.example.demo.chen;

public class NetMainShopAndGoodsList {
    String body;
    String head;

    NetMainShopAndGoodsList(){
        this.head = Main.bean2Str(new HeadBase(100034));
        this.body = Main.bean2Str(new Body());
    }

    class Body{

        double lon =121.442446;
        double lat =31.194214;
        int num = 10;
        int gnum = 3;

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getGnum() {
            return gnum;
        }

        public void setGnum(int gnum) {
            this.gnum = gnum;
        }
    }
}
