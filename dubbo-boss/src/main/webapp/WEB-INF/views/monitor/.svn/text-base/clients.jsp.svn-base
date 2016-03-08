<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/views/common/metas.jsp" %>
<html>
<head>
  <title>客户机列表</title>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="information">
      <form id="clientQueryForm" method="get" action="">
        <input type="hidden" name="port" value="${param.port}">

        <div class="search">
          <div class="search_tit">
            <h2 class="fw fleft f14">客户机查询</h2>
          </div>
          <div class="search_con">
            <ul class="fix">
              <li>
                <label class="text_tit">客户机地址：</label>
                <input type="text" name="address" class="input_text" value="${param.address}">
              </li>
            </ul>
            <div class="btn">
              <input type="submit" class="btn_sure fw" value="查询"/>
              <input type="button" onclick="clearAll();" class="btn_cancel fw"
                     value="清空"/>
            </div>
            <div class="clearer"></div>
          </div>
        </div>
      </form>
    </div>
    <div class="clearer"></div>
    <div class="result">
      <h2 class="fw">查询结果</h2>
      <table class="list">
        <tbody>
        <c:forEach items="${result.data}" var="item">
          <tr>
            <td>${item}</td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
      <%@ include file="/WEB-INF/views/common/paginator.jsp" %>
    </div>
    <div class="clearer"></div>
  </div>
</div>
</body>
</html>
