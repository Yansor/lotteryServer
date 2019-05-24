package lottery.domains.content.biz;

import javautils.jdbc.PageList;
import org.springframework.stereotype.Service;

@Service
public abstract interface UserWhitelistService
{
  public abstract PageList search(String paramString, int paramInt1, int paramInt2);
  
  public abstract boolean add(String paramString1, String paramString2, String paramString3, Integer paramInteger, String paramString4, String paramString5, String paramString6, String paramString7);
  
  public abstract boolean delete(int paramInt);
}
