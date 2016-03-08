package com.yeepay.g3.facade.soa.center.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.utils.common.StringUtils;

/**
 * 服务信息
 *
 * @author：menghao.chen wang.bao
 * @since：2014年7月3日 上午11:53:58
 * @version:
 */
public class ServiceInfoDTO implements Serializable {

	private static final long serialVersionUID = -6728743727174369470L;
	/**
	 * ID
	 */
	private Long id;

	/**
	 * 应用名
	 */
	private String appName;

	/**
	 * 服务接口
	 */
	private String serviceInterface;

	/**
	 * 服务接口
	 */
	private String serviceInterfaceLowcase;

	/**
	 * 服务名
	 */
	private String serviceName;

	/**
	 * 服务描述
	 */
	private String serviceDesc;

	/**
	 * 服务协议
	 */
	private String serviceProtocol;

	/**
	 * 服务签名
	 */
	private String serviceSign;

	/**
	 * 提供者数量
	 */
	private Integer providerCount;

	/**
	 * 消费者数量
	 */
	private Integer consumerCount;

	/**
	 * 状态
	 */
	private SoaStatusEnum status;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 最后更新日期
	 */
	private Date lastModifyDate;

	private List<MethodInfoDTO> methodList;

	private AppInfoDTO appInfo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getServiceInterface() {
		return serviceInterface;
	}

	public void setServiceInterface(String serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	public String getServiceInterfaceLowcase() {
		return serviceInterfaceLowcase;
	}

	public void setServiceInterfaceLowcase(String serviceInterfaceLowcase) {
		this.serviceInterfaceLowcase = serviceInterfaceLowcase;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	public String getServiceProtocol() {
		return serviceProtocol;
	}

	public void setServiceProtocol(String serviceProtocol) {
		this.serviceProtocol = serviceProtocol;
	}

	public String getServiceSign() {
		return serviceSign;
	}

	public void setServiceSign(String serviceSign) {
		this.serviceSign = serviceSign;
	}

	public Integer getProviderCount() {
		return providerCount;
	}

	public void setProviderCount(Integer providerCount) {
		this.providerCount = providerCount;
	}

	public Integer getConsumerCount() {
		return consumerCount;
	}

	public void setConsumerCount(Integer consumerCount) {
		this.consumerCount = consumerCount;
	}

	public SoaStatusEnum getStatus() {
		return status;
	}

	public void setStatus(SoaStatusEnum status) {
		this.status = status;
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

	public List<MethodInfoDTO> getMethodList() {
		return methodList;
	}

	public void setMethodList(List<MethodInfoDTO> methodList) {
		this.methodList = methodList;
	}

	public AppInfoDTO getAppInfo() {
		return appInfo;
	}

	public void setAppInfo(AppInfoDTO appInfo) {
		this.appInfo = appInfo;
	}

	public String getJavaDoc() {
		if (appInfo != null && StringUtils.isNotBlank(appInfo.getJavaDoc())) {
			StringBuilder path = new StringBuilder();
			path.append(appInfo.getJavaDoc());
			path.append("/");
			path.append(appName.replace("-hessian", "-facade"));
			path.append("/");
			path.append(serviceInterface.replaceAll("\\.", "/"));
			path.append(".html");
			return path.toString();
		}
		return null;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
