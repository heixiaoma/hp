<#include "./header.ftl">
<style>
    .mdui-col span{
        display: inline-block;
        padding: 0.25em 0.4em;
        font-size: 75%;
        font-weight: 700;
        line-height: 1;
        text-align: center;
        white-space: nowrap;
        vertical-align: baseline;
        border-radius: 0.25rem;
        transition: color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out;
    }
    .pay{
        margin-top: 10px;
        padding: 10px;
        box-shadow: 20px 0px 10px 0px rgba(0,0,0,0.5);
        text-align: center;
    }
</style>
<#--监控页面-->
<div id="page-index" class="mdui-container">
    <div class="banner"><h1><strong>H</strong><strong>P</strong>内网穿透
        </h1>
        <div class="meta">我们支持TCP级别的所有协议，针对HTTP协议做了大量的优化工作， 可以实现 http/https ws/wss 协议。</div>
        <div class="actions"><a href="https://gitee.com/HServer/hp-android-client/releases" class="mdui-btn mdui-ripple"
                                target="_blank">APK下载</a><a
                    href="https://gitee.com/HServer/hp/releases" class="mdui-btn mdui-ripple">其他端下载</a>
        </div>
        <div class="mdui-typo" style="margin-top: 50px">
            <span><a href="https://gitee.com/HServer/hp" rel="nofollow" target="_blank">开源地址</a></span>
            <span><a href="https://jq.qq.com/?_wv=1027&k=jWfkkSda" rel="nofollow"
                     target="_blank">QQ群：432217351</a></span>
        </div>
    </div>

    <div class="feature case">
        <svg class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg">
            <path d="M870.56478157 1012.78372471H132.34317294c-63.27613764 0-116.00625254-56.94852472-116.00625373-128.66148079v-4.21840981L159.76283256 126.91779293c0-69.60375176 52.7301149-126.55227529 116.00625254-126.55227528h451.36978432c63.27613764 0 116.00625254 56.94852472 116.00625254 126.55227528l143.42591215 757.20445099c0 71.71295608-52.7301149 128.66148079-116.00625254 128.66148079zM58.52101137 886.23144824c0 46.40250079 33.74727373 84.36818432 73.82216157 84.36818431h738.22160863c40.07488785 0 73.82216038-37.96568234 73.82216038-84.36818431l-143.42591214-757.20445099c0-48.51170629-33.74727373-86.47738863-73.82216039-86.47738863H275.7690851c-40.07488785 0-73.82216038 37.96568234-73.82216038 86.47738863v4.21840981L58.52101137 886.23144824z"></path>
            <path d="M891.65682706 105.82574745h126.55227647-126.55227647z"></path>
            <path d="M514.10920431 377.9131404c-113.89704824 0-206.70205099-103.35102549-206.7020498-232.01250629 0-12.65522705 8.43681843-21.09204549 21.09204549-21.09204548s21.09204549 8.43681843 21.09204549 21.09204548c0 103.35102549 73.82216038 189.82841412 164.51795882 189.82841412s164.51795883-84.36818432 164.51795882-189.82841412c0-12.65522705 8.43681843-21.09204549 21.09204549-21.09204548s21.09204549 8.43681843 21.09204668 21.09204548c0 128.66148079-92.80500276 232.01250629-206.70205099 232.01250629z"></path>
        </svg>
        <h2>申明</h2>
        <div class="mdui-clearfix">
            <div class="meta" style="text-indent:25px">
                本项目是一个公益项目，请各路大神不用恶意攻击，滥用资源，免费开源的项目真的不多
                使用服务器来进行内网穿透，下载对应的终端的软件包，下载安装免费使用，软件内没有任何的广告，放心使用，对软件有问题也可以加入交流群，进行咨询。
                软件系统，会自动清理一个月未登录登录的账号，如果出现账号不存在，或者登录失败问题，请进行重新注册使用。
                本软件可应用于远程办公、远程联调、接口联调、接口回调、公众号调试、本地开发微信、MySQL、TCP端口转发等一系列功能。
                软件永远免费，但禁止搭建，涉政治、色情、暴力、赌博、诈骗、影视、私、 违反国家法律、给国家造成负面影响等等的网站，出现问题后果自负，本站概不负责。如有发现一律封号处理。
            </div>
        </div>
    </div>

    <div class="feature advantage">
        <svg class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg">
            <path d="M751.103601 0.001707a17.271438 17.271438 0 0 0-15.63304 9.420784l-74.990808 149.503751-167.935721 23.893293a16.861839 16.861839 0 0 0-9.48905 28.876752l129.877116 116.292073-32.529012 164.18106c-2.389329 13.85811 12.390379 24.405293 25.019692 17.851703L750.932935 432.470319l146.295223 77.584938c12.629312 6.485323 27.306621-4.027727 24.985558-17.817571l-32.153546-164.181059 128.682452-116.326207a16.827705 16.827705 0 0 0-9.489051-28.876751l-167.799187-23.893294-75.093208-149.503751A17.305571 17.305571 0 0 0 751.103601 0.001707zM750.932935 55.126948l63.590294 126.634456c2.491729 5.051725 7.372788 8.533319 12.970645 9.318384l142.267496 20.3093-110.148083 98.645169a16.418106 16.418106 0 0 0-4.949325 14.882108l27.750354 139.263768-123.425928-65.774824a17.510371 17.510371 0 0 0-16.04264 0l-122.538462 65.740691 28.091687-139.263768a16.759439 16.759439 0 0 0-4.949326-14.950375l-111.342747-98.645169 142.199229-20.275166a17.066638 17.066638 0 0 0 12.970645-9.284252zM563.199915 614.400683c-28.057553 0-51.199915 23.142361-51.199915 51.199914v307.199488c0 28.057553 23.142361 51.199915 51.199915 51.199915h307.199488c28.057553 0 51.199915-23.142361 51.199914-51.199915v-307.199488c0-28.057553-23.142361-51.199915-51.199914-51.199914z m0 34.133276h307.199488c9.762117 0 17.066638 7.304521 17.066638 17.066638v307.199488c0 9.762117-7.304521 17.066638-17.066638 17.066639h-307.199488a16.657039 16.657039 0 0 1-17.066639-17.066639v-307.199488c0-9.762117 7.304521-17.066638 17.066639-17.066638zM51.200768 102.401536C23.143215 102.401536 0.000853 125.543897 0.000853 153.601451v307.199488c0 28.057553 23.142361 51.199915 51.199915 51.199914h307.199488c28.057553 0 51.199915-23.142361 51.199915-51.199914v-307.199488c0-28.057553-23.142361-51.199915-51.199915-51.199915z m0 34.133276h307.199488c9.762117 0 17.066638 7.304521 17.066638 17.066639v307.199488c0 9.762117-7.304521 17.066638-17.066638 17.066638h-307.199488a16.657039 16.657039 0 0 1-17.066638-17.066638v-307.199488c0-9.762117 7.304521-17.066638 17.066638-17.066639z m0 477.865871c-28.057553 0-51.199915 23.142361-51.199915 51.199914v307.199488c0 28.057553 23.142361 51.199915 51.199915 51.199915h307.199488c28.057553 0 51.199915-23.142361 51.199915-51.199915v-307.199488c0-28.057553-23.142361-51.199915-51.199915-51.199914z m0 34.133276h307.199488c9.762117 0 17.066638 7.304521 17.066638 17.066638v307.199488c0 9.762117-7.304521 17.066638-17.066638 17.066639h-307.199488a16.657039 16.657039 0 0 1-17.066638-17.066639v-307.199488c0-9.762117 7.304521-17.066638 17.066638-17.066638z"></path>
        </svg>
        <h2>特色</h2>
        <div class="mdui-clearfix">

            <div class="item">
                <svg class="svg" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg">
                    <path d="M547.90126933 136.80896c5.074944 0 9.198336-4.118016 9.198336-9.19296s-4.123392-9.19296-9.198336-9.19296a9.19296 9.19296 0 0 0 0 18.38592z"></path>
                    <path d="M948.60680533 63.716864H147.20110933c-36.890112 0-66.791424 29.901312-66.791424 66.786048v195.315456h32.976384V130.502912a33.84192 33.84192 0 0 1 33.804288-33.809664h801.40032a33.84192 33.84192 0 0 1 33.798912 33.809664v469.16352H401.95362133v32.57856H982.41109333v100.58496a33.831168 33.831168 0 0 1-33.793536 33.804288H401.95362133v32.965632h38.121216c-8.983296 26.885376-21.283584 52.840704-38.121216 73.441536v76.833792h379.690752a16.69248 16.69248 0 1 0 0.016128-33.401088h-0.016128c-68.393472 0-105.616896-56.09856-125.927424-116.868864h292.88448c36.873984 0 66.780672-29.895936 66.780672-66.780672V130.502912c0.005376-36.884736-29.901312-66.786048-66.775296-66.786048z m-285.567744 827.0976l19.643904 21.197568H417.99022933l14.767872-16.310784c21.606144-31.309824 32.745216-52.958976 45.927168-89.306112l2.171904-6.789888h134.066688l2.171904 6.789888c12.805632 39.88992 24.896256 61.162752 45.943296 84.419328zM164.87202133 893.093888a29.901312 29.901312 0 1 0 59.802624 0c0-16.509696-13.38624-29.895936-29.901312-29.895936s-29.901312 13.380864-29.901312 29.895936z"></path>
                    <path d="M322.27054933 358.074368H62.85704533A47.427072 47.427072 0 0 0 15.42997333 405.50144v531.842304a47.427072 47.427072 0 0 0 47.427072 47.421696h259.413504a47.421696 47.421696 0 0 0 47.427072-47.421696v-531.84768a47.41632 47.41632 0 0 0-47.427072-47.421696z m9.795072 579.264a9.800448 9.800448 0 0 1-9.795072 9.789696H62.85704533a9.805824 9.805824 0 0 1-9.795072-9.789696V405.496064c0-5.397504 4.392192-9.795072 9.795072-9.795072h259.413504c5.397504 0 9.795072 4.392192 9.795072 9.795072v531.842304z"></path>
                    <path d="M353.36533333 828.985088a19.020288 19.020288 0 0 1-19.020288 19.014912H55.20162133a19.020288 19.020288 0 0 1 0-38.0352h279.1488a19.020288 19.020288 0 0 1 19.014912 19.020288zM1003.86133333 173.312a16.128 16.128 0 0 1-16.128 16.128H110.73570133a16.128 16.128 0 1 1 0-32.256H987.73333333a16.128 16.128 0 0 1 16.128 16.128z"></path>
                </svg>
                <label>多端支持</label><span>Android端、Win、Linux、Mac、NAS、Docker等环境 X86、ARM、等CPU架构。</span></div>

            <div class="item">
                <svg class="svg" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg">
                    <path d="M812 328.544c-57.152 0-101.504 44.352-101.504 101.504s44.352 101.504 101.504 101.504 101.504-44.352 101.504-101.504c0.736-57.152-44.352-101.504-101.504-101.504z m0 163.904c-35.328 0-62.4-27.072-62.4-62.4s27.072-62.4 62.4-62.4 62.4 27.072 62.4 62.4-26.304 62.4-62.4 62.4zM211.264 328.544c-57.152 0-101.504 44.352-101.504 101.504s44.352 101.504 101.504 101.504 101.504-44.352 101.504-101.504-44.352-101.504-101.504-101.504z m0 163.904c-35.328 0-62.4-27.072-62.4-62.4s27.072-62.4 62.4-62.4 62.4 27.072 62.4 62.4-27.072 62.4-62.4 62.4z m539.072-281.184c0-57.152-44.352-101.504-101.504-101.504s-101.504 44.352-101.504 101.504 44.352 101.504 101.504 101.504a100.608 100.608 0 0 0 101.504-101.504z m-102.24 62.4c-35.328 0-62.4-27.072-62.4-62.4s27.072-62.4 62.4-62.4 62.4 27.072 62.4 62.4-26.304 62.4-62.4 62.4zM375.168 109.76c-57.152 0-101.504 44.352-101.504 101.504s44.352 101.504 101.504 101.504 101.504-44.352 101.504-101.504S432.32 109.76 375.168 109.76z m0 163.904c-35.328 0-62.4-27.072-62.4-62.4s27.072-62.4 62.4-62.4 62.4 27.072 62.4 62.4-27.072 62.4-62.4 62.4zM272.16 921.76l62.4 27.808A466.304 466.304 0 0 0 512 984.16c35.328 0 62.4-27.072 62.4-62.4 0-15.776-6.016-30.08-15.776-40.608a95.392 95.392 0 0 1-27.808-68.416c0-57.152 44.352-101.504 101.504-101.504h98.496a253.504 253.504 0 0 0 253.376-253.376c0-230.048-212.032-417.28-472.16-417.28C251.136 39.808 39.872 251.84 39.872 511.968c0 124.064 43.616 231.552 121.792 316.512 3.744 15.04-3.008 25.568-16.544 26.304-9.024 0.736-18.784-8.256-18.784-8.256l-4.512-3.744c-4.512-5.248-8.256-9.76-12.768-15.04C41.376 740.544 0.032 630.752 0.032 511.968 0.032 230.016 229.344 0.736 511.264 0.736s511.232 205.248 511.232 457.12c0 161.632-131.584 293.216-293.216 293.216h-96.992c-35.328 0-62.4 27.072-62.4 62.4 0 15.776 6.016 30.08 15.776 40.608 18.048 18.048 27.808 42.112 27.808 68.416 0 57.152-44.352 101.504-101.504 101.504-93.216 0-180.448-24.8-255.616-68.416-0.768-0.736-17.28-28.576 15.776-33.824z"></path>
                </svg>
                <label>二级域名赠送</label><span>
                    注册的账号就是二级域名的名字，方便用户快速开发调试。
                </span></div>
            <div class="item">
                <svg class="svg" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg">
                    <path d="M17.066667 1024c-3.413333 0-6.826667 0-6.826667-3.413333-6.826667-3.413333-10.24-13.653333-6.826667-23.893334 174.08-310.613333 341.333333-604.16 723.626667-791.893333 6.826667-3.413333 17.066667 0 23.893333 6.826667 3.413333 6.826667 0 17.066667-6.826666 23.893333C368.64 419.84 211.626667 696.32 30.72 1013.76c-3.413333 6.826667-6.826667 10.24-13.653333 10.24z"></path>
                    <path d="M300.373333 750.933333c-51.2 0-88.746667-23.893333-105.813333-64.853333-85.333333-191.146667 92.16-382.293333 102.4-389.12 3.413333-3.413333 10.24-6.826667 13.653333-3.413333 6.826667 0 10.24 3.413333 13.653334 6.826666l27.306666 47.786667c6.826667-37.546667 20.48-85.333333 37.546667-105.813333 27.306667-30.72 167.253333-109.226667 184.32-119.466667 6.826667-3.413333 13.653333-3.413333 20.48 3.413333l34.133333 34.133334 20.48-64.853334 10.24-10.24c10.24-3.413333 218.453333-78.506667 344.746667-85.333333 6.826667 0 10.24 3.413333 13.653333 6.826667 3.413333 3.413333 3.413333 10.24 0 17.066666l-170.666666 341.333334c-3.413333 3.413333-6.826667 6.826667-13.653334 10.24l-167.253333 23.893333 85.333333 27.306667c3.413333 0 6.826667 3.413333 10.24 10.24s3.413333 10.24 0 13.653333c-23.893333 68.266667-293.546667 293.546667-457.386666 300.373333h-3.413334z m3.413334-416.426666c-40.96 47.786667-143.36 194.56-78.506667 334.506666C238.933333 703.146667 266.24 716.8 307.2 716.8c139.946667-6.826667 368.64-194.56 419.84-262.826667l-136.533333-44.373333c-6.826667-3.413333-13.653333-10.24-10.24-17.066667s6.826667-13.653333 13.653333-13.653333l228.693333-34.133333 153.6-307.2c-112.64 13.653333-266.24 68.266667-300.373333 78.506666L648.533333 194.56c-3.413333 3.413333-6.826667 10.24-13.653333 10.24s-10.24 0-17.066667-3.413333l-40.96-40.96c-58.026667 34.133333-146.773333 85.333333-163.84 105.813333-17.066667 20.48-34.133333 92.16-37.546666 133.12 0 6.826667-6.826667 13.653333-13.653334 13.653333s-13.653333-3.413333-17.066666-6.826666L303.786667 334.506667z"></path>
                </svg>
                <label>固定端口</label><span>我们支持动态端口与固定端口模式，穿透FTP MYSQL Redis MQ等服务需要。</span></div>
            <div class="item">
                <svg class="svg" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg">
                    <path d="M1011.122176 1011.122176H12.877824V12.877824h998.244352zM61.81137067 61.81137067v900.37725866h900.37725866V61.81137067z"></path>
                    <path d="M756.17792 482.27310933c-5.741568-0.01792001-11.01721601 0.12663468-14.95842133 0.50176-30.81045334 2.67844267-59.59116799 16.135168-79.29002667 37.07409067-9.86794668 10.49156267-18.80763732 25.53361067-23.174144 38.997504-4.813312 14.83895467-4.77866667 14.55581867-4.75477333 37.80164267 0.025088 29.69941333 1.53514667 37.05736533 11.63963733 56.80878932 10.16064 19.862528 25.64113068 36.08610133 47.97422933 50.27993601 9.78670933 6.21943466 34.85320534 18.71684267 55.32142933 27.58485333 44.556288 19.30222933 58.65216 28.20607999 65.19296001 41.190912l2.69994666 5.35808001v11.22986666c0 10.83204266-0.04420267 11.389952-2.24597333 15.85442134-5.024768 10.33506133-16.88900267 18.79808-32.080384 22.88622933-5.745152 1.54112-8.37819733 1.76213333-21.21489067 1.76691199-12.56669867 0-15.61787734-0.25924267-21.37736533-1.69284266-10.05550933-2.54464-18.52927999-6.06293333-25.15131732-10.44974933-8.11178666-5.37122134-24.055808-21.46935467-30.54165336-30.83554135-2.863616-4.13474134-5.737984-7.52401067-6.38429865-7.52401066-1.06683732 0-56.04539734 31.43167999-65.64096 37.52686934-3.23515733 2.05482667-4.05469867 2.99264-4.05469868 4.63889067 0 3.41674668 12.845056 22.83246933 22.90773334 34.62621867 20.50048001 24.03072 61.612544 45.21693866 98.029568 50.52245332 14.39573333 2.09544533 38.30698667 2.77282133 55.01320534 1.54231467 10.94673067-0.802816 22.01053867-2.87914667 33.89866667-6.36637866 43.01277866-12.61687467 72.36096-39.420416 83.23601066-76.02858667 4.43579734-14.930944 6.228992-33.298944 4.763136-48.79377066-3.00458667-31.79127467-12.77576532-54.26653867-32.06485333-73.73482667-17.912832-18.07530667-42.50504533-32.64068268-91.03957334-53.92486401-42.07974401-18.44804268-53.06350932-25.61723734-59.128832-38.57578666-5.23502933-11.189248-4.23748267-26.58850133 2.34871468-36.15778132 7.70798933-11.20119467 23.43936-16.73608533 40.98423466-14.41126401 16.314368 2.15278933 26.86446933 8.61952 37.20191999 22.78587734 5.36883199 7.354368 9.09499733 11.18685866 10.881024 11.18685866 1.76093867 0 9.350656-4.68309334 32.90948266-20.324864 23.98293334-15.91773866 31.52366933-21.32002133 32.03976535-22.94954667 0.802816-2.523136-2.95560533-8.429568-13.95131734-21.91735467-22.07624533-27.07831466-48.02798933-41.452544-81.35202133-45.058048-8.18107733-0.88883201-19.059712-1.37984-28.63138133-1.40970666z m-265.09653334 4.594688c-19.78129067-0.01194667-42.37960533 0-67.48671999 0.02389334-130.210304 0.12782933-160.43178667 0.34286933-161.24654934 1.18272-0.79206401 0.79206401-0.99754668 8.77602132-0.99754666 38.57339733v37.574656l59.20887466 0.21504 59.214848 0.21265067 0.01194667 167.60456533c0.00477867 92.184064 0.21265067 168.38587733 0.50176 169.33922134l0.44561066 1.741824h43.03189335c38.52799999 0 43.07968001-0.08482133 43.49900799-1.21856001 0.25685333-0.67259733 0.44561066-76.87441067 0.44561068-169.33922132V564.65732266l59.21245865-0.21265066 59.211264-0.21504v-38.02624c0-32.95726933-0.12663468-38.119424-1.15643734-38.76096-0.54357333-0.34167467-34.56409601-0.548352-93.91155199-0.577024z"></path>
                </svg>
                <label>自定义域名</label><span>我们支持用户自定义域名,只需要域名名字或者二级名字和账号名字一样就可以解析。</span></div>

            <div class="item">
                <svg class="svg" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg">
                    <path d="M200.7282726 722.78983111h-69.92060114C68.87954964 722.78983111 18.58673778 664.85794893 18.58673778 593.65963853V132.86874074c0-71.17112509 50.29281185-129.06222934 112.22093368-129.06222933h476.93896818C669.67476148 3.73854815 719.96757333 61.62965239 719.96757333 132.86874074v297.67907557a27.1853037 27.1853037 0 0 1-54.37060741 0V132.86874074c0-40.49250987-26.49207846-74.69162193-57.85032628-74.69162193H130.80767146C99.44942364 58.10915555 72.95734518 92.30826762 72.95734518 132.86874074v460.79089779c0 40.49250987 26.49207846 74.75958519 57.85032628 74.75958516H200.7282726a27.1853037 27.1853037 0 0 1 0 54.37060742z"></path>
                    <path d="M890.47379816 1009.59478519H413.53482999C351.60670815 1009.59478519 301.3138963 951.662903 301.3138963 880.4645926V582.78551703a27.1853037 27.1853037 0 0 1 54.37060739 0v297.67907557c0 40.49250987 26.49207846 74.75958519 57.8503263 74.75958519h476.93896817c31.35824781 0 57.85032627-34.19911205 57.85032628-74.75958519V419.67369481c0-40.49250987-26.49207846-74.69162193-57.85032628-74.69162192H820.55319704a27.1853037 27.1853037 0 0 1 0-54.3706074h69.92060112C952.40192 290.54350223 1002.69473185 348.43460647 1002.69473185 419.67369481v460.79089779c0 71.1983104-50.29281185 129.1301926-112.22093369 129.13019259z"></path>
                    <path d="M681.06540373 703.27078306a27.07656248 27.07656248 0 0 1-19.02971259-7.84296012l-331.82381701-327.58290965a27.1853037 27.1853037 0 0 1 38.1953517-38.69827981l331.82381701 327.58290963a27.1853037 27.1853037 0 0 1-19.02971259 46.52764729z"></path>
                </svg>
                <label>HTTPS支持</label>
                <span>支持用户自定义域名的同时在云厂商申请ssl证书如Nginx上配置https进行安全加密或者系统分配的域名也可以申请证书进行配置。</span></div>
            <div class="item">
                <svg class="svg" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg">
                    <path d="M50.92343 987.890344a14.476705 14.476705 0 0 1-10.483727-4.427999l-0.174297-0.174296c-3.148863-3.059554-4.867342-6.994913-4.835651-11.081522 0.03025-3.959847 1.715598-7.715147 4.746342-10.570155l132.894711-133.175602c2.77002-2.771461 6.526761-4.299797 10.573036-4.299798 4.09381 0 7.955706 1.561467 10.879856 4.394869 2.991852 2.993293 4.672879 6.966104 4.641189 11.097366-0.03025 4.00018-1.678145 7.787171-4.642629 10.662346l-132.837092 133.061805c-2.903984 2.909746-6.724105 4.512987-10.761738 4.512986z m146.669908-2.162142c-4.040513 0-7.787171-1.524015-10.549989-4.288274l-0.181499-0.177178c-2.928472-2.752735-4.587891-6.719784-4.550439-10.885617 0.034571-4.005942 1.655098-7.755481 4.445285-10.287824l0.288094-0.275129 58.519018-58.575197c2.846365-2.92415 6.572856-4.49282 10.574477-4.49282 4.049156 0 7.885123 1.60036 10.80063 4.507224 3.072519 2.981769 4.720414 6.80045 4.717533 10.823678-0.002881 4.010263-1.649336 7.811659-4.633986 10.701238l-58.642899 58.663065c-2.824758 2.728247-6.692415 4.286833-10.786225 4.286834zM52.604456 841.162818c-4.128382 0-7.913932-1.609003-10.659464-4.530272l-0.182939-0.188702c-6.054288-6.038442-6.035562-15.656448 0.030249-21.925365l58.843125-58.069593 0.100832-0.106595a14.315372 14.315372 0 0 1 10.515418-4.538915 14.318253 14.318253 0 0 1 10.518298 4.538915l0.167095 0.177178 0.177177 0.165654c2.679271 2.52226 4.219131 6.048526 4.332928 9.927706 0.126761 4.309881-1.547063 8.64569-4.592212 11.914112L63.683097 836.439523c-2.996174 3.079721-6.898402 4.723295-11.078641 4.723295z m628.843575-361.214675c-76.36786 0-138.496691-61.886833-138.496692-137.956516 0-76.053837 62.130272-137.927707 138.496692-137.927706 76.212289 0 138.2158 61.873869 138.2158 137.927706 0 76.068242-62.003511 137.956516-138.2158 137.956516z m0-245.380869c-59.220527 0-107.401305 48.190862-107.401306 107.422913 0 58.939635 48.179338 106.888499 107.401306 106.888499 59.249336 0 107.451722-47.950304 107.451721-106.888499-0.00144-59.23205-48.202386-107.422912-107.451721-107.422913z"></path>
                    <path d="M571.44524 1002.201395c-37.60054 0-75.082962-8.798379-108.392348-25.445869-8.717713-4.354535-12.507585-16.002161-8.239478-25.094396 0.929102-1.6623 2.033941-3.194958 3.063876-4.242179l0.394688-0.411974c16.127481-17.334594 29.258789-37.681206 37.972181-58.838802l16.742562-43.098807-35.971371 6.626153c-9.355841 1.7228-19.888544 3.35485-31.301373 4.850056-25.90826 3.478731-53.159037 5.165519-83.374297 5.165519-2.497772 0-4.996984-0.011524-7.493315-0.033131h-0.18438c-6.048526 0-10.479406-2.794508-13.193248-5.236101L166.647761 681.929147c-4.013144-4.377583-5.776277-9.348638-5.240424-14.776322l0.099393-1.001126v-1.005446c0-30.791447 1.761693-59.489894 5.387351-87.724511 0.643889-4.171596 1.208553-8.311501 1.75593-12.318884 0.884447-6.472023 1.719919-12.58537 2.837723-18.651181l6.705379-36.393428-42.722845 17.07675c-21.69345 8.819986-41.581994 21.573892-59.226289 37.952014-3.523385 3.202161-8.121359 4.962413-12.95413 4.963854-5.058924 0-9.743327-1.871168-13.285438-5.287959l-0.023047-0.03025c-1.148053-1.490885-2.719604-3.533468-3.066757-4.27675l-0.364438-0.95503-0.424938-0.82971c-22.003151-43.778708-29.829214-94.925411-22.036282-144.020887 7.951384-49.808508 31.036327-95.003196 66.759937-130.682151l0.180058-0.18294c31.559217-32.321224 72.513167-54.749314 118.435292-64.86284a244.811884 244.811884 0 0 1 49.699032-5.109341c20.89111 0 41.929147 2.693675 62.532163 8.003241l11.316318 2.915508 17.563628-17.632771 1.234481-2.283142C430.86131 127.423813 528.635967 71.525006 634.879136 43.033986A633.555345 633.555345 0 0 1 800.785055 20.921359a633.618726 633.618726 0 0 1 165.864146 22.096782l0.299617 0.079226c5.904479 1.50673 10.55287 5.655278 12.787036 11.29327v0.325546l0.688544 2.58564c28.930362 108.444204 28.835291 222.89372-0.273689 330.976367-29.10898 108.395228-86.2754 207.32226-165.321089 286.105785l-0.401891 0.40045-2.18375 2.432951-14.097862 12.727976 3.326041 12.125861c9.849921 35.919514 10.815035 74.604727 2.791628 111.878281-9.62953 44.958451-32.004321 85.87639-64.70583 118.347422l-2.208237 2.239928c-35.575242 34.473284-80.012244 56.889849-128.515688 64.835472a249.895296 249.895296 0 0 1-37.388791 2.829079z m-40.323025-99.497456c-5.161197 11.227009-9.62953 20.064281-14.053208 27.780869l-13.757911 24.009723 27.000135 6.060049a182.209138 182.209138 0 0 0 39.828945 4.442404c11.134819 0 22.311411-1.052982 33.222957-3.127256 41.050461-6.856628 78.358587-25.604321 107.90835-54.230745l0.849876-0.822508 1.439028-1.758811c26.897862-26.612649 45.436687-60.856898 53.647356-99.125816 8.547738-37.522755 5.545802-77.04488-8.681702-114.302588l-0.119559-0.308261c-2.513617-6.274679-0.285213-14.672609 5.185685-19.532748l0.280892-0.249201 12.794238-12.236777 10.019896-10.257573 1.251767-1.270493c74.98501-74.449156 128.865721-167.470268 155.819762-269.017511 25.542381-94.867792 27.535989-195.184875 5.746027-290.036822l-2.879496-12.534954-12.549358-2.816115c-43.006617-9.648256-87.318298-14.541526-131.703444-14.541526-52.944407 0-105.894576 6.921449-157.376907 20.574206-101.581814 27.35305-194.757056 81.307225-269.534639 156.047356l-4.838533 4.703128h-1.054422l-16.867882 18.708801-0.203106 0.237677c-3.746658 4.419356-8.940986 6.95314-14.253433 6.95458-2.140536 0-4.266667-0.420617-6.320774-1.250326l-0.381724-0.149809c-22.61535-8.61688-47.162368-7.514922-71.130318-12.987261-42.326716-9.664101-108.115778 23.773486-108.115777 23.773487L117.444249 310.291245c-30.353545 30.353545-49.779698 68.534594-56.184019 110.423407-4.063561 23.593428-3.615575 48.527932 1.296421 72.127122l5.668242 27.23061 24.280531-13.56921c7.755481-4.334369 17.949674-9.849921 28.03007-14.067612 24.562863-10.113527 51.793473-16.251362 80.837632-18.204637 2.875174 0.03025 3.991537 0.267927 4.294036 0.350034l1.489444 0.639568 1.728562 0.409093c5.499707 1.302183 8.634166 4.901913 10.292145 7.69354 2.661985 4.475535 3.395183 9.952194 2.015215 15.031285a714.817916 714.817916 0 0 0-17.233761 83.570201l-0.056179 0.420616c-2.853567 23.10799-4.58357 46.302408-5.13815 68.939366l-0.213189 8.737879 164.608058 164.89039 8.756606-0.211749c21.367905-0.517128 43.918433-2.301868 68.942246-5.453613 31.864596-4.237857 59.281026-9.818231 83.951924-17.076749a18.351564 18.351564 0 0 1 4.713212-0.668378c0.270808 0 0.538735 0.008643 0.805222 0.027369l0.440783 0.03025 0.440783 0.011524c4.887508 0.12244 9.455233 2.218321 12.864821 5.903038 3.406707 3.680396 5.142471 8.416655 4.887509 13.337295l-0.014405 0.316903c-1.001125 27.442359-6.999235 54.886158-17.825793 81.575152z"></path>
                </svg>
                <label>高性能</label>
                <span>我们使用HServer做为后台基石，在http协议上做了大量优化操作，通过SNI协议解析或者明文HOST解析。</span></div>
        </div>
    </div>

    <div class="feature">
        <svg class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg">
            <path d="M585.36391111 50.39445333a156.44444445 156.44444445 0 0 0-145.59004444 0L111.82876445 222.78826667a99.55555555 99.55555555 0 0 0-53.23207112 88.12088888v403.00657778a99.55555555 99.55555555 0 0 0 53.23207112 88.12088889l327.94737777 172.39608889a156.44444445 156.44444445 0 0 0 145.58776889 0l327.94737778-172.39608889a99.55555555 99.55555555 0 0 0 53.23207111-88.12088889V310.91029333a99.55555555 99.55555555 0 0 0-53.23207111-88.12088888L585.36391111 50.39217778z m13.23463111-25.17788444l327.94737778 172.39495111c42.0864 22.12295111 68.44074667 65.75217778 68.44074667 113.29991111v403.00544c0 47.54773333-26.35548445 91.17582222-68.44074667 113.29991111L598.59854222 999.61059555a184.88888889 184.88888889 0 0 1-172.05930667 0L98.59185778 827.21678222c-42.0864-22.12408889-68.44074667-65.75217778-68.44074667-113.29991111V310.91029333c0-47.54659555 26.35548445-91.17582222 68.44074667-113.29991111L426.53923555 25.21770667a184.88888889 184.88888889 0 0 1 172.05930667 0z"></path>
            <path d="M336.50574222 410.05852445l99.46453333-165.79697778c25.37699555-42.29802667 80.23950222-56.01735111 122.53866667-30.64035556a89.31555555 89.31555555 0 0 1 30.64149333 30.64035556l100.34062223 167.25333333A48.47957333 48.47957333 0 0 1 700.87111111 410.16888889c26.70592 0 48.35555555 21.64963555 48.35555556 48.35555556 0 12.34602667-4.62734222 23.61230222-12.24248889 32.1581511l94.43669333 157.41383112c25.37699555 42.29916445 11.65767111 97.16167111-30.64035556 122.53866666a89.31555555 89.31555555 0 0 1-45.94915555 12.72490667H560.50574222c-6.07345778 19.76547555-24.4736 34.13333333-46.23018667 34.13333333-21.75658667 0-40.15672889-14.36785778-46.23018666-34.13333333H270.29048889c-49.32721778 0-89.31555555-39.98833778-89.31555556-89.31555555a89.31555555 89.31555555 0 0 1 12.72604445-45.94801778l95.77813333-159.65070222A48.16099555 48.16099555 0 0 1 278.18666667 457.38666667c0-26.70592 21.64963555-48.35555555 48.35555555-48.35555556 3.41560889 0 6.74816 0.35384889 9.96352 1.02741334z m328.53447111 15.99374222L564.75875555 258.89564445a60.87111111 60.87111111 0 0 0-20.88277333-20.88277334c-28.82787555-17.29422222-66.21866667-7.94510222-83.51288889 20.88277334L361.39235555 423.86545778c8.36266667 8.69262222 13.50428445 20.50616889 13.50428445 33.52120889 0 5.25312-0.83740445 10.30940445-2.38592 15.04483555l81.62190222 47.12448C467.50151111 501.51082667 488.95431111 489.81333333 513.13777778 489.81333333c24.74780445 0 46.63523555 12.25045333 59.92789333 31.01809778l81.84035556-47.25077333A48.31459555 48.31459555 0 0 1 652.51555555 458.52444445c0-12.49848889 4.74225778-23.88992 12.52465778-32.47217778zM560.50574222 754.91555555h194.32561778a60.87111111 60.87111111 0 0 0 31.31505778-8.67328c28.82787555-17.29422222 38.17813333-54.68501333 20.88277333-83.51288888l-94.36728889-157.29777778A48.45795555 48.45795555 0 0 1 700.87111111 506.88a48.14506667 48.14506667 0 0 1-29.44227556-9.99424l-86.71004444 50.06222222A73.63128889 73.63128889 0 0 1 586.52444445 563.2c0 35.46453333-25.15512889 65.05244445-58.59555556 71.89617778v87.64074667c15.53749333 4.56476445 27.82663111 16.72078222 32.57685333 32.1786311zM313.36334222 503.92519111l-95.27068444 158.80419556A60.87111111 60.87111111 0 0 0 209.41937778 694.04444445c0 33.61792 27.25319111 60.87111111 60.87111111 60.8711111h197.75488c4.63303111-15.07783111 16.44088889-27.01539555 31.43907556-31.82933333v-87.76817777c-34.00590222-6.39772445-59.73333333-36.25301333-59.73333334-72.11804445a73.54595555 73.54595555 0 0 1 2.18453334-17.84263111l-85.94090667-49.61735111A48.14506667 48.14506667 0 0 1 326.54222222 505.74222222c-4.56817778 0-8.98844445-0.63374222-13.17888-1.81703111zM513.13777778 608.14222222c24.82062222 0 44.94222222-20.1216 44.94222222-44.94222222s-20.1216-44.94222222-44.94222222-44.94222222-44.94222222 20.1216-44.94222223 44.94222222 20.1216 44.94222222 44.94222223 44.94222222z m-186.59555556-130.84444444c10.99662222 0 19.91111111-8.91448889 19.91111111-19.91111111s-8.91448889-19.91111111-19.91111111-19.91111112-19.91111111 8.91448889-19.91111111 19.91111112 8.91448889 19.91111111 19.91111111 19.91111111z m374.32888889 1.13777777c10.99662222 0 19.91111111-8.91448889 19.91111111-19.9111111s-8.91448889-19.91111111-19.91111111-19.91111112-19.91111111 8.91448889-19.91111111 19.91111112 8.91448889 19.91111111 19.91111111 19.9111111z m-186.59555556 310.61333334c10.99662222 0 19.91111111-8.91448889 19.91111112-19.91111111s-8.91448889-19.91111111-19.91111112-19.91111111-19.91111111 8.91448889-19.9111111 19.91111111 8.91448889 19.91111111 19.9111111 19.91111111z"></path>
        </svg>
        <h2>通过 Docker 安装</h2>
        <div class="mdui-typo"><pre class="hljs php"><code class="lang-bash"><span
                            class="hljs-comment"># 通过 docker pull 拉取镜像</span>
