<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <c:choose>
    <c:when test="${method eq 'add'}">
      <title>新增消费者</title>
    </c:when>
    <c:otherwise>
      <title>编辑消费者</title>
    </c:otherwise>
  </c:choose>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="result">
      <div class="information">
        <c:choose>
          <c:when test="${method eq 'add'}">
            <h1 class="fw">新增消费者</h1>
          </c:when>
          <c:otherwise>
            <h1 class="fw">编辑消费者</h1>
          </c:otherwise>
        </c:choose>

        <div class="input_cont">
          <form:form commandName="weight" action="${ctxPath}weights/${method}" method="POST">
            <ul>
              <li>
                <label class="text_tit">服务名：</label>
                <form:hidden path="id"/>
                <form:select path="service" cssClass="input_text" cssStyle="width:650px">
                  <form:option value="" label="请选择一个服务"/>
                  <form:options items="${serviceList}"/>
                </form:select>
                <form:errors path="service" cssClass="alert-danger" element="div"/>
              </li>
              <li>
                <label class="text_tit">消费者地址：</label>
                <form:textarea path="address" cols="60" rows="6" required="required"/>
                <form:errors path="address" cssClass="alert-danger" element="div"/>
                <div class="help-block">多个地址用换行符分隔，地址必需是0.0.0.0到255.255.255.255的IP地址</div>
              </li>
              <li>
                <label class="text_tit">权重：</label>
                <form:input path="weight" cssClass="input_text"/>
              </li>
              <li>
                <label class="text_tit">&nbsp;</label>
                <input type="submit" value="确 认" class="btn_sure fw">
                <input type="button" value="返 回" class="btn_cancel fw" onclick="window.history.go(-1)">
              </li>
            </ul>
          </form:form>
          <div class="clearer"></div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
