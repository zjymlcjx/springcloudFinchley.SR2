package com.haobai.base.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

public class Base64Utils {

	/**
	 * @param imgStr
	 *            base64编码字符串
	 * @param path
	 *            图片路径-具体到文件
	 * @return
	 * @Description: 将base64编码字符串转换为图片
	 * @Author:
	 * @CreateTime:
	 */
	public static boolean Base64ToImage(String imgStr, String path) {
		if (imgStr == null) {
			return false;
		}
		  Base64 base64 = new Base64();
		  //BASE64Decoder base64 = new BASE64Decoder();
		try {
			// 解密
			//byte[] b = base64.decodeBuffer(imgStr);
			byte[] b = base64.decode(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(path);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @Description: 根据图片地址转换为base64编码字符串
	 * @Author:
	 * @CreateTime:
	 * @return
	 */
	public static String ImageToBase64(String imgFile) {
		InputStream inputStream = null;
		byte[] data = null;
		try {
			inputStream = new FileInputStream(imgFile);
			data = new byte[inputStream.available()];
			inputStream.read(data);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 加密
		//BASE64Encoder base64 = new BASE64Encoder();
		Base64 base64 = new Base64();
		return base64.encodeToString(data);
	}

	public static void main(String[] args) {
		String strImg = ImageToBase64("C:\\Users\\wen\\Desktop\\img\\20171211133621935383.PNG");
		System.out.println(strImg);
		Base64ToImage(strImg, "C:\\Users\\wen\\Desktop\\img\\test\\index10.jpg");
		System.out.println(ImageToBase64("C:\\Users\\wen\\Desktop\\img\\test\\index10.jpg"));
	}
}
