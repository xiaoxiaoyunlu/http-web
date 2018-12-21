package com.example.demo.interceptor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import com.sun.istack.internal.Nullable;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class StringCallAdapterFactory extends CallAdapter.Factory {
    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if(returnType == String.class)
            return new StringCallAdapter();
         return null;
    }

    class StringCallAdapter implements CallAdapter<String,String>{
        @Override
        public Type responseType() {
            return String.class;
        }

        @Override
        public String adapt(Call<String> call) {
            try {
                return call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
    }
}
