<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="stylesheet" href="/admin/css/mdui.min.css"/>
    <link rel="stylesheet" href="/admin/css/paging.css"/>
    <script src="/admin/js/jquery.min.js"></script>
    <script src="/admin/js/paging.js"></script>
    <script src="/admin/js/mdui.min.js"></script>
    <title>内网穿透</title>
</head>
<body class="mdui-drawer-body-left mdui-appbar-with-toolbar  mdui-theme-primary-indigo mdui-theme-accent-pink mdui-theme-layout-auto">
<header class="mdui-appbar mdui-appbar-fixed">
    <div class="mdui-toolbar mdui-color-theme">
        <span class="mdui-btn mdui-btn-icon mdui-ripple mdui-ripple-white"
              mdui-drawer="{target: '#main-drawer', swipe: true}"><i class="mdui-icon material-icons">menu</i></span>
        <a href="/admin/proxy" class="mdui-typo-headline mdui-hidden-xs">内网穿透</a>
        <div class="mdui-toolbar-spacer"></div>
    </div>
</header>
<!--菜單-->
<div class="mdui-drawer" id="main-drawer">
    <div class="mdui-list" mdui-collapse="{accordion: true}" style="margin-bottom: 76px;">
<#--        <div class="mdui-collapse-item mdui-collapse-item-open">-->
<#--            <a class="mdui-collapse-item-header mdui-list-item mdui-ripple" href="/admin">-->
<#--                <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">near_me</i>-->
<#--                <div class="mdui-list-item-content">系统监控</div>-->
<#--            </a>-->
<#--        </div>-->

        <a class="mdui-collapse-item mdui-collapse-item-open" href="/admin/proxy">
            <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                <i class="mdui-list-item-icon mdui-icon  material-icons mdui-text-color-black">layers</i>
                <div class="mdui-list-item-content">穿透集群</div>
            </div>
        </a>

        <a class="mdui-collapse-item mdui-collapse-item-open" href="/admin/user">
            <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-deep-orange">layers</i>
                <div class="mdui-list-item-content">用户管理</div>
            </div>
        </a>
        <a class="mdui-collapse-item mdui-collapse-item-open" href="/admin/log">
            <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">layers</i>
                <div class="mdui-list-item-content">用户日志</div>
            </div>
        </a>

        <a class="mdui-collapse-item mdui-collapse-item-open" href="/admin/tips">
            <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                <i class="mdui-list-item-icon mdui-icon  material-icons mdui-text-color-purple">layers</i>
                <div class="mdui-list-item-content">公告管理</div>
            </div>
        </a>
        <a class="mdui-collapse-item mdui-collapse-item-open" href="/admin/app">
            <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-green">layers</i>
                <div class="mdui-list-item-content">app管理</div>
            </div>
        </a>
    </div>
</div>
