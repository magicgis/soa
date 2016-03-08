<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <title>查看消费者</title>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="information">
      <div class="setup">
        <h1 class="fw f14">消费者详情(${consumer.service})</h1>

      </div>
      <div class="clearer"></div>
      <div class="input_cont">
        <ul>
          <li>
            <label class="text_tit">机器：</label>
            <label class="text_tit" style="width:86%;text-align:left;">${consumer.address}</label>
          </li>
          <li>
            <label class="text_tit">进程号：</label>
            <label class="text_tit" style="width:86%;text-align:left;">${paramMap.get('pid')}</label>
          </li>
          <li>
            <label class="text_tit">应用名：</label>
            <label class="text_tit" style="width:86%;text-align:left;">${paramMap.get('application')}</label>
          </li>
          <li>
            <label class="text_tit">服务名：</label>
            <label class="text_tit" style="width:86%;text-align:left;">${consumer.service}</label>
          </li>
          <li>
            <label class="text_tit">接口名：</label>
            <label class="text_tit" style="width:86%;text-align:left;">${paramMap.get('interface')}</label>
          </li>
          <li>
            <label class="text_tit">方法列表：</label>
            <label class="text_tit" style="width:86%;text-align:left;">${paramMap.get('methods')}</label>
          </li>
          <li>
            <label class="text_tit">消费者地址：</label>
            <label class="text_tit"
                   style="width:86%;text-align:left;">consumer://${consumer.address}/${consumer.service}?${consumer.parameters}</label>
          </li>
          <li>
            <label class="text_tit">动态配置：</label>
            <label class="text_tit" style="width:86%;text-align:left;">${consumer.override.params}</label>
          </li>
          <li>
            <label class="text_tit">协议：</label>
            <label class="text_tit" style="width:86%;text-align:left;">${paramMap.get('protocol')}</label>
          </li>
          <li>
            <label class="text_tit">协议版本：</label>
            <label class="text_tit" style="width:86%;text-align:left;">${paramMap.get('dubbo')}</label>
          </li>
          <li>
            <label class="text_tit">时间戳：</label>
            <label class="text_tit" style="width:86%;text-align:left;">${paramMap.get('timestamp')}</label>
          </li>
          <li>
            <label class="text_tit">服务版本：</label>
            <label class="text_tit" style="width:86%;text-align:left;">${paramMap.get('revision')}</label>
          </li>
          <li>
            <label class="text_tit">所属端：</label>
            <label class="text_tit" style="width:86%;text-align:left;">${paramMap.get('side')}</label>
          </li>
          <li>
            <label class="text_tit">检查更新：</label>
            <label class="text_tit" style="width:86%;text-align:left;">${paramMap.get('check')}</label>
          </li>
          <li>
            <label class="text_tit">集群：</label>
            <label class="text_tit" style="width:86%;text-align:left;">${paramMap.get('cluster')}</label>
          </li>
          <li>
            <label class="text_tit">可用性：</label>
            <label class="text_tit" style="width:86%;text-align:left;">${paramMap.get('available')}</label>
          </li>
          <li>
            <label class="text_tit">****：</label>
            <label class="text_tit" style="width:86%;text-align:left;">${paramMap}</label>
          </li>
          <li>
            <label class="text_tit">访问：</label>
            <label class="text_tit" style="width:86%;text-align:left;">
              <c:choose>
                <c:when test="${consumer.forbid}">
                  <span class="danger">已禁止</span>
                </c:when>
                <c:otherwise>
                  <span class="success">允许</span>
                </c:otherwise>
              </c:choose>
            </label>
          </li>
          <li>
            <label class="text_tit">降级：</label>
            <label class="text_tit" style="width:86%;text-align:left;">
              <c:choose>
                <c:when test="${consumer.mock eq 'force%3Areturn+null'}">
                  <span class="danger">已屏蔽</span>
                </c:when>
                <c:when test="${consumer.mock eq 'fail%3Areturn+null'}">
                  <span class="warning">已容错</span>
                </c:when>
                <c:otherwise>
                  <span>未降级${consumer.mock}</span>
                </c:otherwise>
              </c:choose>
            </label>
          </li>
          <li>
            <label class="text_tit" id="routed">路由：</label>
            <label class="text_tit" style="width:86%;text-align:left;">
              <c:choose>
                <c:when test="${null != consumer.routes && fn:length(consumer.routes) > 0}">
                  <%--<a href="${ctxPath}consumers/${consumer.id}"><span class="success">已路由(${fn:length(consumer.routes)})</span></a>--%>
                  <table>
                    <tr>
                      <th>路由名称</th>
                      <th>路由规则</th>
                    </tr>
                    <c:forEach items="${consumer.routes}" var="route">
                      <tr>
                        <td>${route.name}</td>
                        <td>${route.matchRule} => ${route.filterRule}</td>
                      </tr>
                    </c:forEach>
                  </table>
                </c:when>
                <c:otherwise>
                  <span>未路由</span>
                </c:otherwise>
              </c:choose>
            </label>
          </li>
          <li>
            <label class="text_tit" id="notified">通知：</label>
            <label class="text_tit" style="width:86%;text-align:left;">
              <c:choose>
                <c:when test="${null != consumer.providers && fn:length(consumer.providers) > 0}">
                  <%--<a href="${ctxPath}consumers/${consumer.id}"><span--%>
                  <%--class="success">已通知(${fn:length(consumer.providers)})</span></a>--%>
                  <table>
                    <tr>
                      <th>服务地址</th>
                    </tr>
                    <c:forEach items="${consumer.providers}" var="provider">
                      <tr>
                        <td>${provider.url}?${provider.parameters}</td>
                      </tr>
                    </c:forEach>
                  </table>
                </c:when>
                <c:otherwise>
                  <span>未通知</span>
                </c:otherwise>
              </c:choose>
            </label>
          </li>
          <li>
            <label class="text_tit">&nbsp;</label>
            <input type="button" value="返 回" class="btn_cancel fw" onclick="window.history.go(-1)">
          </li>
        </ul>
        <div class="clearer"></div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
