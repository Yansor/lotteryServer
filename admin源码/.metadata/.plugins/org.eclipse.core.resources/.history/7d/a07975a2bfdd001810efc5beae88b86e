package lottery.domains.content.global;

public class CriticalLogTypeEnum
{
    public enum CriticalLogType
    {
        CRITICAL_LOG_TYPE_0("CRITICAL_LOG_TYPE_0", 0, 0, "修改用户登录密码"), 
        CRITICAL_LOG_TYPE_1("CRITICAL_LOG_TYPE_1", 1, 1, "修改用户资金密码"), 
        CRITICAL_LOG_TYPE_2("CRITICAL_LOG_TYPE_2", 2, 2, "修改用户绑定取款人"), 
        CRITICAL_LOG_TYPE_3("CRITICAL_LOG_TYPE_3", 3, 3, "修改用户上下级转账权限"), 
        CRITICAL_LOG_TYPE_4("CRITICAL_LOG_TYPE_4", 4, 4, "重置用户绑定邮箱"), 
        CRITICAL_LOG_TYPE_5("CRITICAL_LOG_TYPE_5", 5, 5, "修改用户绑定邮箱"), 
        CRITICAL_LOG_TYPE_6("CRITICAL_LOG_TYPE_6", 6, 6, "操作用户转账"), 
        CRITICAL_LOG_TYPE_7("CRITICAL_LOG_TYPE_7", 7, 7, "充值"), 
        CRITICAL_LOG_TYPE_8("CRITICAL_LOG_TYPE_8", 8, -5, "<span>第三方处理失败 <i class=\"fa fa-question-circle cursor-pointer\" title=\"第三方状态，请前往第三方后台核对数据后再手动处理\" class=\"tippy\"></i></span> "), 
        CRITICAL_LOG_TYPE_9("CRITICAL_LOG_TYPE_9", 9, -6, "<span>银行处理失败 <i class=\"fa fa-question-circle cursor-pointer\" title=\"第三方状态，请前往第三方后台核对数据后再手动处理\" class=\"tippy\"></i></span>"), 
        CRITICAL_LOG_TYPE_10("CRITICAL_LOG_TYPE_10", 10, -7, "<span>第三方拒绝支付 <i class=\"fa fa-question-circle cursor-pointer\" title=\"第三方状态，请前往第三方后台核对数据后再手动处理\" class=\"tippy\"></i></span>");
        
        private int type;
        private String content;
        
        private CriticalLogType(final String s, final int n, final int type, final String content) {
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
            CriticalLogType[] values;
            for (int length = (values = values()).length, i = 0; i < length; ++i) {
                final CriticalLogType st = values[i];
                if (st.getType() == id) {
                    desc = st.getContent();
                }
            }
            return desc;
        }
    }
}
