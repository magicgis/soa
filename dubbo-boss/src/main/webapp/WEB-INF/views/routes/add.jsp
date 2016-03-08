<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <c:choose>
    <c:when test="${method eq 'add'}">
      <title>新增路由规则</title>
    </c:when>
    <c:otherwise>
      <title>编辑路由规则</title>
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
            <c:when test="${formMethod eq 'add'}">
              新增路由规则
            </c:when>
            <c:otherwise>
              编辑路由规则
            </c:otherwise>
          </c:choose>
        </h1>
      </div>
      <div class="clearer"></div>
      <form:form commandName="route" action="${ctxPath}routes/${formMethod}" method="POST">
        <table class="list_info">
          <tbody>
          <tr>
            <td class="info_tit">路由名称：</td>
            <td class="info_con" colspan="2"><form:input path="name" cssClass="input_text" cssStyle="width:350px"/>
              <div class="help-block">可使用中文，由1-200个字符组成</div>
            </td>
          </tr>
          <tr>
            <td class="info_tit">优先级：</td>
            <td class="info_con" colspan="2"><form:input path="priority" cssClass="input_text"/>
              <div class="help-block">数字越大越优先</div>
            </td>
          </tr>
          <c:choose>
            <c:when test="${formMethod eq 'add'}">
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
                  <form:select path="service" cssClass="input_text chosen" onchange="loadMethod()">
                    <form:option value="*" label="所有服务"/>
                    <form:options items="${serviceList}"/>
                  </form:select>
                  <form:errors path="service" cssClass="alert-danger" element="div"/>
                </td>
              </tr>
            </c:when>
            <c:otherwise>
              <tr>
                <td class="info_tit">服务名：</td>
                <td class="info_con" colspan="2">
                  <form:hidden path="id"/>
                  <form:input path="service" cssClass="input_text" readonly="true" cssStyle="width:650px"/>
                </td>
              </tr>
            </c:otherwise>
          </c:choose>
          <tr>
            <td class="info_tit">方法名：</td>
            <td class="info_con" colspan="2">
              <select id="method" name="method" class="input_text chosen" multiple>
              </select>

              <div class="help-block">多个方法名用逗号分隔</div>
            </td>
          </tr>
          <tr>
            <td class="info_tit">【匹配条件】<br/>消费者满足匹配条件时使用当前规则进行过滤</td>
            <td class="info_tit" style="text-align:center">匹配</td>
            <td class="info_tit" style="text-align:center">不匹配</td>
          </tr>
          <tr>
            <td class="info_tit">消费者地址：</td>
            <td class="info_con"><input name="consumerHost" value="${consumerHost}" class="input_text"
                                        style="width:300px"/></td>
            <td class="info_con"><input name="unconsumerHost" value="${ununconsumerHost}" class="input_text"
                                        style="width:300px;margin-left:45px"/>

              <div class="help-block">多个值用逗号分隔，以星号结尾表示通配地址段</div>
            </td>
          </tr>
          <tr>
            <td class="info_tit">消费者应用名：</td>
            <td class="info_con"><input name="consumerApplication" value="${consumerApplication}" class="input_text"
                                        style="width:300px"/></td>
            <td class="info_con"><input name="unconsumerApplication" value="${unconsumerApplication}" class="input_text"
                                        style="width:300px;margin-left:45px"/>

              <div class="help-block">多个值用逗号分隔</div>
            </td>
          </tr>
          <tr>
            <td class="info_tit">消费者版本：</td>
            <td class="info_con"><input name="consumerVersion" value="${consumerVersion}" class="input_text"
                                        style="width:300px"/></td>
            <td class="info_con"><input name="unconsumerVersion" value="${unconsumerVersion}" class="input_text"
                                        style="width:300px;margin-left:45px"/>

              <div class="help-block">多个值用逗号分隔</div>
            </td>
          </tr>
          <tr>
            <td class="info_tit">消费者集群：</td>
            <td class="info_con"><input name="consumerCluster" value="${consumerCluster}" class="input_text"
                                        style="width:300px"/></td>
            <td class="info_con"><input name="unconsumerCluster" value="${unconsumerCluster}" class="input_text"
                                        style="width:300px;margin-left:45px"/>

              <div class="help-block">暂不支持</div>
            </td>
          </tr>
          <tr>
            <td class="info_tit">【过滤规则】<br/>满足过滤规则的提供者地址将被推送给消费者</td>
            <td class="info_tit" style="text-align:center">匹配</td>
            <td class="info_tit" style="text-align:center">不匹配</td>
          </tr>
          <tr>
            <td class="info_tit">提供者地址：</td>
            <td class="info_con"><input name="providerHost" value="${providerHost}" class="input_text"
                                        style="width:300px"/></td>
            <td class="info_con"><input name="unproviderHost" value="${unproviderHost}" class="input_text"
                                        style="width:300px;margin-left:45px"/>

              <div class="help-block">多个值用逗号分隔，以星号结尾表示通配地址段</div>
            </td>
          </tr>
          <tr>
            <td class="info_tit">提供者版本：</td>
            <td class="info_con"><input name="providerVersion" value="${providerVersion}" class="input_text"
                                        style="width:300px"/></td>
            <td class="info_con"><input name="unproviderVersion" value="${unproviderVersion}" class="input_text"
                                        style="width:300px;margin-left:45px"/>

              <div class="help-block">暂不支持</div>
            </td>
          </tr>
          <tr>
            <td class="info_tit">提供者集群：</td>
            <td class="info_con"><input name="providerCluster" value="${providerCluster}" class="input_text"
                                        style="width:300px"/></td>
            <td class="info_con"><input name="unproviderCluster" value="${unproviderCluster}" class="input_text"
                                        style="width:300px;margin-left:45px"/>

              <div class="help-block">暂不支持</div>
            </td>
          </tr>
          <tr>
            <td class="info_tit">提供者协议：</td>
            <td class="info_con"><input name="providerProtocol" value="${providerProtocol}" class="input_text"
                                        style="width:300px"/></td>
            <td class="info_con"><input name="unproviderProtocol" value="${unproviderProtocol}" class="input_text"
                                        style="width:300px;margin-left:45px"/></td>
          </tr>
          <tr>
            <td class="info_tit">提供者端口：</td>
            <td class="info_con"><input name="providerPort" value="${providerPort}" class="input_text"
                                        style="width:300px"/></td>
            <td class="info_con"><input name="unproviderPort" value="${unproviderPort}" class="input_text"
                                        style="width:300px;margin-left:45px"/></td>
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
      <%--<li style="overflow:visible;clear:both;">--%>
      <div class="clearer"></div>
    </div>
  </div>
