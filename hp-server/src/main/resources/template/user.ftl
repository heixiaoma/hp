<#include "/header.ftl">
<#--监控页面-->
<div style="padding: 1rem">

    <div style="padding: .6rem">
        <button class="mdui-btn mdui-btn-raised mdui-btn-dense mdui-color-deep-purple-accent mdui-ripple"
                onclick="new mdui.Dialog('#addUser').open()">
            添加
        </button>
    </div>

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
            <#if list??>
                <#list list as userVo>
                    <tr>
                        <div class="mdui-dialog" id="${userVo.username}">
                            <form method="post" action="/user/edit?page=${page}">
                                <div class="mdui-dialog-title">编辑</div>
                                <div class="mdui-dialog-content">
                                    <div class="mdui-textfield">
                                        <input class="mdui-textfield-input" name="username" readonly type="text"
                                               value="${userVo.username}"/>
                                    </div>
                                    <div class="mdui-textfield">
                                        <input class="mdui-textfield-input" name="password" type="text"
                                               value="${userVo.password}"/>
                                    </div>
                                    <div class="mdui-textfield">
                                        <input class="mdui-textfield-input" name="ports" type="text"
                                               value="<#list userVo.ports as port>${port?c},</#list>"/>
                                    </div>
                                </div>
                                <div class="mdui-dialog-actions">
                                    <button class="mdui-btn mdui-ripple" mdui-dialog-cancel>取消</button>
                                    <button type="submit" class="mdui-btn mdui-ripple">确定</button>
                                </div>
                            </form>
                        </div>

                        <td>${userVo.username}</td>
                        <td>${userVo.password}</td>
                        <td>
                            <#list userVo.ports as port>
                                ${port?c} &nbsp;
                            </#list>
                        </td>
                        <td>${userVo.createTime}</td>
                        <td>
                            <button class="mdui-btn mdui-btn-raised mdui-btn-dense mdui-color-deep-purple-accent mdui-ripple"
                                    onclick="new mdui.Dialog('#${userVo.username}').open()">
                                编辑
                            </button>
                            <a class="mdui-btn mdui-btn-raised mdui-btn-dense mdui-color-deep-orange-accent mdui-ripple"
                               href="/user/remove?page=${page}&username=${userVo.username}">
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

    <div class="mdui-dialog" id="addUser">
        <form method="post" action="/user/add?page=${page}">
            <div class="mdui-dialog-content">
                <div class="mdui-textfield">
                    <input class="mdui-textfield-input" name="username" placeholder="用户名" type="text"/>
                </div>
                <div class="mdui-textfield">
                    <input class="mdui-textfield-input" name="password" placeholder="密码" type="text"/>
                </div>
                <div class="mdui-textfield">
                    <input class="mdui-textfield-input" name="ports" placeholder="端口号 逗号分隔" type="text"/>
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
    var pageConst =${page};
    $('#box').paging({
        initPageNo: ${page}, // 初始页码
        totalPages: ${totalPage}, //总页数
        slideSpeed: 600, // 缓动速度。单位毫秒
        jump: true, //是否支持跳转
        callback: function (page) { // 回调函数
            if (pageConst != page) {
                location.href = "/user?page=" + page;
            }
            console.log(page)
        }
    })


    var tab = new mdui.Tab('#example4-tab');


</script>
</body>
</html>