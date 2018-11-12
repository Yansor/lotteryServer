package lottery.domains.content.biz.impl;

import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.content.entity.PaymentChannelQrCode;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import javautils.image.ImageUtil;
import org.apache.commons.lang.StringUtils;
import javautils.encrypt.PaymentChannelEncrypt;
import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Criterion;
import java.util.Iterator;
import lottery.domains.content.entity.PaymentChannel;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import lottery.domains.content.vo.payment.PaymentChannelVO;
import java.util.List;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.dao.PaymentChannelQrCodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.PaymentChannelDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.PaymentChannelService;

@Service
public class PaymentChannelServiceImpl implements PaymentChannelService
{
    @Autowired
    private PaymentChannelDao paymentChannelDao;
    @Autowired
    private PaymentChannelQrCodeDao paymentChannelQrCodeDao;
    @Autowired
    private DbServerSyncDao dbServerSyncDao;
    
    @Override
    public List<PaymentChannelVO> listAllVOs() {
        final List<PaymentChannel> paymentChannels = this.paymentChannelDao.listAll();
        if (CollectionUtils.isNotEmpty((Collection)paymentChannels)) {
            final List<PaymentChannelVO> paymentChannelVOs = new ArrayList<PaymentChannelVO>();
            for (final PaymentChannel paymentChannel : paymentChannels) {
                final PaymentChannelVO vo = new PaymentChannelVO(paymentChannel);
                this.decryptSensitiveProperties(vo);
                paymentChannelVOs.add(vo);
            }
            return paymentChannelVOs;
        }
        return new ArrayList<PaymentChannelVO>();
    }
    
    @Override
    public List<PaymentChannel> listAllFullProperties() {
        final List<PaymentChannel> paymentChannels = this.paymentChannelDao.listAll();
        if (CollectionUtils.isNotEmpty((Collection)paymentChannels)) {
            for (final PaymentChannel paymentChannel : paymentChannels) {
                this.decryptSensitiveProperties(paymentChannel);
            }
        }
        return paymentChannels;
    }
    
    @Override
    public List<PaymentChannelVO> listAllMobileScanVOs() {
        final int type = 2;
        final Integer[] subType = { 2, 4, 6 };
        final List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add((Criterion)Restrictions.eq("type", (Object)type));
        criterions.add(Restrictions.in("subType", (Object[])subType));
        final List<Order> orders = new ArrayList<Order>();
        orders.add(Order.asc("sequence"));
        final List<PaymentChannel> paymentChannels = this.paymentChannelDao.listAll(criterions, orders);
        if (CollectionUtils.isNotEmpty((Collection)paymentChannels)) {
            final List<PaymentChannelVO> paymentChannelVOs = new ArrayList<PaymentChannelVO>();
            for (final PaymentChannel paymentChannel : paymentChannels) {
                final PaymentChannelVO vo = new PaymentChannelVO(paymentChannel);
                this.decryptSensitiveProperties(vo);
                paymentChannelVOs.add(vo);
            }
            return paymentChannelVOs;
        }
        return new ArrayList<PaymentChannelVO>();
    }
    
    @Override
    public PaymentChannelVO getVOById(final int id) {
        final PaymentChannel paymentChannel = this.paymentChannelDao.getById(id);
        if (paymentChannel != null) {
            final PaymentChannelVO vo = new PaymentChannelVO(paymentChannel);
            this.decryptSensitiveProperties(vo);
            return vo;
        }
        return null;
    }
    
    @Override
    public PaymentChannel getFullPropertyById(final int id) {
        final PaymentChannel paymentChannel = this.paymentChannelDao.getById(id);
        if (paymentChannel != null) {
            this.decryptSensitiveProperties(paymentChannel);
            return paymentChannel;
        }
        return null;
    }
    
