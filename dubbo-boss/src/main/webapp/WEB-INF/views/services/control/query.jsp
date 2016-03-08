<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
        <table class="list">
          <tbody>
          <tr>
              <c:if test="${viewLogicHelper.isSoaAdmin()}">
              <th width="15px"><input type="checkbox" onclick="checkAll('queryTable', 'recordId', this.checked)"/></th>
              </c:if>
              <th width="200px">应用名</th>
              <th width="200px">服务名</th>
              <th width="350px">接口</th>
              <th>协议</th>
              <th>提供者</th>
              <th>消费者</th>
              <th>负责人</th>
              <th width="40px">状态</th>
              <th width="80px">最后同步时间</th>
              <c:if test="${viewLogicHelper.isSoaAdmin()}">
              <th width="80px">操作</th>
              </c:if>
          </tr>
          <c:forEach items="${result.data}" var="service">
              <tr>
                  <c:if test="${viewLogicHelper.isSoaAdmin()}">
                  <td><input type="checkbox" name="recordId" value="${service.serviceInterface}"/></td>
                  </c:if>
                  <td>
                      <a href="${ctxPath}applications/detail/${service.appName}">${service.appName}</a>
                  </td>
                  <td>
                      <a href="${ctxPath}services/detail/${service.serviceInterface}">${service.serviceName}</a>
                  </td>
                  <td>
                      <a href="${ctxPath}services/detail/${service.serviceInterface}">${service.serviceInterface}</a>
                  </td>
                  <td>${viewLogicHelper.breakLine(service.serviceProtocol, ",")}</td>
                  <td>
                      <a href="${ctxPath}providers?service=${service.serviceInterface}">${service.providerCount}</a>
                  </td>
                  <td>
                      <a href="${ctxPath}consumers?service=${service.serviceInterface}">${service.consumerCount}</a>
                  </td>
                  <td>
                    <c:forEach items="${service.appInfo.ownerList}" var="owner">
                      ${owner.ownerName}<br/>
                    </c:forEach>
                  </td>
                  <td>${viewLogicHelper.getStatusDesc(service.status)}</td>
                  <td><fmt:formatDate value="${service.lastModifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                  <c:if test="${viewLogicHelper.isSoaAdmin()}">
                  <td>
                    <c:choose>
                      <c:when test="${service.status=='ACTIVE'}">
                        <a href="javascript:void(0)" name="serviceOperate" type="forbid" serviceId="${service.id}">禁用</a>
                      </c:when>
                      <c:when test="${service.status=='FORBID'}">
                        <a href="javascript:void(0)" name="serviceOperate" type="active" serviceId="${service.id}">启用</a>
                      </c:when>
                      <c:when test="${service.status=='OFFLINE'}">
                        <a href="javascript:void(0)" name="serviceOperate" type="active" serviceId="${service.id}">启用</a>
                        <a href="javascript:void(0)" name="serviceOperate" type="delete" serviceId="${service.id}">删除</a>
                      </c:when>
                      <c:when test="${service.status=='DOWN'}">
                        <a href="javascript:void(0)" name="serviceOperate" type="offline" serviceId="${service.id}">下线</a>
                        <a href="javascript:void(0)" name="serviceOperate" type="delete" serviceId="${service.id}">删除</a>
                      </c:when>
                    </c:choose>
                  </td>
                  </c:if>
              </tr>
          </c:forEach>
        </tbody>
      </table>
