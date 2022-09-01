<#include "/header.ftl">
<script type="text/javascript" src="/admin/js/echarts.min.js"></script>
<#--监控页面-->
<div style="padding: 1rem">

    <div class="mdui-card">
        <div class="mdui-card-actions mdui-card-actions-stacked">
            <button class="mdui-btn mdui-ripple">连接使用数：${statisticsSize}</button>
        </div>
        <div class="mdui-card-actions mdui-card-actions-stacked" id="chart" style="height: 800px;width: 100%">
        </div>
        <#if (statisticsSize>0)>
            <div class="mdui-card-actions mdui-card-actions-stacked">
                <table class="mdui-table">
                    <thead>
                    <tr>
                        <th>用户名</th>
                        <th>前往穿透</th>
                        <th>来源IP</th>
                        <th>连接时间</th>
                        <th>端口</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if statisticsData?exists>
                        <#list statisticsData?keys as key>
                            <tr>
                                <td>${statisticsData[key].username}</td>
                                <td><a href="//${statisticsData[key].username}.${host}" target="_blank">访问${statisticsData[key].username}</a></td>
                                <td>${statisticsData[key].ip}</td>
                                <td>${statisticsData[key].date}</td>
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


<script type="text/javascript">
    var dom = document.getElementById('chart');
    var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
    });
    var app = {};

    var option;
    const dateList = [${flowType}]
    const flowSend = [${flowSend}]
    const flowReceive = [${flowReceive}]
    option = {
        // Make gradient line here
        visualMap: [
            {
                show: false,
                type: 'continuous',
                seriesIndex: 0,
                min: 0,
                max: 400
            },
            {
                show: false,
                type: 'continuous',
                seriesIndex: 1,
                dimension: 0,
                min: 0,
                max: dateList.length - 1
            }
        ],
        title: [
            {
                left: 'center',
                text: '入网'
            },
            {
                top: '55%',
                left: 'center',
                text: '出网'
            }
        ],
        tooltip: {
            trigger: 'axis'
        },
        xAxis: [
            {
                data: dateList
            },
            {
                data: dateList,
                gridIndex: 1
            }
        ],
        yAxis: [
            {
                axisLabel: {
                    formatter: '{value}/KB'
                }
            },
            {
                gridIndex: 1,
                axisLabel: {
                    formatter: '{value}/KB'
                }
            }
        ],
        grid: [
            {
                bottom: '60%'
            },
            {
                top: '60%'
            }
        ],
        series: [
            {
                type: 'line',
                showSymbol: false,
                data: flowSend,
                smooth: true,
                name: '入网',
                stack: 'Total',
            },
            {
                showSymbol: false,
                type: 'line',
                smooth: true,
                name: '出网',
                stack: 'Total',
                data: flowReceive,
                xAxisIndex: 1,
                yAxisIndex: 1
            }
        ]
    };

    if (option && typeof option === 'object') {
        myChart.setOption(option);
    }

    window.addEventListener('resize', myChart.resize);
</script>


<script>
    var pageConst = ${page?c};
    $('#box').paging({
        initPageNo: ${page?c}, // 初始页码
        totalPages: ${totalPage?c}, //总页数
        slideSpeed: 600, // 缓动速度。单位毫秒
        jump: true, //是否支持跳转
        callback: function (page) { // 回调函数
            if (pageConst != page) {
                location.href = "/admin/?page=" + page;
            }
            console.log(page)
        }
    })

    var tab = new mdui.Tab('#example4-tab');

</script>
</body>
</html>
