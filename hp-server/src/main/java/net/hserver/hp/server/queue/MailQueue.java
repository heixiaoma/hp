package net.hserver.hp.server.queue;

import cn.hserver.core.ioc.annotation.queue.QueueHandler;
import cn.hserver.core.ioc.annotation.queue.QueueListener;
import net.hserver.hp.server.config.ConstConfig;
import net.hserver.hp.server.utils.MailUtils;

import java.util.Random;

@QueueListener(queueName = "EMAIL")
public class MailQueue {
    Random randObj = new Random();

    @QueueHandler
    public void send(String username) {
        String code = generateCode4();
        if (MailUtils.sendMail(username, "穿透验证", "验证码为:" + code)) {
            ConstConfig.EMAIL_CODE.put(username, code);
        } else {
            ConstConfig.EMAIL.invalidate(username);
        }
    }

    public String generateCode4() {
        return Integer.toString(1000 + randObj.nextInt(9000));
    }
}