    @Override
    public boolean add(final String name, final String mobileName, final String frontName, final String channelCode, final String merCode, final double totalCredits, final double minTotalRecharge, final double maxTotalRecharge, final double minUnitRecharge, final double maxUnitRecharge, final String maxRegisterTime, final String qrCodeContent, final int fixedQRAmount, final int type, final int subType, final double consumptionPercent, final String whiteUsernames, final String startTime, final String endTime, final String fixedAmountQrs, final int addMoneyType) {
        final PaymentChannel entity = new PaymentChannel();
        entity.setName(name);
        entity.setMobileName(mobileName);
        entity.setFrontName(frontName);
        entity.setChannelCode(channelCode);
        entity.setMerCode(merCode);
        entity.setTotalCredits(totalCredits);
        entity.setMinTotalRecharge(minTotalRecharge);
        entity.setMaxTotalRecharge(maxTotalRecharge);
        entity.setMinUnitRecharge(minUnitRecharge);
        entity.setMaxUnitRecharge(maxUnitRecharge);
        entity.setMaxRegisterTime(maxRegisterTime);
        entity.setFixedQRAmount(fixedQRAmount);
        entity.setType(type);
        entity.setSubType(subType);
        entity.setConsumptionPercent(consumptionPercent);
        entity.setStartTime(startTime);
        entity.setEndTime(endTime);
        entity.setMaxRegisterTime(maxRegisterTime);
        final String md5Key = PaymentChannelEncrypt.encrypt(RandomStringUtils.random(20, true, true));
        entity.setMd5Key(md5Key);
        entity.setPayUrl(null);
        entity.setArmourUrl(null);
        entity.setStatus(-1);
        entity.setThirdFee(0.0);
        entity.setThirdFeeFixed(0);
        entity.setUsedCredits(0.0);
        entity.setWhiteUsernames(whiteUsernames);
        entity.setAddMoneyType(addMoneyType);
        final int maxSequence = this.paymentChannelDao.getMaxSequence();
        entity.setSequence(maxSequence + 1);
        if (StringUtils.isNotEmpty(qrCodeContent)) {
            String qrUrlCode = ImageUtil.encodeQR(qrCodeContent, 200, 200);
            qrUrlCode = PaymentChannelEncrypt.encrypt(qrUrlCode);
            entity.setQrUrlCode(qrUrlCode);
        }
        final boolean added = this.paymentChannelDao.add(entity);
        if (added && fixedQRAmount == 1) {
            final JSONArray jsonArray = JSONArray.parseArray(fixedAmountQrs);
            for (final Object object : jsonArray) {
                final JSONObject jsonObject = JSONObject.parseObject(object.toString());
                final PaymentChannelQrCode paymentChannelQrCode = new PaymentChannelQrCode();
                final Double amount = Double.valueOf(jsonObject.getString("amount"));
                final String fuxQRCodeContent = jsonObject.getString("qrCodeContent");
                if (StringUtils.isNotEmpty(fuxQRCodeContent)) {
                    String base64QR = ImageUtil.encodeQR(fuxQRCodeContent, 200, 200);
                    base64QR = PaymentChannelEncrypt.encrypt(base64QR);
                    paymentChannelQrCode.setQrUrlCode(base64QR);
                }
                paymentChannelQrCode.setChannelId(entity.getId());
                paymentChannelQrCode.setMoney(amount);
                this.paymentChannelQrCodeDao.add(paymentChannelQrCode);
            }
        }
        if (added) {
            this.dbServerSyncDao.update(DbServerSyncEnum.PAYMENT_CHANNEL);
        }
        return added;
    }
    
