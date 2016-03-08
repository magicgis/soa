<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>注册中心监控</title>
  <%@include file="/WEB-INF/views/common/metas.jsp" %>
  <style>
    .list_info tr td.long_tit {
      text-align: right;
      padding: 5px;
      width: 30%;
      background: #fafcff;
    }
  </style>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="information">
      <div class="setup">
        <h1 class="fw fleft f14">
          注册中心列表
        </h1>
      </div>
      <div class="clearer"></div>
      <div>
        <div class="result">
          <table class="list_info">
            <tr>
              <td class="long_tit">地址</td>
              <td class="info_con">状态</td>
              <td class="info_con">已注册(${registeredCount})</td>
              <td class="info_con">已订阅(${subscribedCount})</td>
            </tr>
            <c:forEach var="item" items="${result}">
              <tr>
                <td class="long_tit">${item.server}</td>
                <td class="info_con">${item.available?"Connected":"Disconnected"}</td>
                <td class="info_con">
                  <a href="${ctxPath}monitor/registries/registered?registry=${item.address}">
                    Registered(${item.registeredSize})
                  </a>
                </td>
                <td class="info_con">
                  <a href="${ctxPath}monitor/registries/subscribed?registry=${item.address}">
                    Subscribed(${item.subscribedSize})
                  </a>
                </td>
              </tr>
            </c:forEach>
          </table>
        </div>
        <div class="clearer"></div>
      </div>
    </div>
  </div>
</div>
</body>