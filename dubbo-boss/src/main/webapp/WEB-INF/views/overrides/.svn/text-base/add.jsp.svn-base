<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <c:choose>
    <c:when test="${method eq 'add'}">
      <title>新增动态配置</title>
    </c:when>
    <c:otherwise>
      <title>编辑动态配置</title>
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
        新增动态配置
      </c:when>
      <c:otherwise>
        编辑动态配置
      </c:otherwise>
    </c:choose>
  </h1>
</div>
<div class="clearer"></div>
<form:form commandName="override" action="${ctxPath}overrides/${method}" method="POST">
<table class="list_info">
<tbody>
<c:if test="${method eq 'add'}">
  <tr>
    <td class="info_tit">应用名：</td>
    <td class="info_con" colspan="2">
      <select id="application" name="providerApplication" class="input_text chosen">
        <option value="">所有应用</option>
        <c:forEach items="${applications}" var="application">
          <option value="${application}">${application}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
</c:if>
<tr>
  <td class="info_tit">服务名：</td>
  <td class="info_con" colspan="2">
    <c:choose>
      <c:when test="${method eq 'add'}">
        <form:hidden path="id"/>
        <form:select path="service" cssClass="input_text chosen">
          <form:option value="" label="所有服务"/>
          <form:options items="${serviceList}"/>
        </form:select>
        <form:errors path="service" cssClass="alert-danger" element="div"/>
      </c:when>
      <c:otherwise>
        <form:input path="service" cssClass="input_text" readonly="true" cssStyle="width:650px"/>
      </c:otherwise>
    </c:choose>
  </td>
</tr>
<tr>
  <td class="info_tit">消费者应用名：</td>
  <td class="info_con">
    <c:choose>
      <c:when test="${method eq 'add'}">
        <form:select path="application" cssClass="input_text chosen" style="width:400px">
          <form:option value="" label="所有消费者"/>
          <form:options items="${consumerApplications}"/>
        </form:select>
        &nbsp;<input type="checkbox" id="consumerAppType" onclick="loadApplications()"/> 加载所有应用
      </c:when>
      <c:otherwise>
        <form:input path="application" cssClass="input_text" readonly="true" cssStyle="width:650px"/>
      </c:otherwise>
    </c:choose>
  </td>
</tr>
<tr>
  <td class="info_tit">只推送给指定消费者地址：</td>
  <td class="info_con">
    <c:choose>
      <c:when test="${method eq 'add'}">
        <form:input path="address" class="input_text" style="width:400px"/>
        <div class="help-block">&nbsp;不填表示对所有机器上的消费者生效</div>
      </c:when>
      <c:otherwise>
        ${override.address}
      </c:otherwise>
    </c:choose>
  </td>
</tr>
<tr>
  <td class="info_tit">状态：</td>
  <td class="info_con" colspan="2">
    <c:choose>
      <c:when test="${method eq 'add'}">
        <form:select path="enabled" cssClass="input_text">
          <form:option value="0" label="禁用"/>
          <form:option value="1" label="启用"/>
        </form:select>
        <div class="help-block">&nbsp;白名单优先，只要有白名单，则白名单生效，否则黑名单生效</div>
      </c:when>
      <c:otherwise>
        <c:choose>
          <c:when test="${override.enabled}">
            <span class="success">已启用</span>
          </c:when>
          <c:otherwise>
            <span class="error">禁用</span>
          </c:otherwise>
        </c:choose>
      </c:otherwise>
    </c:choose>
  </td>
