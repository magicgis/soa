<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>路由规则</title>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <!--search star-->
    <div class="information">
      <form id="serviceQueryForm" method="get" action="">
        <div class="search">
          <div class="search_tit">
            <h2 class="fw fleft f14">路由规则</h2>
          </div>
          <div class="search_con">
            <ul class="fix">
              <li>
                <label class="text_tit">服务名：</label>
                <select id="service" name="service" class="input_text">
                  <option selected="selected" value="">全部</option>
                  <c:forEach items="${serviceList}" var="service">
                    <option value="${service}" label="${service}"/>
                  </c:forEach>
                </select>
              </li>
              <%--<li>--%>
              <%--<label class="text_tit">应用名：</label>--%>
              <%--<select id="application" name="application" class="input_text">--%>
              <%--<option selected="selected" value="">全部</option>--%>
              <%--<c:forEach items="${applications}" var="item">--%>
              <%--<option value="${item}"--%>
              <%--<c:if test="${item == application}">selected="selected"</c:if>>${item}</option>--%>
              <%--</c:forEach>--%>
              <%--</select>--%>
              <%--</li>--%>
              <li>
                <label class="text_tit">机器：</label>
                <input type="text" id="address" name="address" class="input_text"/>
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
              <input type="button" class="btn_sure fw" value="查询"/>
              <input type="button" onclick="$('#serviceQueryForm')[0].reset();" class="btn_cancel fw"
                     value="清空"/>
            </div>
            <div class="clearer"></div>
          </div>
        </div>
      </form>
    </div>
    <div class="clearer"></div>
    <div class="submenu">
      <div align="right" style="padding-right: 30px; height: 0px;">
        <a href="${ctxPath}routes/add">新增路由规则</a>
      </div>
    </div>
    <%@ include file="/WEB-INF/views/routes/control/listControl.jsp" %>
  </div>
  <div class="clearer"></div>
  <br/>
</div>
<script type="text/javascript">
  $(function () {
    $("table.list tr").removeClass("even");
  });
</script>
</body>
</html>
