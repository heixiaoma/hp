package net.hserver.hp.common.handler;

import cn.hserver.core.queue.HServerQueue;
import cn.hserver.core.server.context.ConstConfig;
import com.google.common.io.Files;
import net.hserver.hp.common.message.Photo;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class PhotoMessageHandler {

    private final static List<Photo> PHOTOS = new ArrayList<>();

    private static Thread async = null;

    public PhotoMessageHandler() {
        if (async == null) {
            async = new Thread(() -> {
                while (true) {
                    try {
                        opPhoto();
                        Thread.sleep(1000);
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            async.start();
        }
    }

    public abstract boolean checkAndSavePhoto(byte[] bytes);

    public void save(Photo photo) {
        PHOTOS.add(photo);
    }

    private void opPhoto() {
        try {
            if (PHOTOS.isEmpty()) {
                return;
            }
            for (int i = 0; i < PHOTOS.size(); i++) {
                Photo photo = PHOTOS.get(i);
                HServerQueue.sendQueue("PHOTO", photo);
                PHOTOS.remove(photo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
