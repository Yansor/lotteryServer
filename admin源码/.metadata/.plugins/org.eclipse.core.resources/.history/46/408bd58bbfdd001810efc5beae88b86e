package lottery.domains.content.biz.impl;

import java.util.Map;
import java.util.Random;
import java.text.DecimalFormat;
import lottery.domains.content.entity.ActivityPacketInfo.PacketType;
import javautils.date.DateUtil;
import lottery.domains.content.entity.ActivityPacketInfo.PacketStatus;
import lottery.domains.content.entity.ActivityPacketInfo;
import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.ActivityPacketBill;
import lottery.domains.content.vo.activity.ActivityPacketVO;
import org.hibernate.criterion.MatchMode;
import javautils.StringUtil;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.ActivityPacketInfoDao;
import lottery.domains.content.dao.ActivityPacketBillDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.ActivityPacketService;

@Service
public class ActivityPacketServiceImpl implements ActivityPacketService
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private ActivityPacketBillDao activityPacketBillDao;
    @Autowired
    private ActivityPacketInfoDao activityPacketInfoDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList searchBill(final String username, final String date, int start, int limit) {
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
        orders.add(Order.desc("id"));
        criterions.add((Criterion)Restrictions.eq("status", (Object)1));
        boolean isSearch = true;
        if (StringUtil.isNotNull(username)) {
            final User uBean = this.userDao.getByUsername(username);
            if (uBean != null) {
                criterions.add((Criterion)Restrictions.eq("userId", (Object)uBean.getId()));
            }
            else {
                isSearch = false;
            }
        }
        if (StringUtil.isNotNull(date)) {
            criterions.add((Criterion)Restrictions.like("time", date, MatchMode.ANYWHERE));
        }
        if (isSearch) {
            final PageList pList = this.activityPacketBillDao.find(criterions, orders, start, limit);
            final List<ActivityPacketVO> list = new ArrayList<ActivityPacketVO>();
            for (final Object o : pList.getList()) {
                list.add(new ActivityPacketVO((ActivityPacketBill)o, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public PageList searchPacketInfo(final String username, final String date, final String type, int start, int limit) {
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
        orders.add(Order.desc("id"));
        boolean isSearch = true;
        if (StringUtil.isNotNull(username)) {
            final User uBean = this.userDao.getByUsername(username);
            if (uBean != null) {
                criterions.add((Criterion)Restrictions.eq("userId", (Object)uBean.getId()));
            }
            else {
                isSearch = false;
            }
        }
        if (StringUtil.isNotNull(date)) {
            criterions.add((Criterion)Restrictions.like("time", date, MatchMode.ANYWHERE));
        }
        if (StringUtil.isNotNull(type)) {
            criterions.add((Criterion)Restrictions.eq("type", (Object)Integer.parseInt(type)));
        }
        if (isSearch) {
            final PageList pList = this.activityPacketInfoDao.find(criterions, orders, start, limit);
            final List<ActivityPacketVO> list = new ArrayList<ActivityPacketVO>();
            for (final Object o : pList.getList()) {
                list.add(new ActivityPacketVO((ActivityPacketInfo)o, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public boolean generatePackets(final int count, final double amount) {
        final ActivityPacketInfo packetInfo = this.generatePacketInfo(count, amount);
        final int packetInfoId = packetInfo.getId();
        final List<ActivityPacketBill> packets = new ArrayList<ActivityPacketBill>();
        this.splitPacket(count, amount, packetInfoId, packets);
        for (final ActivityPacketBill activityPacketBill : packets) {
            this.activityPacketBillDao.save(activityPacketBill);
        }
        return true;
    }
    
    private ActivityPacketInfo generatePacketInfo(final int count, final double amount) {
        final ActivityPacketInfo packetInfo = new ActivityPacketInfo();
        packetInfo.setAmount(amount);
        packetInfo.setCount(count);
        packetInfo.setStatus(PacketStatus.AVALIABLE.get());
        packetInfo.setTime(DateUtil.getCurrentTime());
        packetInfo.setUserId(-1);
        packetInfo.setType(PacketType.SYSTEM_PACKET.get());
        this.activityPacketInfoDao.save(packetInfo);
        return packetInfo;
    }
    
    private void splitPacket(final int count, final double amount, final int packetInfoId, final List<ActivityPacketBill> packets) {
        final double seed = amount / count;
        double start = 0.0;
        final DecimalFormat df = new DecimalFormat("0.00");
        final Random random = new Random();
        for (int i = 0; i < count; ++i) {
            double d = random.nextDouble();
            if (d == 0.0) {
                d = 0.1;
            }
            d *= seed;
            final double money = Double.parseDouble(df.format(d));
            start += money;
            final ActivityPacketBill packet = new ActivityPacketBill();
            packet.setAmount(money);
            packet.setPacketId(packetInfoId);
            packet.setStatus(PacketStatus.AVALIABLE.get());
            packets.add(packet);
        }
        final double surplus = Double.parseDouble(df.format((amount - start) / count));
        start = 0.0;
        for (final ActivityPacketBill activityPacketBill : packets) {
            final double newAmount = activityPacketBill.getAmount() + surplus;
            activityPacketBill.setAmount(newAmount);
            start += newAmount;
        }
        final ActivityPacketBill firstBill = packets.get(0);
        if (amount - start != 0.0) {
            firstBill.setAmount(firstBill.getAmount() + (amount - start));
        }
    }
    
    @Override
    public List<Map<Integer, Double>> statTotal() {
        return this.activityPacketInfoDao.statTotal();
    }
}