    @Override
    public boolean edit(final int id, final String name, final String mobileName, final String frontName, final double totalCredits, final double minTotalRecharge, final double maxTotalRecharge, final double minUnitRecharge, final double maxUnitRecharge, final String maxRegisterTime, final String qrCodeContent, final int fixedQRAmount, final double consumptionPercent, final String whiteUsernames, final String startTime, final String endTime, final String fixedAmountQrs) {
        final PaymentChannel entity = this.paymentChannelDao.getById(id);
        if (entity == null) {
            return false;
        }
        entity.setName(name);
        entity.setMobileName(mobileName);
        entity.setFrontName(frontName);
        entity.setTotalCredits(totalCredits);
        entity.setMinTotalRecharge(minTotalRecharge);
        entity.setMaxTotalRecharge(maxTotalRecharge);
        entity.setMinUnitRecharge(minUnitRecharge);
        entity.setMaxUnitRecharge(maxUnitRecharge);
        entity.setMaxRegisterTime(maxRegisterTime);
        entity.setFixedQRAmount(fixedQRAmount);
        entity.setConsumptionPercent(consumptionPercent);
        entity.setStartTime(startTime);
        entity.setEndTime(endTime);
        entity.setMaxRegisterTime(maxRegisterTime);
        entity.setWhiteUsernames(whiteUsernames);
        if (StringUtils.isNotEmpty(qrCodeContent)) {
            String base64QR = ImageUtil.encodeQR(qrCodeContent, 200, 200);
            base64QR = PaymentChannelEncrypt.encrypt(base64QR);
            entity.setQrUrlCode(base64QR);
        }
        final boolean updated = this.paymentChannelDao.update(entity);
        if (updated) {
            if (updated && fixedQRAmount == 1) {
                final JSONArray jsonArray = JSONArray.parseArray(fixedAmountQrs);
                for (final Object object : jsonArray) {
                    final JSONObject jsonObject = JSONObject.parseObject(object.toString());
                    final String amount = jsonObject.getString("amount");
                    final String fixQRCodeContent = jsonObject.getString("qrCodeContent");
                    final String paymentChannelQrCodeId = jsonObject.getString("id");
                    final PaymentChannelQrCode paymentChannelQrCode = new PaymentChannelQrCode();
                    paymentChannelQrCode.setMoney(Double.valueOf(amount));
                    paymentChannelQrCode.setChannelId(id);
                    if (StringUtils.isNotEmpty(fixQRCodeContent)) {
                        String base64QR2 = ImageUtil.encodeQR(fixQRCodeContent, 200, 200);
                        base64QR2 = PaymentChannelEncrypt.encrypt(base64QR2);
                        paymentChannelQrCode.setQrUrlCode(base64QR2);
                    }
                    if (StringUtils.isEmpty(paymentChannelQrCodeId)) {
                        this.paymentChannelQrCodeDao.add(paymentChannelQrCode);
                    }
                    else {
                        final PaymentChannelQrCode qrCode = this.paymentChannelQrCodeDao.getById(Integer.valueOf(paymentChannelQrCodeId));
                        qrCode.setMoney(Double.valueOf(amount));
                        qrCode.setId(Integer.valueOf(paymentChannelQrCodeId));
                        if (StringUtils.isNotEmpty(fixQRCodeContent)) {
                            String base64QR3 = ImageUtil.encodeQR(fixQRCodeContent, 200, 200);
                            base64QR3 = PaymentChannelEncrypt.encrypt(base64QR3);
                            qrCode.setQrUrlCode(base64QR3);
                        }
                        this.paymentChannelQrCodeDao.update(qrCode);
                    }
                }
            }
            this.dbServerSyncDao.update(DbServerSyncEnum.PAYMENT_CHANNEL);
        }
        return updated;
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final PaymentChannel entity = this.paymentChannelDao.getById(id);
        if (entity != null) {
            entity.setStatus(status);
            final boolean updated = this.paymentChannelDao.update(entity);
            if (updated) {
                this.dbServerSyncDao.update(DbServerSyncEnum.PAYMENT_CHANNEL);
            }
            return updated;
        }
        return false;
    }
    
    @Override
    public boolean resetCredits(final int id) {
        final PaymentChannel entity = this.paymentChannelDao.getById(id);
        if (entity != null) {
            entity.setUsedCredits(0.0);
            return this.paymentChannelDao.update(entity);
        }
        return false;
    }
    
    @Override
    public boolean delete(final int id) {
        final PaymentChannelVO paymentChannel = this.getVOById(id);
        if (paymentChannel == null) {
            return false;
        }
        final boolean deleted = this.paymentChannelDao.delete(id);
        if (deleted) {
            this.paymentChannelDao.batchModSequence(paymentChannel.getSequence());
        }
        return deleted;
    }
    
