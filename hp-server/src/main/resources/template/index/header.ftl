<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="stylesheet" href="/index/css/index.css"/>
    <link rel="stylesheet" href="/common/css/mdui.min.css"/>
    <link rel="stylesheet" href="/common/css/paging.css"/>
    <script src="/common/js/jquery.min.js"></script>
    <script src="/common/js/paging.js"></script>
    <script src="/common/js/mdui.min.js"></script>
    <title>内网穿透</title>
</head>
<body class="mdui-appbar-with-toolbar  mdui-theme-primary-indigo mdui-theme-accent-pink mdui-theme-layout-auto">
<header class="mdui-appbar mdui-appbar-fixed">
    <div class="mdui-toolbar mdui-color-theme">
        <div class="mdui-toolbar-spacer"></div>
        <div mdui-dialog="{target: '#login_dialog'}" class=" mdui-btn mdui-btn-dense">登录</div>
        <div mdui-dialog="{target: '#register_dialog'}" class="register mdui-btn mdui-btn-dense">注册</div>
    </div>
</header>
<!--菜單-->
<div class="mdui-dialog mc-account mc-login" id="login_dialog" style="height: auto">
    <div>
        <button id="closeLogin" mdui-dialog-close="{target: '#login_dialog'}" class="mdui-btn mdui-btn-icon close"><i
                    class="mdui-icon material-icons">close</i></button>
        <div class="mdui-dialog-title">登录</div>
    </div>
    <form>
        <div class="mdui-textfield mdui-textfield-floating-label mdui-textfield-has-bottom mdui-textfield-invalid-html5">
            <label class="mdui-textfield-label">账号</label><input id="username" class="mdui-textfield-input" name="name"
                                                                 type="text" required="">
            <div class="mdui-textfield-error">账号不能为空</div>
        </div>
        <div class="mdui-textfield mdui-textfield-floating-label mdui-textfield-has-bottom"><label
                    class="mdui-textfield-label">密码</label><input id="password" class="mdui-textfield-input"
                                                                  name="password"
                                                                  type="password" required="">
            <div class="mdui-textfield-error">密码不能为空</div>
        </div>
        <div class="actions mdui-clearfix">
            <button type="button" id="login_btn" class="mdui-btn mdui-btn-raised mdui-color-theme action-btn">登录
            </button>
        </div>
    </form>
</div>

<div class="mc-account mc-login mdui-dialog" id="register_dialog" style="height: auto">
    <div>
        <button id="closeReg" mdui-dialog-close="{target: '#register_dialog'}" class="mdui-btn mdui-btn-icon close"><i
                    class="mdui-icon material-icons">close</i></button>
        <div class="mdui-dialog-title">创建新账号</div>
    </div>
    <form class="">
        <div class="mdui-textfield mdui-textfield-floating-label mdui-textfield-has-bottom"><label
                    class="mdui-textfield-label">用户名(也是你的二级域名名字)</label><input id="reg_username" class="mdui-textfield-input"
                                                                               name="username"
                                                                               type="text" required="">
            <div class="mdui-textfield-error">用户名不能为空</div>
        </div>
        <div class="mdui-textfield mdui-textfield-floating-label mdui-textfield-has-bottom"><label
                    class="mdui-textfield-label">密码</label><input id="reg_password" class="mdui-textfield-input" name="password"
                                                                  type="password" required="">
            <div class="mdui-textfield-error">密码不能为空</div>
        </div>
        <div class="actions mdui-clearfix">
            <button id="reg_btn" type="button" class="mdui-btn mdui-btn-raised mdui-color-theme action-btn">注册并登录</button>
        </div>
    </form>
</div>


<script>
    $(function () {
        $("#login_btn").click(function () {
            let val = $("#username").val();
            let val1 = $("#password").val();
            if (val && val1) {
                $.post("/user/login", {username: val, password: val1}, function (result) {
                    if (result.code === 200) {
                        document.cookie = "authUser="+val+"|"+val1
                        location.href = "/index/index";
                    } else {
                        $("#closeLogin").click()
                        mdui.alert(result.msg);
                    }
                });
            }
        })


        $("#reg_btn").click(function () {
            let val = $("#reg_username").val();
            let val1 = $("#reg_password").val();
            if (val && val1) {
                $.post("/user/reg", {username: val, password: val1}, function (result) {
                    if (result.code === 200) {
                        document.cookie = "authUser="+val+"|"+val1
                        location.href = "/index/index";
                    } else {
                        $("#closeReg").click()
                        mdui.alert(result.msg);
                    }
                });
            }
        })

    })


</script>