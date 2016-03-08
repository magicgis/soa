<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>机器查询</title>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <style type="text/css">
  .list td a {
    white-space: nowrap !important;
  }
  </style>
  <script src="${resourceUrl}/js/addressMgr.js"></script>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <!--search star-->
    <div class="information">
      <form id="serviceQueryForm" method="get" action="${ctxPath}addresses/query">
        <div class="search">
          <div class="search_tit">
            <h2 class="fw fleft f14">机器查询</h2>
          </div>
          <div class="search_con">
            <ul class="fix">
              <li>
                <label class="text_tit">IP地址：</label>
                <input type="text" name="address" value="<c:out value='${address}'/>" class="input_text" />
              </li>
              <li>
                <label class="text_tit">应用：</label>
                <input type="text" name="appName" value="<c:out value='${appName}'/>" class="input_text" />
              </li>
              <li>
                <label class="text_tit">服务：</label>
                <input type="text" name="serviceName" value="<c:out value='${serviceName}'/>" class="input_text" />
              </li>
              <li>
                <label class="text_tit">环境：</label>
                <div class="select_border">
                  <select name="environment" class="select">
                    <option value="">全部</option>
                    <option value="test">内测</option>
                    <option value="product">生产</option>
                  </select>
                </div>
              </li>
              <li>
                <label class="text_tit">状态：</label>
                <div class="select_border">
                  <select name="status" class="select">
                    <option value="">全部</option>
                    <option value="ACTIVE">启用</option>
                    <option value="FORBID">禁用</option>
                  </select>
                </div>
              </li>
              <li>
                <label class="text_tit">应用角色：</label>
                <div class="select_border">
                  <select name="side" class="select">
                    <option value="">全部</option>
                    <option value="provider">提供者</option>
                    <option value="consumer">消费者</option>
                  </select>
                </div>
              </li>
              <li>
                <label class="text_tit">运行角色：</label>
                <div class="select_border">
                  <select name="role" class="select">
                    <option value="">全部</option>
                    <option value="OUT">对外</option>
                    <option value="IN">对内</option>
                  </select>
                </div>
              </li>
              <li>
                <label class="help-block" style="margin-left: 40px;">(优先级：应用-&gt;服务-&gt;IP; IP支持*)</label>
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
    <div class="result">
      <h2 class="fw">查询结果</h2>
      <%@ include file="/WEB-INF/views/addresses/control/query.jsp" %>
      <%@ include file="/WEB-INF/views/common/paginator.jsp" %>
    </div>
  </div>
  <div class="clearer"></div>
  <br/>
</div>
</body>
</html>
