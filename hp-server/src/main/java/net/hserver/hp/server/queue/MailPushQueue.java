package net.hserver.hp.server.queue;

import cn.hserver.core.ioc.annotation.queue.QueueHandler;
import cn.hserver.core.ioc.annotation.queue.QueueListener;
import cn.hserver.core.server.util.PropUtil;
import net.hserver.hp.server.config.ConstConfig;
import net.hserver.hp.server.utils.MailUtils;

import java.util.Random;

@QueueListener(queueName = "EMAIL_PUSH")
public class MailPushQueue {
    @QueueHandler
    public void send(String title,String message) {
        MailUtils.sendMail(PropUtil.getInstance().get("notice.mail"), title, message);
    }

}
