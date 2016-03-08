<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>服务器监控</title>
  <%@include file="/WEB-INF/views/common/metas.jsp" %>
  <style>
    .input_cont ul li label{
      width: 17.5%;
    }
    .list_info tr td.info_con {
      min-width: 100px;
    }
  </style>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="information">
      <div class="setup">
        <h1 class="fw fleft f14">
          服务器列表
        </h1>
      </div>
      <div class="clearer"></div>
      <div>
        <div class="result">
          <table class="list">
            <tbody>
            <tr>
              <td class="info_tit">服务器地址</td>
              <td class="info_con">客户端(${clientCount})</td>
            </tr>
            <c:forEach items="${servers}" var="item">
              <tr class="result">
                <td>${item.address}</td>
                <td><a href="${ctxPath}monitor/servers/clients?port=${item.port}">Clients(${item.clientSize})</a>
                </td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </div>
        <div class="clearer"></div>
      </div>
    </div>
    <div class="information">
      <div class="setup">
        <h1 class="fw fleft f14">
          运行时状态
        </h1>
      </div>
      <div class="clearer"></div>
      <div>
        <div class="result">
          <table class="list_info">
            <tr>
              <td class="info_tit">名称</td>
              <td class="info_con">状态</td>
              <td class="info_con">描述</td>
            </tr>
            <c:forEach var="item" items="${statusList}">
              <tr>
                <td class="info_tit">${item.name}</td>
                <td class="info_con">${item.level}</td>
                <td class="info_con">${viewLogicHelper.handleThreadPoolInfo(item.description)}</td>
              </tr>
            </c:forEach>
          </table>
        </div>
        <div class="clearer"></div>
      </div>
    </div>
    <div class="information">
      <div class="setup">
        <h1 class="fw fleft f14">
          操作系统信息
        </h1>
      </div>
      <div class="clearer"></div>
      <div class="input_cont">
        <ul>
          <li>
            <label class="text_tit">Dubbo-Version：</label>

            <p>
              ${system.version}
            </p>
          </li>
          <li>
            <label class="text_tit">Host：</label>

            <p>
              ${system.host}
            </p>
          </li>
          <li>
            <label class="text_tit">OS：</label>

            <p>
              ${system.os}
            </p>
          </li>
          <li>
            <label class="text_tit">JVM：</label>

            <p>
              ${system.java}
            </p>
          </li>
          <li>
            <label class="text_tit">CPU：</label>

            <p>
              ${system.cpu}
            </p>
          </li>
          <li>
            <label class="text_tit">Locale：</label>

            <p>
              ${system.locale}
            </p>
          </li>
          <li>
            <label class="text_tit">Uptime：</label>

            <p>
              ${system.uptime}
            </p>
          </li>
          <li>
            <label class="text_tit">Time：</label>

            <p>
              ${system.time}
            </p>
          </li>
        </ul>
      </div>
    </div>
  </div>
</div>
</div>
</body>
</html>
