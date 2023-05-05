package net.hserver.hp.proxy.queue;

import cn.hserver.HServerApplication;
import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.ioc.annotation.queue.QueueHandler;
import cn.hserver.core.ioc.annotation.queue.QueueListener;
import cn.hserver.core.server.context.ConstConfig;
import com.google.common.io.Files;
import net.hserver.hp.common.message.Photo;
import net.hserver.hp.proxy.handler.HpServerHandler;
import net.hserver.hp.proxy.service.HttpService;
import net.hserver.hp.proxy.service.nsfw.NsfwService;
import net.hserver.hp.proxy.utils.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@QueueListener(queueName = "PHOTO")
public class PhotoQueue {
    private static final Logger log = LoggerFactory.getLogger(PhotoQueue.class);

    @Autowired
    private NsfwService nsfwService;

    @QueueHandler
    public void check(Photo photo) {
        try {
            float prediction = nsfwService.getPrediction(photo.getData());
            log.info("图片涉黄校验，分数 {}, 用户 {},域名 {}", prediction, photo.getUsername(), photo.getDomain());
            if (prediction > 0.5) {
                String path = ConstConfig.PATH + "photo" + File.separator + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + File.separator;
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String md5 = Md5Util.get(photo.getData());
                String desPath = path + photo.getDomain() + "_" + md5 + photo.getPhotoType().getTips();
                Files.write(photo.getData(), new File(desPath));
                //封号
                HttpService.noticePush(photo.getUsername(), "异常通知", "分数 " + prediction + ", 用户 " + photo.getUsername() + " 域名 " + photo.getDomain() + " 地址 " + desPath);
                //下线
                HpServerHandler.offline(photo.getDomain().split("\\.")[0]);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
