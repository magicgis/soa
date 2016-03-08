<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
        <table class="list">
          <tbody>
          <tr>
              <c:if test="${viewLogicHelper.isSoaAdmin()}">
              <th width="15px"><input type="checkbox" onclick="checkAll('queryTable', 'recordId', this.checked)"/></th>
              </c:if>
              <th>应用名</th>
              <th>应用标题</th>
              <th>机器数</th>
              <th>依赖数</th>
              <th>被依赖数</th>
              <th>角色</th>
              <th>暴露服务数</th>
              <th>依赖服务数</th>
              <th width="250px">javaDoc</th>
              <th>负责人</th>
              <th width="40px">状态</th>
              <th width="80px">最后同步时间</th>
              <c:if test="${viewLogicHelper.isSoaAdmin()}">
              <th width="80px">操作</th>
              </c:if>
          </tr>
          <c:forEach items="${result.data}" var="app">
              <tr>
                  <c:if test="${viewLogicHelper.isSoaAdmin()}">
                  <td><input type="checkbox" name="recordId" value="${app.appName}"/></td>
                  </c:if>
                  <td>
                      <a href="${ctxPath}applications/detail/${app.appName}">${app.appName}</a>
                  </td>
                  <td>
                      <a href="${ctxPath}applications/detail/${app.appName}">${app.appTitle}</a>
                  </td>
                  <td>
                      <a href="${ctxPath}addresses/application/${app.appName}">${app.addressCount}</a>
                  </td>
                  <td>
                    <a href="${ctxPath}monitor/dependencies?application=${app.appName}">${app.depAppCount}</a>
                  </td>
                  <td>
                    <a href="${ctxPath}monitor/dependencies?application=${app.appName}&reverse=true">${app.depByAppCount}</a>
                  </td>
                  <td>
                    <c:choose>
                      <c:when test="${app.role == 'PROVIDER'}">提供者</c:when>
                      <c:when test="${app.role == 'CONSUMER'}">消费者</c:when>
                      <c:otherwise>提供者、消费者</c:otherwise>
                    </c:choose>
                  </td>
                  <td>
                     <a href="${ctxPath}services/query?appName=${app.appName}">${app.expServiceCount}</a>
                  </td>
                  <td>
                     <a href="${ctxPath}consumers/list?application=${app.appName}">${app.refServiceCount}</a>
                  </td>
                  <td>
                    <c:if test="${app.javaDoc != null && app.javaDoc != ''}">
                      <a href="${app.javaDoc}" target="_blank">${app.javaDoc}</a>
                    </c:if>
                  </td>
                  <td>
                    <c:forEach items="${app.ownerList}" var="owner">
                      ${owner.ownerName}<br/>
                    </c:forEach>
                  </td>
                  <td>${viewLogicHelper.getStatusDesc(app.status)}</td>
                  <td><fmt:formatDate value="${app.lastModifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                  <c:if test="${viewLogicHelper.isSoaAdmin()}">
                  <td>
                    <c:choose>
                      <c:when test="${app.status=='ACTIVE'}">
                        <a href="javascript:void(0)" name="appOperate" type="forbid" appName="${app.appName}">禁用</a>
                      </c:when>
                      <c:when test="${app.status=='FORBID'}">
                        <a href="javascript:void(0)" name="appOperate" type="active" appName="${app.appName}">启用</a>
                      </c:when>
                      <c:when test="${app.status=='OFFLINE'}">
                        <a href="javascript:void(0)" name="appOperate" type="active" appName="${app.appName}">启用</a>
                        <a href="javascript:void(0)" name="appOperate" type="delete" appName="${app.appName}">删除</a>
                      </c:when>
                      <c:when test="${app.status=='DOWN'}">
                        <a href="javascript:void(0)" name="appOperate" type="offline" appName="${app.appName}">下线</a>
                        <a href="javascript:void(0)" name="appOperate" type="delete" appName="${app.appName}">删除</a>
                      </c:when>
                    </c:choose>
                  </td>
                  </c:if>
              </tr>
          </c:forEach>
        </tbody>
      </table>
