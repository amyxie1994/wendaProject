/**
 * Created by amyxie in 2018
 * MailSender.java
 * 13 Mar. 2018
 */
package com.example.snsProject.util;

import java.util.Map;
import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author amyxie
 *
 */
@Service
public class MailSender implements InitializingBean {
	
	//public 
	private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
    private JavaMailSenderImpl mailSender;

    @Autowired
    private   FreeMarkerConfigurer freeMarkerConfigurer;
    
    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {  
        this.freeMarkerConfigurer = freeMarkerConfigurer;  
    }
    
    
    public boolean sendWithHTMLTemplate(String to, String subject,
            String template, Map<String, Object> model) {
try {
		String nick = MimeUtility.encodeText("牛客中级课");
		InternetAddress from = new InternetAddress(nick + "<course@nowcoder.com>");
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
       Configuration configuration = freeMarkerConfigurer.getConfiguration();
       Template t = configuration.getTemplate("mailTemplate");
      
       mimeMessageHelper.setTo(to);
       mimeMessageHelper.setFrom(from);
       mimeMessageHelper.setSubject(subject);
       mimeMessageHelper.setText(FreeMarkerTemplateUtils.processTemplateIntoString(t, to), true); 
       mailSender.send(mimeMessage);
	
				return true;
	} catch (Exception e) {
		logger.error("发送邮件失败" + e.getMessage());
		return false;
	}
}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
		mailSender = new JavaMailSenderImpl();
        mailSender.setUsername("course@nowcoder.com");
        mailSender.setPassword("NKnk123");
        mailSender.setHost("smtp.exmail.qq.com");
        //mailSender.setHost("smtp.qq.com");
        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf8");
        
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable", true);
        //javaMailProperties.put("mail.smtp.auth", true);
        //javaMailProperties.put("mail.smtp.starttls.enable", true);
        mailSender.setJavaMailProperties(javaMailProperties);
    }
        
		
	}  



