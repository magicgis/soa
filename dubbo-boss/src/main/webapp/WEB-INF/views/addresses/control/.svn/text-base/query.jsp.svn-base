<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
        <table class="list">
          <tbody>
          <tr>
              <th>机器IP</th>
              <th width="600px">应用列表</th>
              <th>应用角色</th>
              <th>环境</th>
              <th>状态</th>
              <th>运行角色</th>
              <c:if test="${viewLogicHelper.isSoaAdmin()}">
              <th>操作</th>
              </c:if>
          </tr>
          <c:forEach items="${result.data}" var="address">
              <tr>
                  <td>
                    <c:if test="${address.side=='provider'}">
                      <a href="${ctxPath}providers/detaillist?address=${address.address}">${address.address}</a>
                    </c:if>
                    <c:if test="${address.side=='consumer'}">
                      <a href="${ctxPath}consumers/detaillist?address=${address.address}">${address.address}</a>
                    </c:if>
                  </td>
                  <td>
                    <c:forEach items="${address.appList}" var="appName">
                      <a href="${ctxPath}applications/detail/${appName}">${appName}</a>
                    </c:forEach>
                  </td>
                  <td>
                    <c:if test="${address.side=='provider'}">提供者</c:if>
                    <c:if test="${address.side=='consumer'}">消费者</c:if>
                  </td>
                  <td>
                    <c:choose>
                      <c:when test="${address.environment=='test'}">内测</c:when>
                      <c:when test="${address.environment=='product'}">生产</c:when>
                      <c:otherwise>${address.environment}</c:otherwise>
                    </c:choose>
                  </td>
                  <td>${viewLogicHelper.getStatusDesc(address.status)}</td>
                  <td>
                    <c:if test="${address.role=='IN'}">对内</c:if>
                    <c:if test="${address.role=='OUT'}">对外</c:if>
                  </td>
                  <c:if test="${viewLogicHelper.isSoaAdmin()}">
                  <td>
                    <a href="javascript:void(0)" name="addressOperate" type="disable" address="${address.address}" side="${address.side}">禁用</a>
                    <a href="javascript:void(0)" name="addressOperate" type="enable" address="${address.address}" side="${address.side}">启用</a>
                    <c:choose>
                      <c:when test="${address.environment=='test' && address.role=='OUT'}">
                        <a href="javascript:void(0)" name="addressOperate" type="in" address="${address.address}">对内</a>
                      </c:when>
                      <c:when test="${address.environment=='test' && address.role=='IN'}">
                        <a href="javascript:void(0)" name="addressOperate" type="out" address="${address.address}">对外</a>
                      </c:when>
                    </c:choose>
                  </td>
                  </c:if>
              </tr>
          </c:forEach>
        </tbody>
      </table>
