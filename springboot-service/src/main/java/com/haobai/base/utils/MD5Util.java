package com.haobai.base.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    private static String encodingCharset = "UTF-8";

	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	public static String MD5(String text) {
		MessageDigest msgDigest = null;
		try {
			msgDigest = MessageDigest.getInstance("MD5");
			msgDigest.update(text.getBytes("UTF-8"));
			byte[] bytes = msgDigest.digest();
			String md5Str = new String(encodeHex(bytes));
			return md5Str.toUpperCase();
		} catch (Exception e) {

		}
		return "";
	}
	/**
     * 加密
     * 
     * @param str          加密字符串
     * @param instanceType 加密类型
     * @param UpperCase    是否转化为大写
     * @return String
     */
    public static String commonEncrypt(String str, String instanceType, boolean UpperCase) {
        str = str.trim();
        byte value[];
        try {
            value = str.getBytes(encodingCharset);
        } catch (UnsupportedEncodingException e) {
            value = str.getBytes();
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(instanceType);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        if (UpperCase) {
            return toHex(md.digest(value)).toUpperCase();
        }
        return toHex(md.digest(value));
    }

	 public static String md5UpperCase(String str) {
	        return commonEncrypt(str, "MD5", true);
	    }
	public static char[] encodeHex(byte[] data) {

		int l = data.length;
		char[] out = new char[l << 1];
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}
		return out;
	}
	public static String toHex(byte input[]) {
        if (input == null)
            return null;
        StringBuffer output = new StringBuffer(input.length * 2);
        for (int i = 0; i < input.length; i++) {
            int current = input[i] & 0xff;
            if (current < 16)
                output.append("0");
            output.append(Integer.toString(current, 16));
        }

        return output.toString();
    }
}
