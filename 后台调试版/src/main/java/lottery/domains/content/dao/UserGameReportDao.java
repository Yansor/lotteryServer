package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.HistoryUserGameReport;
import lottery.domains.content.entity.UserGameReport;
import lottery.domains.content.vo.bill.HistoryUserGameReportVO;
import lottery.domains.content.vo.bill.UserGameReportVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserGameReportDao
{
  public abstract boolean save(UserGameReport paramUserGameReport);
  
  public abstract UserGameReport get(int paramInt1, int paramInt2, String paramString);
  
  public abstract boolean update(UserGameReport paramUserGameReport);
  
  public abstract List<UserGameReport> list(int paramInt, String paramString1, String paramString2);
  
  public abstract List<UserGameReport> find(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract List<HistoryUserGameReport> findHistory(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract List<UserGameReportVO> reportByUser(String paramString1, String paramString2);
  
  public abstract UserGameReportVO sumLowersAndSelf(int paramInt, String paramString1, String paramString2);
  
  public abstract HistoryUserGameReportVO historySumLowersAndSelf(int paramInt, String paramString1, String paramString2);
  
  public abstract UserGameReportVO sum(int paramInt, String paramString1, String paramString2);
}
