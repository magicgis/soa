<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>依赖关系</title>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="result">
      <h2 class="fw">
      <c:choose>
        <c:when test="${reverse}">Used By(${depCount})</c:when>
        <c:otherwise>Depends On(${depCount})</c:otherwise>
      </c:choose>
      </h2>
      <table class="list">
        <tbody>
          <tr>
            <th>${application}</th>
          </tr>
          <c:forEach items="${dependencies}" var="dep">
            <tr>
              <td>
                <c:forEach begin="1" end="${dep.level}" step="1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|</c:forEach>
                <c:choose>
                  <c:when test="${reverse}">&lt;--</c:when>
                  <c:otherwise>--&gt;</c:otherwise>
                </c:choose>
                <a href="${ctxPath}applications/detail/${dep.application}">${dep.application}</a>
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
