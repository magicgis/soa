<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>业务日志查询</title>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <script type="text/javascript">
  $(document).ready(function () {
      DatePickerExt.between("createTimeStart", "createTimeEnd", {maxDate: 0, showButtonPanel: true});
  });
  </script>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <!--search star-->
    <div class="information">
      <form id="logQueryForm" method="get" action="${ctxPath}logs/query">
        <div class="search">
          <div class="search_tit">
            <h2 class="fw fleft f14">业务日志查询</h2>
          </div>
          <div class="search_con">
            <ul class="fix">
              <li>
                <label class="text_tit">机器：</label>
                <input type="text" name="host" value="${host}" class="input_text" />
              </li>
              <li>
                <label class="text_tit">应用：</label>
                <div class="select_border">
                  <div class="container">
                    <select name="application" class="select">
                      <option selected="selected" value="">全部</option>
                      <c:forEach items="${applications}" var="application">
                        <option value="${application.appName}">${application.appName}</option>
                      </c:forEach>
                    </select>
                  </div>
                </div>
              </li>
              <li>
                <label class="text_tit">关键字：</label>
                <input type="text" name="keyword" value="${keyword}" class="input_text" />
              </li>
              <li>
                <label class="text_tit">Logger：</label>
                <input type="text" name="loggerName" value="${loggerName}" class="input_text" />
              </li>
              <li>
                <label class="text_tit">GUID：</label>
                <input type="text" name="guid" value="${guid}" class="input_text" />
              </li>
              <li class="between">
                <label class="text_tit">创建日期：</label>
                <input type="text" readonly="true" value="${createTimeStart}" name="createTimeStart" class="input_text" id="createTimeStart"/>
                <span>至</span>
                <input type="text" readonly="true" value="${createTimeEnd}" name="createTimeEnd" class="input_text" id="createTimeEnd"/>
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
        <q:table queryService="logQueryService" queryKey="queryBizLog" formId="logQueryForm" class="list" pageSize="20">
          <q:nodata>无符合条件的记录</q:nodata>
          <q:column title="机器" value="${host}" width="80px"/>
          <q:column title="应用" value="${application}" width="90px"/>
          <q:column title="GUID" value="${guid}" width="95px"/>
          <q:column title="线程号" value="${thread_name}" width="80px"/>
          <q:column title="创建时间" escapeHtml="false" width="90px">
            ${_messageFormater.formatDate(create_time)}<br>
          </q:column>
          <q:column title="Logger" value="${logger_name}" width="180px"/>
          <q:column title="日志内容" value="${log_content}"/>
        </q:table>
    </div>
  </div>
</div>
</body>
</html>
