package com.example.demo.interceptor;

import com.example.demo.utils.DESUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by user on 2017/11/10.
 */

public class ManoConverterFactory extends Converter.Factory {

	public static ManoConverterFactory create() {
		return create(new Gson());
	}

	public static ManoConverterFactory create(Gson gson) {
		return new ManoConverterFactory(gson);

	}

	private final Gson gson;

	private ManoConverterFactory(Gson gson) {
		if (gson == null)
			throw new NullPointerException("gson == null");
		this.gson = gson;
	}

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type,
			Annotation[] annotations, Retrofit retrofit) {
		TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
		return new JsonResponseBodyConverter<>(gson, adapter); // 响应
	}

	@Override
	public Converter<?, RequestBody> requestBodyConverter(Type type,
			Annotation[] parameterAnnotations, Annotation[] methodAnnotations,
			Retrofit retrofit) {

		TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
		return new JsonRequestBodyConverter<>(gson, adapter); // 请求
	}

	class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
		private final MediaType MEDIA_TYPE = MediaType
				.parse("application/json; charset=UTF-8");
		private final Gson gson;
		private final TypeAdapter<T> adapter;

		/**
		 * 构造器
		 */

		public JsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
			this.gson = gson;
			this.adapter = adapter;
		}

		@Override
		public RequestBody convert(T value) throws IOException {
			byte[] data = null;

			Class c = value.getClass();
			String jsonStr = gson.toJson(value);

			try {
				data = DESUtil.encrypt(jsonStr.getBytes(),
						DESUtil.ENCRYPT_KEY.getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return RequestBody.create(MEDIA_TYPE, data);
		}

	}

	class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
		private final Gson mGson;// gson对象
		private final TypeAdapter<T> adapter;

		/**
		 * 构造器
		 */
		public JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
			this.mGson = gson;
			this.adapter = adapter;
		}

		@Override
		public T convert(ResponseBody value) throws IOException {
			JsonReader jsonReader = gson.newJsonReader(value.charStream());
			try {
				return adapter.read(jsonReader);
			} finally {
				value.close();
			}
		}

	}

}