<#include "./header.ftl">
<#--监控页面-->
<div style="padding: 1rem">
    <div style="padding: 1rem">通用注册验证码：${reg_code} (一小时变化一次)</div>
    <div class="mdui-table-fluid">
        <table class="mdui-table">
            <thead>
            <tr>
                <th>名字</th>
                <th>主机地址</th>
                <th>端口</th>
                <th>连接数量</th>
                <th>用户级别限定</th>
                <th>查看统计</th>
            </tr>
            </thead>
            <tbody>
            <#if list??>
                <#list list as app>
                    <tr>
                        <td>${app.name}</td>
                        <td>${app.ip}</td>
                        <td>${app.port}</td>
                        <td>${app.num}</td>
                        <td>${app.level}</td>
                        <td>
                            <a target="_blank" href="http://${app.ip}/statistics?token=${token}">数据分析</a>
                            <a target="_blank" href="http://${app.ip}/backList?token=${token}">黑名单</a>
                            <a target="_blank" href="http://${app.ip}/photoList?token=${token}">图片过滤</a>
                        </td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>

</div>
</body>
</html>
