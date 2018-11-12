package lottery.domains.content.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javautils.date.Moment;
import lottery.domains.content.api.sb.Win88SBAPI;
import lottery.domains.content.api.sb.Win88SBSportBetLogResult.Data;
import org.apache.commons.lang.StringUtils;
import javautils.math.MathUtil;
import lottery.domains.content.api.ag.AGAPI;
import lottery.domains.content.api.ag.AGBetRecord;
import lottery.domains.content.api.pt.PTPlayerGameResult;
import javax.persistence.UniqueConstraint;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "game_bets", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "platform_id", "bets_id", "game_code" }) })
public class GameBets implements Serializable
{
    private int id;
    private int userId;
    private int platformId;
    private String betsId;
    private String gameCode;
    private String gameType;
    private String gameName;
    private double money;
    private double prizeMoney;
    private double progressiveMoney;
    private double progressivePrize;
    private double balance;
    private int status;
    private String time;
    private String prizeTime;
    private String ext1;
    private String ext2;
    private String ext3;
    
    public GameBets() {
    }
    
    public GameBets(final PTPlayerGameResult result, final UserGameAccount account) {
        this.setUserId(account.getUserId());
        this.setPlatformId(account.getPlatformId());
        this.setBetsId(result.getGAMEID());
        this.setGameCode(result.getGAMECODE());
        this.setGameType(result.getGAMETYPE());
        this.setGameName(result.getGAMENAME());
        this.setMoney(Double.valueOf(result.getBET()));
        this.setProgressiveMoney(Double.valueOf(result.getPROGRESSIVEBET()));
        if (this.getMoney() > 0.0 && this.getProgressiveMoney() > 0.0) {
            this.setProgressiveMoney(0.0);
        }
        this.setPrizeMoney(Double.valueOf(result.getWIN()));
        this.setProgressivePrize(Double.valueOf(result.getPROGRESSIVEWIN()));
        this.setBalance(Double.valueOf(result.getBALANCE()));
        this.setTime(result.getGAMEDATE());
        this.setPrizeTime(result.getGAMEDATE());
        this.setStatus(1);
    }
    
    public GameBets(final AGBetRecord record, final UserGameAccount account) {
        this.setUserId(account.getUserId());
        this.setPlatformId(account.getPlatformId());
        this.setBetsId(record.getBillNo());
        this.setGameCode(record.getGameCode());
        this.setGameType(AGAPI.transRound(record.getRound()));
        this.setGameName(AGAPI.transGameType(record.getGameType()));
        this.setMoney(Double.valueOf(record.getBetAmount()));
        record.getGameType();
        if ("BR".equals(record.getDataType()) || "EBR".equals(record.getDataType())) {
            final Double netAmount = Double.valueOf(record.getNetAmount());
            final double prizeMoney = MathUtil.add(this.getMoney(), netAmount);
            this.setPrizeMoney(prizeMoney);
        }
        else {
            final double prizeMoney2 = Double.valueOf(record.getNetAmount());
            this.setPrizeMoney(prizeMoney2);
        }
        this.setProgressiveMoney(0.0);
        this.setProgressivePrize(0.0);
        final double beforeCredit = StringUtils.isEmpty(record.getBeforeCredit()) ? 0.0 : Double.valueOf(record.getBeforeCredit());
        double balance = beforeCredit - this.getMoney() + this.prizeMoney;
        if (balance < 0.0) {
            balance = 0.0;
        }
        this.setBalance(balance);
        this.setStatus(1);
        this.setTime(record.getBetTime());
        this.setPrizeTime(record.getBetTime());
        if (this.getPrizeMoney() < 0.0) {
            this.setPrizeMoney(0.0);
        }
        if (this.getMoney() < 0.0) {
            this.setMoney(0.0);
        }
    }
    
