package com.example.demo.chen;

import java.util.UUID;


/**
 * Created by user on 2017/11/9.
 */
class HeadBase{
    int mcd;
    int ver = 1;
    int type = 1;
    long lsb = UUID.randomUUID().getLeastSignificantBits();
    long msb = UUID.randomUUID().getMostSignificantBits();

    HeadBase(int msg){
        this.mcd = msg;
    }

    public int getMcd() {
        return mcd;
    }

    public void setMcd(int mcd) {
        this.mcd = mcd;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getLsb() {
        return lsb;
    }

    public void setLsb(long lsb) {
        this.lsb = lsb;
    }

    public long getMsb() {
        return msb;
    }

    public void setMsb(long msb) {
        this.msb = msb;
    }
}