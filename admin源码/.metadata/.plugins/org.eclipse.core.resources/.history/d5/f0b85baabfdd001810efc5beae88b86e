package lottery.domains.content.biz.impl;

import lottery.domains.utils.lottery.open.OpenTime;
import lottery.domains.content.entity.Lottery;
import java.util.Iterator;
import lottery.domains.content.entity.LotteryCrawlerStatus;
import java.util.ArrayList;
import lottery.domains.content.vo.lottery.LotteryCrawlerStatusVO;
import java.util.List;
import lottery.domains.utils.lottery.open.LotteryOpenUtil;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.LotteryCrawlerStatusDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.LotteryCrawlerStatusService;

@Service
public class LotteryCrawlerStatusServiceImpl implements LotteryCrawlerStatusService
{
    @Autowired
    private LotteryCrawlerStatusDao lotteryCrawlerStatusDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    @Autowired
    private LotteryOpenUtil lotteryOpenUtil;
    
    @Override
    public List<LotteryCrawlerStatusVO> listAll() {
        final List<LotteryCrawlerStatusVO> list = new ArrayList<LotteryCrawlerStatusVO>();
        final List<LotteryCrawlerStatus> clist = this.lotteryCrawlerStatusDao.listAll();
        for (final LotteryCrawlerStatus lotteryCrawlerStatus : clist) {
            final Lottery lottery = this.lotteryDataFactory.getLottery(lotteryCrawlerStatus.getShortName());
            OpenTime openTime = null;
            if (lottery != null) {
                openTime = this.lotteryOpenUtil.getCurrOpenTime(lottery.getId());
            }
            final LotteryCrawlerStatusVO lotteryCrawlerStatusVO = new LotteryCrawlerStatusVO();
            lotteryCrawlerStatusVO.setBean(lotteryCrawlerStatus);
            lotteryCrawlerStatusVO.setOpenTime(openTime);
            list.add(lotteryCrawlerStatusVO);
        }
        return list;
    }
    
    @Override
    public LotteryCrawlerStatus getByLottery(final String lottery) {
        return this.lotteryCrawlerStatusDao.get(lottery);
    }
    
    @Override
    public boolean update(final String lottery, final String lastExpect, final String lastUpdate) {
        final LotteryCrawlerStatus entity = this.getByLottery(lottery);
        if (entity != null) {
            entity.setLastExpect(lastExpect);
            entity.setLastUpdate(lastUpdate);
            return this.lotteryCrawlerStatusDao.update(entity);
        }
        return false;
    }
}
