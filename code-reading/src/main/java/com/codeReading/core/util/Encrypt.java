/**
 * @author Rofly
 * @email: tianyadaxue@163.com
 */
package com.codeReading.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * commons :采用DES加解密模式进行加密
 * [加密过程]：明文字符串-->getBytes以GBK格式-->执行des加密-->字符串转为16进制-->加密结果串
 * [解密过程]：加密字符串-->16进制转字符串-->执行des解密-->以GBK格式newString-->明文结果串
 * @author  :蔡鹏飞
 * time    :2012-12-12
 */
public final class Encrypt extends AbsEncrypt {
	private static Encrypt encrypt = null;
	/* 伪单例 */
	public static Encrypt getInstance() {
		if (null == encrypt)
			encrypt = new Encrypt();
		return encrypt;
	}
	private Encrypt() {}// 构造函数私有化
}

abstract class AbsEncrypt implements IEncrypt{
	private static final int ENCRYPT_MODE = 1; // 加密模式
	private static final int DECRYPT_MODE = 2; // 解密模式
	/* byte[] 数组密钥为null时会加载指定密钥文件中密钥串；不为null时不加载文件 */
	private static byte[] KEYFORPASS = null;
	private static byte[] KEYFORALL = "KSagZxG3".getBytes();
	private static final String KEY_FILE_PASS = "/password_key.des";
	private static final String KEY_FILE_ALL = "";//对公司密钥，使用密钥字符串，因此不指定
	