    @Override
    public boolean moveUp(final int id) {
        final PaymentChannel entity = this.paymentChannelDao.getById(id);
        if (entity != null && entity.getSequence() != 1) {
            final List<PaymentChannel> prev = this.paymentChannelDao.getBySequenceUp(entity.getSequence());
            if (prev != null && prev.size() > 0) {
                final PaymentChannel paymentChannel = prev.get(0);
                final int adminUserMenuSort = entity.getSequence() - paymentChannel.getSequence();
                if (adminUserMenuSort > 1) {
                    this.paymentChannelDao.modSequence(entity.getId(), -1);
                }
                else {
                    this.paymentChannelDao.updateSequence(entity.getId(), prev.get(0).getSequence());
                    this.paymentChannelDao.updateSequence(prev.get(0).getId(), entity.getSequence());
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean moveDown(final int id) {
        final PaymentChannel entity = this.paymentChannelDao.getById(id);
        final int total = this.paymentChannelDao.getMaxSequence();
        if (entity != null && entity.getSequence() != total) {
            final List<PaymentChannel> nexts = this.paymentChannelDao.getBySequenceDown(entity.getSequence());
            if (nexts != null && nexts.size() > 0) {
                final PaymentChannel nextPaymentChannel = nexts.get(0);
                final int nextPaymentChannelSequence = nextPaymentChannel.getSequence() - entity.getSequence();
                if (nextPaymentChannelSequence > 1) {
                    this.paymentChannelDao.modSequence(entity.getId(), 1);
                }
                else {
                    this.paymentChannelDao.updateSequence(entity.getId(), nextPaymentChannel.getSequence());
                    this.paymentChannelDao.updateSequence(nextPaymentChannel.getId(), entity.getSequence());
                }
                return true;
            }
        }
        return false;
    }
    
    private void decryptSensitiveProperties(final PaymentChannel paymentChannel) {
        String payUrl = paymentChannel.getPayUrl();
        if (StringUtils.isNotEmpty(payUrl)) {
            payUrl = PaymentChannelEncrypt.decrypt(payUrl);
            paymentChannel.setPayUrl(payUrl);
        }
        String armourUrl = paymentChannel.getArmourUrl();
        if (StringUtils.isNotEmpty(armourUrl)) {
            armourUrl = PaymentChannelEncrypt.decrypt(armourUrl);
            paymentChannel.setArmourUrl(armourUrl);
        }
        String qrUrlCode = paymentChannel.getQrUrlCode();
        if (StringUtils.isNotEmpty(qrUrlCode)) {
            qrUrlCode = PaymentChannelEncrypt.decrypt(qrUrlCode);
            paymentChannel.setQrUrlCode(qrUrlCode);
        }
        String md5Key = paymentChannel.getMd5Key();
        if (StringUtils.isNotEmpty(md5Key)) {
            md5Key = PaymentChannelEncrypt.decrypt(md5Key);
            paymentChannel.setMd5Key(md5Key);
        }
        String rsaPublicKey = paymentChannel.getRsaPublicKey();
        if (StringUtils.isNotEmpty(rsaPublicKey)) {
            rsaPublicKey = PaymentChannelEncrypt.decrypt(rsaPublicKey);
            paymentChannel.setRsaPublicKey(rsaPublicKey);
        }
        String rsaPrivateKey = paymentChannel.getRsaPrivateKey();
        if (StringUtils.isNotEmpty(rsaPrivateKey)) {
            rsaPrivateKey = PaymentChannelEncrypt.decrypt(rsaPrivateKey);
            paymentChannel.setRsaPrivateKey(rsaPrivateKey);
        }
        String rsaPlatformPublicKey = paymentChannel.getRsaPlatformPublicKey();
        if (StringUtils.isNotEmpty(rsaPlatformPublicKey)) {
            rsaPlatformPublicKey = PaymentChannelEncrypt.decrypt(rsaPlatformPublicKey);
            paymentChannel.setRsaPlatformPublicKey(rsaPlatformPublicKey);
        }
        String ext1 = paymentChannel.getExt1();
        if (StringUtils.isNotEmpty(ext1)) {
            ext1 = PaymentChannelEncrypt.decrypt(ext1);
            paymentChannel.setExt1(ext1);
        }
        String ext2 = paymentChannel.getExt2();
        if (StringUtils.isNotEmpty(ext2)) {
            ext2 = PaymentChannelEncrypt.decrypt(ext2);
            paymentChannel.setExt2(ext2);
        }
        String ext3 = paymentChannel.getExt3();
        if (StringUtils.isNotEmpty(ext3)) {
            ext3 = PaymentChannelEncrypt.decrypt(ext3);
            paymentChannel.setExt3(ext3);
        }
    }
    
    private void decryptSensitiveProperties(final PaymentChannelVO paymentChannelVO) {
        String qrUrlCode = paymentChannelVO.getQrUrlCode();
        if (StringUtils.isNotEmpty(qrUrlCode)) {
            qrUrlCode = PaymentChannelEncrypt.decrypt(qrUrlCode);
            paymentChannelVO.setQrUrlCode(qrUrlCode);
        }
    }
}
