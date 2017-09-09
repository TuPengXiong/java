package com.tupengxiong.common.controller;

import com.tupengxiong.common.beans.Image;
import com.tupengxiong.common.common.MsgEnum;
import com.tupengxiong.common.plugins.qiniu.QiNiuImagePlugin;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by tpx on 2017/9/9.
 */
@Controller("imageController")
@RequestMapping("/image")
@ResponseBody
public class ImageController {

    @Resource
    private QiNiuImagePlugin qiNiuImagePlugin;

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
