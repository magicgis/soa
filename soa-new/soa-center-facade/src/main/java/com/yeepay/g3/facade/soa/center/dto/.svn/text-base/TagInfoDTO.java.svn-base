package com.yeepay.g3.facade.soa.center.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yeepay.g3.facade.soa.center.enums.TagTypeEnum;

/**
 * 服务标签信息
 *
 * @author：menghao.chen
 * @since：2014年7月3日 下午12:05:22
 * @version:
 */
public class TagInfoDTO implements Serializable {

	private static final long serialVersionUID = 8174700064899645488L;

	private Long id;

	private String tagName;

	private TagTypeEnum tagType;

	/**
	 * 关联主键（应用名、服务名）
	 */
	private String refKey;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public TagTypeEnum getTagType() {
		return tagType;
	}

	public void setTagType(TagTypeEnum tagType) {
		this.tagType = tagType;
	}

	public String getRefKey() {
		return refKey;
	}

	public void setRefKey(String refKey) {
		this.refKey = refKey;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
