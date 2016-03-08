<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>服务查询</title>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="result">
      <div class="tag">
        <c:choose>
          <c:when test="${empty application}">
            <a href="${ctxPath}services" class="on"><em>服务</em></a><span>|</span>
            <a href="${ctxPath}addresses"><em>机器</em></a><span>|</span>
            <a href="${ctxPath}overrides"><em>动态配置</em></a><span></span>
          </c:when>
          <c:otherwise>
            <a href="${ctxPath}services/application/${application}" class="on"><em>服务</em></a><span>|</span>
            <a href="${ctxPath}addresses/application/${application}"><em>机器</em></a><span>|</span>
            <a href="${ctxPath}overrides/application/${application}"><em>动态配置</em></a><span></span>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
    <!--search star-->
    <div class="information">
      <form id="serviceQueryForm" method="get" action="">
        <div class="search">
          <div class="search_tit">
            <h2 class="fw fleft f14">服务查询</h2>
          </div>
          <div class="search_con">
            <ul class="fix">
              <%--<li>--%>
              <%--<label class="text_tit">服务名：</label>--%>
              <%--<input type="text" id="service" name="service" class="input_text"/>--%>
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
              <%--<li>--%>
              <%--<label class="text_tit">机器IP：</label>--%>
              <%--<input type="text" id="address" name="address" class="input_text"/>--%>
              <%--</li>--%>
              <li>
                <label class="text_tit">角色：</label>
                <select id="type" name="type" class="input_text">
                  <option selected="selected" value="">全部</option>
                  <option value="provider">提供服务</option>
                  <option value="consumer">消费服务</option>
                </select>
              </li>
              <li>
                <label class="text_tit">降级：</label>
                <select id="tolerant" name="tolerant" class="input_text">
                  <option selected="selected" value="">全部</option>
                  <option value="provider">正常</option>
                  <option value="consumer">容错</option>
                  <option value="consumer">屏蔽</option>
                </select>
              </li>
            </ul>
            <div class="btn">
              <input type="button" onclick="queryServices();" class="btn_sure fw" value="查询"/>
              <input type="button" onclick="$('#serviceQueryForm')[0].reset();" class="btn_cancel fw"
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
        <tr>
          <th width="15px"><input type="checkbox" onclick="checkAll('queryTable', 'recordId', this.checked)"/>
          </th>
          <th>服务</th>
          <th width="80px">角色</th>
        </tr>
        <c:forEach items="${services}" var="service">
          <c:set var="isProvider" value="${providerServices.contains(service)}"/>
          <tr <c:if test="${isProvider}">class="success"</c:if>>
            <td><input type="checkbox" name="recordId" value="${service}"/></td>
            <c:choose>
              <c:when test="${isProvider}">
                <td>
                  <a href="${ctxPath}applications/${application}/services/${service}/providers">${service}</a>
                </td>
                <td>提供服务</td>
              </c:when>
              <c:otherwise>
                <td>
                  <a href="${ctxPath}applications/${application}/services/${service}/consumers">${service}</a>
                </td>
                <td>消费服务</td>
              </c:otherwise>
            </c:choose>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
    <c:if test="${viewLogicHelper.isSoaAdmin()}">
      <div style="margin:20px auto auto 20px;height:30px;">
        <ul>
          <li>
                <span id="button">
                    <a href="javascript:void(0)" onclick="batchDisable(this, 0, 'batchDisable')"
                       class="btn_sure_a fw"><b>批量屏蔽</b><em></em></a>
                    <a href="javascript:void(0)" onclick="batchTolerant(this, 0, 'batchTolerant')"
                       class="btn_sure_a fw"><b>批量容错</b><em></em></a>
                    <a href="javascript:void(0)" onclick="batchRecover(this, 0, 'batchRecover')"
                       class="btn_sure_a fw"><b>批量恢复</b><em></em></a>
                </span>
          </li>
        </ul>
      </div>
    </c:if>
  </div>
  <div class="clearer"></div>
  <br/>
</div>
<script type="text/javascript">
  $(function () {
    $("table.list tr").removeClass("even");
  });
  function queryServices() {
    var application = $("#application").val();
    if (application != '') {
      window.location.href = GV.ctxPath + "services/application/" + application + "?" + $("#serviceQueryForm").serialize();
    } else {
      window.location.href = GV.ctxPath + "services?" + $("#serviceQueryForm").serialize();
    }
  }
</script>
</body>
</html>