docker pull registry.cn-shenzhen.aliyuncs.com/hserver/hp:v4
<span class="hljs-comment"># 通过 docker run 运行容器</span>
docker run -P -d -p 5000:5000 -e server_ip=ksweb.club -e server_port=9091 -e username=heixiaoma -e password=123456 -e remote_port=5000 -e ip=127.0.0.1 -e port=5000 registry.cn-shenzhen.aliyuncs.com/hserver/hp:v4
</code></pre>
        </div>
    </div>
    <div class="feature">
        <svg class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg">
            <path d="M85.333333 34.133333A51.2 51.2 0 0 0 34.133333 85.333333v853.333334A51.2 51.2 0 0 0 85.333333 989.866667h853.333334a51.2 51.2 0 0 0 51.2-51.2V85.333333A51.2 51.2 0 0 0 938.666667 34.133333H85.333333zM85.333333 0h853.333334a85.333333 85.333333 0 0 1 85.333333 85.333333v853.333334a85.333333 85.333333 0 0 1-85.333333 85.333333H85.333333a85.333333 85.333333 0 0 1-85.333333-85.333333V85.333333a85.333333 85.333333 0 0 1 85.333333-85.333333z"></path>
            <path d="M296.504889 620.600889a17.066667 17.066667 0 0 1-24.120889 24.149333L171.804444 544.142222a45.511111 45.511111 0 0 1 0-64.341333l100.579556-100.579556a17.066667 17.066667 0 0 1 24.120889 24.149334L195.982222 503.950222a11.377778 11.377778 0 0 0 0 16.099556l100.551111 100.551111zM594.574222 289.820444l30.151111 16.014223-219.448889 412.672-30.151111-16.014223zM727.495111 620.600889a17.066667 17.066667 0 0 0 24.120889 24.149333l100.579556-100.579555a45.511111 45.511111 0 0 0 0-64.341334l-100.579556-100.579555a17.066667 17.066667 0 0 0-24.120889 24.149333l100.551111 100.551111a11.377778 11.377778 0 0 1 0 16.099556l-100.551111 100.551111z"></path>
        </svg>
        <h2>从 命令行 运行</h2>
        <div class="mdui-typo"><pre class="hljs php"><code class="lang-bash"><span
                            class="hljs-comment"># 通过参数运行</span>