</tr>
<tr>
  <td class="info_tit">动态配置：</td>
  <td class="info_con" colspan="2">
    <table id="overrideConfig">
      <thead>
      <tr>
        <td>方法</td>
        <td>参数</td>
        <td>值</td>
      </tr>
      </thead>
      <tbody>
      <c:choose>
        <c:when test="${method eq 'add'}">
          <tr>
            <td>
              <select name="overrideMethod" class="overrideMethod input_text" style="width:400px">
              </select>
            </td>
            <td>
              <select name="overrideKey" class="input_text">
                <option value="timeout">timeout:int 方法调用超时时间(毫秒)</option>
                <option value="retries">retries:int 远程服务调用重试次数，不包括第一次调用，不需要重试请设为0</option>
                <option value="loadbalance">loadbalance:string
                  负载均衡策略，可选值：random,roundrobin,leastactive，分别表示：随机，轮循，最少活跃调用
                </option>
                <option value="async">async:boolean 是否异步执行，不可靠异步，只是忽略返回值，不阻塞执行线程</option>
                <option value="sent">sent:boolean 异步调用时，标记sent=true时，表示网络已发出数据</option>
                <option value="actives">actives:int 每服务消费者最大并发调用限制</option>
                <option value="executes">executes:int 每服务每方法最大使用线程数限制- -，此属性只在<dubbo:method>作为<dubbo:service>子标签时有效
                </option>
                <option value="deprecated">deprecated:boolean 服务方法是否过时，此属性只在<dubbo:method>作为<dubbo:service>子标签时有效
                </option>
                <option value="sticky">sticky:boolean 设置true 该接口上的所有方法使用同一个provider.如果需要更复杂的规则，请使用用路由</option>
                <option value="return">return:boolean
                  方法调用是否需要返回值,async设置为true时才生效，如果设置为true，则返回future，或回调onreturn等方法，如果设置为false，则请求发送成功后直接返回Null
                </option>
                <option value="oninvoke">oninvoke:string 方法执行前拦截</option>
                <option value="onreturn">onreturn:string 方法执行返回后拦截</option>
                <option value="onthrow">onthrow:string 方法执行有异常拦截</option>
                <option value="cache">cache:string/boolean 以调用参数为key，缓存返回结果，可选：lru, threadlocal, jcache等</option>
                <option value="validation">validation:validation 是否启用JSR303标准注解验证，如果启用，将对方法参数上的注解进行校验</option>
              </select>
            </td>
            <td>
              <input type="text" name="overrideValue" class="customValue input_text" value="${mockDefaultMethodJson}" placeholder="值" style="width:300px"/>
              &nbsp;<a href="javascript:void(0)" onclick="addOverrideItem()">添加</a>
            </td>
          </tr>
          <tr id="overrideTemplate">
            <td>
              <select name="overrideMethod" class="overrideMethod input_text" style="width:400px">
              </select>
            </td>
            <td>
              <select name="overrideKey" class="input_text">
                <option value="timeout">timeout:int 方法调用超时时间(毫秒)</option>
                <option value="retries">retries:int 远程服务调用重试次数，不包括第一次调用，不需要重试请设为0</option>
                <option value="loadbalance">loadbalance:string
                  负载均衡策略，可选值：random,roundrobin,leastactive，分别表示：随机，轮循，最少活跃调用
                </option>
                <option value="async">async:boolean 是否异步执行，不可靠异步，只是忽略返回值，不阻塞执行线程</option>
                <option value="sent">sent:boolean 异步调用时，标记sent=true时，表示网络已发出数据</option>
                <option value="actives">actives:int 每服务消费者最大并发调用限制</option>
                <option value="executes">executes:int 每服务每方法最大使用线程数限制- -，此属性只在<dubbo:method>作为<dubbo:service>子标签时有效
                </option>
                <option value="deprecated">deprecated:boolean 服务方法是否过时，此属性只在<dubbo:method>作为<dubbo:service>子标签时有效
                </option>
                <option value="sticky">sticky:boolean 设置true 该接口上的所有方法使用同一个provider.如果需要更复杂的规则，请使用用路由</option>
                <option value="return">return:boolean
                  方法调用是否需要返回值,async设置为true时才生效，如果设置为true，则返回future，或回调onreturn等方法，如果设置为false，则请求发送成功后直接返回Null
                </option>
                <option value="oninvoke">oninvoke:string 方法执行前拦截</option>
                <option value="onreturn">onreturn:string 方法执行返回后拦截</option>
                <option value="onthrow">onthrow:string 方法执行有异常拦截</option>
                <option value="cache">cache:string/boolean 以调用参数为key，缓存返回结果，可选：lru, threadlocal, jcache等</option>
                <option value="validation">validation:validation 是否启用JSR303标准注解验证，如果启用，将对方法参数上的注解进行校验</option>
              </select>
            </td>
            <td>
              <input type="text" name="overrideValue" class="customValue input_text" placeholder="值" style="width:300px"/>
              &nbsp;<a href="javascript:void(0)" onclick="removeOverrideItem(this)">删除</a>
            </td>
          </tr>
        </c:when>
        <c:otherwise>
          <form:input path="id" type="hidden"/>
          <c:forEach items="${parameters}" var="p">
            <tr id="overrideTemplate">
              <td>
                <c:choose>
                  <c:when test="${fn:contains(p.key, '.')}">
                    <select name="overrideMethod" class="overrideMethod input_text" style="width:400px">
                      <option value="${fn:substringBefore(p.key, '.')}">${fn:substringBefore(p.key, '.')}</option>
                      <c:forEach items="${methods}" var="method">
                        <option value="${method}">${method}</option>
                      </c:forEach>
                    </select>
                  </c:when>
                  <c:otherwise>
                    所有方法
                    <input type="hidden" name="overrideMethod" value=""/>
                  </c:otherwise>
                </c:choose>
              </td>
              <td>
                <select name="overrideKey" class="input_text">
                  <option value="timeout">timeout:int 方法调用超时时间(毫秒)</option>
                  <option value="retries">retries:int 远程服务调用重试次数，不包括第一次调用，不需要重试请设为0</option>
                  <option value="loadbalance">loadbalance:string
                    负载均衡策略，可选值：random,roundrobin,leastactive，分别表示：随机，轮循，最少活跃调用
                  </option>
                  <option value="async">async:boolean 是否异步执行，不可靠异步，只是忽略返回值，不阻塞执行线程</option>
                  <option value="sent">sent:boolean 异步调用时，标记sent=true时，表示网络已发出数据</option>
                  <option value="actives">actives:int 每服务消费者最大并发调用限制</option>
                  <option value="executes">executes:int 每服务每方法最大使用线程数限制- -，此属性只在<dubbo:method/>作为<dubbo:service/>子标签时有效
                  </option>
                  <option value="deprecated">deprecated:boolean 服务方法是否过时，此属性只在<dubbo:method/>作为<dubbo:service/>子标签时有效
                  </option>
                  <option value="sticky">sticky:boolean 设置true 该接口上的所有方法使用同一个provider.如果需要更复杂的规则，请使用用路由</option>
                  <option value="return">return:boolean
                    方法调用是否需要返回值,async设置为true时才生效，如果设置为true，则返回future，或回调onreturn等方法，如果设置为false，则请求发送成功后直接返回Null
                  </option>
                  <option value="oninvoke">oninvoke:string 方法执行前拦截</option>
                  <option value="onreturn">onreturn:string 方法执行返回后拦截</option>
                  <option value="onthrow">onthrow:string 方法执行有异常拦截</option>
                  <option value="cache">cache:string/boolean 以调用参数为key，缓存返回结果，可选：lru, threadlocal, jcache等</option>
                  <option value="validation">validation:validation 是否启用JSR303标准注解验证，如果启用，将对方法参数上的注解进行校验</option>
                  <c:choose>
                    <c:when test="${fn:contains(p.key, '.')}">
                      <option value="${fn:substringAfter(p.key, '.')}" selected="selected">${fn:substringAfter(p.key, '.')}</option>
                    </c:when>
                    <c:otherwise>
                      <option value="${p.key}" selected="selected">${p.key}</option>
                    </c:otherwise>
                  </c:choose>
                </select>
              </td>
              <td>
                <input type="text" name="overrideValue" class="customValue input_text" value="${p.value}" placeholder="值" style="width:300px"/>
                &nbsp;<a href="javascript:void(0)" onclick="addOverrideItem()">添加</a>
                &nbsp;<a href="javascript:void(0)" onclick="removeOverrideItem(this)">删除</a>
              </td>
            </tr>
          </c:forEach>
        </c:otherwise>
      </c:choose>
      </tbody>
    </table>
    <p class="help-block">方法参数说明：<a href="http://wiki.yeepay.com/pages/viewpage.action?pageId=24314436">http://wiki.yeepay.com/pages/viewpage.action?pageId=24314436</a>
    </p>
  </td>
