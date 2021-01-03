<#include "/header.ftl">
<#--监控页面-->
<div style="padding: 1rem">
    <div class="mdui-table-fluid">
        <table class="mdui-table">
            <thead>
            <tr>
                <th>用户名</th>
                <th>密码</th>
                <th>开通端口</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#list list as userVo>
            <tr>
                <td>${userVo.username}</td>
                <td>${userVo.password}</td>
                <td>
                    <#list userVo.ports as port>
                        ${port?c} &nbsp;
                    </#list>
                </td>
                <td>${userVo.createTime}</td>
                <td>
                    <button class="mdui-btn mdui-btn-raised mdui-btn-dense mdui-color-deep-purple-accent mdui-ripple">
                        编辑
                    </button>
                    <button class="mdui-btn mdui-btn-raised mdui-btn-dense mdui-color-deep-orange-accent mdui-ripple">
                        删除
                    </button>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
    <div class="pagger-box pagger" id="box"></div>
</div>
<script>
    var pageConst=${page};
    $('#box').paging({
        initPageNo: ${page}, // 初始页码
        totalPages: ${totalPage}, //总页数
        slideSpeed: 600, // 缓动速度。单位毫秒
        jump: true, //是否支持跳转
        callback: function(page) { // 回调函数
            if (pageConst!=page){
                location.href="/user?page="+page;
            }
            console.log(page)
        }
    })
</script>
</body>
</html>