</div>
<script type="text/javascript">
  var validateRule = {
    "name": {
      req: true,
      label: "路由名称"
    },
    "priority": {
      req: true,
      label: "优先级"
    },
    "service": {
      req: true,
      label: "服务名"
    }
  };
  function loadMethod() {
    var service = $("#service").val();
    if (service != "") {
      MessageBoxExt.ajax({
        url: GV.ctxPath + "providers/service/" + service + "/methods",
        type: "GET",
        style: "NONE",
        success: function (response) {
//          console.log(response);
          if (response.status === 'success') {
            $("#method").empty();
            $.each(response.data.methods, function (index, item) {
              $("#method").append("<option value='" + item + "'>" + item + "</option>");
            });

            <c:if test="${method != ''}">
            <c:forEach items="${method}" var="m">
            <%--console.log("item:${m}");--%>
            $("#method option[value='${m}']").attr("selected", "selected");
            </c:forEach>
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
        loadServices(selectedApplication, '*');
      }
    });

    loadMethod();
    ValidateExt.listen("route", validateRule, ValidateExt.nextlineError);
    $("#Submit").click(function () {
      var val = ValidateExt.val("route", validateRule);
      if (!val) {
        return false;
      }
      $("#route").submit();
    });
  });
</script>
</body>
</html>
