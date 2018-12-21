package com.example.demo.chen;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by user on 2017/11/16.
 */

public class ResponseBase {
    private static Gson gson = new Gson();
    protected String head;
    protected String body;

    public HeadBase getHead() {
        return gson.fromJson(changeStr(body), HeadBase.class);
    }

    public String getBodyStr() {
        return changeStr(body);
    }

    protected String changeStr(String str) {
        return str.replace("\\", "")
                .replace("\"{", "{")
                .replace("}\"", "}");
    }

    private ResponseBean getNormalBean() {
        return gson.fromJson(getBodyStr(), ResponseBean.class);
    }

    public static <T extends ResponseBean> T parseBody(String str, Class<T> type) {
        return gson.fromJson(str, type);
    }

    public boolean isOk() {
        return getErrorCode() == 0;
    }

    public int getResult() {
        return getNormalBean().getResult();
    }

    public int getErrorCode() {
        return getNormalBean().getErrorCode();
    }

    public <T extends ResponseBean> T getEntity(Class<T> clz) {
        return parseBody(body, clz);
    }

    public JsonObject getJsonObject() {
        return new JsonParser().parse(getBodyStr()).getAsJsonObject();
    }
}
