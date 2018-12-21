package com.zj.server.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ZipUtil {
	public static byte[] compress(byte[] byteArray) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(byteArray);
		gzip.close();
		byte[] compressByteArray = out.toByteArray();
		return compressByteArray;
	}

	public static byte[] uncompress(byte[] byteArry) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(byteArry);
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		return out.toByteArray();
	}

	public static void main(String[] args) throws IOException {
		String str = "%5B%7B%22lastUpdateTime%22%3A%222011-10-28+9%3A39%3A41%22%2C%22smsList%22%3A%5B%7B%22liveState%22%3A%221";

		System.out.println("原长度：" + str.length());

		byte[] uncompressByte = uncompress(compress(str.getBytes("utf-8")));
		System.out.println("解压缩：" + new String(uncompressByte));
	}
}
