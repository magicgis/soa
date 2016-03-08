<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
    <title>服务监控</title>
    <%@ include file="/WEB-INF/views/common/metas.jsp" %>
    <script src="${resourceUrl}/js/registryMgr.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".list td:contains('暂无')").css('color', 'blue');
            $(".list tr:contains('提供者')").removeClass('even');
            $(".list tr:contains('提供者')").addClass('success');
        });
    </script>
</head>
<body>
<div class="Container">
    <div class="Content fontText">
        <!--search star-->
        <div class="clearer"></div>
        <div class="submenu">
            <div align="right" style="padding-right: 30px; height: 0px;">
            </div>
        </div>
        <div class="clearer"></div>
        <div class="result">
            <h2 class="fw">Services(${services.size()})</h2>
            <table class="list">
                <tbody>
                <tr>
                    <th>服务</th>
                    <th width="160px">应用</th>
                    <th width="90px">提供者数</th>
                    <th width="90px">消费者数</th>
                    <th width="100px">可用操作</th>
                </tr>
                <c:forEach items="${services}" var="service">
                    <tr>
                        <td>
                            <a href="${ctxPath}services/detail/${service.serviceName}">${service.serviceName}</a>
                        </td>
                        <td>
                            <a href="${ctxPath}applications/detail/${service.appName}">${service.appName}</a>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${service.providerCount > 0}">
                                    <a href="${ctxPath}providers/detaillist?service=${service.serviceName}">提供者(${service.providerCount})</a>
                                </c:when>
                                <c:otherwise>暂无</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${service.consumerCount > 0}">
                                    <a href="${ctxPath}consumers/detaillist?service=${service.serviceName}">消费者(${service.consumerCount})</a>
                                </c:when>
                                <c:otherwise>暂无</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="${ctxPath}monitor/services/statistics?service=${service.serviceName}">统计数据</a>
                            <a href="${ctxPath}monitor/services/charts?service=${service.serviceName}">直方图</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
