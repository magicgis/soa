<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>配置查询</title>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <script src="${resourceUrl}/js/configs.js"></script>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="result">
      <div class="tag">
        <c:choose>
          <c:when test="${empty application}">
            <a href="${ctxPath}overrides"><em>动态配置</em></a><span></span>
            <a href="${ctxPath}configs/query" class="on"><em>统一配置</em></a><span></span>
          </c:when>
          <c:otherwise>
            <a href="${ctxPath}overrides/application/${application}"><em>动态配置</em></a><span></span>
            <a href="${ctxPath}configs/query?application=${application}" class="on"><em>统一配置</em></a><span></span>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
    <!--search star-->
    <div class="information">
      <form id="serviceQueryForm" method="get" action="${ctxPath}configs/query">
        <div class="search">
          <div class="search_tit">
            <h2 class="fw fleft f14">配置查询</h2>
          </div>
          <div class="search_con">
            <ul class="fix">
              <li>
                <label class="text_tit">配置键：</label>
                <input type="text" name="configKey" value="${configKey}" class="input_text" />
              </li>
              <li>
                <label class="text_tit">配置名(描述)：</label>
                <input type="text" name="configName" value="${configName}" class="input_text" />
              </li>
              <li>
                <label class="text_tit">值类型：</label>
                <div class="select_border">
                  <div class="container">
                    <select id="valueType" name="valueType" class="select">
                      <option selected="selected" value="">全部</option>
                      <c:forEach items="${valueTypes}" var="valueType">
                        <option value="${valueType.key}">${valueType.value}</option>
                      </c:forEach>
                    </select>
                  </div>
                </div>
              </li>
              <li>
                <label class="text_tit">数据类型：</label>
                <div class="select_border">
                  <div class="container">
                    <select id="dataType" name="dataType" class="select">
                      <option selected="selected" value="">全部</option>
                      <c:forEach items="${dataTypes}" var="dataType">
                        <option value="${dataType.key}">${dataType.value}</option>
                      </c:forEach>
                    </select>
                  </div>
                </div>
              </li>
              <li>
                <label class="text_tit">所属应用：</label>
                <div class="select_border">
                  <div class="container">
                    <select id="application" name="application" class="select">
                      <option selected="selected" value="">全部</option>
                      <option value="commoncfg">公用</option>
                      <c:forEach items="${applications}" var="application">
                        <option value="${application.appName}">${application.appName}</option>
                      </c:forEach>
                    </select>
                  </div>
                </div>
              </li>
            </ul>
            <div class="btn">
              <input type="submit" class="btn_sure fw" value="查询"/>
              <input type="button" class="btn_cancel fw" value="清空" onclick="clearAll();">
            </div>
            <div class="clearer"></div>
          </div>
        </div>
      </form>
    </div>
    <div class="clearer"></div>
    <div class="submenu">
      <div align="right" style="padding-right: 30px; height: 0px;">
        <c:if test="${viewLogicHelper.isSoaAdmin()}">
        <a href="javascript:void(0);" id="addConfig">【新增配置】</a>
        </c:if>
      </div>
    </div>
    <div class="clearer"></div>
    <div class="result">
      <h2 class="fw">查询结果</h2>
        <table class="list">
          <tbody>
          <tr>
              <th>配置名</th>
              <th>配置键</th>
              <th width="400px">配置值</th>
              <th>配置类型</th>
              <th>数据类型</th>
              <th>所属系统</th>
              <th>状态</th>
              <c:if test="${viewLogicHelper.isSoaAdmin()}">
              <th width="120px">操作</th>
              </c:if>
          </tr>
          <c:forEach items="${result.data}" var="config">
              <tr>
                  <td>${config.name}</td>
                  <td>${config.key}</td>
                  <td>${config.valueToString()}</td>
                  <td>${config.valueType.desc}</td>
                  <td><c:if test="${config.valueType.name() != 'STRUCTURE'}">${config.dataType.desc}</c:if></td>
                  <td>${config.domain}</td>
                  <td><c:if test="${config.enabled}">启用</c:if><c:if test="${!config.enabled}">禁用</c:if></td>
                  <c:if test="${viewLogicHelper.isSoaAdmin()}">
                  <td>
                    <a href="javascript:void(0)" name="editConfig" configKey="${config.key}" application="${config.domain}">更新</a>
                    <a href="javascript:void(0)" name="configOperate" type="delete" configKey="${config.key}" application="${config.domain}">删除</a>
                    <c:choose>
                      <c:when test="${config.enabled}">
                        <a href="javascript:void(0)" name="configOperate" type="disable" configKey="${config.key}" application="${config.domain}">禁用</a>
                      </c:when>
                      <c:otherwise>
                        <a href="javascript:void(0)" name="configOperate" type="enable" configKey="${config.key}" application="${config.domain}">启用</a>
                      </c:otherwise>
                    </c:choose>
                  </td>
                  </c:if>
              </tr>
          </c:forEach>
        </tbody>
      </table>
      <%@ include file="/WEB-INF/views/common/paginator.jsp" %>
    </div>
  </div>
</div>
<c:if test="${viewLogicHelper.isSoaAdmin()}">
<%@ include file="/WEB-INF/views/configs/control/editConfigControl.jsp" %>
</c:if>
</body>
</html>
