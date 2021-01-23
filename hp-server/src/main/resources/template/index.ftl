<#include "/header.ftl">
<#--监控页面-->
<div style="padding: 1rem">

    <div class="mdui-card">
        <div class="mdui-card-actions mdui-card-actions-stacked">
            <button class="mdui-btn mdui-ripple">连接使用数：${statisticsSize}</button>
        </div>
        <#if (statisticsSize>0)>
            <div class="mdui-card-actions mdui-card-actions-stacked">
                <table class="mdui-table">
                    <thead>
                    <tr>
                        <th>用户名</th>
                        <th>端口</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if statisticsData?exists>
                    <#list statisticsData?keys as key>
                        <tr>
                            <td>${statisticsData[key]}</td>
                            <td>${key}</td>
                        </tr>
                    </#list>
                    </#if>
                    </tbody>
                </table>
            </div>
        </#if>

    </div>

    <div class="mdui-table-fluid">
        <table class="mdui-table">
            <thead>
            <tr>
                <th>id</th>
                <th>用户名</th>
                <th>端口</th>
                <th>接收</th>
                <th>发送</th>
                <th>连接数</th>
                <th>数据包数</th>
                <th>时间</th>
            </tr>
            </thead>
            <tbody>
            <#if list??>
                <#list list as statistics>
                    <tr>
                        <td>${statistics.id}</td>
                        <td>${statistics.username}</td>
                        <td>${statistics.port?c}</td>
                        <td>${statistics.receive} 字节</td>
                        <td>${statistics.send} 字节</td>
                        <td>${statistics.connectNum}</td>
                        <td>${statistics.packNum}</td>
                        <td>${statistics.createTime}</td>
                        <td>
                            <a class="mdui-btn mdui-btn-raised mdui-btn-dense mdui-color-deep-orange-accent mdui-ripple"
                               href="/statistics/remove?page=${page}&id=${statistics.id}">
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
    var pageConst =${page};
    $('#box').paging({
        initPageNo: ${page}, // 初始页码
        totalPages: ${totalPage}, //总页数
        slideSpeed: 600, // 缓动速度。单位毫秒
        jump: true, //是否支持跳转
        callback: function (page) { // 回调函数
            if (pageConst != page) {
                location.href = "/?page=" + page;
            }
            console.log(page)
        }
    })

    var tab = new mdui.Tab('#example4-tab');

</script>
</body>
</html>