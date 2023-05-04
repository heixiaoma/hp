package net.hserver.hp.proxy.controller;

import cn.hserver.core.server.context.ConstConfig;
import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.interfaces.HttpRequest;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hxm
 */
@Controller
public class PhotoController {
    private static final Logger log = LoggerFactory.getLogger(PhotoController.class);

    private static final String PHOTO_PATH = ConstConfig.PATH + "photo";

    private static List<String> getPhotoDirectory() {
        List<String> data = new ArrayList<>();
        File file = new File(PHOTO_PATH);
        File[] files = file.listFiles();
        if (files == null) {
            return data;
        }
        for (File listFile : files) {
            if (listFile.isDirectory()) {
                data.add(listFile.getName());
            }
        }
        return data;
    }

    private static List<String> getPhotoFile(String time) {
        List<String> data = new ArrayList<>();
        File file = new File(PHOTO_PATH + File.separator + time);
        File[] files = file.listFiles();
        if (files == null) {
            return data;
        }
        for (File listFile : files) {
            if (listFile.isFile()) {
                data.add(listFile.getName());
            }
        }
        return data;
    }

    @GET("/photoList")
    public void photoList(HttpRequest request, HttpResponse response) {
        try {
            Map<String, Object> data = new HashMap<>(2);
            data.put("token", request.query("token"));
            List<String> photoDirectory = getPhotoDirectory();
            data.put("dataSize", photoDirectory.size());
            data.put("data", photoDirectory);
            response.sendTemplate("/photoList.ftl", data);
        } catch (Exception e) {
        }
    }

    @GET("/photo/{time}")
    public void photo(String time, HttpRequest request, HttpResponse response) {
        try {
            Map<String, Object> data = new HashMap<>(2);
            data.put("token", request.query("token"));
            data.put("time", time);
            List<String> photoFile = getPhotoFile(time);
            data.put("dataSize", photoFile.size());
            data.put("data", photoFile);
            response.sendTemplate("/photoDetailList.ftl", data);
        } catch (Exception e) {
        }
    }

    @GET("/photoRemoveAll/{time}")
    public void photoRemoveAll(String time, HttpRequest request, HttpResponse response) {
        try {
            FileUtils.deleteDirectory(new File(PHOTO_PATH + File.separator + time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.redirect("/photoList");
    }


    @GET("/photoDetail/{path}")
    public void photoDetail(String path, HttpRequest request, HttpResponse response) {
        response.setDownloadFile(new File(PHOTO_PATH + File.separator + path));
    }

    @GET("/photoRemove/{path}")
    public void photoRemove(String path, HttpRequest request, HttpResponse response) {
        try {
            FileUtils.delete(new File(PHOTO_PATH + File.separator + path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.redirect("/photo/" + path.split("/")[0]);
    }

}
