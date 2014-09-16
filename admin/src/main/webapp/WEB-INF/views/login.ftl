<!DOCTYPE html>
<html>
<head>
    <title>
        窗帘之家
    </title>
    <link href="${base}/stylesheets/bootstrap.min.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${base}/stylesheets/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${base}/stylesheets/se7en-font.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${base}/stylesheets/style.css" media="all" rel="stylesheet" type="text/css"/>
    <script src="${base}/javascripts/jquery.min.js" type="text/javascript"></script>
    <script src="${base}/javascripts/jquery-ui.js" type="text/javascript"></script>
    <script src="${base}/javascripts/bootstrap.min.js" type="text/javascript"></script>
    <script src="${base}/javascripts/raphael.min.js" type="text/javascript"></script>
    <script src="${base}/javascripts/jquery.mousewheel.js" type="text/javascript"></script>
    <script src="${base}/javascripts/jquery.vmap.min.js" type="text/javascript"></script>
    <script src="${base}/javascripts/jquery.vmap.sampledata.js" type="text/javascript"></script>
    <script src="${base}/javascripts/jquery.vmap.world.js" type="text/javascript"></script>
    <script src="${base}/javascripts/jquery.bootstrap.wizard.js" type="text/javascript"></script>
    <script src="${base}/javascripts/fullcalendar.min.js" type="text/javascript"></script>
    <script src="${base}/javascripts/gcal.js" type="text/javascript"></script>
    <script src="${base}/javascripts/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="${base}/javascripts/datatable-editable.js" type="text/javascript"></script>
    <script src="${base}/javascripts/jquery.easy-pie-chart.js" type="text/javascript"></script>
    <script src="${base}/javascripts/excanvas.min.js" type="text/javascript"></script>
    <script src="${base}/javascripts/jquery.isotope.min.js" type="text/javascript"></script>
    <script src="${base}/javascripts/masonry.min.js" type="text/javascript"></script>
    <script src="${base}/javascripts/modernizr.custom.js" type="text/javascript"></script>
    <script src="${base}/javascripts/jquery.fancybox.pack.js" type="text/javascript"></script>
    <script src="${base}/javascripts/select2.js" type="text/javascript"></script>
    <script src="${base}/javascripts/styleswitcher.js" type="text/javascript"></script>
    <script src="${base}/javascripts/wysiwyg.js" type="text/javascript"></script>
    <script src="${base}/javascripts/jquery.inputmask.min.js" type="text/javascript"></script>
    <script src="${base}/javascripts/jquery.validate.js" type="text/javascript"></script>
    <script src="${base}/javascripts/bootstrap-fileupload.js" type="text/javascript"></script>
    <script src="${base}/javascripts/bootstrap-datepicker.js" type="text/javascript"></script>
    <script src="${base}/javascripts/bootstrap-timepicker.js" type="text/javascript"></script>
    <script src="${base}/javascripts/bootstrap-colorpicker.js" type="text/javascript"></script>
    <script src="${base}/javascripts/bootstrap-switch.min.js" type="text/javascript"></script>
    <script src="${base}/javascripts/daterange-picker.js" type="text/javascript"></script>
    <script src="${base}/javascripts/date.js" type="text/javascript"></script>
    <script src="${base}/javascripts/morris.min.js" type="text/javascript"></script>
    <script src="${base}/javascripts/skycons.js" type="text/javascript"></script>
    <script src="${base}/javascripts/jquery.sparkline.min.js" type="text/javascript"></script>
    <script src="${base}/javascripts/fitvids.js" type="text/javascript"></script>
    <script src="${base}/javascripts/main.js" type="text/javascript"></script>
    <script src="${base}/javascripts/respond.js" type="text/javascript"></script>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
    <script type="text/javascript">
        $(function () {
            $('#kaptchaImage').click(function () {
                $(this).attr('src', '${base}/images/kaptcha.jpg?' + Math.floor(Math.random() * 100));
            })
        });
    </script>
</head>
<body class="login1">
<div class="login-wrapper">
    <div class="login-container">
        <a href="${base}"><img width="100" height="30" src="${base}/images/logo-login%402x.png"/></a>

        <form action="${base}/login" method="post">
            <div class="form-group">
                <input class="form-control" placeholder="邮箱/账号/手机号" type="text" required="required">
            </div>
        <#if Session.showCaptcha?exists && Session.showCaptcha >
            <div class="form-group">
                <input class="form-control" type="text" name="captcha" placeholder="验证码">
                <img id="kaptchaImage" src="${base}/images/kaptcha.jpg" title="看不清,点击刷新"/>
            </div>
        </#if>
            <div class="form-group">
                <input class="form-control" placeholder="密码" type="password" required="required">
                <input type="submit" value="&#xf054;">
            </div>
            <div class="form-options clearfix">
                <a class="pull-right" href="#">忘记密码?</a>

                <div class="text-left">
                    <label class="checkbox"><input type="checkbox"><span>下次自动登陆</span></label>
                </div>
            </div>
        </form>
        <p class="signup">
            还没有账号? <a href="${base}/signup">注册</a>
        </p>
    </div>
</div>
</body>
</html>