./hp-client-golang-amd64 -server_ip ksweb.club -server_port 9091 -username xx -password 123 -remote_port 5000 -ip 127.0.0.1 -port 5000

<span class="hljs-comment"># 通过config.ini运行</span>
./hp-client-golang-amd64
<span class="hljs-comment"># 同目下创建config.ini文件</span>
[hp]
#HP账号
username='heixiaoma'
#HP密码
password='123456'
#外部端口如果没有，服务会随机分配
remote_port=8080
#穿透服务的IP
server_ip='ksweb.club'
#穿透服务的端口
server_port=9091

[proxy]
#本地代理的IP
ip='192.168.123.85'
#本地代理的端口
port=8080
</code></pre>
        </div>
    </div>
    <div class="feature case">
        <h2>最新捐赠者</h2>
        <div class="mdui-clearfix">
            <div class="meta">
                <div class="mdui-row-xs-4">
                    <#if payer??>
                        <#list payer as pay>
                            <div class="mdui-col pay">
                                <div>${pay.username}</div>
                                <div>
                                    <span>￥${pay.price}</span>
                                </div>
                            </div>
                        </#list>
                    </#if>
                </div>
            </div>

            <div class="meta">
                <div style="text-align: center">
                    <img height="300" src="/img/pay.jpg"/>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>