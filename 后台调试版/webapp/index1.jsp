
<!DOCTYPE html>
<html>
<head lang="en">
    

<script>
var cdnDomain = '/';
if (cdnDomain == '/' || cdnDomain == '//') {
    cdnDomain = window.location.protocol + '//' + window.location.host;
    cdnDomain += '/';
}
    var cdnVersion = '2018158888';
    var currentTheme = "white";
</script>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>用户登录</title>
    <link rel="shortcut icon" href="/static/favicon.ico" type="image/x-icon">
    <link href="static/css/login.css" rel="stylesheet">
      <style>
        .logoimg{
  text-align: center!important;
      padding: 0;
          padding-top: 15px;
}
             .logoimg>img{
  
          padding-top: 15px;
}
      </style>
</head>
<body class="login-bg">

<div class="login">
    <div class="login-box" id="loginBox" > 
        <div class="logoimg"><img src="static/picture/lg-logo.png"></div>
        <div class="login-form">
            <div class="login-form-inline">
                <div class="form-control">
                    <input type="text" class="txt" id="username" autofocus autocomplete="off" maxlength="12" placeholder="用户名" autofocus>
                </div>
            </div>
            <div class="login-form-inline">
                <div class="form-control">
                    <input type="password" class="txt" id="password" autocomplete="off" maxlength="50" placeholder="密码">
                </div>
            </div>
            <div class="login-form-inline code-inline">
                <div class="form-control">
                    <input type="text" class="txt force-digit" id="checkCode" autocomplete="off" maxlength="4" placeholder="验证码">
                </div>
                <img src="static/picture/6b54677f819d4dfbaf21667808f5215e.gif" class="imgCode" id="imgCode">
            </div>
        </div>
        <div class="form-info"><span id="demouser" class="demouser">立即试玩</span> 忘记密码？点击这里联系  <span data-command="kefu" class="kefu">在线客服</span> </div>
        <div class="form-action"><button class="btn-login" id="loginBtn">登陆</button></div>
        <div class="anniu">
            <div class="box1">
               <img class="tubiao" src="static/picture/app.png">
               <a href="javascript:;"  data-command="showAppDownload" id="showAppDownload"><p class="wenzi">APP下载</p></a>
            </div>
            <div class="box2">
                <img class="tubiao" src="static/picture/xianlu.png">
                <a href=""><p class="wenzi">线路选择</p></a>
            </div>
            <div class="box3">
                <img class="tubiao" src="static/picture/guaji.png">
                <a href=""><p class="wenzi">挂机软件</p></a>
            </div>
        </div>
        
    </div>
   
</div>


<script type="text/template" id="validate_card_tpl">
    <form novalidate onsubmit="return false;">
        <div class="popup-window">
            <div class="popup-title">系统检测到您本次登录地点与上次不一致，请完成以下验证</div>
            <div class="popup-group-static"><label class="label">上次登录：</label><span class="static-text text-elip" title="<#=lastAddress#>"><#=lastAddress#></span></div>
            <div class="popup-group-static"><label class="label">本次登录：</label><span class="static-text text-elip" title="<#=thisAddress#>"><#=thisAddress#></span></div>
            <div class="popup-group-static"><label class="label">银行卡号：</label><span class="static-text text-elip" title="<#=cardId#>"><#=cardId#></span></div>
            <div class="popup-group"><label class="label">持卡姓名：</label><input type="text" class="input-text" maxlength="30" name="cardName" id="cardName" autofocus autocomplete="off" placeholder="请输入持卡人姓名"></div>
        </div>
    </form>
</script>


<script src="static/js/jquery.min.js"></script>
<script src="static/js/jquery.cookie.js"></script>
<script src="static/js/core.js"></script>
<script src="static/js/sweet.all.min.js"></script>
<script src="static/js/layer.js"></script>
<script src="static/js/core.js"></script>

<script src="static/js/common.js"></script>
<script src="static/js/login.js"></script>
</body>
</html>