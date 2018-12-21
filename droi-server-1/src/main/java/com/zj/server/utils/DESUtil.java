package com.zj.server.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESUtil {
	
	private static final String ALGORITHM = "DES/ECB/NoPadding";

	public DESUtil() {
	}

	/**
	 * 加密操作
	 * 
	 * @param src
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		src = padding(src, (byte) 0);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

		DESKeySpec dks = new DESKeySpec(key);
		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(1, securekey);

		return cipher.doFinal(src);
	}

	/**
	 * 解密操作
	 * 
	 * @param src
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(2, securekey);

		return cipher.doFinal(src);
	}

	public static final byte[] decryptString(String data, byte[] key) {
		try {
			return decrypt(hex2byte(data.getBytes()), key);
		} catch (Exception e) {

		}
		return null;
	}

	private static byte[] padding(byte[] sourceBytes, byte b) {
		int paddingSize = 8 - (sourceBytes.length % 8);
		byte[] paddingBytes = new byte[paddingSize];
		for (int i = 0; i < paddingBytes.length; ++i) {
			paddingBytes[i] = b;
		}
		sourceBytes = addAll(sourceBytes, paddingBytes);
		return sourceBytes;
	}

	public static byte[] hex2byte(byte[] b) {
		if (b.length % 2 != 0) {
			throw new IllegalArgumentException("长度不是偶数");
		}
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[(n / 2)] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	public static byte[] addAll(byte[] array1, byte[] array2) {
		if (array1 == null){
			return clone(array2);
		}	
		if (array2 == null) {
			return clone(array1);
		}
		byte[] joinedArray = new byte[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	public static byte[] clone(byte[] array) {
		if (array == null) {
			return null;
		}
		return ((byte[]) (byte[]) array.clone());
	}

}
