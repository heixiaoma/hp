<#include "./header.ftl">
<#--监控页面-->
<div style="padding: 1rem">

    <div style="padding: .6rem">
        <button class="mdui-btn mdui-btn-raised mdui-btn-dense mdui-color-deep-purple-accent mdui-ripple"
                onclick="new mdui.Dialog('#add').open()">
            添加版本
        </button>

        <form method="post" action="/admin/app/upload?page=${page}" style="margin-top: 10px" enctype="multipart/form-data">
            <button class="mdui-btn mdui-btn-raised mdui-btn-dense mdui-color-deep-purple-accent mdui-ripple"
                    onclick="">
                上传最新APK
            </button>
            <input name="apk" type="file" />
        </form>
    </div>

    <div class="mdui-table-fluid">
        <table class="mdui-table">
            <thead>
            <tr>
                <th>id</th>
                <th>版本号</th>
                <th>更新内容</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#if list??>
                <#list list as app>
                    <tr>
                        <td>${app.id}</td>
                        <td>${app.versionCode}</td>
                        <td>${app.updateContent}</td>
                        <td>${app.createTime}</td>
                        <td>
                            <a class="mdui-btn mdui-btn-raised mdui-btn-dense mdui-color-deep-orange-accent mdui-ripple"
                               href="/admin/app/remove?page=${page}&id=${app.id}">
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
        <form method="post" action="/admin/app/add?page=${page}">
            <div class="mdui-dialog-content">
                <div class="mdui-textfield">
                    <input class="mdui-textfield-input" name="versionCode" placeholder="版本号" type="text"/>
                </div>
                <div class="mdui-textfield">
                    <input class="mdui-textfield-input" name="updateContent" placeholder="更新类容" type="text"/>
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
                location.href = "/admin/app?page=" + page;
            }
            console.log(page)
        }
    })

    var tab = new mdui.Tab('#example4-tab');

</script>
</body>
</html>