package com.tupengxiong.logistics.service;

import org.springframework.stereotype.Component;

/**
 * Created by tpx on 2017/8/17.
 */
public abstract class KdService {
    /**
     * 获取ID
     * @return ID
     */
    public final String getId() {
        return getClass().getAnnotation(Component.class).value();
    }
    /**
     * 获取名称
     *
     * @return 名称
     */
    public abstract String getName();

    /**
     * 获取版本
     *
     * @return 版本
     */
    public abstract String getVersion();

    /**
     * 获取作者
     *
     * @return 作者
     */
    public abstract String getAuthor();

    /**
     * 获取网址
     *
     * @return 网址
     */
    public abstract String getSiteUrl();
}
