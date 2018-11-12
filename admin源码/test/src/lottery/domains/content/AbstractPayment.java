package lottery.domains.content;

import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.PaymentChannelBank;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.entity.UserWithdraw;
import admin.web.WebJSONObject;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public abstract class AbstractPayment
{
    private final Logger log;
    
    public AbstractPayment() {
        this.log = LoggerFactory.getLogger((Class)this.getClass());
    }
    
    public abstract String daifu(final WebJSONObject p0, final UserWithdraw p1, final UserCard p2, final PaymentChannelBank p3, final PaymentChannel p4);
    
    protected void logStart(final UserWithdraw order, final PaymentChannelBank bank, final PaymentChannel channel) {
        final String bankCode = (bank == null) ? "" : bank.getCode();
        this.log.info("开始[{}]代付，注单ID：{}，银行名称：{}，银行代码: {}, 商户号：{}", new Object[] { channel.getName(), order.getBillno(), order.getBankName(), bankCode, channel.getMerCode() });
    }
    
    protected void logSuccess(final UserWithdraw order, final String payOrderId, final PaymentChannel channel) {
        this.log.info("[{}]代付请求成功，我方注单ID:{}，第三方返回注单ID：{}，商户号：{}", new Object[] { channel.getName(), order.getBillno(), payOrderId, channel.getMerCode() });
    }
    
    protected void logException(final UserWithdraw order, final PaymentChannelBank bank, final PaymentChannel channel, final String msg, final Exception e) {
        final String bankCode = (bank == null) ? "" : bank.getCode();
        this.log.error("[{}]代付发生异常：{}，注单ID:{}，商户号：{}", new Object[] { channel.getName(), msg, order.getBillno(), order.getBankName(), bankCode, channel.getMerCode(), e });
    }
    
    protected void logException(final PaymentChannel channel, final String msg, final Exception e) {
        this.log.error("[{}]发生异常：{}，商户号：{}", new Object[] { channel.getName(), msg, channel.getMerCode(), e });
    }
    
    protected void logInfo(final UserWithdraw order, final PaymentChannelBank bank, final PaymentChannel channel, final String msg) {
        final String bankCode = (bank == null) ? "" : bank.getCode();
        this.log.info("[{}]代付{}，注单ID:{}，商户号：{}", new Object[] { channel.getName(), msg, order.getBillno(), order.getBankName(), bankCode, channel.getMerCode() });
    }
    
    protected void logInfo(final PaymentChannel channel, final String msg) {
        this.log.info("[{}]{}，商户号：{}", new Object[] { channel.getName(), msg, channel.getMerCode() });
    }
    
    protected void logWarn(final UserWithdraw order, final PaymentChannelBank bank, final PaymentChannel channel, final String msg) {
        final String bankCode = (bank == null) ? "" : bank.getCode();
        this.log.warn("[{}]代付{}，注单ID:{}，商户号：{}", new Object[] { channel.getName(), msg, order.getBillno(), order.getBankName(), bankCode, channel.getMerCode() });
    }
    
    protected void logWarn(final PaymentChannel channel, final String msg) {
        this.log.warn("[{}]{}，商户号：{}", new Object[] { channel.getName(), msg, channel.getMerCode() });
    }
    
    protected void logError(final UserWithdraw order, final PaymentChannelBank bank, final PaymentChannel channel, final String msg) {
        this.log.error("[{}]代付{}，注单ID:{}，商户号：{}", new Object[] { channel.getName(), msg, order.getBillno(), channel.getMerCode() });
    }
    
    protected void logError(final PaymentChannel channel, final String msg) {
        this.log.error("[{}]{}，商户号：{}", new Object[] { channel.getName(), msg, channel.getMerCode() });
    }
}
