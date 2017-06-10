package com.tupengxiong.weixin.mapper;

import org.apache.ibatis.annotations.Param;

import com.tupengxiong.weixin.bean.Sn;

public interface SnMapper extends Mapper<Sn> {
	
	/**
	 * 获得上次sn
	 * 
	 * @param type
	 *            类型
	 * @return 序列号
	 */
	public Sn getLastSn(@Param("snType") String type);
}
