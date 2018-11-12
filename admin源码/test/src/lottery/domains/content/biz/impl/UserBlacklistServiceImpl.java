package lottery.domains.content.biz.impl;

import lottery.domains.content.entity.PaymentBank;
import java.util.Iterator;
import java.util.List;
import lottery.domains.content.entity.UserBlacklist;
import lottery.domains.content.vo.user.UserBlacklistVO;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.PaymentBankService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserBlacklistDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserBlacklistService;

@Service
public class UserBlacklistServiceImpl implements UserBlacklistService
{
    @Autowired
    private UserBlacklistDao uBlacklistDao;
    @Autowired
    private PaymentBankService paymentBankService;
    
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
        final PageList plist = this.uBlacklistDao.find(criterions, orders, start, limit);
        final List<UserBlacklistVO> list = new ArrayList<UserBlacklistVO>();
        for (final Object tmpBean : plist.getList()) {
            final UserBlacklist bbean = (UserBlacklist)tmpBean;
            if (bbean != null && bbean.getBankId() != null) {
                final PaymentBank pk = this.paymentBankService.getById(bbean.getBankId());
                final UserBlacklistVO vo = new UserBlacklistVO(bbean, pk.getName());
                list.add(vo);
            }
            else {
                final UserBlacklistVO vo2 = new UserBlacklistVO(bbean, "");
                list.add(vo2);
            }
        }
        plist.setList(list);
        return plist;
    }
    
    @Override
    public boolean add(final String username, final String cardName, final String cardId, final Integer bankId, final String ip, final String operatorUser, final String operatorTime, final String remarks) {
        if (StringUtil.isNotNull(operatorUser) && StringUtil.isNotNull(operatorTime)) {
            final UserBlacklist entity = new UserBlacklist(cardName, operatorUser, operatorTime);
            entity.setUsername(username);
            entity.setCardId(cardId);
            entity.setBankId(bankId);
            entity.setIp(ip);
            entity.setRemarks(remarks);
            return this.uBlacklistDao.add(entity);
        }
        return false;
    }
    
    @Override
    public boolean delete(final int id) {
        return this.uBlacklistDao.delete(id);
    }
}
