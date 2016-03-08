<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>监控面板</title>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <style type="text/css">
  .input_cont li table td {
    min-width:80px;
  }
  </style>
  <script type="text/javascript">
  $(document).ready(function(){
    $("#crossRateSlider").slider({
      value : ${soa_router_idc_cross_rate},
      min : 0,
      max : 100,
      disabled : true
    });
    $("div[name=idcSwitchBlock]").each(function () {
      $(this).buttonset({
        disabled : true
      });
    });
  });
  </script>
  <c:if test="${viewLogicHelper.isSoaAdmin()}">
  <script src="${resourceUrl}/js/idc-control.js"></script>
  </c:if>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="clearer"></div>
    <div class="submenu">
      <div align="right" style="padding-right: 30px; height: 0px;">
        <a href="${ctxPath}applications/dependency">【全局依赖图】</a>
      </div>
    </div>
    <div class="clearer"></div>
    <div class="result">
      <h2 class="fw">IDC多活控制</h2>
      <div class="input_cont">
        <ul>
          <li>
            <label class="text_tit">跨机房流量：</label>
            <table>
              <tr>
                <td><span id="crossRateValue">${soa_router_idc_cross_rate}%</span></td>
                <td><div id="crossRateSlider" style="width:300px;display:inline-block;"></div></td>
              </tr>
            </table>
          </li>
          <li>
            <label class="text_tit" for="ipRule">IDC-IP规则：</label>
            <table>
              <c:forEach items="${soa_router_idc_ip_rule.entrySet()}" var="ipRule">
              <tr>
                <td>${ipRule.key}</td>
                <td><input class="input_text" style="width:300px;" type="text" name="ipRule" disabled="disabled" value="${ipRule.value}" old="${ipRule.value}" idc="${ipRule.key}"/></td>
              </tr>
              </c:forEach>
            </table>
            <div class="tip_error" id="error_ipRule"></div>
          </li>
          <li>
            <label class="text_tit">IDC-机房开关：</label>
            <table>
              <c:forEach items="${soa_router_idc_switch.entrySet()}" var="idcSwitch">
              <tr>
                <td>${idcSwitch.key}</td>
                <td>
                  <div name="idcSwitchBlock">
                    <input type="radio" id="${idcSwitch.key}_on" name="${idcSwitch.key}" value="on" <c:if test="${idcSwitch.value}">checked="checked"</c:if> />
                    <label for="${idcSwitch.key}_on">开</label>
                    <input type="radio" id="${idcSwitch.key}_off" name="${idcSwitch.key}" value="off" <c:if test="${!idcSwitch.value}">checked="checked"</c:if> />
                    <label for="${idcSwitch.key}_off">关</label>
                  </div>
                </td>
              </tr>
              </c:forEach>
            </table>
            <div class="tip_error" id="error_idcSwitch"></div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</div>
</body>
</html>
