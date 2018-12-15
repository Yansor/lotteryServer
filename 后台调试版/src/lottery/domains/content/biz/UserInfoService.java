package lottery.domains.content.biz;

public abstract interface UserInfoService
{
  public abstract boolean resetEmail(String paramString);
  
  public abstract boolean modifyEmail(String paramString1, String paramString2);
}
