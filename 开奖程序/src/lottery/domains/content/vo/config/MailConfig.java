/*    */ package lottery.domains.content.vo.config;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MailConfig
/*    */ {
/*    */   private String username;
/*    */   private String personal;
/*    */   private String password;
/*    */   private String host;
/*    */   private int bet;
/*    */   private int open;
/*    */   private int recharge;
/* 17 */   private List<String> receiveMails = new ArrayList();
/*    */   
/*    */   public String getUsername()
/*    */   {
/* 21 */     return this.username;
/*    */   }
/*    */   
/*    */   public void setUsername(String username) {
/* 25 */     this.username = username;
/*    */   }
/*    */   
/*    */   public String getPersonal() {
/* 29 */     return this.personal;
/*    */   }
/*    */   
/*    */   public void setPersonal(String personal) {
/* 33 */     this.personal = personal;
/*    */   }
/*    */   
/*    */   public String getPassword() {
/* 37 */     return this.password;
/*    */   }
/*    */   
/*    */   public void setPassword(String password) {
/* 41 */     this.password = password;
/*    */   }
/*    */   
/*    */   public String getHost() {
/* 45 */     return this.host;
/*    */   }
/*    */   
/*    */   public void setHost(String host) {
/* 49 */     this.host = host;
/*    */   }
/*    */   
/*    */   public List<String> getReceiveMails() {
/* 53 */     return this.receiveMails;
/*    */   }
/*    */   
/*    */   public void setReceiveMails(List<String> receiveMails) {
/* 57 */     this.receiveMails = receiveMails;
/*    */   }
/*    */   
/*    */   public int getBet() {
/* 61 */     return this.bet;
/*    */   }
/*    */   
/*    */   public void setBet(int bet) {
/* 65 */     this.bet = bet;
/*    */   }
/*    */   
/*    */   public int getOpen() {
/* 69 */     return this.open;
/*    */   }
/*    */   
/*    */   public void setOpen(int open) {
/* 73 */     this.open = open;
/*    */   }
/*    */   
/*    */   public int getRecharge() {
/* 77 */     return this.recharge;
/*    */   }
/*    */   
/*    */   public void setRecharge(int recharge) {
/* 81 */     this.recharge = recharge;
/*    */   }
/*    */   
/*    */   public void addReceiveMail(String mail) {
/* 85 */     this.receiveMails.add(mail);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/vo/config/MailConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */