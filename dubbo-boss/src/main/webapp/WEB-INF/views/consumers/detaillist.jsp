<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>消费者列表</title>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <script src="${resourceUrl}/js/registryMgr.js"></script>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="result">
      <h2 class="fw">Consumers(${consumers.size()})</h2>
      <table class="list">
        <tbody>
          <tr>
            <th width="90px">机器</th>
            <th width="200px">服务</th>
            <th>URL</th>
            <c:if test="${viewLogicHelper.isSoaAdmin()}">
            <th width="80px">操作</th>
            </c:if>
          </tr>
          <c:forEach items="${consumers}" var="consumer">
            <c:set value="${consumer.toUrl()}" var="url"></c:set>
            <tr>
              <td>
                <a href="${ctxPath}addresses/query?address=${consumer.address}">${consumer.address}</a>
              </td>
              <td>
                <a href="${ctxPath}services/detail/${consumer.service}">${consumer.service}</a>
              </td>
              <td>
                <a href="${ctxPath}consumers/${consumer.id}">${url}</a>
              </td>
              <c:if test="${viewLogicHelper.isSoaAdmin()}">
              <td>
                <%--
                <a href="javascript:void(0)" name="unsubscribe" objId="${consumer.id}">取消订阅</a>
                --%>
              </td>
              </c:if>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
  <div class="clearer"></div>
  <br/>
</div>
</body>
</html>
