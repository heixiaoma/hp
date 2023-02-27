<#include "./header.ftl">
<#--监控页面-->
<div style="padding: 1rem">

    <div style="padding: .6rem">
        <button class="mdui-btn mdui-btn-raised mdui-btn-dense mdui-color-deep-purple-accent mdui-ripple"
                onclick="new mdui.Dialog('#add').open()">
            添加域名
        </button>
        <div style="display: flex">
            <div class="mdui-textfield" style="width: 100px">
                <label class="mdui-textfield-label">用戶名</label>
                <input class="mdui-textfield-input" id="usernameSearch" name="usernameSearch" type="text" value="${usernameSearch}"/>
            </div>
            <div style="margin-top: 35px;margin-left: 10px">
                <button class="mdui-btn mdui-btn-raised mdui-btn-dense mdui-color-deep-purple-accent mdui-ripple"
                        id="selectButton">
                    查询
                </button>
            </div>
        </div>
    </div>

    <div class="mdui-table-fluid">
        <table class="mdui-table">
            <thead>
            <tr>
                <th>id</th>
                <th>用户名</th>
                <th>二级域名</th>
                <th>自定义域名</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#if list??>
                <#list list as app>
                    <tr>
                        <td>${app.id}</td>
                        <td>${app.username}</td>
                        <td>${app.domain}</td>
                        <td>${app.customDomain}</td>
                        <td>${app.createTime}</td>
                        <td>
                            <a class="mdui-btn mdui-btn-raised mdui-btn-dense mdui-color-deep-orange-accent mdui-ripple"
                               href="/admin/domain/remove?page=${page}&id=${app.id}&usernameSearch=${usernameSearch}">
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

    <#--  添加  -->

    <div class="mdui-dialog" id="add">
        <form method="post" action="/admin/domain/add?page=${page}&usernameSearch=${usernameSearch}">
            <div class="mdui-dialog-content">
                <div class="mdui-textfield">
                    <input class="mdui-textfield-input" name="username" placeholder="用户名" type="text"/>
                </div>
                <div class="mdui-textfield">
                    <input class="mdui-textfield-input" name="domain" placeholder="二级域名" type="text"/>
                </div>
                <div class="mdui-textfield">
                    <input class="mdui-textfield-input" name="customDomain" placeholder="自定义域名" type="text"/>
                </div>
            </div>
            <div class="mdui-dialog-actions">
                <button class="mdui-btn mdui-ripple" mdui-dialog-cancel>取消</button>
                <button type="submit" class="mdui-btn mdui-ripple">确定</button>
            </div>
        </form>
    </div>


</div>
<script>
    var pageConst =${page?c};
    $('#box').paging({
        initPageNo: ${page?c}, // 初始页码
        totalPages: ${totalPage?c}, //总页数
        slideSpeed: 600, // 缓动速度。单位毫秒
        jump: true, //是否支持跳转
        callback: function (page) { // 回调函数
            if (pageConst != page) {
                location.href = "/admin/domain?page=" + page+"&usernameSearch=" + $("#usernameSearch").val();
            }
            console.log(page)
        }
    })

    var tab = new mdui.Tab('#example4-tab');

    $("#selectButton").click(function () {
        location.href = "/admin/domain?page=1&usernameSearch=" + $("#usernameSearch").val();
    })

</script>
</body>
</html>
