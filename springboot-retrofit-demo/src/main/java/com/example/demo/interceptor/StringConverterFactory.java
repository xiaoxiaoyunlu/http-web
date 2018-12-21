package com.example.demo.interceptor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import com.sun.istack.internal.Nullable;

public class StringConverterFactory extends Converter.Factory {
    @Override
	public Converter<?, RequestBody> requestBodyConverter(Type type,
			Annotation[] parameterAnnotations, Annotation[] methodAnnotations,
			Retrofit retrofit) {
		// TODO Auto-generated method stub
		return super.requestBodyConverter(type, parameterAnnotations,
				methodAnnotations, retrofit);
	}



	@Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class) {
            return new StringConverter();
        }
        return null;
    }
    
    

    class StringConverter implements Converter<ResponseBody, String> {

        @Override
        public String convert(ResponseBody value) throws IOException {
            return value.string();
        }
    }
}
