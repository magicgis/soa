<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>应用监控</title>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <script src="${resourceUrl}/js/registryMgr.js"></script>
  <script type="text/javascript">
  $(document).ready(function () {
    $(".list td:contains('No ')").css('color', 'blue');
    $(".list tr:contains('提供者')").removeClass('even');
    $(".list tr:contains('提供者')").addClass('success');
  });
  </script>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <!--search star-->
    <div class="clearer"></div>
    <div class="submenu">
      <div align="right" style="padding-right: 30px; height: 0px;">
        <a href="${ctxPath}applications/dependency">【全局依赖图】</a>
        <c:if test="${viewLogicHelper.isSoaAdmin()}">
        <a href="javascript:void(0);" id="syncRegistry">【同步注册中心】</a>
        <a href="javascript:void(0);" id="clearCache">【清除缓存】</a>
        </c:if>
      </div>
    </div>
    <div class="clearer"></div>
    <div class="result">
      <h2 class="fw">Applications(${applications.size()})</h2>
      <table class="list">
        <tbody>
          <tr>
              <th>应用</th>
              <th>机器数</th>
              <th>暴露服务数</th>
              <th>引用服务数</th>
              <th>依赖数</th>
              <th>被依赖数</th>
              <th>角色</th>
          </tr>
          <c:forEach items="${applications}" var="app">
            <tr>
              <td>
                <a href="${ctxPath}applications/detail/${app.appName}">${app.appName}</a>
              </td>
              <td>
                <a href="${ctxPath}addresses/application/${app.appName}">${app.addressCount}</a>
              </td>
              <td>
                <c:choose>
                <c:when test="${app.expServiceCount > 0}">
                <a href="${ctxPath}providers/detaillist?application=${app.appName}">${app.expServiceCount}</a>
                </c:when>
                <c:otherwise>-</c:otherwise>
                </c:choose>
              </td>
              <td>
                <c:choose>
                <c:when test="${app.refServiceCount > 0}">
                <a href="${ctxPath}consumers/detaillist?application=${app.appName}">${app.refServiceCount}</a>
                </c:when>
                <c:otherwise>-</c:otherwise>
                </c:choose>
              </td>
              <td>
                <c:choose>
                <c:when test="${app.depAppCount > 0}">
                <a href="${ctxPath}monitor/dependencies?application=${app.appName}">${app.depAppCount}</a>
                </c:when>
                <c:otherwise>-</c:otherwise>
                </c:choose>
              </td>
              <td>
                <c:choose>
                <c:when test="${app.depByAppCount > 0}">
                <a href="${ctxPath}monitor/dependencies?reverse=true&application=${app.appName}">${app.depByAppCount}</a>
                </c:when>
                <c:otherwise>-</c:otherwise>
                </c:choose>
              </td>
              <td>
                <c:choose>
                  <c:when test="${app.role == 'PROVIDER'}">提供者</c:when>
                  <c:when test="${app.role == 'CONSUMER'}">消费者</c:when>
                  <c:otherwise>提供者、消费者</c:otherwise>
                </c:choose>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</div>
</body>
</html>
