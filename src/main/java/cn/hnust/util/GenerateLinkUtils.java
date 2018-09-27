package cn.hnust.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.hnust.po.User;
 
 
public class GenerateLinkUtils {
	
	private static final String CHECK_CODE = "checkCode";
 
	public static String generateActivateLink(User user) {
		
		return "http://czs2sv.natappfree.cc/m_ssm/email/activate?id="+user.getId()+"&"+CHECK_CODE+"="+generateCheckcode(user);
	}
 
	/**
	 * 生成校验码，用户名+UUID唯一标识符，为安全把他们加密发送
	 * @param user
	 * @return
	 */
	private static String generateCheckcode(User user) {
		String userName = user.getUsername();
		String randomCode = user.getCodeUrl();
		return md5(userName+":"+randomCode);
	}
 
	private static String md5(String string) {
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("md5");//返回实现指定摘要算法的 MessageDigest 对象。
			md.update(string.getBytes());//使用指定的 byte 数组更新摘要。 
			byte[] md5Bytes = md.digest();//通过执行诸如填充之类的最终操作完成哈希计算。在调用此方法之后，摘要被重置。
			return bytes2Hex(md5Bytes);//本信息摘要
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.out.println("md5这里出错了");
		}
		return null;
	}
 
	
	//二行制转字符串
	private static String bytes2Hex(byte[] byteArray) {
		
		StringBuffer strBuf = new StringBuffer();
		
		for (int i = 0; i < byteArray.length; i++) {
			if(byteArray[i] >= 0 && byteArray[i] < 16) {
				strBuf.append("0");
			}
			strBuf.append(Integer.toHexString(byteArray[i] & 0xFF));
		}
		return strBuf.toString();
	}
 
	public static boolean verifyCheckcode(User user, String checkCode) {
		
		boolean flag = generateCheckcode(user).equals(checkCode);
		
		return flag;
	}

}