</tr>
<tr>
  <td class="info_tit">服务降级：</td>
  <td class="info_con" colspan="2">
    <table id="failConfig">
      <thead>
      <tr>
        <td>方法</td>
        <td>类型</td>
        <td>Mock值</td>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td>
          所有方法
        </td>
        <td>
          <select name="mockDefaultMethodForce" class="input_text">
            <option value="${mockDefaultMethodForce}" selected="selected">${mockDefaultMethodForce}</option>
            <option value="fail">容错</option>
            <option value="force">屏蔽</option>
          </select>
        </td>
        <td>
          <input type="text" name="mockDefaultMethodJson" class="input_text" value="${mockDefaultMethodJson}" placeholder="请参考示例" style="width:300px"/>
          &nbsp;<a href="javascript:void(0)" onclick="addFailItem()">添加</a>
        </td>
      </tr>
      <c:choose>
        <c:when test="${method eq 'add'}">
          <tr id="failTemplate">
            <td>
              <select name="mockMethodName" class="mockMethodName input_text" style="width:400px">
              </select>
            </td>
            <td>
              <select name="mockMethodForce" class="input_text">
                <option value="fail">容错</option>
                <option value="force">屏蔽</option>
              </select>
            </td>
            <td>
              <input type="text" name="mockMethodJson" class="customValue input_text" placeholder="请参考示例" style="width:300px"/>
              &nbsp;<a href="javascript:void(0)" onclick="removeFailItem(this)">删除</a>
            </td>
          </tr>
        </c:when>
        <c:otherwise>
          <c:forEach items="${methodForces}" var="p" varStatus="status">
            <tr id="failTemplate">
              <td>
                <select name="mockMethodName" class="mockMethodName input_text" style="width:400px">
                  <option value="${p.key}" selected="selected">${p.key}</option>
                  <c:forEach items="${methods}" var="method">
                    <option value="${method}">${method}</option>
                  </c:forEach>
                </select>
              </td>
              <td>
                <select name="mockMethodForce" class="input_text">
                  <option value="${p.value}">${p.value}</option>
                  <option value="force">屏蔽</option>
                  <option value="force">屏蔽</option>
                </select>
              </td>
              <td>
                <input type="text" name="mockMethodJson" class="customValue input_text" value="${methodJsons[p.key]}" placeholder="请参考示例" style="width:300px"/>
                &nbsp;<a href="javascript:void(0)" onclick="addFailItem()">添加</a>
                &nbsp;<a href="javascript:void(0)" onclick="removeFailItem(this)">删除</a>
              </td>
            </tr>
          </c:forEach>
        </c:otherwise>
      </c:choose>
      </tbody>
    </table>
    <p class="help-block">示例：return null/empty/JSON或throw com.foo.BarException</p>
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
  var init = false;
  // 加载方法列表
  function loadMethod(service) {
    if (service != "") {
      MessageBoxExt.ajax({
        url: GV.ctxPath + "providers/service/" + service + "/methods",
        type: "GET",
        style: "NONE",
        success: function (response) {
//          console.log(response);
          if (response.status === 'success') {
            $.each($(".mockMethodName"), function (index, mockMethodName) {
              $(mockMethodName).empty();
              $.each(response.data.methods, function (index, item) {
                $(mockMethodName).append("<option value='" + item + "'>" + item + "</option>");
              });
            });
            $.each($(".overrideMethod"), function (index, overrideMethod) {
              $(overrideMethod).empty();
              $(overrideMethod).append("<option value=''>所有方法</option>");
              $.each(response.data.methods, function (index, item) {
                $(overrideMethod).append("<option value='" + item + "'>" + item + "</option>");
              });
            });
          }
        }
      });
      init = true;
    }
  }
  // 加载消费者应用列表
  function loadApplications() {
    var service = "";
    var type = $("input[id='consumerAppType']").attr("checked");
    if (!type) {
      service = $("#service").val();
    }
//    console.log("service:" + service + ", type:" + type);
    MessageBoxExt.ajax({
      url: GV.ctxPath + "consumers/applications",
      data: {"service": service},
      type: "GET",
      style: "NONE",
      success: function (response) {
        console.log(response);
        if (response.status === 'success') {
          $("#application").empty().append("<option value=''>所有消费者</option>");
          $.each(response.data.applications, function (index, item) {
            $("#application").append("<option value='" + item + "'>" + item + "</option>");
          });
        }
      }
    });
  }
  function addOverrideItem() {
    $("#overrideConfig").append(document.getElementById("overrideTemplate").outerHTML);
  }
  function removeOverrideItem(that) {
    $(that).parents("tr#overrideTemplate").remove();
  }
  function addFailItem() {
    $("#failConfig").append(document.getElementById("failTemplate").outerHTML);
  }
  function removeFailItem(that) {
    $(that).parents("tr#failTemplate").remove();
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
    $('#service').change(function (evt, params) {
      var selectedService = params.selected;
      console.log("selectedService:" + selectedService);
      if (typeof selectedService != 'undefined' && selectedService != "") {
        if (init) {
          // 新增：页面加载后修改过服务
          MessageBoxExt.confirm("当前操作可能丢失尚未保存的信息，是否确认继续操作？", function () {
            loadApplications();
            loadMethod(selectedService);
          });
        } else {
          // 编辑：页面加载后没有修改过服务
          MessageBoxExt.confirm("", function () {
            loadApplications();
            loadMethod(selectedService);
          });
        }
      } else {
        // 新增
        loadApplications();
        loadMethod(selectedService);
      }
    });
    $("#submitBtn").click(function () {
      var result = true;
      $(".customValue").each(function (index, item) {
        if (item.value === "") {
          result = false;
        }
      });
      if (!result) {
        MessageBoxExt.alert("请检查表单项并删除无用项");
      }
      return result;
    });
  });
</script>
</body>
</html>
