package lottery.domains.content.biz.impl;

import java.util.List;
import javautils.encrypt.DESUtil;
import lottery.domains.content.biz.PaymentCardService;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.dao.PaymentCardDao;
import lottery.domains.content.entity.PaymentCard;
import lottery.domains.content.global.DbServerSyncEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentCardServiceImpl
  implements PaymentCardService
{
  private static final String SECRET = "h/:#l^e>c*/thZeaKec)Ail{(My)!p";
  private static final DESUtil DES = new DESUtil();
  @Autowired
  private PaymentCardDao paymentCardDao;
  @Autowired
  private DbServerSyncDao dbServerSyncDao;
  
  public List<PaymentCard> listAll()
  {
    List<PaymentCard> paymentCards = this.paymentCardDao.listAll();
    for (PaymentCard paymentCard : paymentCards)
    {
      String decryptBranchName = DES.decryptStr(paymentCard.getBranchName(), "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
      String decryptCardName = DES.decryptStr(paymentCard.getCardName(), "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
      String decryptCardId = DES.decryptStr(paymentCard.getCardId(), "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
      
      paymentCard.setBranchName(decryptBranchName);
      paymentCard.setCardName(decryptCardName);
      paymentCard.setCardId(decryptCardId);
    }
    return paymentCards;
  }
  
  public PaymentCard getById(int id)
  {
    PaymentCard card = this.paymentCardDao.getById(id);
    String decryptBranchName = DES.decryptStr(card.getBranchName(), "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
    String decryptCardName = DES.decryptStr(card.getCardName(), "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
    String decryptCardId = DES.decryptStr(card.getCardId(), "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
    
    card.setBranchName(decryptBranchName);
    card.setCardName(decryptCardName);
    card.setCardId(decryptCardId);
    return card;
  }
  
  public boolean add(int bankId, String branchName, String cardName, String cardId, double totalCredits, double minTotalRecharge, double maxTotalRecharge, String startTime, String endTime, double minUnitRecharge, double maxUnitRecharge)
  {
    String encryptCardName = DES.encryptStr(cardName, "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
    String encryptCardId = DES.encryptStr(cardId, "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
    String encryptBranchName = DES.encryptStr(branchName, "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
    
    int status = 0;
    double usedCredits = 0.0D;
    PaymentCard entity = new PaymentCard(bankId, encryptCardName, encryptCardId, encryptBranchName, totalCredits, usedCredits, minTotalRecharge, maxTotalRecharge, startTime, endTime, minUnitRecharge, maxUnitRecharge, status);
    entity.setBranchName(encryptBranchName);
    boolean added = this.paymentCardDao.add(entity);
    if (added) {
      this.dbServerSyncDao.update(DbServerSyncEnum.PAYMENT_CARD);
    }
    return added;
  }
  
  public boolean edit(int id, int bankId, String branchName, String cardName, String cardId, double totalCredits, double minTotalRecharge, double maxTotalRecharge, String startTime, String endTime, double minUnitRecharge, double maxUnitRecharge)
  {
    PaymentCard entity = this.paymentCardDao.getById(id);
    if (entity != null)
    {
      String encryptCardName = DES.encryptStr(cardName, "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
      String encryptCardId = DES.encryptStr(cardId, "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
      String encryptBranchName = DES.encryptStr(branchName, "h/:#l^e>c*/thZeaKec)Ail{(My)!p");
      
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
      boolean updated = this.paymentCardDao.update(entity);
      if (updated) {
        this.dbServerSyncDao.update(DbServerSyncEnum.PAYMENT_CARD);
      }
      return updated;
    }
    return false;
  }
  
  public boolean updateStatus(int id, int status)
  {
    PaymentCard entity = this.paymentCardDao.getById(id);
    if (entity != null)
    {
      entity.setStatus(status);
      boolean updated = this.paymentCardDao.update(entity);
      if (updated) {
        this.dbServerSyncDao.update(DbServerSyncEnum.PAYMENT_CARD);
      }
      return updated;
    }
    return false;
  }
  
  public boolean resetCredits(int id)
  {
    PaymentCard entity = this.paymentCardDao.getById(id);
    if (entity != null)
    {
      entity.setUsedCredits(0.0D);
      boolean updated = this.paymentCardDao.update(entity);
      if (updated) {
        this.dbServerSyncDao.update(DbServerSyncEnum.PAYMENT_CARD);
      }
      return updated;
    }
    return false;
  }
  
  public boolean addUsedCredits(int cardId, double usedCredits)
  {
    boolean updated = this.paymentCardDao.addUsedCredits(cardId, usedCredits);
    if (updated) {
      this.dbServerSyncDao.update(DbServerSyncEnum.PAYMENT_CARD);
    }
    return updated;
  }
  
  public boolean delete(int id)
  {
    boolean deleted = this.paymentCardDao.delete(id);
    if (deleted) {
      this.dbServerSyncDao.update(DbServerSyncEnum.PAYMENT_CARD);
    }
    return deleted;
  }
}
