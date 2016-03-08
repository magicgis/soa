<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>提供者列表</title>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <script src="${resourceUrl}/js/registryMgr.js"></script>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="result">
      <h2 class="fw">Providers(${providers.size()})</h2>
      <table class="list">
        <tbody>
          <tr>
            <th width="90px">机器</th>
            <th width="60px">协议</th>
            <th width="200px">服务</th>
            <th>URL</th>
            <c:if test="${viewLogicHelper.isSoaAdmin()}">
            <th width="80px">操作</th>
            </c:if>
          </tr>
          <c:forEach items="${providers}" var="provider">
            <c:set value="${provider.toUrl()}" var="url"></c:set>
            <tr>
              <td>
                <a href="${ctxPath}addresses/query?address=${provider.address}">${provider.address}</a>
              </td>
              <td>${url.protocol}</td>
              <td>
                <a href="${ctxPath}services/detail/${provider.service}">${provider.service}</a>
              </td>
              <td>
                <a href="${ctxPath}providers/${provider.id}">${url}</a>
              </td>
              <c:if test="${viewLogicHelper.isSoaAdmin()}">
              <td>
                <%--
                <a href="javascript:void(0)" name="unregister" objId="${provider.id}">取消注册</a>
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
