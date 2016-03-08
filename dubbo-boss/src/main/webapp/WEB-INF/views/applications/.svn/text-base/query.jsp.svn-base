<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>应用查询</title>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <script src="${resourceUrl}/js/appMgr.js"></script>
  <script src="${resourceUrl}/js/registryMgr.js"></script>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <!--search star-->
    <div class="information">
      <form id="serviceQueryForm" method="get" action="${ctxPath}applications/query">
        <div class="search">
          <div class="search_tit">
            <h2 class="fw fleft f14">应用查询</h2>
          </div>
          <div class="search_con">
            <ul class="fix">
              <li>
                <label class="text_tit">应用：</label>
                <input type="text" name="keyword" value="${keyword}" class="input_text" />
              </li>
              <li>
                <label class="text_tit">负责人：</label>
                <input type="text" name="ownerName" value="${ownerName}" class="input_text" />
              </li>
            </ul>
            <div class="btn">
              <input type="submit" class="btn_sure fw" value="查询"/>
              <input type="button" class="btn_cancel fw" value="清空" onclick="clearAll();">
              <span class="help-block">(此处查询静态数据，统计值与详情页动态数据可能有差异)</span>
            </div>
            <div class="clearer"></div>
          </div>
        </div>
      </form>
    </div>
    <div class="clearer"></div>
    <div class="submenu">
      <div align="right" style="padding-right: 30px; height: 0px;">
        <a href="${ctxPath}applications/dependency">【全局依赖图】</a>
        <c:if test="${viewLogicHelper.isSoaAdmin()}">
        <a href="javascript:void(0);" id="syncRegistry">【同步注册中心】</a>
        <a href="javascript:void(0);" id="clearCache">【清除缓存】</a>
        <a href="javascript:void(0);" id="addApp">【注册应用】</a>
        <a href="javascript:void(0);" id="moveJavadoc">【迁移Javadoc】</a>
        </c:if>
      </div>
    </div>
    <div class="clearer"></div>
    <div class="result">
      <h2 class="fw">查询结果</h2>
      <%@ include file="/WEB-INF/views/applications/control/query.jsp" %>
      <%@ include file="/WEB-INF/views/common/paginator.jsp" %>
    </div>
  </div>
</div>
<c:if test="${viewLogicHelper.isSoaAdmin()}">
<%@ include file="/WEB-INF/views/applications/control/editAppControl.jsp" %>
<%@ include file="/WEB-INF/views/applications/control/moveJavadocControl.jsp" %>
</c:if>
</body>
</html>
