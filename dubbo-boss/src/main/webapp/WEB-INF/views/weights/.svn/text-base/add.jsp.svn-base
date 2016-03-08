<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <c:choose>
    <c:when test="${method eq 'add'}">
      <title>新增权重调节</title>
    </c:when>
    <c:otherwise>
      <title>编辑权重调节</title>
    </c:otherwise>
  </c:choose>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="information">
      <div class="setup">
        <c:choose>
          <c:when test="${method eq 'add'}">
            <h1 class="fw">新增权重调节</h1>
          </c:when>
          <c:otherwise>
            <h1 class="fw">编辑权重调节</h1>
          </c:otherwise>
        </c:choose>
      </div>
      <div class="clearer"></div>
      <form:form commandName="weight" action="${ctxPath}weights/${method}" method="POST">
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
              <form:hidden path="id"/>
              <c:choose>
                <c:when test="${method eq 'add'}">
                  <form:select path="service" cssClass="input_text chosen">
                    <form:option value="" label="所有服务"/>
                    <form:options items="${serviceList}"/>
                  </form:select>
                  <form:errors path="service" cssClass="alert-danger" element="div"/>
                </c:when>
                <c:otherwise>
                  <form:input path="service" cssClass="input_text" cssStyle="width:650px" disabled="true"/>
                </c:otherwise>
              </c:choose>
              <div class="help-block">可使用中文，由1-200个字符组成</div>
            </td>
          </tr>
          <tr>
            <td class="info_tit">消费者地址：</td>
            <td class="info_con" colspan="2">
              <form:textarea path="address" cols="60" rows="6" cssStyle="float:left" required="required"
                             disabled="${method eq 'edit'}"/>
              <form:errors path="address" cssClass="alert-danger" element="div"/>
              <div class="help-block">多个地址用换行符分隔，地址必需是0.0.0.0到255.255.255.255的IP地址</div>
            </td>
          </tr>
          <tr>
            <td class="info_tit">权重：</td>
            <td class="info_con" colspan="2">
              <form:input path="weight" cssClass="input_text"/>
            </td>
          </tr>
          <tr>
            <td class="info_tit">&nbsp;</td>
            <td class="info_con">
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
    "weight": {
      req: true,
      label: "权重"
    },
    "address": {
      req: true,
      label: "消费者地址"
    },
    "service": {
      req: true,
      label: "服务名"
    }
  };
  $(function () {
    ValidateExt.listen("weight", validateRule, ValidateExt.nextlineError);
    $("#Submit").click(function () {
      var val = ValidateExt.val("weight", validateRule);
      if (!val) {
        return false;
      }
      $("#weight").submit();
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
