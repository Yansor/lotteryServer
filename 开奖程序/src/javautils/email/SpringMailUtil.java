/*    */ package javautils.email;
/*    */ 
/*    */ import java.util.Properties;
/*    */ import javax.mail.internet.MimeMessage;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.mail.javamail.JavaMailSenderImpl;
/*    */ import org.springframework.mail.javamail.MimeMessageHelper;
/*    */ 
/*    */ 
/*    */ public class SpringMailUtil
/*    */ {
/* 13 */   private static final Logger logger = LoggerFactory.getLogger(SpringMailUtil.class);
/*    */   
/*    */   private String username;
/*    */   private String personal;
/*    */   private String password;
/*    */   private String host;
/* 19 */   private String defaultEncoding = "UTF-8";
/*    */   
/*    */   public SpringMailUtil(String username, String personal, String password, String host) {
/* 22 */     this.username = username;
/* 23 */     this.personal = personal;
/* 24 */     this.password = password;
/* 25 */     this.host = host;
/*    */   }
/*    */   
/*    */   public boolean send(String to, String subject, String htmlText) {
/* 29 */     return sendInternal(to, subject, htmlText);
/*    */   }
/*    */   
/*    */   public boolean sendInternal(String to, String subject, String htmlText) {
/*    */     try {
/* 34 */       JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
/* 35 */       mailSender.setDefaultEncoding(this.defaultEncoding);
/* 36 */       mailSender.setHost(this.host);
/* 37 */       mailSender.setUsername(this.username);
/* 38 */       mailSender.setPassword(this.password);
/* 39 */       Properties properties = new Properties();
/* 40 */       properties.put("mail.smtp.auth", "true");
/* 41 */       properties.put("mail.smtp.starttls.enable", "true");
/* 42 */       properties.put("mail.smtp.host", this.host);
/* 43 */       properties.put("mail.smtp.user", this.username);
/* 44 */       properties.put("mail.smtp.password", this.password);
/* 45 */       properties.put("mail.smtp.port", "587");
/* 46 */       properties.put("mail.smtp.auth", "true");
/* 47 */       mailSender.setJavaMailProperties(properties);
/*    */       
/* 49 */       MimeMessage mimeMessage = mailSender.createMimeMessage();
/* 50 */       MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
/* 51 */       messageHelper.setTo(to);
/* 52 */       messageHelper.setFrom(this.username, this.personal);
/* 53 */       messageHelper.setSubject(subject);
/* 54 */       messageHelper.setText(htmlText, true);
/* 55 */       mailSender.send(mimeMessage);
/* 56 */       return true;
/*    */     } catch (Exception e) {
/* 58 */       logger.error("发送邮件失败！", e == null ? "" : e.getMessage());
/*    */     }
/* 60 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/email/SpringMailUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */