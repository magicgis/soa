<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <title>查看动态配置</title>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="information">
      <div class="setup">
        <h1 class="fw fleft f14">动态配置详情(${override.service})<c:if test="${viewLogicHelper.isSoaAdmin()}"><a
                href="javascript:void(0);"
                class="top_right_edit edit_btn">修改</a></c:if>
        </h1>

      </div>
      <div class="clearer"></div>
      <table class="list_info">
        <tbody>
        <tr>
          <td class="info_tit">服务名：</td>
          <td class="info_con">${override.service}</td>
        </tr>
        <tr>
          <td class="info_tit">应用名：</td>
          <td class="info_con">${override.application}</td>
        </tr>
        <tr>
          <td class="info_tit">客户端地址：</td>
          <td class="info_con">${override.address}</td>
        </tr>
        <tr>
          <td class="info_tit">用户名：</td>
          <td class="info_con">${override.username}</td>
        </tr>
        <tr>
          <td class="info_tit">状态：</td>
          <td class="info_con">
            <c:choose>
              <c:when test="${override.enabled}">
                <span class="success">已启用</span>
              </c:when>
              <c:otherwise>
                <span class="error">禁用</span>
              </c:otherwise>
            </c:choose>
          </td>
        </tr>
        <tr>
          <td class="info_tit">动态配置：</td>
          <td class="info_con">
            <table>
              <thead>
              <tr>
                <th style="width:450px">参数名</th>
                <th>参数值</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach items="${parameters.entrySet()}" var="p">
                <tr>
                  <td>${p.key}</td>
                  <td>${p.value}</td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </td>
        </tr>
        <tr>
          <td class="info_tit">服务降级：</td>
          <td class="info_con">
            <table>
              <thead>
              <tr>
                <th style="width:450px">参数名</th>
                <th>参数值</th>
              </tr>
              </thead>
              <tbody>
              <c:if test="${mockDefaultMethodJson ne ''}">
                <tr>
                  <td>所有方法的Mock值</td>
                  <td>
                    <c:choose>
                      <c:when test="${mockDefaultMethodForce eq 'force'}">
                        屏蔽
                      </c:when>
                      <c:otherwise>
                        容错
                      </c:otherwise>
                    </c:choose>
                    : ${mockDefaultMethodJson}</td>
                </tr>
              </c:if>
              <c:forEach items="${methodJsons.entrySet()}" var="m">
                <tr>
                  <td>${m.key}</td>
                  <td>${m.value}</td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </td>
        </tr>
        <tr>
          <td class="info_tit">创建时间：</td>
          <td class="info_con">${override.created}</td>
        </tr>
        <tr>
          <td class="info_tit">最后修改时间：</td>
          <td class="info_con">${override.modified}</td>
        </tr>
        <tr>
          <td class="info_tit">操作人员：</td>
          <td class="info_con">${override.operator}</td>
        </tr>
        <tr>
          <td class="info_tit">操作IP：</td>
          <td class="info_con">${override.operatorAddress}</td>
        </tr>
        <tr>
          <td class="info_tit"></td>
          <td class="info_con">
            <c:if test="${viewLogicHelper.isSoaAdmin()}">
              <input type="button" value="编 辑" class="btn_sure fw edit_btn">
            </c:if>
            <input type="button" value="返 回" class="btn_cancel fw" onclick="window.history.go(-1)">
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
<script type="application/javascript">
  $(function () {
    $(".edit_btn").click(function () {
      window.location.href = "${ctxPath}overrides/edit?id=" +${override.id};
    });
  });
</script>
</body>
</html>
