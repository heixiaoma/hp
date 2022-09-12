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
        <div id="login" class=" mdui-btn mdui-btn-dense">登录</div>
        <div class="register mdui-btn mdui-btn-dense">注册</div>
    </div>
</header>
<!--菜單-->
<div class="mdui-dialog" id="login_dialog">
   <div>aaa</div>
</div>

<script>
    var tab = new mdui.Tab('#login');
    document.getElementById('login_dialog').addEventListener('open.mdui.dialog', function () {
        console.log("----------")
        tab.handleUpdate();
    });
</script>
