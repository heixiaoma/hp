<#include "./header_index.ftl">
<#--监控页面-->

<style>

    #cards {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
    }

    #cards .mdui-card {
        width: 30%;
        margin: 1px;
    }

    @media only screen and (max-width: 767px){
        #cards .mdui-card {
            width: 40%;
            margin: 1px;
        }
    }

    #cards .mdui-card::after {
        content: '';
        flex: auto; /* 或者flex: 1 */
    }
</style>

<div style="padding: 1rem">
    <h1 class="title mdui-text-color-theme">可用穿透服务列表</h1>
    <div id="cards">
        <#if list??>
            <#list list as server>
                <div class="mdui-card">
                    <div class="mdui-card-primary">
                        <div class="mdui-card-primary-title">${server.name}</div>
                        <div class="mdui-card-primary-subtitle">${server.ip}:${server.port}</div>
                    </div>
                    <div class="mdui-card-actions">
                        <div class="mdui-card-primary-subtitle">连接数：${server.num}</div>
                    </div>
                </div>
            </#list>
        </#if>
    </div>
</div>
</body>
</html>