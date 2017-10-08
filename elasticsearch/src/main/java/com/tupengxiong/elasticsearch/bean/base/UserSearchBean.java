package com.tupengxiong.elasticsearch.bean.base;

import java.util.Date;

import com.tupengxiong.elasticsearch.annotation.EsSearch;
import com.tupengxiong.elasticsearch.annotation.EsSearch.BuilderType;
import com.tupengxiong.elasticsearch.annotation.EsSearch.RangeType;

/**
 * 用户搜索bean
 * 
 * @ClassName: UserSearchBean
 * @Description: 用户搜索bean
 * @author tupx
 * @date 2017年9月25日 上午9:56:25
 *
 */
public class UserSearchBean extends BaseSearch {

	@EsSearch(order = true, enableSearch = false)
	private Long id;
	@EsSearch
	private String username;
	@EsSearch
	private String phone;
	@EsSearch
	private String password;

	@EsSearch(propertyName = "createTime", builderType = BuilderType.rangeQuery, rangeType = RangeType.start)
	private Date createTimeStart;

	@EsSearch(propertyName = "createTime", builderType = BuilderType.rangeQuery, rangeType = RangeType.end)
	private Date createTimeEnd;

	@EsSearch(propertyName = "modifyTime", builderType = BuilderType.rangeQuery, rangeType = RangeType.start)
	private Date modifyTimeStart;

	@EsSearch(propertyName = "modifyTime", builderType = BuilderType.rangeQuery, rangeType = RangeType.end)
	private Date modifyTimeEnd;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Date getModifyTimeStart() {
		return modifyTimeStart;
	}

	public void setModifyTimeStart(Date modifyTimeStart) {
		this.modifyTimeStart = modifyTimeStart;
	}

	public Date getModifyTimeEnd() {
		return modifyTimeEnd;
	}

	public void setModifyTimeEnd(Date modifyTimeEnd) {
		this.modifyTimeEnd = modifyTimeEnd;
	}

	
	
}
