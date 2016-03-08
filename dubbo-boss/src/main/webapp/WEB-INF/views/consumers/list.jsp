<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <title>服务消费者</title>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <%--<div class="result">--%>
    <%--<div class="tag">--%>
    <%--<c:choose>--%>
    <%--<c:when test="${empty application}">--%>
    <%--<a href="${ctxPath}services"><em>服务</em></a><span>|</span>--%>
    <%--<a href="${ctxPath}addresses" class="on"><em>机器</em></a><span>|</span>--%>
    <%--<a href="${ctxPath}overrides"><em>动态配置</em></a><span></span>--%>
    <%--</c:when>--%>
    <%--<c:otherwise>--%>
    <%--<a href="${ctxPath}services/application/${application}"><em>服务</em></a><span>|</span>--%>
    <%--<a href="${ctxPath}addresses/application/${application}" class="on"><em>机器</em></a><span>|</span>--%>
    <%--<a href="${ctxPath}overrides/application/${application}"><em>动态配置</em></a><span></span>--%>
    <%--</c:otherwise>--%>
    <%--</c:choose>--%>
    <%--</div>--%>
    <%--</div>--%>
    <!--search star-->
    <div class="information">
      <form id="queryForm" method="get" action="">
        <div class="search">
          <div class="search_tit">
            <h2 class="fw fleft f14">服务消费者</h2>
          </div>
          <div class="search_con">
            <ul class="fix">
              <%--<li>--%>
              <%--<label class="text_tit">服务名：</label>--%>
              <%--<select id="service" name="service" class="input_text">--%>
              <%--<option selected="selected" value="">全部</option>--%>
              <%--<c:forEach items="${serviceList}" var="service">--%>
              <%--<option value="${service}" label="${service}"/>--%>
              <%--</c:forEach>--%>
              <%--</select>--%>
              <%--</li>--%>
              <li>
                <label class="text_tit">应用名：</label>
                <select id="application" name="application" class="input_text">
                  <option selected="selected" value="">全部</option>
                  <c:forEach items="${applications}" var="item">
                    <option value="${item}"
                            <c:if test="${item == application}">selected="selected"</c:if>>${item}</option>
                  </c:forEach>
                </select>
              </li>
              <li>
                <label class="text_tit">机器IP：</label>
                <input type="text" id="address" name="address" class="input_text"/>
              </li>
              <li>
                <label class="text_tit help-block">(过滤条件二选一)</label>
              </li>
              <%--<li>--%>
              <%--<label class="text_tit">角色：</label>--%>
              <%--<select id="serviceType" name="type" class="input_text">--%>
              <%--<option selected="selected" value="">全部</option>--%>
              <%--<option value="provider">服务提供者</option>--%>
              <%--<option value="consumer">服务消费者</option>--%>
              <%--</select>--%>
              <%--</li>--%>
            </ul>
            <div class="btn">
              <input type="button" onclick="query()" class="btn_sure fw" value="查 询"/>
              <input type="button" onclick="$('#queryForm')[0].reset();" class="btn_cancel fw"
                     value="清 空"/>
            </div>
            <div class="clearer"></div>
          </div>
        </div>
      </form>
    </div>
    <div class="clearer"></div>
    <%@ include file="/WEB-INF/views/consumers/control/listControl.jsp" %>
  </div>
  <div class="clearer"></div>
  <br/>
</div>
<script type="text/javascript">
  $(function () {
    <c:if test="${info} neq ''">
    MessageBoxExt.alert(${info});
    </c:if>
  });
  function query() {
    window.location.href = GV.ctxPath + "consumers?" + $("#queryForm").serialize();
  }
</script>
</body>
</html>
