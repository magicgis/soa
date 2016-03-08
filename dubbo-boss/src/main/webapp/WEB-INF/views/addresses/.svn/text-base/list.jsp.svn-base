<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
    <title>机器查询</title>
    <%@ include file="/WEB-INF/views/common/metas.jsp" %>
</head>
<body>
<div class="Container">
    <div class="Content fontText">
        <div class="result">
            <div class="tag">
                <c:choose>
                    <c:when test="${empty application}">
                        <a href="${ctxPath}services"><em>服务</em></a><span>|</span>
                        <a href="${ctxPath}addresses" class="on"><em>机器</em></a><span>|</span>
                        <a href="${ctxPath}overrides"><em>动态配置</em></a><span></span>
                    </c:when>
                    <c:otherwise>
                        <a href="${ctxPath}services/application/${application}"><em>服务</em></a><span>|</span>
                        <a href="${ctxPath}addresses/application/${application}" class="on"><em>机器</em></a><span>|</span>
                        <a href="${ctxPath}overrides/application/${application}"><em>动态配置</em></a><span></span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <!--search star-->
        <div class="information">
            <form id="addresseQueryForm" method="get" action="">
                <div class="search">
                    <div class="search_tit">
                        <h2 class="fw fleft f14">机器查询</h2>
                    </div>
                    <div class="search_con">
                        <ul class="fix">
                            <%--<li>--%>
                            <%--<label class="text_tit">服务名：</label>--%>
                            <%--<input type="text" id="service" name="service" class="input_text"/>--%>
                            <%--</li>--%>
                            <li>
                                <label class="text_tit">应用名：</label>
                                <select id="application" name="application" class="input_text">
                                    <option selected="selected" value="">全部</option>
                                    <c:forEach items="${applications}" var="item">
                                        <option value="${item}"
                                                <c:if test="${item == application}">selected="selected"</c:if>>${item}</option>
                                    </c:forEach>
                                </select>
                            </li>
                            <%--<li>--%>
                            <%--<label class="text_tit">机器IP：</label>--%>
                            <%--<input type="text" id="address" name="address" class="input_text"/>--%>
                            <%--</li>--%>
                            <li>
                                <label class="text_tit">角色：</label>
                                <select id="serviceType" name="type" class="input_text">
                                    <option selected="selected" value="">全部</option>
                                    <option value="provider">服务提供者</option>
                                    <option value="consumer">服务消费者</option>
                                </select>
                            </li>
                        </ul>
                        <div class="btn">
                            <input type="button" onclick="queryAddresses()" class="btn_sure fw" value="查询"/>
                            <input type="button" onclick="$('#addresseQueryForm')[0].reset();" class="btn_cancel fw"
                                   value="清空"/>
                        </div>
                        <div class="clearer"></div>
                    </div>
                </div>
            </form>
        </div>
        <div class="clearer"></div>
        <%@ include file="/WEB-INF/views/addresses/control/listControl.jsp" %>
    </div>
</div>
</body>
</html>