    public GameBets(final Data record, final UserGameAccount account) {
        this.setUserId(account.getUserId());
        this.setPlatformId(13);
        this.setBetsId(record.getTransId());
        this.setGameCode(record.getMatchId());
        if ("29".equals(record.getBetType())) {
            this.setGameType("串关");
            this.setGameName("混合赛事");
        }
        else {
            this.setGameType(Win88SBAPI.transSportType(record.getSportType()));
            this.setGameName(StringUtils.isEmpty(record.getLeagueName()) ? "未知" : record.getLeagueName());
        }
        this.setMoney(Double.valueOf(record.getStake()));
        final Double winLoseAmount = Double.valueOf(record.getWinLoseAmount());
        final double prizeMoney = MathUtil.add(this.getMoney(), winLoseAmount);
        this.setPrizeMoney(prizeMoney);
        this.setProgressiveMoney(0.0);
        this.setProgressivePrize(0.0);
        this.setBalance(0.0);
        this.setStatus(Win88SBAPI.transTicketStatus(record.getTicketStatus()));
        String transactionTime = record.getTransactionTime();
        transactionTime = transactionTime.replaceAll("T", " ");
        if (transactionTime.lastIndexOf(".") > -1) {
            transactionTime = transactionTime.substring(0, transactionTime.lastIndexOf("."));
        }
        transactionTime = new Moment().fromTime(transactionTime).add(12, "hours").toSimpleTime();
        this.setTime(transactionTime);
        if (StringUtils.isNotEmpty(record.getWinLostDateTime())) {
            String winLostDateTime = record.getWinLostDateTime();
            winLostDateTime = winLostDateTime.replaceAll("T", " ");
            if (winLostDateTime.lastIndexOf(".") > -1) {
                winLostDateTime = winLostDateTime.substring(0, winLostDateTime.lastIndexOf("."));
            }
            winLostDateTime = new Moment().fromTime(winLostDateTime).add(12, "hours").toSimpleTime();
            this.setPrizeTime(winLostDateTime);
        }
        this.setExt1(record.getVersionKey());
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    @Column(name = "platform_id", nullable = false)
    public int getPlatformId() {
        return this.platformId;
    }
    
    public void setPlatformId(final int platformId) {
        this.platformId = platformId;
    }
    
    @Column(name = "bets_id", nullable = false, length = 128)
    public String getBetsId() {
        return this.betsId;
    }
    
    public void setBetsId(final String betsId) {
        this.betsId = betsId;
    }
    
    @Column(name = "game_code", nullable = false, length = 128)
    public String getGameCode() {
        return this.gameCode;
    }
    
    public void setGameCode(final String gameCode) {
        this.gameCode = gameCode;
    }
    
    @Column(name = "game_type", nullable = false, length = 128)
    public String getGameType() {
        return this.gameType;
    }
    
    public void setGameType(final String gameType) {
        this.gameType = gameType;
    }
    
    @Column(name = "game_name", nullable = false, length = 128)
    public String getGameName() {
        return this.gameName;
    }
    
    public void setGameName(final String gameName) {
        this.gameName = gameName;
    }
    
    @Column(name = "money", nullable = false, precision = 12, scale = 4)
    public double getMoney() {
        return this.money;
    }
    
    public void setMoney(final double money) {
        this.money = money;
    }
    
    @Column(name = "prize_money", nullable = false, precision = 12, scale = 4)
    public double getPrizeMoney() {
        return this.prizeMoney;
    }
    
    public void setPrizeMoney(final double prizeMoney) {
        this.prizeMoney = prizeMoney;
    }
    
    @Column(name = "progressive_money", precision = 12, scale = 4)
    public double getProgressiveMoney() {
        return this.progressiveMoney;
    }
    
    public void setProgressiveMoney(final double progressiveMoney) {
        this.progressiveMoney = progressiveMoney;
    }
    
    @Column(name = "progressive_prize", precision = 12, scale = 4)
    public double getProgressivePrize() {
        return this.progressivePrize;
    }
    
    public void setProgressivePrize(final double progressivePrize) {
        this.progressivePrize = progressivePrize;
    }
    
    @Column(name = "balance", precision = 12, scale = 4)
    public double getBalance() {
        return this.balance;
    }
    
    public void setBalance(final double balance) {
        this.balance = balance;
    }
    
    @Column(name = "status")
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    @Column(name = "time", nullable = false, length = 50)
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
    
    @Column(name = "prize_time", length = 50)
    public String getPrizeTime() {
        return this.prizeTime;
    }
    
    public void setPrizeTime(final String prizeTime) {
        this.prizeTime = prizeTime;
    }
    
    @Column(name = "ext1", length = 128)
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(final String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name = "ext2", length = 128)
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(final String ext2) {
        this.ext2 = ext2;
    }
    
    @Column(name = "ext3", length = 128)
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(final String ext3) {
        this.ext3 = ext3;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof GameBets)) {
            return false;
        }
        final GameBets other = (GameBets)obj;
        final EqualsBuilder builder = new EqualsBuilder();
        return builder.append(this.getUserId(), other.getUserId()).append(this.getPlatformId(), other.getPlatformId()).append((Object)this.getBetsId(), (Object)other.getBetsId()).append((Object)this.getGameCode(), (Object)other.getGameCode()).append((Object)this.getGameType(), (Object)other.getGameType()).append((Object)this.getGameName(), (Object)other.getGameName()).append(this.getMoney(), other.getMoney()).append(this.getPrizeMoney(), other.getPrizeMoney()).append(this.getProgressiveMoney(), other.getProgressiveMoney()).append(this.getProgressivePrize(), other.getProgressivePrize()).append(this.getBalance(), other.getBalance()).append(this.getStatus(), other.getStatus()).append((Object)this.getTime(), (Object)other.getTime()).append((Object)this.getPrizeTime(), (Object)other.getPrizeTime()).append((Object)this.getExt1(), (Object)other.getExt1()).append((Object)this.getExt2(), (Object)other.getExt2()).append((Object)this.getExt3(), (Object)other.getExt3()).isEquals();
    }
}
