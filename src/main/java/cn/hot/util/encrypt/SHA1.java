package cn.hot.util.encrypt;

import cn.hot.exception.AesException;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class SHA1 {

	public static String encode(String code){
		try {
			// SHA1签名生成
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(code.getBytes());
			byte[] digest = md.digest();

			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			LoggerFactory.getLogger(SHA1.class).error("加密时发生异常:",e);
			throw new AesException(AesException.ComputeSignatureError);
		}
	}
}
