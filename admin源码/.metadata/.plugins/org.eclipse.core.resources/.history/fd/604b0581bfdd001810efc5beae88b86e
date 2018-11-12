package lottery.web.content.validate;

import javautils.StringUtil;
import lottery.domains.utils.lottery.open.OpenTime;
import lottery.domains.content.entity.Lottery;
import admin.web.WebJSONObject;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.utils.lottery.open.LotteryOpenUtil;
import org.springframework.stereotype.Component;

@Component
public class CodeValidate
{
    @Autowired
    private LotteryOpenUtil lotteryOpenUtil;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    public boolean validateExpect(final WebJSONObject json, final String lottery, final String expect) {
        boolean isTrueExpect = false;
        final Lottery lotteryBean = this.lotteryDataFactory.getLottery(lottery);
        if (lotteryBean != null) {
            final OpenTime bean = this.lotteryOpenUtil.getCurrOpenTime(lotteryBean.getId());
            if (bean.getExpect().compareTo(expect) > 0) {
                isTrueExpect = true;
            }
        }
        if (!isTrueExpect) {
            json.set(2, "2-2101");
        }
        return isTrueExpect;
    }
    
    public boolean validateCode(final WebJSONObject json, final String lottery, final String code) {
        boolean isTrueCode = false;
        final Lottery lotteryBean = this.lotteryDataFactory.getLottery(lottery);
        if (lotteryBean != null) {
            switch (lotteryBean.getType()) {
                case 1: {
                    if (this.isSsc(code)) {
                        isTrueCode = true;
                        break;
                    }
                    break;
                }
                case 2: {
                    if (this.is11x5(code)) {
                        isTrueCode = true;
                        break;
                    }
                    break;
                }
                case 3: {
                    if (this.isK3(code)) {
                        isTrueCode = true;
                        break;
                    }
                    break;
                }
                case 4: {
                    if (this.is3d(code)) {
                        isTrueCode = true;
                        break;
                    }
                    break;
                }
                case 5: {
                    if (this.isBjkl8(code)) {
                        isTrueCode = true;
                        break;
                    }
                    break;
                }
                case 6: {
                    if (this.isBjpk10(code)) {
                        isTrueCode = true;
                        break;
                    }
                    break;
                }
                case 7: {
                    if (this.isSsc(code)) {
                        isTrueCode = true;
                        break;
                    }
                    break;
                }
            }
        }
        if (!isTrueCode) {
            json.set(2, "2-2100");
        }
        return isTrueCode;
    }
    
    public boolean isSsc(final String s) {
        if (!StringUtil.isNotNull(s)) {
            return false;
        }
        final String[] codes = s.split(",");
        if (codes.length != 5) {
            return false;
        }
        String[] array;
        for (int length = (array = codes).length, i = 0; i < length; ++i) {
            final String tmpS = array[i];
            if (!StringUtil.isInteger(tmpS)) {
                return false;
            }
            if (tmpS.length() != 1) {
                return false;
            }
            final int tmpC = Integer.parseInt(tmpS);
            if (tmpC < 0 || tmpC > 9) {
                return false;
            }
        }
        return true;
    }
    
    public boolean is11x5(final String s) {
        if (!StringUtil.isNotNull(s)) {
            return false;
        }
        final String[] codes = s.split(",");
        if (codes.length != 5) {
            return false;
        }
        String[] array;
        for (int length = (array = codes).length, i = 0; i < length; ++i) {
            final String tmpS = array[i];
            if (!StringUtil.isInteger(tmpS)) {
                return false;
            }
            if (tmpS.length() != 2) {
                return false;
            }
            final int tmpC = Integer.parseInt(tmpS);
            if (tmpC < 1 || tmpC > 11) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isK3(final String s) {
        if (!StringUtil.isNotNull(s)) {
            return false;
        }
        final String[] codes = s.split(",");
        if (codes.length != 3) {
            return false;
        }
        String[] array;
        for (int length = (array = codes).length, i = 0; i < length; ++i) {
            final String tmpS = array[i];
            if (!StringUtil.isInteger(tmpS)) {
                return false;
            }
            if (tmpS.length() != 1) {
                return false;
            }
            final int tmpC = Integer.parseInt(tmpS);
            if (tmpC < 1 || tmpC > 6) {
                return false;
            }
        }
        return true;
    }
    
    public boolean is3d(final String s) {
        if (!StringUtil.isNotNull(s)) {
            return false;
        }
        final String[] codes = s.split(",");
        if (codes.length != 3) {
            return false;
        }
        String[] array;
        for (int length = (array = codes).length, i = 0; i < length; ++i) {
            final String tmpS = array[i];
            if (!StringUtil.isInteger(tmpS)) {
                return false;
            }
            if (tmpS.length() != 1) {
                return false;
            }
            final int tmpC = Integer.parseInt(tmpS);
            if (tmpC < 0 || tmpC > 9) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isBjkl8(final String s) {
        if (!StringUtil.isNotNull(s)) {
            return false;
        }
        final String[] codes = s.split(",");
        if (codes.length != 20) {
            return false;
        }
        String[] array;
        for (int length = (array = codes).length, i = 0; i < length; ++i) {
            final String tmpS = array[i];
            if (!StringUtil.isInteger(tmpS)) {
                return false;
            }
            if (tmpS.length() != 2) {
                return false;
            }
            final int tmpC = Integer.parseInt(tmpS);
            if (tmpC < 1 || tmpC > 80) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isBjpk10(final String s) {
        if (!StringUtil.isNotNull(s)) {
            return false;
        }
        final String[] codes = s.split(",");
        if (codes.length != 10) {
            return false;
        }
        String[] array;
        for (int length = (array = codes).length, i = 0; i < length; ++i) {
            final String tmpS = array[i];
            if (!StringUtil.isInteger(tmpS)) {
                return false;
            }
            if (tmpS.length() != 2) {
                return false;
            }
            final int tmpC = Integer.parseInt(tmpS);
            if (tmpC < 1 || tmpC > 10) {
                return false;
            }
        }
        return true;
    }
}
