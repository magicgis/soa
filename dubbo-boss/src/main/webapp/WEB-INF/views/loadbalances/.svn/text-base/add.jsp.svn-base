<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <c:choose>
    <c:when test="${method eq 'add'}">
      <title>新增负载均衡</title>
    </c:when>
    <c:otherwise>
      <title>编辑负载均衡</title>
    </c:otherwise>
  </c:choose>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="information">
      <div class="setup">
        <h1 class="fw f14">
          <c:choose>
            <c:when test="${method eq 'add'}">
              新增负载均衡
            </c:when>
            <c:otherwise>
              编辑负载均衡
            </c:otherwise>
          </c:choose>
        </h1>
      </div>
      <div class="clearer"></div>
      <form:form commandName="loadbalance" action="${ctxPath}loadbalances/${method}" method="POST">
        <table class="list_info">
          <tbody>
          <tr>
            <td class="info_tit">应用名：</td>
            <td class="info_con">
              <select id="application" name="providerApplication" class="input_text chosen">
                <option value="">所有应用</option>
                <c:forEach items="${applications}" var="application">
                  <option value="${application}">${application}</option>
                </c:forEach>
              </select>
            </td>
          </tr>
          <tr>
            <td class="info_tit">服务名：</td>
            <td class="info_con">
              <form:hidden path="id"/>
              <form:select path="service" cssClass="input_text chosen" cssStyle="width:650px"
                           onchange="loadMethod()">
                <form:option value="" label="所有服务"/>
                <form:options items="${serviceList}"/>
              </form:select>
              <form:errors path="service" cssClass="alert-danger" element="div"/>
              <div class="help-block">支持通配符，只支持一个*符且要在接口末尾（分组、版本不支持通配符）</div>
            </td>
          </tr>
          <tr>
            <td class="info_tit">方法名：</td>
            <td class="info_con">
              <form:select path="method" cssClass="input_text chosen" cssStyle="width:650px">
                <form:option value="*" label="*"/>
              </form:select>
              <form:errors path="method" cssClass="alert-danger" element="div"/>
              <div class="help-block">* 代表所有方法</div>
            </td>
          </tr>
          <tr>
            <td class="info_tit">负载均衡策略：</td>
            <td class="info_con">
              <form:select path="strategy" cssClass="input_text">
                <form:option value="" label="请选择一个策略"/>
                <form:option value="roundrobin" label="轮询"/>
                <form:option value="random" label="随机"/>
                <form:option value="leastactive" label="最少并发"/>
              </form:select>
            </td>
          </tr>
          <tr>
            <td class="info_tit">&nbsp;</td>
            <td class="info_con">
              <input type="submit" id="submitBtn" value="确 认" class="btn_sure fw">
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
    "strategy": {
      req: true,
      label: "负载均衡策略"
    }
  };
  function loadMethod() {
    var service = $("#service").val();
    if (service != "") {
      MessageBoxExt.ajax({
        url: "${ctxPath}providers/service/" + service + "/methods",
        type: "GET",
        style: "NONE",
        success: function (response) {
          console.log(response);
          if (response.status === 'success') {
            $("#method").empty();
            $("#method").append("<option value='*'>*</option>");
            $.each(response.data.methods, function (index, item) {
              $("#method").append("<option value='" + item + "'>" + item + "</option>");
            });
            <c:if test="${loadbalance.method != ''}">
            $("#method").val('${loadbalance.method}');
            </c:if>
            $("#method").trigger("chosen:updated");
          }
        }
      });
    }
  }
  $(function () {
    $('.chosen').chosen({width: "650px"});
    $('#application').change(function (evt, params) {
      var selectedApplication = params.selected;
      console.log("selectedApplication:" + selectedApplication);
      if (typeof selectedApplication != 'undefined' && selectedApplication != "") {
        loadServices(selectedApplication);
      }
    });
    loadMethod();
    ValidateExt.listen("loadbalance", validateRule, ValidateExt.nextlineError);
    $("#Submit").click(function () {
      var val = ValidateExt.val("loadbalance", validateRule);
      if (!val) {
        return false;
      }
      $("#loadbalance").submit();
    });
  });
</script>
</body>
</html>
