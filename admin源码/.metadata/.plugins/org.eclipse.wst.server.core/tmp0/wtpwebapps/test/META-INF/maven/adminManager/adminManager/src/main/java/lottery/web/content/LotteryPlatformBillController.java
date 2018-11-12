package lottery.web.content;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import admin.domains.content.entity.AdminUser;
import javautils.StringUtil;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import lottery.domains.content.vo.payment.PaymentChannelVO;
import lottery.domains.content.global.PaymentConstant;
import lottery.domains.content.entity.UserWithdraw;
import lottery.domains.content.entity.UserBill;
import org.apache.poi.hssf.usermodel.HSSFRow;
import lottery.domains.content.vo.user.UserVO;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import java.io.File;
import javautils.excel.ExcelUtil;
import org.springframework.util.ResourceUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import lottery.domains.content.entity.UserRecharge;
import java.util.List;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserWithdrawDao;
import lottery.domains.content.biz.UserRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserBillDao;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class LotteryPlatformBillController extends AbstractActionController
{
    @Autowired
    private UserBillDao uBillDao;
    @Autowired
    private UserRechargeService uRechargeService;
    @Autowired
    private UserWithdrawDao uWithdrawDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    private final String thridTemplate = "classpath:config/template/recharge-thrid.xls";
    private final String transferTemplate = "classpath:config/template/recharge-transfer.xls";
    private final String systemTemplate = "classpath:config/template/recharge-system.xls";
    private final String withdrawTemplate = "classpath:config/template/withdraw.xls";
    private final String rechargeTemplate = "classpath:config/template/recharge.xls";
    
    private HSSFWorkbook getChannelExcel(final List<UserRecharge> list) {
        try {
            final File file = ResourceUtils.getFile("classpath:config/template/recharge-thrid.xls");
            final HSSFWorkbook workbook = ExcelUtil.getInstance().read(file);
            final HSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 0, j = list.size(); i < j; ++i) {
                final UserRecharge tmpBean = list.get(i);
                final UserVO tmpUser = this.lotteryDataFactory.getUser(tmpBean.getUserId());
                HSSFRow row = sheet.getRow(i + 1);
                if (row == null) {
                    row = sheet.createRow(i + 1);
                }
                if (tmpUser != null) {
                    ExcelUtil.getCell(row, "A").setCellValue(tmpUser.getUsername());
                }
                ExcelUtil.getCell(row, "B").setCellValue("在线存款");
                ExcelUtil.getCell(row, "C").setCellValue(tmpBean.getMoney());
                ExcelUtil.getCell(row, "D").setCellValue(tmpBean.getPayTime());
                ExcelUtil.getCell(row, "E").setCellValue(tmpBean.getBillno());
                ExcelUtil.getCell(row, "F").setCellValue(tmpBean.getPayBillno());
                ExcelUtil.getCell(row, "G").setCellValue(tmpBean.getRemarks());
            }
            return workbook;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private HSSFWorkbook getTransferExcel(final List<UserRecharge> list) {
        try {
            final File file = ResourceUtils.getFile("classpath:config/template/recharge-transfer.xls");
            final HSSFWorkbook workbook = ExcelUtil.getInstance().read(file);
            final HSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 0, j = list.size(); i < j; ++i) {
                final UserRecharge tmpBean = list.get(i);
                final UserVO tmpUser = this.lotteryDataFactory.getUser(tmpBean.getUserId());
                HSSFRow row = sheet.getRow(i + 1);
                if (row == null) {
                    row = sheet.createRow(i + 1);
                }
                if (tmpUser != null) {
                    ExcelUtil.getCell(row, "A").setCellValue(tmpUser.getUsername());
                }
                ExcelUtil.getCell(row, "B").setCellValue("转账存款");
                ExcelUtil.getCell(row, "C").setCellValue(tmpBean.getMoney());
                ExcelUtil.getCell(row, "D").setCellValue(tmpBean.getFeeMoney());
                ExcelUtil.getCell(row, "E").setCellValue(tmpBean.getRecMoney());
                ExcelUtil.getCell(row, "F").setCellValue(tmpBean.getPayTime());
                ExcelUtil.getCell(row, "G").setCellValue(tmpBean.getBillno());
                ExcelUtil.getCell(row, "H").setCellValue(tmpBean.getPayBillno());
                ExcelUtil.getCell(row, "I").setCellValue(tmpBean.getRemarks());
            }
            return workbook;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private HSSFWorkbook getSystemExcel(final List<UserRecharge> list) {
        try {
            final File file = ResourceUtils.getFile("classpath:config/template/recharge-system.xls");
            final HSSFWorkbook workbook = ExcelUtil.getInstance().read(file);
            final HSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 0, j = list.size(); i < j; ++i) {
                final UserRecharge tmpBean = list.get(i);
                final UserVO tmpUser = this.lotteryDataFactory.getUser(tmpBean.getUserId());
                HSSFRow row = sheet.getRow(i + 1);
                if (row == null) {
                    row = sheet.createRow(i + 1);
                }
                if (tmpUser != null) {
                    ExcelUtil.getCell(row, "A").setCellValue(tmpUser.getUsername());
                }
                ExcelUtil.getCell(row, "B").setCellValue("充值未到账");
                ExcelUtil.getCell(row, "C").setCellValue(tmpBean.getMoney());
                ExcelUtil.getCell(row, "D").setCellValue(tmpBean.getPayTime());
                ExcelUtil.getCell(row, "E").setCellValue(tmpBean.getBillno());
                ExcelUtil.getCell(row, "F").setCellValue(tmpBean.getRemarks());
            }
            return workbook;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private HSSFWorkbook addOtherRecharge(final HSSFWorkbook workbook, final List<UserBill> list) {
        try {
            final HSSFSheet sheet = workbook.getSheetAt(0);
            final int lastRow = ExcelUtil.getRowNum(sheet);
            for (int i = 0, j = list.size(); i < j; ++i) {
                final UserBill tmpBean = list.get(i);
                final UserVO tmpUser = this.lotteryDataFactory.getUser(tmpBean.getUserId());
                HSSFRow row = sheet.getRow(i + lastRow);
                if (row == null) {
                    row = sheet.createRow(i + lastRow);
                }
                if (tmpUser != null) {
                    ExcelUtil.getCell(row, "A").setCellValue(tmpUser.getUsername());
                }
                if (tmpBean.getType() == 5) {
                    ExcelUtil.getCell(row, "B").setCellValue("活动补贴");
                }
                if (tmpBean.getType() == 13) {
                    ExcelUtil.getCell(row, "B").setCellValue("管理员增");
                }
                if (tmpBean.getType() == 14) {
                    ExcelUtil.getCell(row, "B").setCellValue("管理员减");
                }
                ExcelUtil.getCell(row, "C").setCellValue(tmpBean.getMoney());
                ExcelUtil.getCell(row, "D").setCellValue(tmpBean.getTime());
                ExcelUtil.getCell(row, "E").setCellValue(tmpBean.getBillno());
                ExcelUtil.getCell(row, "F").setCellValue(tmpBean.getRemarks());
            }
            return workbook;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private HSSFWorkbook getWithdrawExcel(final List<UserWithdraw> list) {
        try {
            final File file = ResourceUtils.getFile("classpath:config/template/withdraw.xls");
            final HSSFWorkbook workbook = ExcelUtil.getInstance().read(file);
            final HSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 0, j = list.size(); i < j; ++i) {
                final UserWithdraw tmpBean = list.get(i);
                final UserVO tmpUser = this.lotteryDataFactory.getUser(tmpBean.getUserId());
                HSSFRow row = sheet.getRow(i + 1);
                if (row == null) {
                    row = sheet.createRow(i + 1);
                }
                ExcelUtil.getCell(row, "A").setCellValue((double)tmpBean.getId());
                if (tmpUser != null) {
                    ExcelUtil.getCell(row, "B").setCellValue(tmpUser.getUsername());
                }
                ExcelUtil.getCell(row, "C").setCellValue(tmpBean.getBeforeMoney());
                ExcelUtil.getCell(row, "D").setCellValue(tmpBean.getMoney());
                ExcelUtil.getCell(row, "E").setCellValue(tmpBean.getRecMoney());
                ExcelUtil.getCell(row, "F").setCellValue(tmpBean.getAfterMoney());
                ExcelUtil.getCell(row, "G").setCellValue(tmpBean.getFeeMoney());
                ExcelUtil.getCell(row, "H").setCellValue(tmpBean.getBillno());
                ExcelUtil.getCell(row, "I").setCellValue(tmpBean.getPayBillno());
                ExcelUtil.getCell(row, "J").setCellValue(tmpBean.getTime());
                ExcelUtil.getCell(row, "K").setCellValue(tmpBean.getOperatorTime());
                ExcelUtil.getCell(row, "L").setCellValue(tmpBean.getOperatorUser());
                ExcelUtil.getCell(row, "M").setCellValue("已完成");
                ExcelUtil.getCell(row, "N").setCellValue(tmpBean.getRemarks());
            }
            return workbook;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private HSSFWorkbook getRechargeExcel(final List<UserRecharge> list) {
        try {
            final File file = ResourceUtils.getFile("classpath:config/template/recharge.xls");
            final HSSFWorkbook workbook = ExcelUtil.getInstance().read(file);
            final HSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 0, j = list.size(); i < j; ++i) {
                final UserRecharge tmpBean = list.get(i);
                final UserVO tmpUser = this.lotteryDataFactory.getUser(tmpBean.getUserId());
                HSSFRow row = sheet.getRow(i + 1);
                if (row == null) {
                    row = sheet.createRow(i + 1);
                }
                ExcelUtil.getCell(row, "A").setCellValue((double)tmpBean.getId());
                if (tmpUser != null) {
                    ExcelUtil.getCell(row, "B").setCellValue(tmpUser.getUsername());
                }
                ExcelUtil.getCell(row, "C").setCellValue(tmpBean.getMoney());
                String channelName = "";
                if (tmpBean.getChannelId() != null) {
                    final PaymentChannelVO paymentChannel = this.lotteryDataFactory.getPaymentChannelVO(tmpBean.getChannelId());
                    if (paymentChannel != null) {
                        channelName = paymentChannel.getName();
                    }
                }
                else {
                    channelName = PaymentConstant.formatPaymentChannelType(tmpBean.getType(), tmpBean.getSubtype());
                }
                ExcelUtil.getCell(row, "D").setCellValue(channelName);
                ExcelUtil.getCell(row, "E").setCellValue(tmpBean.getTime());
                ExcelUtil.getCell(row, "F").setCellValue(tmpBean.getPayTime());
                ExcelUtil.getCell(row, "G").setCellValue("已完成");
                ExcelUtil.getCell(row, "H").setCellValue(tmpBean.getBillno());
                ExcelUtil.getCell(row, "I").setCellValue(tmpBean.getPayBillno());
                ExcelUtil.getCell(row, "J").setCellValue(tmpBean.getRemarks());
            }
            return workbook;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void out(final HttpServletResponse response, final HSSFWorkbook workbook, final String filename) {
        OutputStream os = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
            os = (OutputStream)response.getOutputStream();
            workbook.write(os);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
                response.flushBuffer();
            }
            catch (Exception ex) {}
        }
        try {
            if (os != null) {
                os.flush();
                os.close();
            }
            response.flushBuffer();
        }
        catch (Exception ex2) {}
    }
    
    @RequestMapping(value = { "/lottery-platform-bill/download" }, method = { RequestMethod.GET })
    @ResponseBody
    public void LOTTERY_PLATFORM_BILL_DOWNLOAD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final String action = HttpUtil.getStringParameterTrim(request, "action");
            final String sDate = HttpUtil.getStringParameterTrim(request, "sDate");
            final String eDate = HttpUtil.getStringParameterTrim(request, "eDate");
            if (!StringUtil.isNotNull(sDate) || !StringUtil.isNotNull(eDate)) {
                return;
            }
            if (!StringUtil.isNotNull(action)) {
                return;
            }
            if ("recharge".equals(action)) {
                final List<UserRecharge> userRecharges = this.uRechargeService.listByPayTimeAndStatus(sDate, eDate, 1);
                final HSSFWorkbook workbook = this.getRechargeExcel(userRecharges);
                this.out(response, workbook, "recharge-" + sDate + ".xls");
            }
            if ("withdraw".equals(action)) {
                final List<UserWithdraw> list = this.uWithdrawDao.listByOperatorTime(sDate, eDate);
                final HSSFWorkbook workbook = this.getWithdrawExcel(list);
                this.out(response, workbook, "withdraw-" + sDate + ".xls");
            }
        }
        else {
            json.set(2, "2-6");
        }
    }
}
