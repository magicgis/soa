<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/metas.jsp" %>
    <title>监控数据</title>
</head>
<body>
<div class="Container">
    <div class="Content fontText">
        <div class="information">
            <form id="monitorDataForm" method="get" action="">
                <div class="search">
                    <div class="search_tit">
                        <h2 class="fw fleft f14">监控数据</h2>
                    </div>
                    <div class="search_con">
                        <ul class="fix">
                            <%--<li>--%>
                            <%--<label class="text_tit">应用名：</label>--%>
                            <%--<select id="application" name="application" class="input_text">--%>
                            <%--<option selected="selected" value="">全部</option>--%>
                            <%--<c:forEach items="${applications}" var="item">--%>
                            <%--<option value="${item}"--%>
                            <%--<c:if test="${item == application}">selected="selected"</c:if>>${item}</option>--%>
                            <%--</c:forEach>--%>
                            <%--</select>--%>
                            <%--</li>--%>
                                <li>
                                    <label class="text_tit">请求日期：</label>
                                    <input type="text" id="dateStr" name="dateStr" class="input_text"/>
                                </li>
                            <li>
                                <label class="text_tit">服务名：</label>
                                <select id="service" name="service" class="input_text">
                                    <option value="" label="全部"/>
                                    <c:forEach items="${services}" var="service">
                                        <option value="${service}" label="${service}"/>
                                    </c:forEach>
                                </select>
                            </li>
                            <li>
                                <label class="text_tit">生产者机器IP：</label>
                                <input type="text" id="providerAddress" name="providerAddress" class="input_text"/>
                            </li>
                            <li>
                                <label class="text_tit">消费者机器IP：</label>
                                <input type="text" id="consumerAddress" name="consumerAddress" class="input_text"/>
                            </li>
                            <li>
                                <label class="text_tit">角色：</label>
                                <select id="serviceType" name="serviceType" class="input_text">
                                    <option selected="selected" value="">全部</option>
                                    <option value="provider">服务提供者</option>
                                    <option value="consumer">服务消费者</option>
                                </select>
                            </li>
                        </ul>
                        <div class="btn">
                            <input type="submit" class="btn_sure fw" value="查 询"/>
                            <input type="button" onclick="$('#queryForm')[0].reset();" class="btn_cancel fw"
                                   value="清 空"/>
                        </div>
                        <div class="clearer"></div>
                    </div>
                </div>
            </form>
        </div>
        <div class="clearer"></div>
        <div class="result">
            <h2 class="fw">查询结果</h2>
            <q:table queryService="soaQueryService" queryKey="monitorData" formId="monitorDataForm" class="list">
                <q:nodata>无符合条件的记录</q:nodata>
                <q:column title="服务" escapeHtml="false">
                    <a href="${ctxPath}services/detail/${service}">${service}</a>
                </q:column>
                <q:column title="方法" value="${method}"/>
                <q:column title="角色" width="60px">
                    <c:choose>
                        <c:when test="${type eq 'consumer'}">
                            消费者
                        </c:when>
                        <c:otherwise>
                            提供者
                        </c:otherwise>
                    </c:choose>
                </q:column>
                <q:column title="调用成功次数" value="${success}" width="80px"/>
                <q:column title="调用失败次数" value="${failure}" width="80px"/>
                <q:column title="平均用时(ms)" value="${elapsed}" width="80px"/>
                <q:column title="最大用时(ms)" value="${max_elapsed}" width="80px"/>
                <q:column title="平均并发" value="${concurrent}" width="80px"/>
                <q:column title="最大并发" value="${max_concurrent}" width="80px"/>
                <q:column title="采集时间" width="90px">
                    ${_messageFormater.formatDate(create_date)}
                </q:column>
            </q:table>
        </div>
    </div>
    <div class="clearer"></div>
</div>
<script type="text/javascript">
    $(function () {
        DatePickerExt.date("dateStr", {maxDate: 0});
        $("#dateStr").datepicker("setDate", "0");
    });
</script>
</body>
</html>
