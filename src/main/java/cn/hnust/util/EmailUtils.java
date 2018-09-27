package cn.hnust.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import cn.hnust.po.User;
 
public class EmailUtils {
	
	private static final String FROM = "1786082175@qq.com";
	
	public static void sendAccountActivateEmail(User user) {
		
		Session session = getSession();
		MimeMessage message = new MimeMessage(session);  
		try {
			message.setSubject("这是一封激活账号的邮件");
			message.setSentDate(new Date());
			//setFrom 表示用哪个邮箱发送邮件
			message.setFrom(new InternetAddress(FROM));
			/**
			 * RecipientType.TO||BCC||CC
			 *     TO表示主要接收人
			 *     BCC表示秘密抄送人
			 *     CC表示抄送人
			 * InternetAddress  接收者的邮箱地址
			 */
			message.setRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));
			message.setContent("<a target='_BLANK' href='"+GenerateLinkUtils.generateActivateLink(user)+"'>"+user.getUsername()+"先生/女士您好，请点击此链接激活账号"+GenerateLinkUtils.generateActivateLink(user)
			+"</a>","text/html;charset=utf-8");
			/*message.setContent("<a target='_BLANK' href='http://localhost:8091/m_ssm/email/activate'>"+user.getUsername()+"先生/女士您好，请点击此链接激活账号"+GenerateLinkUtils.generateActivateLink(user)
			+"</a>","text/html;charset=utf-8");*/
			Transport.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public static Session getSession() {
		
		Properties prop = new Properties();
		prop.setProperty("mail.host", "smtp.qq.com");//设置服务器主机名
		prop.setProperty("mail.smtp.auth", "true");//设置需要认证
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
                               //第一个为用户名，第二个为POP3/SMTP的密码
				return new PasswordAuthentication("1786082175","kkkwqkqevrlqbfbb");
				
			}
		};
		Session session = Session.getInstance(prop, auth);
		return session;
	}
}