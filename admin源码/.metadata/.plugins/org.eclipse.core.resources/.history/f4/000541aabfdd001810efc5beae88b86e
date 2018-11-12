package lottery.domains.content.biz.impl;

import javautils.date.DateUtil;
import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.UserMessage;
import lottery.domains.content.vo.user.UserMessageVO;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserMessageService;

@Service
public class UserMessageServiceImpl implements UserMessageService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserMessageDao uMessageDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList search(final String toUser, final String fromUser, final String sTime, final String eTime, final int type, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        boolean isSearch = true;
        if (StringUtil.isNotNull(toUser)) {
            final User user = this.uDao.getByUsername(toUser);
            if (user != null) {
                criterions.add((Criterion)Restrictions.eq("toUid", (Object)user.getId()));
            }
            else {
                isSearch = false;
            }
        }
        if (StringUtil.isNotNull(fromUser)) {
            final User user = this.uDao.getByUsername(fromUser);
            if (user != null) {
                criterions.add((Criterion)Restrictions.eq("fromUid", (Object)user.getId()));
            }
            else {
                isSearch = false;
            }
        }
        if (StringUtil.isNotNull(sTime)) {
            criterions.add((Criterion)Restrictions.ge("time", (Object)sTime));
        }
        if (StringUtil.isNotNull(eTime)) {
            criterions.add((Criterion)Restrictions.lt("time", (Object)eTime));
        }
        if (type == 0) {
            criterions.add((Criterion)Restrictions.eq("toUid", (Object)type));
        }
        else if (type == 1) {
            criterions.add((Criterion)Restrictions.gt("toUid", (Object)type));
        }
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<UserMessageVO> list = new ArrayList<UserMessageVO>();
            final PageList pList = this.uMessageDao.search(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new UserMessageVO((UserMessage)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public UserMessageVO getById(final int id) {
        final UserMessage bean = this.uMessageDao.getById(id);
        if (bean != null) {
            return new UserMessageVO(bean, this.lotteryDataFactory);
        }
        return null;
    }
    
    @Override
    public boolean delete(final int id) {
        return this.uMessageDao.delete(id);
    }
    
    @Override
    public boolean save(final int id, final String content) {
        final UserMessage userMessage = this.uMessageDao.getById(id);
        userMessage.setToStatus(1);
        this.uMessageDao.update(userMessage);
        final UserMessage replyMessage = new UserMessage(userMessage.getFromUid(), 0, 1, "回复>>" + userMessage.getSubject(), content, DateUtil.getCurrentTime(), 0, 0);
        return this.uMessageDao.save(replyMessage);
    }
}
