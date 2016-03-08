<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <title>查看提供者</title>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="information">
      <div class="setup">
        <h1 class="fw f14">提供者详情(${provider.service})</h1>

      </div>
      <div class="clearer"></div>
      <table class="list_info">
        <tbody>
        <tr>
          <td class="info_tit">所有*：</td>
          <td class="info_con">${paramMap}</td>
        </tr>
        <tr>
          <td class="info_tit">机器：</td>
          <td class="info_con">${provider.address}</td>
        </tr>
        <tr>
          <td class="info_tit">进程号：</td>
          <td class="info_con">${paramMap.get('pid')}</td>
        </tr>
        <tr>
          <td class="info_tit">应用名：</td>
          <td class="info_con">${paramMap.get('application')}</td>
        </tr>
        <tr>
          <td class="info_tit">服务名：</td>
          <td class="info_con">${provider.service}</td>
        </tr>
        <tr>
          <td class="info_tit">接口名：</td>
          <td class="info_con">${paramMap.get('interface')}</td>
        </tr>
        <tr>
          <td class="info_tit">方法列表：</td>
          <td class="info_con">${paramMap.get('methods')}</td>
        </tr>
        <tr>
          <td class="info_tit">提供者地址：</td>
          <td class="info_con">provider://${provider.address}/${provider.service}?${provider.parameters}</td>
        </tr>
        <tr>
          <td class="info_tit">动态配置：</td>
          <td class="info_con">${provider.override.params}</td>
        </tr>
        <tr>
          <td class="info_tit">协议：</td>
          <td class="info_con">${paramMap.get('protocol')}</td>
        </tr>
        <tr>
          <td class="info_tit">协议版本：</td>
          <td class="info_con">${paramMap.get('dubbo')}</td>
        </tr>
        <tr>
          <td class="info_tit">时间戳：</td>
          <td class="info_con">${paramMap.get('timestamp')}</td>
        </tr>
        <tr>
          <td class="info_tit">服务版本：</td>
          <td class="info_con">${paramMap.get('revision')}</td>
        </tr>
        <tr>
          <td class="info_tit">所属端：</td>
          <td class="info_con">${paramMap.get('side')}</td>
        </tr>
        <tr>
          <td class="info_tit">集群：</td>
          <td class="info_con">${paramMap.get('cluster')}</td>
        </tr>
        <tr>
          <td class="info_tit">环境：</td>
          <td class="info_con">${paramMap.get('environment')}</td>
        </tr>
        <tr>
          <td class="info_tit">可用性：</td>
          <td class="info_con">${paramMap.get('available')}</td>
        </tr>
        <tr>
          <td class="info_tit">绑定所有IP：</td>
          <td class="info_con">${paramMap.get('anyhost')}</td>
        </tr>
        <tr>
          <td class="info_tit">类型：</td>
          <td class="info_con">
            <c:choose>
              <c:when test="${provider.dynamic}">
                <span class="success">动态</span>
              </c:when>
              <c:otherwise>
                <span>静态</span>
              </c:otherwise>
            </c:choose>
          </td>
        </tr>
        <tr>
          <td class="info_tit">状态：</td>
          <td class="info_con">
            <c:choose>
              <c:when test="${provider.enabled}">
                <span class="success">已启用</span>
              </c:when>
              <c:otherwise>
                <span class="error">禁用</span>
              </c:otherwise>
            </c:choose>
          </td>
        </tr>
        <tr>
          <td class="info_tit">检查：</td>
          <td class="info_con">
            <c:choose>
              <c:when test="${provider.enabled}">
                <span class="success">已启用</span>
              </c:when>
              <c:otherwise>
                <span class="error">禁用</span>
              </c:otherwise>
            </c:choose>
          </td>
        </tr>
        <tr>
          <td class="info_tit"></td>
          <td class="info_con"><input type="button" value="返 回" class="btn_cancel fw" onclick="window.history.go(-1)">
          </td>
        </tr>
        </tbody>
      </table>
      <div class="clearer"></div>
    </div>
  </div>
</div>
</body>
</html>
