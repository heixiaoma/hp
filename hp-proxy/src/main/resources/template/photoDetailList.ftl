<#include "/header.ftl">
<#--监控页面-->
<div style="padding: 1rem">
    <div class="mdui-card">
        <#if (dataSize>0)>
            <div class="mdui-card-actions mdui-card-actions-stacked">
                <table class="mdui-table">
                    <thead>
                    <tr>
                        <th>图片</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list data as key>
                        <tr>
                            <td>
                                <img style="height: 100px" src="/photoDetail/${time}/${key}"/>
                            </td>
                            <td><a href="/photoRemove/${time}/${key}">删除</a></td>
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
