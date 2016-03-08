<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>应用负责人批量维护</title>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <script>
    function updateOwnRelation() {
      var loginName = $("#loginName").val();
      if (loginName == null && loginName.trim() == "") {
        MessageBox.alert("应用负责人登录名不能为空");
        return;
      }
      var target = 'owners/batchUpdate';
      MessageBoxExt.ajax({
        url: GV.ctxPath + target,
        type: "Post",
        data: "loginName=" + loginName.trim() + "&" + $("input[name='ownApp']:checked").serialize(),
        style: "REDIRECT"
      });
    }
  </script>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <!--search star-->
    <div class="information">
      <form id="applicationQueryForm" method="get" action="">
        <div class="search">
          <div class="search_tit">
            <h2 class="fw fleft f14">应用查询</h2>
          </div>
          <div class="search_con">
            <ul class="fix">
              <li>
                <label class="text_tit">应用负责人登录名：</label>
                <input type="text" id="loginName" name="loginName" class="input_text"/>
              </li>
            </ul>
            <div class="btn">
              <input type="submit" class="btn_sure fw" value="查询"/>
              <input type="button" onclick="$('#applicationQueryForm')[0].reset();" class="btn_cancel fw"
                     value="清空"/>
            </div>
            <div class="clearer"></div>
          </div>
        </div>
      </form>
    </div>
    <div class="clearer"></div>
    <div class="submenu">
      <div align="right" style="padding-right: 30px; height: 0px;">
        <c:if test="${viewLogicHelper.isSoaAdmin()}">
          <a href="javascript:void(0);" onclick="updateOwnRelation()">修改关联</a>
        </c:if>
      </div>
    </div>
    <div class="clearer"></div>
    <div class="result">
      <h2 class="fw">应用列表</h2>
      <table id="apps" class="list">
        <thead>
        <tr>
          <th><input type="checkbox" onclick="checkAll('apps','ownApp',this.checked)"></th>
          <th>应用</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${appOwnerMap}" var="item">
          <tr>
            <td><input type="checkbox" name="ownApp" value="${item.key}" ${item.value?"checked='checked'":""}></td>
            <td>${item.key}</td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
    <div class="clearer"></div>
    <br/>
  </div>
</div>
</body>
</html>
