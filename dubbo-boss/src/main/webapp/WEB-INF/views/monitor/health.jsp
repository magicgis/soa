<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>应用健康检查</title>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <style type="text/css">
  .list td a {
    white-space: nowrap !important;
  }
  .list td img {
    width : 25px;
  }
  .list td textarea {
    width : 100%;
    border : none;
    min-height : 200px;
  }
  </style>
  <script src="${resourceUrl}/js/health.js"></script>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <!--search star-->
    <div class="information">
      <form id="serviceQueryForm" method="get" action="${ctxPath}monitor/health">
        <div class="search">
          <div class="search_tit">
            <h2 class="fw fleft f14">应用健康检查</h2>
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
                <label class="text_tit">状态：</label>
                <div class="select_border">
                  <select name="status" class="select">
                    <option value="">全部</option>
                    <option value="OK">正常</option>
                    <option value="WARN">警告</option>
                    <option value="ERROR">异常</option>
                    <option value="UNKNOW">未知</option>
                  </select>
                </div>
              </li>
              <li>
                <label class="text_tit">应用类型：</label>
                <div class="select_border">
                  <select name="side" class="select">
                    <option value="both" <c:if test="${side == 'both'}">selected="selected"</c:if>>全部</option>
                    <option value="provider" <c:if test="${side == 'provider'}">selected="selected"</c:if>>提供者</option>
                    <option value="consumer" <c:if test="${side == 'consumer'}">selected="selected"</c:if>>消费者</option>
                  </select>
                </div>
              </li>
            </ul>
            <div class="btn">
              <input type="button" class="btn_sure fw" value="查询" onclick="doSearch();"/>
              <input type="button" class="btn_cancel fw" value="清空" onclick="clearAll();doSearch();">
            </div>
            <div class="clearer"></div>
          </div>
        </div>
      </form>
    </div>
    <div class="clearer"></div>
    <div class="result">
      <h2 class="fw">查询结果(<c:out value="${result.size()}"></c:out>)</h2>
        <table class="list table table-condensed table-hover table-striped" id="health">
          <thead>
            <tr>
              <th data-column-id="address" width="200px">机器</th>
              <th data-column-id="appName" width="200px">应用</th>
              <th data-column-id="status"  width="80px">健康状态</th>
              <th data-column-id="detail">详情</th>
            </tr>
          </thead>
          <tbody>
          <c:forEach items="${result.data}" var="address">
              <c:forEach items="${address.appList}" var="appName">
              <tr address="${address.address}" appName="${appName}">
                  <td>
                    <c:if test="${address.side=='provider'}">
                      <a href="${ctxPath}providers/detaillist?address=${address.address}">${address.address}</a>
                    </c:if>
                    <c:if test="${address.side=='consumer'}">
                      <a href="${ctxPath}consumers/detaillist?address=${address.address}">${address.address}</a>
                    </c:if>
                  </td>
                  <td>
                      <a href="${ctxPath}applications/detail/${appName}">${appName}</a>
                  </td>
                  <td>
                    <img style="display:none;" name="OK" title="正常" src="${resourceUrl}/images/ico_success.png"></img>
                    <img style="display:none;" name="WARN" title="警告" src="${resourcePath}/boss/images/u79_original.png"></img>
                    <img style="display:none;" name="ERROR" title="异常" src="${resourcePath}/boss/images/u129_original.png"></img>
                    <img style="display:none;" name="UNKNOW" title="未知" src="${resourceUrl}/images/ico_help.png"></img>
                  </td>
                  <td>
                    <a name="detail" href="javascript:void(0);" style="float:right;">详情</a>
                    <div></div>
                    <div style="display:none;margin-right: 40px;"><textarea readonly="readonly"></textarea></div>
                  </td>
              </tr>
            </c:forEach>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
  <div class="clearer"></div>
  <br/>
</div>
</body>
</html>
