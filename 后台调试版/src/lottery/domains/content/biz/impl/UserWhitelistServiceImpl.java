package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.UserWhitelistService;
import lottery.domains.content.dao.UserWhitelistDao;
import lottery.domains.content.entity.UserWhitelist;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWhitelistServiceImpl
  implements UserWhitelistService
{
  @Autowired
  private UserWhitelistDao uWhitelistDao;
  
  public PageList search(String keyword, int start, int limit)
  {
    if (start < 0) {
      start = 0;
    }
    if (limit < 0) {
      limit = 10;
    }
    if (limit > 100) {
      limit = 100;
    }
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    if (StringUtil.isNotNull(keyword)) {
      criterions.add(Restrictions.or(new Criterion[] {
        Restrictions.eq("username", keyword), 
        Restrictions.like("cardName", keyword, MatchMode.ANYWHERE), 
        Restrictions.like("cardId", keyword, MatchMode.ANYWHERE), 
        Restrictions.like("ip", keyword, MatchMode.ANYWHERE) }));
    }
    return this.uWhitelistDao.find(criterions, orders, start, limit);
  }
  
  public boolean add(String username, String cardName, String cardId, Integer bankId, String ip, String operatorUser, String operatorTime, String remarks)
  {
    if ((StringUtil.isNotNull(username)) && 
      (StringUtil.isNotNull(operatorUser)) && (StringUtil.isNotNull(operatorTime)))
    {
      UserWhitelist entity = new UserWhitelist(cardName, operatorUser, operatorTime);
      entity.setUsername(username);
      entity.setCardId(cardId);
      entity.setBankId(bankId);
      entity.setIp(ip);
      entity.setRemarks(remarks);
      return this.uWhitelistDao.add(entity);
    }
    return false;
  }
  
  public boolean delete(int id)
  {
    return this.uWhitelistDao.delete(id);
  }
}
