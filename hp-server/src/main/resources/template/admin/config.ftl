<#include "./header.ftl">
<#--监控页面-->
<div style="padding: 1rem">
    <div class="mdui-table-fluid">
        <table class="mdui-table">
            <thead>
            <tr>
                <th>id</th>
                <th>用户名</th>
                <th>用户内网</th>
                <th>外网服务</th>
                <th>类型</th>
                <th>域名</th>
                <th>端口</th>
                <th>设备ID</th>
                <th>时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#if list??>
                <#list list as statistics>
                    <tr>
                        <td>${statistics.id}</td>
                        <td>${statistics.username}</td>
                        <td>${statistics.userHost}</td>
                        <td>${statistics.serverHost}</td>
                        <td>${statistics.type}</td>
                        <td>${statistics.domain}</td>
                        <td>
                            <#if statistics.port??> ${statistics.port}<#else>无端口</#if>.
                        </td>
                        <td>${statistics.deviceId}</td>
                        <td>${statistics.createTime}</td>
                        <td>
                            <a class="mdui-btn mdui-btn-raised mdui-btn-dense mdui-color-deep-orange-accent mdui-ripple"
                               href="/admin/config/remove?page=${page}&id=${statistics.id}">
                                删除
                            </a>
                        </td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
    <div class="pagger-box pagger" id="box"></div>


</div>


<script>
    var pageConst = ${page?c};
    $('#box').paging({
        initPageNo: ${page?c}, // 初始页码
        totalPages: ${totalPage?c}, //总页数
        slideSpeed: 600, // 缓动速度。单位毫秒
        jump: true, //是否支持跳转
        callback: function (page) { // 回调函数
            if (pageConst != page) {
                location.href = "/admin/config?page=" + page;
            }
            console.log(page)
        }
    })

    var tab = new mdui.Tab('#example4-tab');

</script>
</body>
</html>
