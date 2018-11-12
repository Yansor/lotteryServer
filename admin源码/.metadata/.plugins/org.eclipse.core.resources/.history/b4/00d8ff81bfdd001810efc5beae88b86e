package lottery.domains.utils.lottery.open;

import javautils.date.Moment;
import java.util.Iterator;
import java.util.ArrayList;
import lottery.domains.content.entity.LotteryOpenTime;
import javautils.date.DateUtil;
import java.util.List;
import lottery.domains.content.entity.Lottery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.stereotype.Component;

@Component
public class LotteryOpenUtil
{
    @Autowired
    private LotteryDataFactory df;
    @Autowired
    @Qualifier("highOpenTimeUtil")
    private OpenTimeUtil highOpenTimeUtil;
    @Autowired
    @Qualifier("lowOpenTimeUtil")
    private OpenTimeUtil lowOpenTimeUtil;
    
    public OpenTime getOpentime(final int lotteryId, String expect) {
        final Lottery lottery = this.df.getLottery(lotteryId);
        if (lottery != null) {
            if (lottery.getExpectTrans() == 1) {
                expect = this.trans(lotteryId, Integer.parseInt(expect));
                final OpenTime bean = this.highOpenTimeUtil.getOpenTime(lotteryId, expect);
                return this.trans(lotteryId, bean);
            }
            switch (lottery.getType()) {
                case 1:
                case 2:
                case 3:
                case 7: {
                    if ("bjk3".equals(lottery.getShortName())) {
                        expect = this.trans(lotteryId, Integer.parseInt(expect));
                        final OpenTime bean = this.highOpenTimeUtil.getOpenTime(lotteryId, expect);
                        return this.trans(lotteryId, bean);
                    }
                    return this.highOpenTimeUtil.getOpenTime(lotteryId, expect);
                }
                case 4: {
                    return this.lowOpenTimeUtil.getOpenTime(lotteryId, expect);
                }
                case 5:
                case 6: {
                    if ("bjpk10".equals(lottery.getShortName())) {
                        expect = this.trans(lotteryId, Integer.parseInt(expect));
                        final OpenTime bean = this.highOpenTimeUtil.getOpenTime(lotteryId, expect);
                        return this.trans(lotteryId, bean);
                    }
                    return this.highOpenTimeUtil.getOpenTime(lotteryId, expect);
                }
            }
        }
        return null;
    }
    
    public List<OpenTime> getOpenTimeList(final int lotteryId, final int count) {
        final Lottery lottery = this.df.getLottery(lotteryId);
        if (lottery != null) {
            if (lottery.getExpectTrans() == 1) {
                final List<OpenTime> list = this.highOpenTimeUtil.getOpenTimeList(lotteryId, count);
                return this.trans(lotteryId, list);
            }
            switch (lottery.getType()) {
                case 1:
                case 2:
                case 3:
                case 7: {
                    if ("bjk3".equals(lottery.getShortName())) {
                        final List<OpenTime> list = this.highOpenTimeUtil.getOpenTimeList(lotteryId, count);
                        return this.trans(lotteryId, list);
                    }
                    return this.highOpenTimeUtil.getOpenTimeList(lotteryId, count);
                }
                case 4: {
                    return this.lowOpenTimeUtil.getOpenTimeList(lotteryId, count);
                }
                case 5:
                case 6: {
                    if ("bjpk10".equals(lottery.getShortName())) {
                        final List<OpenTime> list = this.highOpenTimeUtil.getOpenTimeList(lotteryId, count);
                        return this.trans(lotteryId, list);
                    }
                    return this.highOpenTimeUtil.getOpenTimeList(lotteryId, count);
                }
            }
        }
        return null;
    }
    
    public List<OpenTime> getOpenDateList(final int lotteryId, final String date) {
        final Lottery lottery = this.df.getLottery(lotteryId);
        if (lottery != null) {
            if (lottery.getExpectTrans() == 1) {
                final List<OpenTime> list = this.highOpenTimeUtil.getOpenDateList(lotteryId, date);
                return this.trans(lotteryId, list);
            }
            switch (lottery.getType()) {
                case 1:
                case 2:
                case 3:
                case 7: {
                    if ("bjk3".equals(lottery.getShortName())) {
                        final List<OpenTime> list = this.highOpenTimeUtil.getOpenDateList(lotteryId, date);
                        return this.trans(lotteryId, list);
                    }
                    return this.highOpenTimeUtil.getOpenDateList(lotteryId, date);
                }
                case 4: {
                    return this.lowOpenTimeUtil.getOpenDateList(lotteryId, date);
                }
                case 5:
                case 6: {
                    if ("bjpk10".equals(lottery.getShortName())) {
                        final List<OpenTime> list = this.highOpenTimeUtil.getOpenDateList(lotteryId, date);
                        return this.trans(lotteryId, list);
                    }
                    return this.highOpenTimeUtil.getOpenDateList(lotteryId, date);
                }
            }
        }
        return null;
    }
    
