package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import java.io.File;
import java.util.List;
import javautils.StringUtil;
import javautils.excel.ExcelUtil;
import javautils.http.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.UserRechargeService;
import lottery.domains.content.dao.UserBillDao;
import lottery.domains.content.dao.UserWithdrawDao;
import lottery.domains.content.entity.UserBill;
import lottery.domains.content.entity.UserRecharge;
import lottery.domains.content.entity.UserWithdraw;
import lottery.domains.content.global.PaymentConstant;
import lottery.domains.content.vo.payment.PaymentChannelVO;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LotteryPlatformBillController
  extends AbstractActionController
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
  
  private HSSFWorkbook getChannelExcel(List<UserRecharge> list)
  {
    try
    {
      File file = ResourceUtils.getFile("classpath:config/template/recharge-thrid.xls");
      HSSFWorkbook workbook = ExcelUtil.getInstance().read(file);
      HSSFSheet sheet = workbook.getSheetAt(0);
      int i = 0;
      for (int j = list.size(); i < j; i++)
      {
        UserRecharge tmpBean = (UserRecharge)list.get(i);
        UserVO tmpUser = this.lotteryDataFactory.getUser(tmpBean.getUserId());
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
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  private HSSFWorkbook getTransferExcel(List<UserRecharge> list)
  {
    try
    {
      File file = ResourceUtils.getFile("classpath:config/template/recharge-transfer.xls");
      HSSFWorkbook workbook = ExcelUtil.getInstance().read(file);
      HSSFSheet sheet = workbook.getSheetAt(0);
      int i = 0;
      for (int j = list.size(); i < j; i++)
      {
        UserRecharge tmpBean = (UserRecharge)list.get(i);
        UserVO tmpUser = this.lotteryDataFactory.getUser(tmpBean.getUserId());
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
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  private HSSFWorkbook getSystemExcel(List<UserRecharge> list)
  {
    try
    {
      File file = ResourceUtils.getFile("classpath:config/template/recharge-system.xls");
      HSSFWorkbook workbook = ExcelUtil.getInstance().read(file);
      HSSFSheet sheet = workbook.getSheetAt(0);
      int i = 0;
      for (int j = list.size(); i < j; i++)
      {
        UserRecharge tmpBean = (UserRecharge)list.get(i);
        UserVO tmpUser = this.lotteryDataFactory.getUser(tmpBean.getUserId());
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
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  private HSSFWorkbook addOtherRecharge(HSSFWorkbook workbook, List<UserBill> list)
  {
    try
    {
      HSSFSheet sheet = workbook.getSheetAt(0);
      int lastRow = ExcelUtil.getRowNum(sheet);
      int i = 0;
      for (int j = list.size(); i < j; i++)
      {
        UserBill tmpBean = (UserBill)list.get(i);
        UserVO tmpUser = this.lotteryDataFactory.getUser(tmpBean.getUserId());
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
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  private HSSFWorkbook getWithdrawExcel(List<UserWithdraw> list)
  {
    try
    {
      File file = ResourceUtils.getFile("classpath:config/template/withdraw.xls");
      HSSFWorkbook workbook = ExcelUtil.getInstance().read(file);
      HSSFSheet sheet = workbook.getSheetAt(0);
      int i = 0;
      for (int j = list.size(); i < j; i++)
      {
        UserWithdraw tmpBean = (UserWithdraw)list.get(i);
        UserVO tmpUser = this.lotteryDataFactory.getUser(tmpBean.getUserId());
        HSSFRow row = sheet.getRow(i + 1);
        if (row == null) {
          row = sheet.createRow(i + 1);
        }
        ExcelUtil.getCell(row, "A").setCellValue(tmpBean.getId());
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
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  private HSSFWorkbook getRechargeExcel(List<UserRecharge> list)
  {
    try
    {
      File file = ResourceUtils.getFile("classpath:config/template/recharge.xls");
      HSSFWorkbook workbook = ExcelUtil.getInstance().read(file);
      HSSFSheet sheet = workbook.getSheetAt(0);
      int i = 0;
      for (int j = list.size(); i < j; i++)
      {
        UserRecharge tmpBean = (UserRecharge)list.get(i);
        UserVO tmpUser = this.lotteryDataFactory.getUser(tmpBean.getUserId());
        HSSFRow row = sheet.getRow(i + 1);
        if (row == null) {
          row = sheet.createRow(i + 1);
        }
        ExcelUtil.getCell(row, "A").setCellValue(tmpBean.getId());
        if (tmpUser != null) {
          ExcelUtil.getCell(row, "B").setCellValue(tmpUser.getUsername());
        }
        ExcelUtil.getCell(row, "C").setCellValue(tmpBean.getMoney());
        
        String channelName = "";
        if (tmpBean.getChannelId() != null)
        {
          PaymentChannelVO paymentChannel = this.lotteryDataFactory.getPaymentChannelVO(tmpBean.getChannelId().intValue());
          if (paymentChannel != null) {
            channelName = paymentChannel.getName();
          }
        }
        else
        {
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
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  /* Error */
  private void out(HttpServletResponse response, HSSFWorkbook workbook, String filename)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aload_1
    //   4: ldc_w 315
    //   7: invokeinterface 317 2 0
    //   12: aload_1
    //   13: ldc_w 322
    //   16: invokeinterface 324 2 0
    //   21: aload_1
    //   22: ldc_w 327
    //   25: new 329	java/lang/StringBuilder
    //   28: dup
    //   29: ldc_w 331
    //   32: invokespecial 333	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   35: aload_3
    //   36: invokevirtual 335	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   39: ldc_w 339
    //   42: invokevirtual 335	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: invokevirtual 341	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   48: invokeinterface 344 3 0
    //   53: aload_1
    //   54: invokeinterface 348 1 0
    //   59: astore 4
    //   61: aload_2
    //   62: aload 4
    //   64: invokevirtual 352	org/apache/poi/hssf/usermodel/HSSFWorkbook:write	(Ljava/io/OutputStream;)V
    //   67: goto +70 -> 137
    //   70: astore 5
    //   72: aload 5
    //   74: invokevirtual 160	java/lang/Exception:printStackTrace	()V
    //   77: aload 4
    //   79: ifnull +13 -> 92
    //   82: aload 4
    //   84: invokevirtual 356	java/io/OutputStream:flush	()V
    //   87: aload 4
    //   89: invokevirtual 361	java/io/OutputStream:close	()V
    //   92: aload_1
    //   93: invokeinterface 364 1 0
    //   98: goto +65 -> 163
    //   101: astore 7
    //   103: goto +60 -> 163
    //   106: astore 6
    //   108: aload 4
    //   110: ifnull +13 -> 123
    //   113: aload 4
    //   115: invokevirtual 356	java/io/OutputStream:flush	()V
    //   118: aload 4
    //   120: invokevirtual 361	java/io/OutputStream:close	()V
    //   123: aload_1
    //   124: invokeinterface 364 1 0
    //   129: goto +5 -> 134
    //   132: astore 7
    //   134: aload 6
    //   136: athrow
    //   137: aload 4
    //   139: ifnull +13 -> 152
    //   142: aload 4
    //   144: invokevirtual 356	java/io/OutputStream:flush	()V
    //   147: aload 4
    //   149: invokevirtual 361	java/io/OutputStream:close	()V
    //   152: aload_1
    //   153: invokeinterface 364 1 0
    //   158: goto +5 -> 163
    //   161: astore 7
    //   163: return
    // Line number table:
    //   Java source line #342	-> byte code offset #0
    //   Java source line #344	-> byte code offset #3
    //   Java source line #345	-> byte code offset #12
    //   Java source line #346	-> byte code offset #21
    //   Java source line #347	-> byte code offset #53
    //   Java source line #348	-> byte code offset #61
    //   Java source line #349	-> byte code offset #67
    //   Java source line #350	-> byte code offset #72
    //   Java source line #353	-> byte code offset #77
    //   Java source line #354	-> byte code offset #82
    //   Java source line #355	-> byte code offset #87
    //   Java source line #357	-> byte code offset #92
    //   Java source line #358	-> byte code offset #98
    //   Java source line #351	-> byte code offset #106
    //   Java source line #353	-> byte code offset #108
    //   Java source line #354	-> byte code offset #113
    //   Java source line #355	-> byte code offset #118
    //   Java source line #357	-> byte code offset #123
    //   Java source line #358	-> byte code offset #129
    //   Java source line #360	-> byte code offset #134
    //   Java source line #353	-> byte code offset #137
    //   Java source line #354	-> byte code offset #142
    //   Java source line #355	-> byte code offset #147
    //   Java source line #357	-> byte code offset #152
    //   Java source line #358	-> byte code offset #158
    //   Java source line #361	-> byte code offset #163
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	164	0	this	LotteryPlatformBillController
    //   0	164	1	response	HttpServletResponse
    //   0	164	2	workbook	HSSFWorkbook
    //   0	164	3	filename	String
    //   1	147	4	os	java.io.OutputStream
    //   70	3	5	e	Exception
    //   106	29	6	localObject	Object
    //   101	1	7	localException1	Exception
    //   132	1	7	localException2	Exception
    //   161	1	7	localException3	Exception
    // Exception table:
    //   from	to	target	type
    //   3	67	70	java/lang/Exception
    //   77	98	101	java/lang/Exception
    //   3	77	106	finally
    //   108	129	132	java/lang/Exception
    //   137	158	161	java/lang/Exception
  }
  
  @RequestMapping(value={"/lottery-platform-bill/download"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public void LOTTERY_PLATFORM_BILL_DOWNLOAD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      String action = HttpUtil.getStringParameterTrim(request, "action");
      String sDate = HttpUtil.getStringParameterTrim(request, "sDate");
      String eDate = HttpUtil.getStringParameterTrim(request, "eDate");
      if ((!StringUtil.isNotNull(sDate)) || (!StringUtil.isNotNull(eDate))) {
        return;
      }
      if (!StringUtil.isNotNull(action)) {
        return;
      }
      if ("recharge".equals(action))
      {
        List<UserRecharge> userRecharges = this.uRechargeService.listByPayTimeAndStatus(sDate, eDate, 1);
        HSSFWorkbook workbook = getRechargeExcel(userRecharges);
        out(response, workbook, "recharge-" + sDate + ".xls");
      }
      if ("withdraw".equals(action))
      {
        List<UserWithdraw> list = this.uWithdrawDao.listByOperatorTime(sDate, eDate);
        HSSFWorkbook workbook = getWithdrawExcel(list);
        out(response, workbook, "withdraw-" + sDate + ".xls");
      }
    }
    else
    {
      json.set(Integer.valueOf(2), "2-6");
    }
  }
}
