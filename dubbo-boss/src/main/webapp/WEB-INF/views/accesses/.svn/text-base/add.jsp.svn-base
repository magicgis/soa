<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <title>新增访问控制</title>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="information">
      <div class="setup">
        <h1 class="fw f14">新增访问控制</h1>
      </div>
      <div class="clearer"></div>
      <form:form commandName="access" action="${ctxPath}accesses/add" method="POST">
        <table class="list_info">
          <tbody>
          <tr>
            <td class="info_tit">应用名：</td>
            <td class="info_con" colspan="2">
              <select id="application" name="application" class="input_text chosen">
                <option value="">所有应用</option>
                <c:forEach items="${applications}" var="application">
                  <option value="${application}">${application}</option>
                </c:forEach>
              </select>
            </td>
          </tr>
          <tr>
            <td class="info_tit">服务名：</td>
            <td class="info_con" colspan="2">
              <form:select path="service" cssClass="input_text chosen">
                <form:option value="" label="所有服务"/>
                <form:options items="${serviceList}"/>
              </form:select>
              <form:errors path="service" cssClass="alert-danger" element="div"/>
              <div class="help-block">由1-200个字符组成</div>
            </td>
          </tr>
          <tr>
            <td class="info_tit">消费者地址：</td>
            <td class="info_con" colspan="2">
              <form:textarea path="address" cols="60" rows="6" cssStyle="float:left;" required="required"/>
              <form:errors path="address" cssClass="alert-danger" element="div"/>
              <div class="help-block">多个地址用换行符分隔，地址必需是0.0.0.0到255.255.255.255的IP地址</div>
            </td>
          </tr>
          <tr>
            <td class="info_tit">状态：</td>
            <td class="info_con" colspan="2">
              <form:select path="allow" cssClass="input_text">
                <form:option value="0" label="禁用"/>
                <form:option value="1" label="启用"/>
              </form:select>
              <div class="help-block">白名单优先，只要有白名单，则白名单生效，否则黑名单生效</div>
            </td>
          </tr>
          <tr>
            <td class="info_tit">&nbsp;</td>
            <td class="info_con" colspan="2">
              <input id="Submit" type="button" value="确 认" class="btn_sure fw">
              <input type="button" value="返 回" class="btn_cancel fw" onclick="window.history.go(-1)">
            </td>
          </tr>
          </tbody>
        </table>
      </form:form>
      <div class="clearer"></div>
    </div>
  </div>
</div>
<script type="text/javascript">
  var validateRule = {
    "service": {
      req: true,
      label: "服务名"
    },
    "address": {
      req: true,
      label: "消费者地址"
    }
  };
  $(function () {
    ValidateExt.listen("access", validateRule, ValidateExt.nextlineError);
    $("#Submit").click(function () {
      var val = ValidateExt.val("access", validateRule);
      if (!val) {
        return false;
      }
      $("#access").submit();
    });
    $('.chosen').chosen({width: "650px"});
    $('#application').change(function (evt, params) {
      var selectedApplication = params.selected;
      console.log("selectedApplication:" + selectedApplication);
      if (typeof selectedApplication != 'undefined' && selectedApplication != "") {
        loadServices(selectedApplication);
      }
    });
  });
</script>
</body>
</html>