    public OpenTime getCurrOpenTime(final int lotteryId) {
        final Lottery lottery = this.df.getLottery(lotteryId);
        if (lottery != null) {
            final String currTime = DateUtil.getCurrentTime();
            if (lottery.getExpectTrans() == 1) {
                final OpenTime bean = this.highOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
                return this.trans(lotteryId, bean);
            }
            switch (lottery.getType()) {
                case 1:
                case 2:
                case 3:
                case 7: {
                    if ("bjk3".equals(lottery.getShortName())) {
                        final OpenTime bean = this.highOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
                        return this.trans(lotteryId, bean);
                    }
                    return this.highOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
                }
                case 4: {
                    return this.lowOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
                }
                case 5:
                case 6: {
                    if ("bjpk10".equals(lottery.getShortName())) {
                        final OpenTime bean = this.highOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
                        return this.trans(lotteryId, bean);
                    }
                    return this.highOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
                }
            }
        }
        return null;
    }
    
    public OpenTime getLastOpenTime(final int lotteryId) {
        final Lottery lottery = this.df.getLottery(lotteryId);
        if (lottery != null) {
            final String currTime = DateUtil.getCurrentTime();
            if (lottery.getExpectTrans() == 1) {
                final OpenTime bean = this.highOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
                return this.trans(lotteryId, bean);
            }
            switch (lottery.getType()) {
                case 1:
                case 2:
                case 3:
                case 7: {
                    if ("bjk3".equals(lottery.getShortName())) {
                        final OpenTime bean = this.highOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
                        return this.trans(lotteryId, bean);
                    }
                    return this.highOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
                }
                case 4: {
                    return this.lowOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
                }
                case 5:
                case 6: {
                    if ("bjpk10".equals(lottery.getShortName())) {
                        final OpenTime bean = this.highOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
                        return this.trans(lotteryId, bean);
                    }
                    return this.highOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
                }
            }
        }
        return null;
    }
    
    public OpenTime trans(final int lotteryId, final OpenTime bean) {
        final Lottery lottery = this.df.getLottery(lotteryId);
        final String refLotteryName = String.valueOf(lottery.getShortName()) + "_ref";
        final List<LotteryOpenTime> opList = this.df.listLotteryOpenTime(refLotteryName);
        final LotteryOpenTime lotteryOpenTime = opList.get(0);
        final String refDate = lotteryOpenTime.getOpenTime();
        final int refExpect = Integer.parseInt(lotteryOpenTime.getExpect());
        final int times = lottery.getTimes();
        return OpenTimeTransUtil.trans(bean, refDate, refExpect, times);
    }
    
    public String trans(final int lotteryId, final int expect) {
        final Lottery lottery = this.df.getLottery(lotteryId);
        final String refLotteryName = String.valueOf(lottery.getShortName()) + "_ref";
        final List<LotteryOpenTime> opList = this.df.listLotteryOpenTime(refLotteryName);
        final LotteryOpenTime lotteryOpenTime = opList.get(0);
        final String refDate = lotteryOpenTime.getOpenTime();
        final int refExpect = Integer.parseInt(lotteryOpenTime.getExpect());
        final int times = lottery.getTimes();
        return OpenTimeTransUtil.trans(expect, refDate, refExpect, times);
    }
    
    public List<OpenTime> trans(final int lotteryId, final List<OpenTime> list) {
        final List<OpenTime> nList = new ArrayList<OpenTime>();
        for (final OpenTime bean : list) {
            nList.add(this.trans(lotteryId, bean));
        }
        return nList;
    }
    
    public String subtractExpect(final String lotteryShortName, final String expect) {
        final Lottery lottery = this.df.getLottery(lotteryShortName);
        if (lottery == null) {
            return null;
        }
        String subExpect;
        if (expect.indexOf("-") <= -1) {
            Integer integerExpect = Integer.valueOf(expect);
            --integerExpect;
            if (integerExpect.toString().length() >= expect.length()) {
                subExpect = integerExpect.toString();
            }
            else {
                subExpect = String.format("%0" + expect.length() + "d", integerExpect);
            }
        }
        else {
            final String[] split = expect.split("-");
            final int formatCount = split[1].length();
            String date = split[0];
            final String currExpect = split[1];
            if (currExpect.equals("001") || currExpect.equals("0001")) {
                date = new Moment().fromDate(date).subtract(1, "days").format("yyyyMMdd");
                subExpect = String.format("%0" + formatCount + "d", lottery.getTimes());
            }
            else {
                Integer integer = Integer.valueOf(currExpect);
                --integer;
                if (integer.toString().length() >= formatCount) {
                    subExpect = integer.toString();
                }
                else {
                    subExpect = String.format("%0" + formatCount + "d", integer);
                }
            }
            subExpect = String.valueOf(date) + "-" + subExpect;
        }
        return subExpect;
    }
}
