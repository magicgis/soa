<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <title>查看路由规则</title>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="information">
      <div class="setup">
        <h1 class="fw fleft f14">路由规则详情(${route.name})<c:if test="${viewLogicHelper.isSoaAdmin()}"><a
                href="javascript:void(0);"
                class="top_right_edit edit_btn">修改</a></c:if>
        </h1>

      </div>
      <div class="clearer"></div>
      <table class="list_info">
        <tbody>
        <tr>
          <td class="info_tit">路由名称：</td>
          <td class="info_con">${route.name}</td>
        </tr>
        <tr>
          <td class="info_tit">匹配规则：</td>
          <td class="info_con">${route.matchRule}</td>
        </tr>
        <tr>
          <td class="info_tit">过滤规则：</td>
          <td class="info_con">${route.filterRule}</td>
        </tr>
        <tr>
          <td class="info_tit">优先级：</td>
          <td class="info_con">${route.priority}</td>
        </tr>
        <tr>
          <td class="info_tit">用户名：</td>
          <td class="info_con">${route.username}</td>
        </tr>
        <tr>
          <td class="info_tit">状态：</td>
          <td class="info_con">
            <c:choose>
              <c:when test="${route.enabled}">
                <span class="success">已启用</span>
              </c:when>
              <c:otherwise>
                <span class="error">禁用</span>
              </c:otherwise>
            </c:choose>
          </td>
        </tr>
        <tr>
          <td class="info_tit">创建时间：</td>
          <td class="info_con">${route.created}</td>
        </tr>
        <tr>
          <td class="info_tit">最后修改时间：</td>
          <td class="info_con">${route.modified}</td>
        </tr>
        <tr>
          <td class="info_tit">操作人员：</td>
          <td class="info_con">${route.operator}</td>
        </tr>
        <tr>
          <td class="info_tit">操作IP：</td>
          <td class="info_con">${route.operatorAddress}</td>
        </tr>
        <tr>
          <td class="info_tit"></td>
          <td class="info_con">
            <c:if test="${viewLogicHelper.isSoaAdmin()}">
              <input type="button" value="预 览" class="btn_sure fw">
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
      window.location.href = "${ctxPath}routes/edit?id=" +${route.id};
    });
  });
</script>
</body>
</html>
