<#include "/header.ftl">
<#--监控页面-->
<div style="padding: 1rem">
    <a href="/clearAll?token=${token}">清除所有</a>
    <div class="mdui-card">
        <#if (dataSize>0)>
            <div class="mdui-card-actions mdui-card-actions-stacked">
                <table class="mdui-table">
                    <thead>
                    <tr>
                        <th>Ip</th>
                        <th>用户</th>
                        <th>错误次数</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    <#list data?keys as key>
                        <tr>
                            <td>
                                ${key}
                            </td>
                            <td>
                              ${data[key].username}
                            </td>
                            <td>
                              ${data[key].count}
                            </td>
                            <td><a href="/clearBack?ip=${key}&token=${token}">清除记录</a></td>
                        </tr>

                    </#list>
                    </tbody>
                </table>
            </div>
        </#if>

    </div>
</div>
</body>
</html>
