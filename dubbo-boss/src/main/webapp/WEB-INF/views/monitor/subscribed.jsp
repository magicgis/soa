<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/views/common/metas.jsp" %>
<html>
<head>
  <title>已订阅列表</title>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="information">
      <form id="registeredQueryForm" method="get" action="">
        <input type="hidden" name="registry" value="${param.registry}">

        <div class="search">
          <div class="search_tit">
            <h2 class="fw fleft f14">已订阅列表</h2>
          </div>
          <div class="search_con">
            <ul class="fix">
              <li>
                <label class="text_tit">订阅服务路径：</label>
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
      <table class="list_info">
        <tbody>
        <tr>
          <th class="info_tit">已订阅服务（${result.paginator.totalCount}）</th>
        </tr>
        <c:forEach items="${result.data}" var="item">
          <tr>
            <td class="info_con">${item}</td>
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
