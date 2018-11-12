package lottery.domains.content.global;

public class RemitStatusConstants
{
    public enum Status
    {
        SMS_REMIT_STATUS_0("SMS_REMIT_STATUS_0", 0, 0, "待处理"), 
        SMS_REMIT_STATUS_1("SMS_REMIT_STATUS_1", 1, 1, "银行处理中"), 
        SMS_REMIT_STATUS_2("SMS_REMIT_STATUS_2", 2, 2, "<span style=\"color: #35AA47;\">打款完成</span>"), 
        SMS_REMIT_STATUS_3("SMS_REMIT_STATUS_3", 3, 3, "<span>第三方待处理 <i class=\"fa fa-question-circle cursor-pointer tippy\" title=\"系统自动同步第三方状态,请稍候刷新再看\"></i></span>"), 
        SMS_REMIT_STATUS_4("SMS_REMIT_STATUS_4", 4, -1, "<span style=\"color: #D84A38;\">请求失败</span>"), 
        SMS_REMIT_STATUS_5("SMS_REMIT_STATUS_5", 5, -2, "<span style=\"color: #D84A38;\">打款失败</span>"), 
        SMS_REMIT_STATUS_6("SMS_REMIT_STATUS_6", 6, -3, "<span>查询状态中 <i class=\"fa fa-question-circle cursor-pointer tippy\" title=\"指API代付过程中发生未知异常，系统尝试主动与第三方进行核对，超过10分钟后系统不再处理，此时请前往第三方后台核对数据后再手动处理\"></i></span>"), 
        SMS_REMIT_STATUS_7("SMS_REMIT_STATUS_7", 7, -4, "<span style=\"color: #D84A38;\">未知状态 <i class=\"fa fa-question-circle cursor-pointer tippy\" title=\"指API代付过程中发生未知异常或第三方处理超时，系统无法确定该次代付是否成功，请前往第三方后台核对数据后再手动处理\"></i></span>"), 
        SMS_REMIT_STATUS_8("SMS_REMIT_STATUS_8", 8, -5, "<span>第三方处理失败 <i class=\"fa fa-question-circle cursor-pointer tippy\" title=\"第三方状态，请前往第三方后台核对数据后再手动处理\"></i></span> "), 
        SMS_REMIT_STATUS_9("SMS_REMIT_STATUS_9", 9, -6, "<span>银行处理失败 <i class=\"fa fa-question-circle cursor-pointer tippy\" title=\"第三方状态，请前往第三方后台核对数据后再手动处理\"></i></span>"), 
        SMS_REMIT_STATUS_10("SMS_REMIT_STATUS_10", 10, -7, "<span>第三方拒绝支付 <i class=\"fa fa-question-circle cursor-pointer tippy\" title=\"第三方状态，请前往第三方后台核对数据后再手动处理\"></i></span>");
        
        private int type;
        private String content;
        
        private Status(final String s, final int n, final int type, final String content) {
            this.type = type;
            this.content = content;
        }
        
        public int getType() {
            return this.type;
        }
        
        public String getContent() {
            return this.content;
        }
        
        public static String getTypeByContent(final int id) {
            String desc = null;
            Status[] values;
            for (int length = (values = values()).length, i = 0; i < length; ++i) {
                final Status st = values[i];
                if (st.getType() == id) {
                    desc = st.getContent();
                }
            }
            return desc;
        }
    }
}