	public String MD5(String showInfo){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.reset();
			byte[] byteArray = md.digest(showInfo.getBytes("UTF-8"));
			StringBuffer md5sb = new StringBuffer();
			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
					md5sb.append("0").append(
							Integer.toHexString(0xFF & byteArray[i]));
				else
					md5sb.append(Integer.toHexString(0xFF & byteArray[i]));
			}
			return new String(md5sb);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String enc_des(String showInfo, int keyType){
		byte[] result = null;
		try {
			byte[] tmp = showInfo.getBytes("GBK");
			result = cipher(tmp, getPrivateKey(keyType), ENCRYPT_MODE, getTransformation(keyType));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null == result ? null : b2hex(result);
	}
	
	public String dec_des(String encryInfo, int keyType) throws IllegalBlockSizeException,IllegalArgumentException {
		byte[] result = null;
		String resultStr = null;
		try {
			try {
				byte[] tmp = hex2b(encryInfo);
				result = cipher(tmp, getPrivateKey(keyType), DECRYPT_MODE, getTransformation(keyType));
				resultStr = new String(result,"GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return resultStr;
		} catch (ArrayIndexOutOfBoundsException e) {//base64抛出数组越界 
			throw new IllegalBlockSizeException(e.getMessage());
		} catch (NullPointerException e) {
			throw new IllegalBlockSizeException(e.getMessage());
		}
	}
	
	/**
	 * @comments :获取私钥
	 * @creater :蔡鹏飞
	 * @time :2012-12-12 下午12:54:49
	 * @param keyType 私钥类型：0.CRM平台内部加密；1.公司内部平台交互加密
	 * @return
	 * @throws Exception
	 */
	private byte[] getPrivateKey(int keyType) throws IllegalArgumentException {
		byte[] pri_key = null;
		if (keyType == KEY_TYPE_FOR_PASS) {
			if (KEYFORPASS != null)
				return KEYFORPASS;
			pri_key = readKeyData(KEY_FILE_PASS, getKeyLenth(keyType));
		} else if (keyType == KEY_TYPE_FOR_ALL) {
			if (KEYFORALL != null)
				return KEYFORALL;
			pri_key = readKeyData(KEY_FILE_ALL, getKeyLenth(keyType));
		} else {
			throw new IllegalArgumentException("传入的加密key类型错误.");
		}
		return pri_key;
	}

	private byte[] readKeyData(String keyFile, int length) {
		InputStream inputStream = null;
		byte[] key = new byte[length];
		byte[] tmp = new byte[length*2];
		try {
			
			inputStream = this.getClass().getResourceAsStream(keyFile);
			if (null == inputStream)
				throw new FileNotFoundException(keyFile + " not found.");
			inputStream.read(tmp);
			key = hex2b(new String(tmp, "GBK"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return key;
	}

	private String getTransformation(int keyType) throws IllegalArgumentException {
		return "DES";
		//不区分
/*		String result = null;
		if (keyType == KEY_TYPE_FOR_PASS) {
			result = "DES/ECB/NoPadding";
		} else if (keyType == KEY_TYPE_FOR_ALL) {
			result = "DES/ECB/PKCS5Padding";
		} else {
			throw new IllegalArgumentException("传入的加密key类型错误.");
		}
		return result;*/
	}

	private int getKeyLenth(int keyType) throws IllegalArgumentException {
		if (keyType == KEY_TYPE_FOR_PASS) {
			return 8;
		} else if (keyType == KEY_TYPE_FOR_ALL) {
			return 24;
		} else {
			throw new IllegalArgumentException("传入的加密key类型错误.");
		}
	}

	private byte[] cipher(byte[] binfo, byte[] bkey, int mode, String transformation) throws IllegalBlockSizeException {
		String algs[] = transformation.split("/");
		SecretKey key = new SecretKeySpec(bkey, algs[0]);
		Cipher cipher = null;
		byte[] result = null;
		try {
			cipher = Cipher.getInstance(transformation);
			cipher.init(mode, key);
			result = cipher.doFinal(binfo);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return result;
	}

	private String b2hex(byte[] bs) {
		int iLen = bs.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = bs[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	private byte[] hex2b(String str) throws UnsupportedEncodingException {
		byte[] arrB = str.getBytes("GBK");
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2,"GBK");
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}
	/**
	 *生成随机数方法，此方法中涉及线程休眠，请勿用于其他用途
	 * @creater   :蔡鹏飞
	 * @time      :2012-12-25  下午05:20:04
	 * @param length 生成随机串的长度
	 * @return 指定长度的随机串
	 */
	protected static String GetRandomString(int length) throws InterruptedException{
		StringBuffer result = new StringBuffer("");
		Random r = new Random();
		for(int i = 0; i<length; i++){
			int index = Math.abs(r.nextInt()%62);
			char[] chars = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
							'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
							'0','1','2','3','4','5','6','7','8','9'};
			result.append(chars[index]);
			Thread.sleep(Math.abs(r.nextInt()%177));
		}
		return new String(result);
	}
	
	/* 生成密钥文件方法，不对外暴露 */
	protected void createPrivateKey(String fileName, String keyInfo, int keyType)
			throws Exception {
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(new File(fileName));
			byte[] bytes = keyInfo.getBytes("GBK");
			if (getKeyLenth(keyType) != bytes.length) {
				throw new Exception("所给信息不能长度不符合生成密钥长度规则，当前长度[" + bytes.length + 
						"],规定长度[" + getKeyLenth(keyType) + "]。");
			}
			outputStream.write(b2hex(bytes).getBytes("GBK"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != outputStream) {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

interface IEncrypt {
	public static final int KEY_TYPE_FOR_PASS = 0; // CRM平台内部加密时，传入此类型
	public static final int KEY_TYPE_FOR_ALL = 1; // 公司内部平台交互加密时，传入此类型
	/**
	 *【DES/3EDS入口】采用DES/3EDS加解密模式进行加密
	 * @creater :蔡鹏飞
	 * @time :2012-12-11 下午06:04:17
	 * @param showInfo 需要加密明文信息
	 * @param keyType  私钥类型：0.CRM平台内部加密；1.公司内部平台交互加密（调用时使用KEY_TYPE_FOR_PASS/KEY_TYPE_FOR_ALL常量）
	 * @return des加密方式加密后的密文
	 * @throws Exception
	 */
	public String enc_des(String showInfo, int keyType);
	/**
	 * 【DES/3EDS入口】采用DES/3EDS加解密模式进行解密
	 * @creater :蔡鹏飞
	 * @time :2012-12-11 下午06:04:17
	 * @param showInfo  需要解密的密文信息
	 * @param keyType  私钥类型：0.CRM平台内部加密；1.公司内部平台交互加密（调用时使用KEY_TYPE_FOR_PASS/KEY_TYPE_FOR_ALL常量）
	 * @return des方式解密后的密文
	 * @throws IllegalBlockSizeException 
	 * @throws Exception
	 */
	public String dec_des(String showInfo, int keyType) throws IllegalBlockSizeException;
	/**
	 * 将字符串以MD5加密
	 * @param showInfo 待加密字符串
	 * @return 密文字符串
	 */
	public String MD5(String showInfo);
}