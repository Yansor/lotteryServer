package lottery.domains.content.biz.impl;

import java.util.Iterator;
import java.util.Map;
import lottery.domains.content.entity.Lottery;
import javautils.date.Moment;
import lottery.domains.content.entity.LotteryOpenCode;
import java.util.HashMap;
import lottery.domains.utils.lottery.open.OpenTime;
import javautils.StringUtil;
import java.util.ArrayList;
import lottery.domains.content.vo.lottery.LotteryOpenStatusVO;
import java.util.List;
import javautils.redis.JedisTemplate;
import lottery.domains.utils.lottery.open.LotteryOpenUtil;
import lottery.domains.content.dao.LotteryOpenCodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.LotteryOpenStatusService;

@Service
public class LotteryOpenStatusServiceImpl implements LotteryOpenStatusService
{
    private static final String ADMIN_OPEN_CODE_KEY = "ADMIN_OPEN_CODE:%s";
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    @Autowired
    private LotteryOpenCodeDao lotteryOpenCodeDao;
    @Autowired
    private LotteryOpenUtil lotteryOpenUtil;
    @Autowired
    private JedisTemplate jedisTemplate;
    
    @Override
    public List<LotteryOpenStatusVO> search(final String lotteryId, final String date) {
        final List<LotteryOpenStatusVO> list = new ArrayList<LotteryOpenStatusVO>();
        if (StringUtil.isDateString(date) && StringUtil.isInteger(lotteryId)) {
            final Lottery lottery = this.lotteryDataFactory.getLottery(Integer.parseInt(lotteryId));
            if (lottery != null) {
                final List<OpenTime> openList = this.lotteryOpenUtil.getOpenDateList(lottery.getId(), date);
                if (openList != null) {
                    final String[] expects = new String[openList.size()];
                    for (int i = 0, j = openList.size(); i < j; ++i) {
                        expects[i] = openList.get(i).getExpect();
                    }
                    final Map<String, LotteryOpenCode> openCodeMap = new HashMap<String, LotteryOpenCode>();
                    if (expects != null && expects.length > 0) {
                        final List<LotteryOpenCode> lotteryOpenCodeList = this.lotteryOpenCodeDao.list(lottery.getShortName(), expects);
                        for (final LotteryOpenCode tmpCodeBean : lotteryOpenCodeList) {
                            openCodeMap.put(tmpCodeBean.getExpect(), tmpCodeBean);
                        }
                    }
                    for (final OpenTime openTime : openList) {
                        final LotteryOpenStatusVO tmpBean = new LotteryOpenStatusVO();
                        tmpBean.setLottery(lottery);
                        tmpBean.setOpenTime(openTime);
                        if (openCodeMap.containsKey(openTime.getExpect())) {
                            tmpBean.setOpenCode(openCodeMap.get(openTime.getExpect()));
                        }
                        else {
                            final String key = String.format("ADMIN_OPEN_CODE:%s", lottery.getShortName());
                            final boolean iskey = this.jedisTemplate.hexists(key, openTime.getExpect());
                            if (iskey) {
                                final String code = this.jedisTemplate.hget(key, openTime.getExpect());
                                final LotteryOpenCode bean = new LotteryOpenCode();
                                bean.setCode(code);
                                bean.setOpenStatus(0);
                                bean.setLottery(lottery.getShortName());
                                bean.setTime(new Moment().toSimpleTime());
                                bean.setInterfaceTime(new Moment().toSimpleTime());
                                tmpBean.setOpenCode(bean);
                            }
                        }
                        list.add(tmpBean);
                    }
                }
            }
        }
        return list;
    }
    
    @Override
    public boolean doManualControl(final String lottery, final String expect) {
        final LotteryOpenCode entity = this.lotteryOpenCodeDao.get(lottery, expect);
        if (entity != null) {
            entity.setOpenStatus(0);
            entity.setOpenTime(null);
            return this.lotteryOpenCodeDao.update(entity);
        }
        return false;
    }
}
