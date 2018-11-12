package lottery.domains.content.api.sb;

import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;

public class Win88SBSportBetLogResult extends Win88SBResult
{
    @JSONField(name = "LastVersionKey")
    private String LastVersionKey;
    @JSONField(name = "TotalRecord")
    private int TotalRecord;
    @JSONField(name = "Data")
    private List<Data> Data;
    
    public String getLastVersionKey() {
        return this.LastVersionKey;
    }
    
    public void setLastVersionKey(final String lastVersionKey) {
        this.LastVersionKey = lastVersionKey;
    }
    
    public int getTotalRecord() {
        return this.TotalRecord;
    }
    
    public void setTotalRecord(final int totalRecord) {
        this.TotalRecord = totalRecord;
    }
    
    public List<Data> getData() {
        return this.Data;
    }
    
    public void setData(final List<Data> data) {
        this.Data = data;
    }
    
    public static class Data
    {
        @JSONField(name = "TransId")
        private String TransId;
        @JSONField(name = "PlayerName")
        private String PlayerName;
        @JSONField(name = "TransactionTime")
        private String TransactionTime;
        @JSONField(name = "MatchId")
        private String MatchId;
        @JSONField(name = "LeagueId")
        private String LeagueId;
        @JSONField(name = "LeagueName")
        private String LeagueName;
        @JSONField(name = "SportType")
        private String SportType;
        @JSONField(name = "AwayId")
        private String AwayId;
        @JSONField(name = "AwayIDName")
        private String AwayIDName;
        @JSONField(name = "HomeId")
        private String HomeId;
        @JSONField(name = "HomeIDName")
        private String HomeIDName;
        @JSONField(name = "MatchDateTime")
        private String MatchDateTime;
        @JSONField(name = "BetType")
        private String BetType;
        @JSONField(name = "ParlayRefNo")
        private String ParlayRefNo;
        @JSONField(name = "BetTeam")
        private String BetTeam;
        @JSONField(name = "HDP")
        private String HDP;
        @JSONField(name = "AwayHDP")
        private String AwayHDP;
        @JSONField(name = "HomeHDP")
        private String HomeHDP;
        @JSONField(name = "Odds")
        private String Odds;
        @JSONField(name = "AwayScore")
        private String AwayScore;
        @JSONField(name = "HomeScore")
        private String HomeScore;
        @JSONField(name = "IsLive")
        private String IsLive;
        @JSONField(name = "TicketStatus")
        private String TicketStatus;
        @JSONField(name = "Stake")
        private String Stake;
        @JSONField(name = "WinLoseAmount")
        private String WinLoseAmount;
        @JSONField(name = "WinLostDateTime")
        private String WinLostDateTime;
        @JSONField(name = "VersionKey")
        private String VersionKey;
        @JSONField(name = "LastBallNo")
        private String LastBallNo;
        
        public String getTransId() {
            return this.TransId;
        }
        
        public void setTransId(final String transId) {
            this.TransId = transId;
        }
        
        public String getPlayerName() {
            return this.PlayerName;
        }
        
        public void setPlayerName(final String playerName) {
            this.PlayerName = playerName;
        }
        
        public String getTransactionTime() {
            return this.TransactionTime;
        }
        
        public void setTransactionTime(final String transactionTime) {
            this.TransactionTime = transactionTime;
        }
        
        public String getMatchId() {
            return this.MatchId;
        }
        
        public void setMatchId(final String matchId) {
            this.MatchId = matchId;
        }
        
        public String getLeagueId() {
            return this.LeagueId;
        }
        
        public void setLeagueId(final String leagueId) {
            this.LeagueId = leagueId;
        }
        
        public String getLeagueName() {
            return this.LeagueName;
        }
        
        public void setLeagueName(final String leagueName) {
            this.LeagueName = leagueName;
        }
        
        public String getSportType() {
            return this.SportType;
        }
        
        public void setSportType(final String sportType) {
            this.SportType = sportType;
        }
        
        public String getAwayId() {
            return this.AwayId;
        }
        
        public void setAwayId(final String awayId) {
            this.AwayId = awayId;
        }
        
        public String getAwayIDName() {
            return this.AwayIDName;
        }
        
        public void setAwayIDName(final String awayIDName) {
            this.AwayIDName = awayIDName;
        }
        
        public String getHomeId() {
            return this.HomeId;
        }
        
        public void setHomeId(final String homeId) {
            this.HomeId = homeId;
        }
        
        public String getHomeIDName() {
            return this.HomeIDName;
        }
        
        public void setHomeIDName(final String homeIDName) {
            this.HomeIDName = homeIDName;
        }
        
        public String getMatchDateTime() {
            return this.MatchDateTime;
        }
        
        public void setMatchDateTime(final String matchDateTime) {
            this.MatchDateTime = matchDateTime;
        }
        
        public String getBetType() {
            return this.BetType;
        }
        
        public void setBetType(final String betType) {
            this.BetType = betType;
        }
        
        public String getParlayRefNo() {
            return this.ParlayRefNo;
        }
        
        public void setParlayRefNo(final String parlayRefNo) {
            this.ParlayRefNo = parlayRefNo;
        }
        
        public String getBetTeam() {
            return this.BetTeam;
        }
        
        public void setBetTeam(final String betTeam) {
            this.BetTeam = betTeam;
        }
        
        public String getHDP() {
            return this.HDP;
        }
        
        public void setHDP(final String HDP) {
            this.HDP = HDP;
        }
        
        public String getAwayHDP() {
            return this.AwayHDP;
        }
        
        public void setAwayHDP(final String awayHDP) {
            this.AwayHDP = awayHDP;
        }
        
        public String getHomeHDP() {
            return this.HomeHDP;
        }
        
        public void setHomeHDP(final String homeHDP) {
            this.HomeHDP = homeHDP;
        }
        
        public String getOdds() {
            return this.Odds;
        }
        
        public void setOdds(final String odds) {
            this.Odds = odds;
        }
        
        public String getAwayScore() {
            return this.AwayScore;
        }
        
        public void setAwayScore(final String awayScore) {
            this.AwayScore = awayScore;
        }
        
        public String getHomeScore() {
            return this.HomeScore;
        }
        
        public void setHomeScore(final String homeScore) {
            this.HomeScore = homeScore;
        }
        
        public String getIsLive() {
            return this.IsLive;
        }
        
        public void setIsLive(final String isLive) {
            this.IsLive = isLive;
        }
        
        public String getTicketStatus() {
            return this.TicketStatus;
        }
        
        public void setTicketStatus(final String ticketStatus) {
            this.TicketStatus = ticketStatus;
        }
        
        public String getStake() {
            return this.Stake;
        }
        
        public void setStake(final String stake) {
            this.Stake = stake;
        }
        
        public String getWinLoseAmount() {
            return this.WinLoseAmount;
        }
        
        public void setWinLoseAmount(final String winLoseAmount) {
            this.WinLoseAmount = winLoseAmount;
        }
        
        public String getWinLostDateTime() {
            return this.WinLostDateTime;
        }
        
        public void setWinLostDateTime(final String winLostDateTime) {
            this.WinLostDateTime = winLostDateTime;
        }
        
        public String getVersionKey() {
            return this.VersionKey;
        }
        
        public void setVersionKey(final String versionKey) {
            this.VersionKey = versionKey;
        }
        
        public String getLastBallNo() {
            return this.LastBallNo;
        }
        
        public void setLastBallNo(final String lastBallNo) {
            this.LastBallNo = lastBallNo;
        }
    }
}
