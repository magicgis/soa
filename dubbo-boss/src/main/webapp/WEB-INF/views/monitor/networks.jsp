<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>网络监控</title>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <style type="text/css">
  .list td a {
    white-space: nowrap !important;
  }
  </style>
  <script type="text/javascript">
  function deleteNetwork(id) {
    if (!id) {
      return;
    }
    MessageBoxExt.confirm("确认删除？", function () {
      MessageBoxExt.ajax({
        url : GV.ctxPath + 'monitor/networks/delete',
        type : 'POST',
        dataType : "json",
        style : "redirect",
        data : {
          "networkid" : id
        },
        cache : false
      });
    });
  }
  function clearUnknown () {
    MessageBoxExt.confirm("确认清除？", function () {
      MessageBoxExt.ajax({
        url : GV.ctxPath + 'monitor/networks/clearUnknown',
        type : 'POST',
        dataType : "json",
        style : "redirect",
        cache : false
      });
    });
  }
  </script>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <!--search star-->
    <div class="information">
      <form id="serviceQueryForm" method="get" action="${ctxPath}/monitor/networks">
        <div class="search">
          <div class="search_tit">
            <h2 class="fw fleft f14">网络状态查询</h2>
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
                <label class="text_tit">分类：</label>
                <div class="select_border">
                  <select name="type" class="select">
                    <option value="all">全部</option>
                    <option value="consumer">消费者</option>
                    <option value="provider">提供者</option>
                  </select>
                </div>
              </li>
              <li>
                <label class="text_tit">状态：</label>
                <div class="select_border">
                  <select name="status" class="select">
                    <option value="">全部</option>
                    <option value="ACTIVE">正常</option>
                    <option value="DOWN">异常</option>
                    <option value="UNKNOWN">未知</option>
                  </select>
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
    <c:if test="${viewLogicHelper.isSoaAdmin()}">
    <div class="clearer"></div>
    <div class="submenu">
      <div align="right" style="padding-right: 30px; height: 0px;">
        <a href="javascript:void(0);" onclick="clearUnknown();">【清除“未知”数据】</a>
      </div>
    </div>
    </c:if>
    <div class="clearer"></div>
    <div class="result">
      <h2 class="fw">查询结果</h2>
        <table class="list">
          <tbody>
          <tr>
              <th>消费者</th>
              <th width="360px">消费者列表</th>
              <th>提供者</th>
              <th width="360px">提供者列表</th>
              <th>状态</th>
              <c:if test="${viewLogicHelper.isSoaAdmin()}">
              <th>操作</th>
              </c:if>
          </tr>
          <c:forEach items="${result.data}" var="network">
              <tr>
                  <td>
                    <a href="${ctxPath}consumers/detaillist?address=${network.consumer}">${network.consumer}</a>
                  </td>
                  <td>
                    <c:forEach items="${network.consumerAppList}" var="appName">
                      <a href="${ctxPath}applications/detail/${appName}">${appName}</a>
                    </c:forEach>
                  </td>
                  <td>
                    <a href="${ctxPath}providers/detaillist?address=${network.provider}">${network.provider}</a>
                  </td>
                  <td>
                    <c:forEach items="${network.providerAppList}" var="appName">
                      <a href="${ctxPath}applications/detail/${appName}">${appName}</a>
                    </c:forEach>
                  </td>
                  <td>
                    <c:choose>
                      <c:when test="${network.status == 'ACTIVE'}">正常</c:when>
                      <c:when test="${network.status == 'DOWN'}">异常</c:when>
                      <c:otherwise>未知</c:otherwise>
                    </c:choose>
                  </td>
                  <c:if test="${viewLogicHelper.isSoaAdmin()}">
                  <td>
                    <a href="javascript:void(0)" onclick="javascript:deleteNetwork(${network.id});">删除</a>
                  </td>
                  </c:if>
              </tr>
          </c:forEach>
        </tbody>
      </table>
      <%@ include file="/WEB-INF/views/common/paginator.jsp" %>
    </div>
  </div>
  <div class="clearer"></div>
  <br/>
</div>
</body>
</html>
