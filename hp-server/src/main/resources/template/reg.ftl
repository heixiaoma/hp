<#include "/header.ftl">
<#--监控页面-->
<div style="padding: 1rem">
    <form method="post" action="/admin/setTime">
        <div class="mdui-dialog-content">
            <div class="mdui-textfield">
                <label class="mdui-textfield-label">注册模式：-1免费关闭注册 0 免费注册 >0 每天24小时时间内注册(小时数)</label>
                <label>
                    <input class="mdui-textfield-input" name="time" placeholder="时间模式" type="number" value="${time}"/>
                </label>
            </div>
        </div>
        <div class="mdui-dialog-actions">
            <button type="submit" class="mdui-btn mdui-ripple">确定</button>
        </div>
    </form>
</div>
</body>
</html>