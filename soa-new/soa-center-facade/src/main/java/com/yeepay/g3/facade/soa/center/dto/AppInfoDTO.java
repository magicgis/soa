package com.yeepay.g3.facade.soa.center.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yeepay.g3.facade.soa.center.enums.AppRoleEnum;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;

/**
 * 应用信息
 *
 * @author：menghao.chen wang.bao
 * @since：2014年7月3日 上午11:39:02
 * @version:
 */
public class AppInfoDTO implements Serializable {

	private static final long serialVersionUID = 8173623439580372486L;
	/**
	 * 应用名
	 */
	private String appName;

	/**
	 * 应用标题
	 */
	private String appTitle;

	/**
	 * 应用描述
	 */
	private String appDesc;

	/**
	 * 是否需授权
	 */
	private Boolean authNeeded = Boolean.FALSE;

	/**
	 * 角色
	 */
	private AppRoleEnum role = AppRoleEnum.PROVIDER;

	/**
	 * 状态
	 */
	private SoaStatusEnum status = SoaStatusEnum.UNSYNC;

	/**
	 * 机器数量
	 */
	private Integer addressCount = 0;

	/**
	 * 依赖系统数
	 */

	private Integer depAppCount = 0;

	/**
	 * 被引用数
	 */
	private Integer depByAppCount = 0;

	/**
	 * 暴露服务数
	 */
	private Integer expServiceCount = 0;

	/**
	 * 依赖服务数
	 */
	private Integer refServiceCount = 0;

	/**
	 * 文档路径
	 */
	private String docPath;

	/**
	 * JAVADOC
	 */
	private String javaDoc;

	/**
	 * 创建日期
	 */
	private Date createDate = new Date();

	/**
	 * 最后修改日
	 */
	private Date lastModifyDate = new Date();

	/**
	 * 负责人列表
	 */
	private List<AppOwnerDTO> ownerList;

	/**
	 * 依赖哪些应用
	 */
	private List<AppDependencyDTO> depAppList;

	/**
	 * 被哪些应用依赖
	 */
	private List<AppDependencyDTO> depByAppList;

	private AppUpgradeInfoDTO latestUpgrade;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppTitle() {
		return appTitle;
	}

	public void setAppTitle(String appTitle) {
		this.appTitle = appTitle;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public Boolean getAuthNeeded() {
		return authNeeded;
	}

	public void setAuthNeeded(Boolean authNeeded) {
		this.authNeeded = authNeeded;
	}

	public SoaStatusEnum getStatus() {
		return status;
	}

	public void setStatus(SoaStatusEnum status) {
		this.status = status;
	}

	public Integer getAddressCount() {
		return addressCount;
	}

	public void setAddressCount(Integer addressCount) {
		this.addressCount = addressCount;
	}

	public Integer getDepAppCount() {
		return depAppCount;
	}

	public void setDepAppCount(Integer depAppCount) {
		this.depAppCount = depAppCount;
	}

	public Integer getDepByAppCount() {
		return depByAppCount;
	}

	public void setDepByAppCount(Integer depByAppCount) {
		this.depByAppCount = depByAppCount;
	}

	public Integer getExpServiceCount() {
		return expServiceCount;
	}

	public void setExpServiceCount(Integer expServiceCount) {
		this.expServiceCount = expServiceCount;
	}

	public Integer getRefServiceCount() {
		return refServiceCount;
	}

	public void setRefServiceCount(Integer refServiceCount) {
		this.refServiceCount = refServiceCount;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public String getJavaDoc() {
		return javaDoc;
	}

	public void setJavaDoc(String javaDoc) {
		this.javaDoc = javaDoc;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public List<AppOwnerDTO> getOwnerList() {
		return ownerList;
	}

	public void setOwnerList(List<AppOwnerDTO> ownerList) {
		this.ownerList = ownerList;
	}

	public List<AppDependencyDTO> getDepAppList() {
		return depAppList;
	}

	public void setDepAppList(List<AppDependencyDTO> depAppList) {
		this.depAppList = depAppList;
	}

	public List<AppDependencyDTO> getDepByAppList() {
		return depByAppList;
	}

	public void setDepByAppList(List<AppDependencyDTO> depByAppList) {
		this.depByAppList = depByAppList;
	}

	public AppUpgradeInfoDTO getLatestUpgrade() {
		return latestUpgrade;
	}

	public void setLatestUpgrade(AppUpgradeInfoDTO latestUpgrade) {
		this.latestUpgrade = latestUpgrade;
	}

	public AppRoleEnum getRole() {
		return role;
	}

	public void setRole(AppRoleEnum role) {
		this.role = role;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
