package com.taobao.common;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;


public class CipherTools {
	private final static Logger log = LoggerFactory.getLogger(CipherTools.class);
	private String charsetName;

	public String encrypt(String value, String key) {
		try {
			byte[] data;
			if (!StringUtils.isEmpty(charsetName)) {
				try {
					data = value.getBytes(charsetName);
				} catch (Exception e1) {
					log.error("charset " + charsetName + " Unsupported!", e1);
					data = value.getBytes();
				}
			} else {
				data = value.getBytes();
			}
			byte[] bytes = encrypt(key, data);
			return encoding(bytes);
		} catch (Exception e) {
			log.error("encrypt error", e);
			return null;
		}

	}

	private String encoding(byte[] bytes) throws Exception {
		return Base32.encode(bytes);
		// return DESCoder.encryptBASE64(bytes);
	}

	private byte[] decoding(String value) throws Exception {
		return Base32.decode(value);
		// return DESCoder.decryptBASE64(value);
	}

	private byte[] encrypt(String key, byte[] data) throws Exception {
		return DESCoder.encrypt(data, key);
	}

	private byte[] decrypt(String key, byte[] data) throws Exception {
		return DESCoder.decrypt(data, key);
	}

	public String decrypt(String value, String key) throws Exception{
		try {
			byte[] data = decoding(value);
			byte[] bytes = decrypt(key, data);
			if (!StringUtils.isEmpty(charsetName)) {
				try {
					return new String(bytes, charsetName);
				} catch (UnsupportedEncodingException e1) {
					log.error("charset " + charsetName + " Unsupported!", e1);
					return new String(bytes);
				}
			} else {
				return new String(bytes);
			}
		} catch (Exception e) {
			log.error("encrypt error", e);
			throw e;
		}
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}
	
	public static void main(String[] args) {
		String key="3ac0bb4abf5b2b5113c350f0ee856a15";
		CipherTools tool=new CipherTools();
		String s1=tool.encrypt("test", key);
		System.out.println(s1);
//		System.out.println(tool.decrypt(s1, key));;
//        Cookie idCookie = new Cookie("id",cookieIdEncrypt(String.valueOf(uu.getId())));
	}
}
