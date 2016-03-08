<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>服务详情</title>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <style>
    .lackInfo_notify{
      right:140px;
      position: absolute;
      color: #0c5b97;
    }
  </style>
  <script type="text/javascript">
    $(document).ready(function () {
      openClose("_serviceInfo", "_serviceInfoBlock");
      $(".inner_table").each(function () {
        $(this).find("th:last").removeAttr("width");
      });
      $("a[name=methodOperate]").click(function () {
        var type = $(this).attr("type");
        var methodId = $(this).attr("methodId");
        if (!type || !methodId) {
          return;
        }
        var opName = $(this).text() + "应用";
        MessageBoxExt.confirm("确认" + opName + "？", function () {
          MessageBoxExt.ajax({
            url: GV.ctxPath + 'services/method/' + type,
            type: 'POST',
            dataType: "json",
            style: "REDIRECT",
            data: {
              "methodId": methodId
            },
            cache: false
          });
        });
      });
      $("#manualNotify").click(function () {
        var target = "notify/mail";
        var appName = "${service.appName}";
        var serviceName = "${service.serviceInterface}";
        MessageBoxExt.confirm("确认要发送邮件吗", function () {
          MessageBoxExt.ajax({
            url: GV.ctxPath + target,
            type: 'POST',
            dataType: "json",
            data: {
              "appName": appName,
              "serviceName": serviceName,
              "lackInfo": "${lackInfo}"
            },
            cache: false
          });
        });
      });
    });
  </script>
  <script src="${resourceUrl}/js/switchTab.js"></script>
  <script src="${resourceUrl}/js/appMgr.js"></script>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <!--search star-->
    <div class="information">
      <div class="setup">
        <h1 class="fw fleft f14">服务详情(${service.serviceInterface})<c:if test="${viewLogicHelper.isSoaAdmin()}"><a href="javascript:void(0);" id="editService" class="top_right_edit">修改</a><c:if test="${isLack}"><a href="javascript:void(0);" id="manualNotify" class="lackInfo_notify">信息不全，邮件提醒</a></c:if></c:if>
        </h1>
        <p><a class="open" href="javascript:void(0);" id="_serviceInfo">点击收起</a></p>
      </div>
      <div class="clearer"></div>
      <form id="serviceInfoForm" name="serviceInfoForm">
        <table class="list_info" id="_serviceInfoBlock">
          <tr>
            <td class="info_tit">服务名：</td>
            <td class="info_con">
              ${service.serviceName}
            </td>
            <td class="info_tit">支持协议：</td>
            <td class="info_con">${service.serviceProtocol}</td>
          </tr>
          <tr>
            <td class="info_tit">所属应用：</td>
            <td class="info_con">
              <a href="${ctxPath}applications/detail/${service.appName}">${service.appName}</a>
            </td>
            <td class="info_tit">负责人：</td>
            <td class="info_con">
              <c:forEach items="${service.appInfo.ownerList}" var="owner">
                <span style="margin-right:10px">${owner.ownerName}</span>
              </c:forEach>
            </td>
          </tr>
          <tr>
            <td class="info_tit">机器数：</td>
            <td class="info_con">${service.providerCount}</td>
            <td class="info_tit">消费者数：</td>
            <td class="info_con">${service.consumerCount}</td>
          </tr>
          <tr>
            <td class="info_tit">javaDoc：</td>
            <td class="info_con" colspan="3">
              <c:set var="javaDoc" value="${service.getJavaDoc()}"/>
              <c:if test="${javaDoc != null && javaDoc != ''}">
                <a href="${javaDoc}" target="_blank">${javaDoc}</a>
              </c:if>
            </td>
          </tr>
          <tr>
            <td class="info_tit">外部文档：</td>
            <td class="info_con" colspan="3">
              <c:if test="${service.appInfo.docPath != null && service.appInfo.docPath != ''}">
                <c:forEach items="${service.appInfo.docPath.split(',')}" var="docPath">
                  <a href="${docPath}" target="_blank">${docPath}</a><br/>
                </c:forEach>
              </c:if>
            </td>
          </tr>
          <tr>
            <td class="info_tit">服务描述：</td>
            <td class="info_con" colspan="3">
              ${viewLogicHelper.breakLine(service.serviceDesc)}
            </td>
          </tr>
          <tr>
            <td class="info_tit">方法列表：</td>
            <td class="info_con" colspan="3">
              <table class="list_info inner_table">
                <tr>
                  <th width="300px">方法名</th>
                  <th>方法描述</th>
                  <th width="60px">状态</th>
                  <c:if test="${viewLogicHelper.isSoaAdmin()}">
                    <th width="80px">操作</th>
                  </c:if>
                </tr>
                <c:forEach items="${service.methodList}" var="method">
                  <tr>
                    <td>${method.methodName}</td>
                    <td>
                        ${viewLogicHelper.breakLine(method.methodDesc)}
                    </td>
                    <td>${viewLogicHelper.getStatusDesc(method.status)}</td>
                    <c:if test="${viewLogicHelper.isSoaAdmin()}">
                      <td>
                        <a herf="javascript:void(0);" name="editMethod" methodId="${method.id}">修改</a>
                        <c:if test="${method.status == 'DOWN'}">
                          <a herf="javascript:void(0);" name="methodOperate" type="offline"
                             methodId="${method.id}">下线</a>
                          <a herf="javascript:void(0);" name="methodOperate" type="delete"
                             methodId="${method.id}">删除</a>
                        </c:if>
                      </td>
                    </c:if>
                  </tr>
                </c:forEach>
              </table>
            </td>
          </tr>
        </table>
        <div class="clearer"></div>
      </form>
    </div>
    <div class="clearer"></div>
    <div class="result">
      <div class="tag" name="tabSwitchOp">
        <a href="javascript:void(0);" class="on" url="${ctxPath}providers/list"
           context="service=${service.serviceInterface}&application=${service.appInfo.appName}"
           ref="_proivders"><em>提供者</em></a><span>|</span>
        <a href="javascript:void(0);" url="${ctxPath}consumers/list" context="service=${service.serviceInterface}"
           ref="_consumers"><em>消费者</em></a><span>|</span>
        <a href="javascript:void(0);" url="${ctxPath}addresses/application/${service.appInfo.appName}" context=""
           ref="_addresses"><em>机器</em></a><span>|</span>
        <a href="javascript:void(0);" url="${ctxPath}overrides/list" context="service=${service.serviceInterface}"
           ref="_overrides" style="display:none;"><em>动态配置</em></a>
      </div>
      <div name="tabSwitchView">
        <div id="_proivders" style="display:none;"></div>
        <div id="_consumers" style="display:none;"></div>
        <div id="_addresses" style="display:none;"></div>
        <div id="_overrides" style="display:none;"></div>
      </div>
    </div>
  </div>
  <div class="clearer"></div>
  <br/>
</div>
<c:if test="${viewLogicHelper.isSoaAdmin()}">
  <%@ include file="/WEB-INF/views/services/control/editServiceControl.jsp" %>
  <%@ include file="/WEB-INF/views/services/control/editMethodControl.jsp" %>
</c:if>
</body>
</html>
