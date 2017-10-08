package com.lovesher.tapechat.plugins.qiniu;

import com.lovesher.tapechat.beans.Image;
import com.lovesher.tapechat.dao.ImageDao;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * Created by tpx on 2017/9/9.
 */
@Component("qiNiuImagePlugin")
public class QiNiuImagePlugin {

    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;
    @Value("${qiniu.defaultBucket}")
    private String defaultBucket;
    @Value("${qiniu.imagePrefixUrl}")
    private String imagePrefixUrl;

    @Resource
    private ImageDao imageDao;

    //构造一个带指定Zone对象的配置类 华东
    private Configuration cfg = new Configuration(Zone.zone0());
    private UploadManager uploadManager = new UploadManager(cfg);

    /**
     * 上传图片
     *
     * @return
     */
    public Image uploadImage(byte[] bytes) {
        // 使用文件的md5sum作为七牛云上的文件名，防止重复上传
        String md5FileName = DigestUtils.md5Hex(bytes);
        Image image = exist(md5FileName);
        if (null == image) {
            //...生成上传凭证，然后准备上传
            Auth auth = Auth.create(accessKey, secretKey);
            String token = auth.uploadToken(defaultBucket);
            Response response = null;
            Map<String, Object> map = null;
            try {
                response = uploadManager.put(bytes, md5FileName, token);
                map = response.jsonToMap().map();
            } catch (QiniuException e) {
                e.printStackTrace();
                return null;
            }
            image = new Image();
            image.setBucket(defaultBucket);
            image.setKey(map.get("key").toString());
            image.setMd5Name(md5FileName);
            image.setCreateTime(new Date());
            image.setModifyTime(new Date());
            image.setHash(map.get("hash").toString());
            image.setUrl(imagePrefixUrl+image.getKey());
            image.setPrefixUrl(imagePrefixUrl);
            imageDao.save(image);
            return image;
        } else {
            return image;
        }
    }


    public Image exist(String md5FileName) {
        return imageDao.findByKey(md5FileName);
    }

    public boolean yunExist(String md5FileName) {
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            FileInfo info = bucketManager.stat(defaultBucket, md5FileName);
        } catch (QiniuException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        QiNiuImagePlugin qiNiuImagePlugin = new QiNiuImagePlugin();
        qiNiuImagePlugin.uploadImage("111".getBytes());
    }
}
