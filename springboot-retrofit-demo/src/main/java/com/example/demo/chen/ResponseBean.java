package com.example.demo.chen;

import java.io.Serializable;

/**
 * Created by user on 2017/11/16.
 */

public class ResponseBean {
    protected int result;
    protected int errorCode;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
