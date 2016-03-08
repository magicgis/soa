<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <title>负载均衡</title>
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
            <h2 class="fw fleft f14">负载均衡</h2>
          </div>
          <div class="search_con">
            <ul class="fix">
              <li>
                <label class="text_tit">服务名：</label>
                <select id="service" name="service" class="input_text" onchange="loadMethod()">
                  <option value="" label="全部"/>
                  <c:forEach items="${serviceList}" var="service">
                    <option value="${service}" label="${service}"/>
                  </c:forEach>
                </select>
              </li>
              <li>
                <label class="text_tit">服务方法：</label>
                <select id="method" name="method" class="input_text">
                  <option value="" label="全部"/>
                  <c:forEach items="${methodList}" var="method">
                    <option value="${method}" label="${method}"/>
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
                <label class="text_tit">机器IP：</label>
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
    <div class="submenu">
      <div align="right" style="padding-right: 30px; height: 0px;">
        <a href="${ctxPath}loadbalances/add">新增负载均衡</a>
      </div>
    </div>
    <%@ include file="/WEB-INF/views/loadbalances/control/listControl.jsp" %>
  </div>
  <div class="clearer"></div>
  <br/>
</div>
<script type="text/javascript">
  $(function () {
    <c:if test="${info} neq ''">
    MessageBoxExt.alert(${info});
    </c:if>
    $('.chosen').chosen({width: "650px"});
    loadMethod();
  });
  function query() {
    window.location.href = GV.ctxPath + "loadbalances?" + $("#queryForm").serialize();
  }
  function loadMethod() {
    var service = $("#service").val();
    if (service != "") {
      MessageBoxExt.ajax({
        url: GV.ctxPath + "providers/service/" + service + "/methods",
        type: "GET",
        style: "NONE",
        success: function (response) {
          console.log(response);
          if (response.status === 'success') {
            $("#method").empty();
            $("#method").append("<option value=''>*</option>");
            $.each(response.data.methods, function (index, item) {
              $("#method").append("<option value='" + item + "'>" + item + "</option>");
            });

            <c:if test="${method != ''}">
            <c:forEach items="${method}" var="m">
            console.log("item:${m}");
            $("#method option[value='${m}']").attr("selected", "selected");
            </c:forEach>
            </c:if>

            $("#method").trigger("chosen:updated");
          }
        }
      });
    }
  }
</script>
</body>
</html>
