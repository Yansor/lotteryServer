package lottery.domains.content.biz;

import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.vo.payment.PaymentChannelVO;
import java.util.List;

public interface PaymentChannelService
{
    List<PaymentChannelVO> listAllVOs();
    
    List<PaymentChannel> listAllFullProperties();
    
    List<PaymentChannelVO> listAllMobileScanVOs();
    
    PaymentChannelVO getVOById(final int p0);
    
    PaymentChannel getFullPropertyById(final int p0);
    
    boolean add(final String p0, final String p1, final String p2, final String p3, final String p4, final double p5, final double p6, final double p7, final double p8, final double p9, final String p10, final String p11, final int p12, final int p13, final int p14, final double p15, final String p16, final String p17, final String p18, final String p19, final int p20);
    
    boolean edit(final int p0, final String p1, final String p2, final String p3, final double p4, final double p5, final double p6, final double p7, final double p8, final String p9, final String p10, final int p11, final double p12, final String p13, final String p14, final String p15, final String p16);
    
    boolean updateStatus(final int p0, final int p1);
    
    boolean resetCredits(final int p0);
    
    boolean delete(final int p0);
    
    boolean moveUp(final int p0);
    
    boolean moveDown(final int p0);
}
