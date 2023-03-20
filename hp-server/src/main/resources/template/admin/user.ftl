<#include "./header.ftl">
<#--监控页面-->
<div style="padding: 1rem">

    <div style="padding: .6rem">
        <button class="mdui-btn mdui-btn-raised mdui-btn-dense mdui-color-deep-purple-accent mdui-ripple"
                onclick="new mdui.Dialog('#addUser').open()">
            添加
        </button>
        <div style="display: flex">
            <div class="mdui-textfield" style="width: 100px">
                <label class="mdui-textfield-label">用戶名</label>
                <input class="mdui-textfield-input" id="username" name="username" type="text" value="${username}"/>
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
                <th>用户名</th>
                <th>密码</th>
                <th>开通端口</th>
                <th>用户级别</th>
                <th>最近登录IP</th>
                <th>IP来源</th>
                <th>最近登录时间</th>
                <th>创建时间</th>
                <th>账号类型</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#if list??>
                <#list list as userVo>
                    <tr>
                        <div class="mdui-dialog" id="${userVo.username}">
                            <form method="post" action="/admin/user/edit?page=${page}">
                                <div class="mdui-dialog-title">编辑</div>
                                <div class="mdui-dialog-content">
                                    <div class="mdui-textfield">
                                        <label class="mdui-textfield-label">用户名</label>
                                        <input class="mdui-textfield-input" name="username" readonly type="text"
                                               value="${userVo.username}"/>
                                    </div>
                                    <div class="mdui-textfield">
                                        <label class="mdui-textfield-label">密码</label>
                                        <input class="mdui-textfield-input" name="password" type="text"
                                               value="${userVo.password}"/>
                                    </div>
                                    <div class="mdui-textfield">
                                        <label class="mdui-textfield-label">类型：-1(封号) 1(正常) 2(待审核，目前也是正常后期放开)</label>
                                        <input class="mdui-textfield-input" name="type" type="text"
                                               value="${userVo.type}"/>
                                    </div>

                                    <div class="mdui-textfield">
                                        <label class="mdui-textfield-label">TCP端口号固定 多个逗号隔开</label>
                                        <input class="mdui-textfield-input" name="ports" type="text"
                                               value="<#list userVo.ports as port>${port?c},</#list>"/>
                                    </div>
                                    <div class="mdui-textfield">
                                        <label class="mdui-textfield-label">用户级别</label>
                                        <input class="mdui-textfield-input" name="level" type="text"
                                               value="${userVo.level?c}"/>
                                    </div>
                                </div>
                                <div class="mdui-dialog-actions">
                                    <div class="mdui-btn mdui-ripple" mdui-dialog-cancel>取消</div>
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
                        <td>${userVo.level?c}</td>
                        <td>${userVo.loginIp}</td>
                        <td>${userVo.ipSource}</td>
                        <td>${userVo.loginTime}</td>
                        <td>${userVo.createTime}</td>
                        <td>
                            <#if userVo.type==-1>
                                封号(${userVo.type})
                            <#else>
                                正常(${userVo.type})
                            </#if>
                        </td>
                        <td>
                            <button class="mdui-btn mdui-btn-raised mdui-btn-dense mdui-color-deep-purple-accent mdui-ripple"
                                    onclick="new mdui.Dialog('#${userVo.username}').open()">
                                编辑
                            </button>
                            <a class="mdui-btn mdui-btn-raised mdui-btn-dense mdui-color-deep-orange-accent mdui-ripple"
                               href="/admin/user/remove?page=${page}&username=${userVo.username}">
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
        <form method="post" action="/admin/user/add?page=${page}">
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
                <div class="mdui-textfield">
                    <input class="mdui-textfield-input" name="level" placeholder="用户级别" type="text"/>
                </div>
            </div>
            <div class="mdui-dialog-actions">
                <div class="mdui-btn mdui-ripple" mdui-dialog-cancel>取消</div>
                <button type="submit" class="mdui-btn mdui-ripple">确定</button>
            </div>
        </form>
    </div>


</div>
<script>
    var pageConst = ${page?c};
    var username = "${username}";
    $('#box').paging({
        initPageNo: ${page?c}, // 初始页码
        totalPages: ${totalPage?c}, //总页数
        slideSpeed: 600, // 缓动速度。单位毫秒
        jump: true, //是否支持跳转
        callback: function (page) { // 回调函数
            if (pageConst != page) {
                location.href = "/admin/user?page=" + page + "&username=" + username;
            }
            console.log(page)
        }
    })


    var tab = new mdui.Tab('#example4-tab');


    $("#selectButton").click(function () {
        location.href = "/admin/user?page=1&username=" + $("#username").val();
    })

</script>
</body>
</html>
