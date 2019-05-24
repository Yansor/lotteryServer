package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.entity.PaymentCard;

public abstract interface PaymentCardService
{
  public abstract List<PaymentCard> listAll();
  
  public abstract PaymentCard getById(int paramInt);
  
  public abstract boolean add(int paramInt, String paramString1, String paramString2, String paramString3, double paramDouble1, double paramDouble2, double paramDouble3, String paramString4, String paramString5, double paramDouble4, double paramDouble5);
  
  public abstract boolean edit(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, double paramDouble1, double paramDouble2, double paramDouble3, String paramString4, String paramString5, double paramDouble4, double paramDouble5);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
  
  public abstract boolean resetCredits(int paramInt);
  
  public abstract boolean addUsedCredits(int paramInt, double paramDouble);
  
  public abstract boolean delete(int paramInt);
}
