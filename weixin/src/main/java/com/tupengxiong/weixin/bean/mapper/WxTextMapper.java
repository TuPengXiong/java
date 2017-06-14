package com.tupengxiong.weixin.bean.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tupengxiong.weixin.bean.WxText;

public interface WxTextMapper extends Mapper<WxText> {

	List<WxText> selectBySendStatus(@Param("sendStatus") Integer sendStatus, @Param("start") int start,
			@Param("end") int end);
	
	Integer wxTextTotalCount(@Param("sendStatus") Integer sendStatus);
}
