package lottery.domains.content.biz.impl;

import lottery.domains.content.entity.UserWhitelist;
import java.util.List;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserWhitelistDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserWhitelistService;

@Service
public class UserWhitelistServiceImpl implements UserWhitelistService
{
    @Autowired
    private UserWhitelistDao uWhitelistDao;
    
    @Override
    public PageList search(final String keyword, int start, int limit) {
        if (start < 0) {
            start = 0;
        }
        if (limit < 0) {
            limit = 10;
        }
        if (limit > 100) {
            limit = 100;
        }
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        if (StringUtil.isNotNull(keyword)) {
            criterions.add((Criterion)Restrictions.or(new Criterion[] { (Criterion)Restrictions.eq("username", (Object)keyword), (Criterion)Restrictions.like("cardName", keyword, MatchMode.ANYWHERE), (Criterion)Restrictions.like("cardId", keyword, MatchMode.ANYWHERE), (Criterion)Restrictions.like("ip", keyword, MatchMode.ANYWHERE) }));
        }
        return this.uWhitelistDao.find(criterions, orders, start, limit);
    }
    
    @Override
    public boolean add(final String username, final String cardName, final String cardId, final Integer bankId, final String ip, final String operatorUser, final String operatorTime, final String remarks) {
        if (StringUtil.isNotNull(username) && StringUtil.isNotNull(operatorUser) && StringUtil.isNotNull(operatorTime)) {
            final UserWhitelist entity = new UserWhitelist(cardName, operatorUser, operatorTime);
            entity.setUsername(username);
            entity.setCardId(cardId);
            entity.setBankId(bankId);
            entity.setIp(ip);
            entity.setRemarks(remarks);
            return this.uWhitelistDao.add(entity);
        }
        return false;
    }
    
    @Override
    public boolean delete(final int id) {
        return this.uWhitelistDao.delete(id);
    }
}
