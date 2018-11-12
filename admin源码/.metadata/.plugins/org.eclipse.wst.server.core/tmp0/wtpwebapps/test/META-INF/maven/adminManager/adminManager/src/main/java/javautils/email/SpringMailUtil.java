package javautils.email;

import java.util.Hashtable;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.util.Properties;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class SpringMailUtil
{
    private static final Logger logger;
    private String username;
    private String personal;
    private String password;
    private String host;
    private String defaultEncoding;
    
    static {
        logger = LoggerFactory.getLogger((Class)SpringMailUtil.class);
    }
    
    public SpringMailUtil(final String username, final String personal, final String password, final String host) {
        this.defaultEncoding = "UTF-8";
        this.username = username;
        this.personal = personal;
        this.password = password;
        this.host = host;
    }
    
    public boolean send(final String to, final String subject, final String htmlText) {
        return this.sendInternal(to, subject, htmlText);
    }
    
    public boolean sendInternal(final String to, final String subject, final String htmlText) {
        try {
            final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setDefaultEncoding(this.defaultEncoding);
            mailSender.setHost(this.host);
            mailSender.setUsername(this.username);
            mailSender.setPassword(this.password);
            final Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", this.host);
            properties.put("mail.smtp.user", this.username);
            properties.put("mail.smtp.password", this.password);
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            mailSender.setJavaMailProperties(properties);
            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(to);
            messageHelper.setFrom(this.username, this.personal);
            messageHelper.setSubject(subject);
            messageHelper.setText(htmlText, true);
            mailSender.send(mimeMessage);
            return true;
        }
        catch (Exception e) {
            SpringMailUtil.logger.error("发送邮件失败！", (Throwable)e);
            return false;
        }
    }
}
