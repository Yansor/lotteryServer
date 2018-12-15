package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.vo.payment.PaymentChannelVO;

public abstract interface PaymentChannelService
{
  public abstract List<PaymentChannelVO> listAllVOs();
  
  public abstract List<PaymentChannel> listAllFullProperties();
  
  public abstract List<PaymentChannelVO> listAllMobileScanVOs();
  
  public abstract PaymentChannelVO getVOById(int paramInt);
  
  public abstract PaymentChannel getFullPropertyById(int paramInt);
  
  public abstract boolean add(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, String paramString6, String paramString7, int paramInt1, int paramInt2, int paramInt3, double paramDouble6, String paramString8, String paramString9, String paramString10, String paramString11, int paramInt4);
  
  public abstract boolean edit(int paramInt1, String paramString1, String paramString2, String paramString3, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, String paramString4, String paramString5, int paramInt2, double paramDouble6, String paramString6, String paramString7, String paramString8, String paramString9);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
  
  public abstract boolean resetCredits(int paramInt);
  
  public abstract boolean delete(int paramInt);
  
  public abstract boolean moveUp(int paramInt);
  
  public abstract boolean moveDown(int paramInt);
}
