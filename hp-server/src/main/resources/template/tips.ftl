<#include "/header.ftl">
<#--监控页面-->
<div style="padding: 1rem">
    <form method="post" action="/admin/setTips">
        <div class="mdui-dialog-content">
            <div class="mdui-textfield">
                <input class="mdui-textfield-input" name="tips" placeholder="公告内容" type="text" value=" ${tips}"/>
            </div>
        </div>
        <div class="mdui-dialog-actions">
            <button type="submit" class="mdui-btn mdui-ripple">确定</button>
        </div>
    </form>
</div>
</body>
</html>