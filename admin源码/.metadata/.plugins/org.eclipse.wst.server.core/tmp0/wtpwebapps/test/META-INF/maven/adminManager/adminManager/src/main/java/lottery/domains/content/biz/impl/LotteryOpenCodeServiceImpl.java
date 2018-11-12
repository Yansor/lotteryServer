package lottery.domains.content.biz.impl;

import java.util.Set;
import java.util.TreeSet;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import lottery.domains.content.entity.Lottery;
import javautils.date.Moment;
import admin.web.WebJSONObject;
import java.util.Iterator;
import java.util.List;
import lottery.domains.content.entity.LotteryOpenCode;
import lottery.domains.content.vo.lottery.LotteryOpenCodeVO;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.content.dao.LotteryDao;
import lottery.domains.utils.lottery.open.LotteryOpenUtil;
import javautils.redis.JedisTemplate;
import lottery.domains.content.dao.LotteryOpenCodeDao;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.web.content.validate.CodeValidate;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.LotteryOpenCodeService;

@Service
public class LotteryOpenCodeServiceImpl implements LotteryOpenCodeService
{
    private static final String OPEN_CODE_KEY = "OPEN_CODE:%s";
    private static final String ADMIN_OPEN_CODE_KEY = "ADMIN_OPEN_CODE:%s";
    private static final int OPEN_CODE_MOST_EXPECT = 50;
    @Autowired
    private CodeValidate codeValidate;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    @Autowired
    private LotteryOpenCodeDao lotteryOpenCodeDao;
    @Autowired
    private JedisTemplate jedisTemplate;
    @Autowired
    private LotteryOpenUtil lotteryOpenUtil;
    @Autowired
    private LotteryDao lotteryDao;
    
    @Override
    public PageList search(final String lottery, final String expect, int start, int limit) {
        if (start < 0) {
            start = 0;
        }
        if (limit < 0) {
            limit = 10;
        }
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        if (StringUtil.isNotNull(lottery)) {
            criterions.add((Criterion)Restrictions.eq("lottery", (Object)lottery));
        }
        if (StringUtil.isNotNull(expect)) {
            criterions.add((Criterion)Restrictions.eq("expect", (Object)expect));
        }
        orders.add(Order.desc("expect"));
        final PageList pList = this.lotteryOpenCodeDao.find(criterions, orders, start, limit);
        final List<LotteryOpenCodeVO> list = new ArrayList<LotteryOpenCodeVO>();
        for (final Object tmpBean : pList.getList()) {
            list.add(new LotteryOpenCodeVO((LotteryOpenCode)tmpBean, this.lotteryDataFactory));
        }
        pList.setList(list);
        return pList;
    }
    
    @Override
    public LotteryOpenCodeVO get(final String lottery, final String expect) {
        final LotteryOpenCode entity = this.lotteryOpenCodeDao.get(lottery, expect);
        if (entity != null) {
            final LotteryOpenCodeVO bean = new LotteryOpenCodeVO(entity, this.lotteryDataFactory);
            return bean;
        }
        return null;
    }
    
    @Override
    public boolean add(final WebJSONObject json, final String lottery, final String expect, final String code, final String opername) {
        if (!this.codeValidate.validateExpect(json, lottery, expect)) {
            final Lottery lo = this.lotteryDao.getByShortName(lottery);
            if (lo != null && lo.getSelf() == 1) {
                final String key = String.format("ADMIN_OPEN_CODE:%s", lottery);
                this.jedisTemplate.hset(key, expect, code);
                return true;
            }
            return false;
        }
        else {
            int openStatus = 0;
            if ("txffc".equals(lottery) || "txlhd".equals(lottery)) {
                final String lastExpect = this.lotteryOpenUtil.subtractExpect(lottery, expect);
                final LotteryOpenCode lastOpenCode = this.lotteryOpenCodeDao.get(lottery, lastExpect);
                if (lastOpenCode != null && lastOpenCode.getCode().equals(code)) {
                    openStatus = 2;
                }
            }
            final LotteryOpenCode entity = this.lotteryOpenCodeDao.get(lottery, expect);
            if (entity == null) {
                final String time = new Moment().toSimpleTime();
                final LotteryOpenCode bean = new LotteryOpenCode();
                bean.setLottery(lottery);
                bean.setExpect(expect);
                bean.setCode(code);
                bean.setTime(time);
                bean.setInterfaceTime(time);
                bean.setOpenStatus(openStatus);
                bean.setRemarks(opername);
                final boolean result = this.lotteryOpenCodeDao.add(bean);
                if (result) {
                    this.addedToRedis(bean);
                }
                return result;
            }
            entity.setCode(code);
            entity.setRemarks(opername);
            entity.setOpenStatus(openStatus);
            final boolean result2 = this.lotteryOpenCodeDao.update(entity);
            if (result2) {
                this.addedToRedis(entity);
            }
            return result2;
        }
    }
    
    @Override
    public boolean delete(final LotteryOpenCode bean) {
        return this.lotteryOpenCodeDao.delete(bean);
    }
    
    @Override
    public LotteryOpenCode getFirstExpectByInterfaceTime(final String lottery, final String startTime, final String endTime) {
        return this.lotteryOpenCodeDao.getFirstExpectByInterfaceTime(lottery, startTime, endTime);
    }
    
    @Override
    public int countByInterfaceTime(final String lottery, final String startTime, final String endTime) {
        return this.lotteryOpenCodeDao.countByInterfaceTime(lottery, startTime, endTime);
    }
    
    @Override
    public List<LotteryOpenCode> getLatest(final String lottery, final int count) {
        return this.lotteryOpenCodeDao.getLatest(lottery, count);
    }
    
    private void addedToRedis(final LotteryOpenCode entity) {
        final String key = String.format("OPEN_CODE:%s", entity.getLottery());
        if ("jsmmc".equals(entity.getLottery())) {
            return;
        }
        final Set<String> hkeys = this.jedisTemplate.hkeys(key);
        if (CollectionUtils.isEmpty((Collection)hkeys)) {
            this.jedisTemplate.hset(key, entity.getExpect(), entity.getCode());
        }
        else {
            final TreeSet<String> sortHKeys = new TreeSet<String>(hkeys);
            final String[] expects = sortHKeys.toArray(new String[0]);
            final String firstExpect = expects[0];
            if (entity.getExpect().compareTo(firstExpect) > 0) {
                this.jedisTemplate.hset(key, entity.getExpect(), entity.getCode());
                sortHKeys.add(entity.getExpect());
            }
            if (CollectionUtils.isNotEmpty((Collection)sortHKeys) && sortHKeys.size() > 50) {
                final int exceedSize = sortHKeys.size() - 50;
                final Iterator<String> iterator = sortHKeys.iterator();
                int count = 0;
                final List<String> delFields = new ArrayList<String>();
                while (iterator.hasNext() && count < exceedSize) {
                    delFields.add(iterator.next());
                    iterator.remove();
                    ++count;
                }
                this.jedisTemplate.hdel(key, (String[])delFields.toArray(new String[0]));
            }
        }
    }
}
