<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>应用详情</title>
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
      openClose("_appInfo", "_appInfoBlock");
      $(".inner_table").each(function () {
        $(this).find("th:last").removeAttr("width");
      });
      $("#manualNotify").click(function () {
        var target = "notify/mail";
        var appName = "${app.appName}";
        MessageBoxExt.confirm("确认要发送邮件吗", function () {
          MessageBoxExt.ajax({
            url: GV.ctxPath + target,
            type: 'POST',
            dataType: "json",
            data: {
              "appName": appName,
              "lackInfo": "${lackInfo}"
            }
          });
        });
      });
    });
  </script>
  <script src="${resourceUrl}/js/switchTab.js"></script>
  <script src="${resourceUrl}/js/serviceMgr.js"></script>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <!--search star-->
    <div class="information">
      <div class="setup">
        <h1 class="fw fleft f14">应用详情(${app.appName})<c:if test="${viewLogicHelper.isSoaAdmin()}"><a href="javascript:void(0);" id="editApp" class="top_right_edit">修改</a><c:if test="${isLack}"><a href="javascript:void(0);" id="manualNotify" class="lackInfo_notify">信息不全，邮件提醒</a></c:if></c:if>
        </h1>
        <p><a class="open" href="javascript:void(0);" id="_appInfo">点击收起</a></p>
      </div>
      <div class="clearer"></div>
      <form id="serviceInfoForm" name="serviceInfoForm">
        <table class="list_info" id="_appInfoBlock">
          <tr>
            <td class="info_tit">应用名：</td>
            <td class="info_con">${app.appTitle}
            </td>
          </tr>
          <tr>
            <td class="info_tit">javaDoc：</td>
            <td class="info_con">
              <c:if test="${app.javaDoc != null && app.javaDoc != ''}">
                <a href="${app.javaDoc}" target="_blank">${app.javaDoc}</a>
              </c:if>
            </td>
          </tr>
          <tr>
            <td class="info_tit">外部文档：</td>
            <td class="info_con">
              <c:if test="${app.docPath != null && app.docPath != ''}">
                <c:forEach items="${app.docPath.split(',')}" var="docPath">
                  <a href="${docPath}" target="_blank">${docPath}</a><br/>
                </c:forEach>
              </c:if>
            </td>
          </tr>
          <tr>
            <td class="info_tit">应用描述：</td>
            <td class="info_con">
              ${viewLogicHelper.breakLine(app.appDesc)}
            </td>
          </tr>
          <tr>
            <td class="info_tit">负责人：</td>
            <td class="info_con">
              <table class="list_info inner_table">
                <tr>
                  <th width="300px">邮箱</th>
                  <th width="200px">姓名</th>
                  <th width="200px">起始日</th>
                  <c:if test="${viewLogicHelper.isSoaAdmin()}">
                    <th>操作</th>
                  </c:if>
                </tr>
                <c:forEach items="${app.ownerList}" var="owner">
                  <tr>
                    <td>${owner.loginName}@yeepay.com</td>
                    <td>${owner.ownerName}</td>
                    <td><fmt:formatDate value="${owner.createDate}" pattern="yyyy-MM-dd"/></td>
                    <c:if test="${viewLogicHelper.isSoaAdmin()}">
                      <td>
                        <a href="javascript:void(0)" name="delOwner" ownerId="${owner.id}">删除</a>
                      </td>
                    </c:if>
                  </tr>
                </c:forEach>
                <c:if test="${viewLogicHelper.isSoaAdmin()}">
                  <tr>
                    <td colspan="4"><a href="javascript:void(0)" name="addOwner">添加</a></td>
                  </tr>
                </c:if>
              </table>
            </td>
          </tr>
          <tr>
            <td class="info_tit">应用授权：</td>
            <td class="info_con">
              <table class="list_info inner_table">
                <tr>
                  <th width="300px">应用</th>
                  <th width="200px">授权状态</th>
                  <th width="200px">最后更新日</th>
                  <c:if test="${viewLogicHelper.isSoaAdmin()}">
                    <th>操作</th>
                  </c:if>
                </tr>
                <c:forEach items="${app.depByAppList}" var="depby">
                  <tr>
                    <td>${depby.appName}</td>
                    <td>${depby.authStatus}</td>
                    <td><fmt:formatDate value="${depby.lastModifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <c:if test="${viewLogicHelper.isSoaAdmin()}">
                      <td>
                        <c:if test="${depby.authStatus=='AUTHED' || depby.authStatus=='PUBLIC'}">
                          <a href="javascript:void(0)" name="authApp" type="unauth" appName="${depby.appName}">取消授权</a>
                        </c:if>
                        <c:if test="${depby.authStatus=='FORBID'}">
                          <a href="javascript:void(0)" name="authApp" type="auth" appName="${depby.appName}">授权</a>
                        </c:if>
                      </td>
                    </c:if>
                  </tr>
                </c:forEach>
                <c:if test="${viewLogicHelper.isSoaAdmin()}">
                  <tr>
                    <td colspan="4"><a href="javascript:void(0)" type="auth" name="authApp">添加</a></td>
                  </tr>
                </c:if>
              </table>
            </td>
          </tr>
          <tr>
            <td class="info_tit">最新升级：</td>
            <td class="info_con">
              <table class="list_info inner_table" id="_latestUpgrade">
                <c:if test="${app.latestUpgrade != null}">
                  <tr>
                    <td>${viewLogicHelper.breakLine(app.latestUpgrade.upgradeInfo)}</td>
                    <td width="200px"><fmt:formatDate value="${app.latestUpgrade.lastModifyDate}"
                                                      pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <c:if test="${viewLogicHelper.isSoaAdmin()}">
                      <td width="200px">
                        <a href="javascript:void(0)" name="editUpgrade" upgradeId="${app.latestUpgrade.id}">修改</a>
                        <a href="javascript:void(0)" name="delUpgrade" upgradeId="${app.latestUpgrade.id}">删除</a>
                      </td>
                    </c:if>
                  </tr>
                </c:if>
                <c:if test="${viewLogicHelper.isSoaAdmin()}">
                  <tr>
                    <td colspan="3"><a href="javascript:void(0)" name="addUpgrade">添加</a></td>
                  </tr>
                </c:if>
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
        <a href="javascript:void(0);" class="on" ref="_dependency"><em>依赖关系</em></a><span>|</span>
        <a href="javascript:void(0);" url="${ctxPath}services/query" context="appName=${app.appName}"
           ref="_services"><em>服务</em></a><span>|</span>
        <a href="javascript:void(0);" url="${ctxPath}addresses/application/${app.appName}"
           context="application=${app.appName}" ref="_addresses"><em>机器</em></a><span>|</span>
        <a href="javascript:void(0);" url="${ctxPath}overrides/list" context="application=${app.appName}"
           ref="_overrides" style="display:none;"><em>动态配置</em></a>
      </div>
      <div name="tabSwitchView">
        <div id="_dependency" style="width:100%;height:400px;"></div>
        <div id="_services" style="display:none;"></div>
        <div id="_addresses" style="display:none;"></div>
        <div id="_overrides" style="display:none;"></div>
      </div>
    </div>
  </div>
  <div class="clearer"></div>
  <br/>
</div>
<c:if test="${viewLogicHelper.isSoaAdmin()}">
  <%@ include file="/WEB-INF/views/applications/control/editAppControl.jsp" %>
  <%@ include file="/WEB-INF/views/applications/control/editOwnerControl.jsp" %>
  <%@ include file="/WEB-INF/views/applications/control/editUpgradeControl.jsp" %>
  <%@ include file="/WEB-INF/views/applications/control/authAppControl.jsp" %>
</c:if>
<script type="text/javascript" src="${resourcePath}/common/component/echarts-2.0.2/echarts-plain.js"></script>
<script type="text/javascript">
  // 基于准备好的dom，初始化echarts图表
  var myChart = echarts.init(document.getElementById('_dependency'));
  var option = ${dependencyMeta};
  // 为echarts对象加载数据
  myChart.setOption(option);
</script>
</body>
</html>
