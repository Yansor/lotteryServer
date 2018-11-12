package lottery.domains.content.biz.impl;

import lottery.domains.content.global.DbServerSyncEnum;
import java.util.Iterator;
import lottery.domains.content.entity.PaymentCard;
import java.util.List;
import lottery.domains.content.dao.DbServerSyncDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.PaymentCardDao;
import javautils.encrypt.DESUtil;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.PaymentCardService;

@Service
public class PaymentCardServiceImpl implements PaymentCardService
{
    private static final String SECRET = "h/:#l^e>c*/thZeaKec)Ail{(My)!p";
    private static final DESUtil DES;
    @Autowired
    private PaymentCardDao paymentCardDao;
    @Autowired
    private DbServerSyncDao dbServerSyncDao;
    
    static {
        DES = DESUtil.getInstance();
    }
    
    @Override
    public List<PaymentCard> listAll() {
        final List<PaymentCard> paymentCards = this.paymentCardDao.listAll();
        for (final PaymentCard paymentCard : paymentCards) {
            final String decryptBranchName = PaymentCardServiceImpl.DES.decryptStr(paymentCard.getBranchName(), "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
            final String decryptCardName = PaymentCardServiceImpl.DES.decryptStr(paymentCard.getCardName(), "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
            final String decryptCardId = PaymentCardServiceImpl.DES.decryptStr(paymentCard.getCardId(), "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
            paymentCard.setBranchName(decryptBranchName);
            paymentCard.setCardName(decryptCardName);
            paymentCard.setCardId(decryptCardId);
        }
        return paymentCards;
    }
    
    @Override
    public PaymentCard getById(final int id) {
        final PaymentCard card = this.paymentCardDao.getById(id);
        final String decryptBranchName = PaymentCardServiceImpl.DES.decryptStr(card.getBranchName(), "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
        final String decryptCardName = PaymentCardServiceImpl.DES.decryptStr(card.getCardName(), "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
        final String decryptCardId = PaymentCardServiceImpl.DES.decryptStr(card.getCardId(), "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
        card.setBranchName(decryptBranchName);
        card.setCardName(decryptCardName);
        card.setCardId(decryptCardId);
        return card;
    }
    
    @Override
    public boolean add(final int bankId, final String branchName, final String cardName, final String cardId, final double totalCredits, final double minTotalRecharge, final double maxTotalRecharge, final String startTime, final String endTime, final double minUnitRecharge, final double maxUnitRecharge) {
        final String encryptCardName = PaymentCardServiceImpl.DES.encryptStr(cardName, "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
        final String encryptCardId = PaymentCardServiceImpl.DES.encryptStr(cardId, "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
        final String encryptBranchName = PaymentCardServiceImpl.DES.encryptStr(branchName, "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
        final int status = 0;
        final double usedCredits = 0.0;
        final PaymentCard entity = new PaymentCard(bankId, encryptCardName, encryptCardId, encryptBranchName, totalCredits, usedCredits, minTotalRecharge, maxTotalRecharge, startTime, endTime, minUnitRecharge, maxUnitRecharge, status);
        entity.setBranchName(encryptBranchName);
        final boolean added = this.paymentCardDao.add(entity);
        if (added) {
            this.dbServerSyncDao.update(DbServerSyncEnum.PAYMENT_CARD);
        }
        return added;
    }
    
    @Override
    public boolean edit(final int id, final int bankId, final String branchName, final String cardName, final String cardId, final double totalCredits, final double minTotalRecharge, final double maxTotalRecharge, final String startTime, final String endTime, final double minUnitRecharge, final double maxUnitRecharge) {
        final PaymentCard entity = this.paymentCardDao.getById(id);
        if (entity != null) {
            final String encryptCardName = PaymentCardServiceImpl.DES.encryptStr(cardName, "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
            final String encryptCardId = PaymentCardServiceImpl.DES.encryptStr(cardId, "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
            final String encryptBranchName = PaymentCardServiceImpl.DES.encryptStr(branchName, "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
            entity.setBankId(bankId);
            entity.setCardName(encryptCardName);
            entity.setCardId(encryptCardId);
            entity.setBranchName(encryptBranchName);
            entity.setTotalCredits(totalCredits);
            entity.setMinTotalRecharge(minTotalRecharge);
            entity.setMaxTotalRecharge(maxTotalRecharge);
            entity.setStartTime(startTime);
            entity.setEndTime(endTime);
            entity.setMinUnitRecharge(minUnitRecharge);
            entity.setMaxUnitRecharge(maxUnitRecharge);
            final boolean updated = this.paymentCardDao.update(entity);
            if (updated) {
                this.dbServerSyncDao.update(DbServerSyncEnum.PAYMENT_CARD);
            }
            return updated;
        }
        return false;
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final PaymentCard entity = this.paymentCardDao.getById(id);
        if (entity != null) {
            entity.setStatus(status);
            final boolean updated = this.paymentCardDao.update(entity);
            if (updated) {
                this.dbServerSyncDao.update(DbServerSyncEnum.PAYMENT_CARD);
            }
            return updated;
        }
        return false;
    }
    
    @Override
    public boolean resetCredits(final int id) {
        final PaymentCard entity = this.paymentCardDao.getById(id);
        if (entity != null) {
            entity.setUsedCredits(0.0);
            final boolean updated = this.paymentCardDao.update(entity);
            if (updated) {
                this.dbServerSyncDao.update(DbServerSyncEnum.PAYMENT_CARD);
            }
            return updated;
        }
        return false;
    }
    
    @Override
    public boolean addUsedCredits(final int cardId, final double usedCredits) {
        final boolean updated = this.paymentCardDao.addUsedCredits(cardId, usedCredits);
        if (updated) {
            this.dbServerSyncDao.update(DbServerSyncEnum.PAYMENT_CARD);
        }
        return updated;
    }
    
    @Override
    public boolean delete(final int id) {
        final boolean deleted = this.paymentCardDao.delete(id);
        if (deleted) {
            this.dbServerSyncDao.update(DbServerSyncEnum.PAYMENT_CARD);
        }
        return deleted;
    }
}
