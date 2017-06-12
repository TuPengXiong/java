package com.tupengxiong.weixin.bean.mapper;

public interface Mapper<T> {
    /**
     * 对对象进行持久化操作，如果成功则返回持久化后的ID
     * 失败则返回null
     * @param bean
     * @return
     */
    long insert(T bean);
    
    /**
     * 删除指定id的持久化对象
     * @param id
     */
    int deleteById(Long id);
    /**
     * 修改指定的持久化对象
     * @param id
     * @param obj
     */
    int update(T bean);
    
    /**
     * 返回持久化对象
     * @param id
     * @return 找到则返回，否则返回空
     */
    T selectById(Long id);
}