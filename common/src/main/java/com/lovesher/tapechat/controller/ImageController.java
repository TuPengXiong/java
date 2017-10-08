package com.lovesher.tapechat.controller;

import com.lovesher.tapechat.beans.Image;
import com.lovesher.tapechat.common.MsgEnum;
import com.lovesher.tapechat.plugins.qiniu.QiNiuImagePlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by tpx on 2017/9/9.
 */
@RestController("imageController")
@RequestMapping("/api/image")
public class ImageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private QiNiuImagePlugin qiNiuImagePlugin;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
    public UnionResp<Image> upload(@RequestPart("upfile") MultipartFile upfile) {
        byte[] bytes = null;
        try {
            InputStream inStream = upfile.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            outStream.close();
            inStream.close();
            bytes = outStream.toByteArray();
        } catch (Exception e1) {
            return new UnionResp(MsgEnum.Fail.getCode(), "暂不支持此类型的图片", null);
        }
        float size = bytes.length / 1024;
        if (size > 5192) {
            return new UnionResp(MsgEnum.Fail.getCode(), "图片大小不能大于5M", null);
        }
        Image image = qiNiuImagePlugin.uploadImage(bytes);
        if (null != image) {
            return new UnionResp(MsgEnum.SUCCESS.getCode(), MsgEnum.SUCCESS.getMsg(), image);
        } else {
            return new UnionResp(MsgEnum.Fail.getCode(), MsgEnum.Fail.getMsg(), null);
        }
    }

}
