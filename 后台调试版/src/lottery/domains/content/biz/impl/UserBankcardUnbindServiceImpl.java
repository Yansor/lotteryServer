package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.UserBankcardUnbindService;
import lottery.domains.content.dao.UserBankcardUnbindDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserBankcardUnbindRecord;
import lottery.domains.content.vo.user.UserBankcardUnbindVO;
import lottery.domains.pool.LotteryDataFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBankcardUnbindServiceImpl
  implements UserBankcardUnbindService
{
  @Autowired
  private UserBankcardUnbindDao userBankcardUnbindDao;
  @Autowired
  private UserDao uDao;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  
  public boolean add(UserBankcardUnbindRecord entity)
  {
    return this.userBankcardUnbindDao.add(entity);
  }
  
  public boolean update(UserBankcardUnbindRecord entity)
  {
    return this.userBankcardUnbindDao.update(entity);
  }
  
  public boolean delByCardId(String cardId)
  {
    return this.userBankcardUnbindDao.delByCardId(cardId);
  }
  
  public boolean updateByParam(String userIds, String cardId, int unbindNum, String unbindTime)
  {
    return this.userBankcardUnbindDao.updateByParam(userIds, cardId, unbindNum, unbindTime);
  }
  
  public UserBankcardUnbindVO getUnbindInfoById(int id)
  {
    UserBankcardUnbindRecord entity = this.userBankcardUnbindDao.getUnbindInfoById(id);
    if (entity != null) {
      return new UserBankcardUnbindVO(entity, this.lotteryDataFactory);
    }
    return null;
  }
  
  public UserBankcardUnbindVO getUnbindInfoBycardId(String cardId)
  {
    UserBankcardUnbindRecord entity = this.userBankcardUnbindDao.getUnbindInfoBycardId(cardId);
    if (entity != null) {
      return new UserBankcardUnbindVO(entity, this.lotteryDataFactory);
    }
    return null;
  }
  
  public PageList search(String userNames, String cardId, String unbindTime, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    if (StringUtil.isNotNull(userNames))
    {
      User userEntity = this.uDao.getByUsername(userNames);
      if (userEntity != null) {
        criterions.add(Restrictions.like("userIds", "#" + userEntity.getId() + "#", MatchMode.ANYWHERE));
      } else {
        criterions.add(Restrictions.like("userIds", String.valueOf("0000"), MatchMode.ANYWHERE));
      }
    }
    if (StringUtil.isNotNull(cardId)) {
      criterions.add(Restrictions.like("cardId", cardId, MatchMode.ANYWHERE));
    }
    if (StringUtil.isNotNull(unbindTime)) {
      criterions.add(Restrictions.like("unbindTime", unbindTime, MatchMode.ANYWHERE));
    }
    orders.add(Order.desc("unbindTime"));
    orders.add(Order.desc("id"));
    
    PageList pList = this.userBankcardUnbindDao.search(criterions, orders, start, limit);
    List<UserBankcardUnbindVO> list = new ArrayList();
    for (Object o : pList.getList()) {
      list.add(new UserBankcardUnbindVO((UserBankcardUnbindRecord)o, this.lotteryDataFactory));
    }
    pList.setList(list);
    return pList;
  }
  
  public List<UserBankcardUnbindVO> listAll()
  {
    List<UserBankcardUnbindVO> list = new ArrayList();
    List<UserBankcardUnbindRecord> clist = this.userBankcardUnbindDao.listAll();
    for (UserBankcardUnbindRecord tmpBean : clist) {
      list.add(new UserBankcardUnbindVO(tmpBean, this.lotteryDataFactory));
    }
    return list;
  }
}
