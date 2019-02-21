package com.waner.primary.common.util;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.waner.primary.common.constants.Constants;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 七牛云对象存储操作工具类
 *
 * @author Monster
 * @date 2019/2/20 10:05
 * @since 1.8
 */
public final class QiniuFileUploadUtil {

    public static String uploadImg(MultipartFile file)throws IOException {
        // zone0 ---> 华东
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(Constants.QINIU_ACCESS_KEY, Constants.QINIU_SECRET_KEY);
        String upToken = auth.uploadToken(Constants.QINIU_HEAD_IMG_BUCKET_NAME);
        Response response = uploadManager.put(file.getBytes(), null ,upToken);
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        return putRet.key;
    }

}
