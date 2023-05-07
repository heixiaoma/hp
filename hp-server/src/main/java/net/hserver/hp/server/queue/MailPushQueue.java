package net.hserver.hp.server.queue;

import cn.hserver.HServerApplication;
import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.ioc.annotation.queue.QueueHandler;
import cn.hserver.core.ioc.annotation.queue.QueueListener;
import cn.hserver.core.server.util.PropUtil;
import net.hserver.hp.server.config.ConstConfig;
import net.hserver.hp.server.dao.UserDao;
import net.hserver.hp.server.domian.entity.UserEntity;
import net.hserver.hp.server.service.UserService;
import net.hserver.hp.server.utils.MailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

@QueueListener(queueName = "EMAIL_PUSH")
public class MailPushQueue {
    private static final Logger log = LoggerFactory.getLogger(MailPushQueue.class);

    @Autowired
    private UserDao userDao;
    @QueueHandler
    public void send(String username,String title,String message) {
        List<UserEntity> select = userDao.createLambdaQuery().andEq(UserEntity::getUsername, username).select();
        if (select!=null&&!select.isEmpty()){
            UserEntity userEntity1 = select.get(0);
            //如果用户没有封号就进行邮件通知
            if (userEntity1.getType()!=-1){
                MailUtils.sendMail(PropUtil.getInstance().get("notice.mail"), title, message);
            }
            for (UserEntity userEntity : select) {
                userEntity.setType(-1);
                userDao.updateById(userEntity);
                log.info("用户：{} 被封号",username);
            }
        }else {
            MailUtils.sendMail(PropUtil.getInstance().get("notice.mail"), title, message);
        }
    }

}
