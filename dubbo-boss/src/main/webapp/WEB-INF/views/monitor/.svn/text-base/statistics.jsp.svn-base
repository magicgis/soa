<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  User: dreambt
  Date: 14-9-11
  Time: 15:48
--%>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/metas.jsp" %>
    <title>服务监控</title>
</head>
<body>
<div class="Container">
    <div class="Content fontText">
        <!--search star-->
        <div class="clearer"></div>
        <div class="submenu">
            <div align="right" style="padding-right: 30px; height: 0px;">
                <a href="${ctxPath}monitor/services/statistics?service=${service}">【统计数据】</a>
                <a href="${ctxPath}monitor/services/data?service=${service}">【原始数据】</a>
                <a href="${ctxPath}monitor/services/statistics?service=${service}&expand=provider">【+提供者】</a>
                <a href="${ctxPath}monitor/services/statistics?service=${service}&expand=consumer">【+消费者】</a>
                <a href="${ctxPath}monitor/services/charts?service=${service}">【散点图】</a>
            </div>
        </div>
        <div class="clearer"></div>
        <div class="clearer"></div>
        <div class="result">
            <h2 class="fw">服务监控统计数据 <input id="search" name="search" value="${service}" placeholder="快速检索"/> <span
                    class="help-block">提示：点击标题栏可排序</span></h2>
            <table id="myTable" class="tablesorter list">
                <thead>
                <tr>
                    <th width="350px">服务名</th>
                    <th width="200px">方法名</th>
                    <th>调用成功次数</th>
                    <th>调用失败次数</th>
                    <th>平均用时(ms)</th>
                    <th>最大用时(ms)</th>
                    <th>平均并发</th>
                    <th>最大并发</th>
                    <th width="100px">可用操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${statisticsData}" var="item">
                    <tr>
                        <td><a href="${ctxPath}services/detail/${item.value.service}"
                               title="点击查看服务信息">${item.value.service}</a></td>
                        <td>${item.value.method}</td>
                        <td>${item.value.success}</td>
                        <td>${item.value.failure}</td>
                        <td>${item.value.elapsed}</td>
                        <td>${item.value.maxElapsed}</td>
                        <td>${item.value.concurrent}</td>
                        <td>${item.value.maxConcurrent}</td>
                        <td>
                            <a href="${ctxPath}monitor/services/data?service=${item.value.service}">原始数据</a>
                            <a href="${ctxPath}monitor/services/charts?service=${item.value.service}">散点图</a>
                        </td>
                    </tr>
                    <c:if test="${expand != null and expand != ''}">
                        <c:forEach items="${item.value.data}" var="data">
                            <tr>
                                <td colspan="2">
                                    <c:choose>
                                        <c:when test="${expand == 'provider'}">
                                            &nbsp;&nbsp;&nbsp;&nbsp; |--> ${data.provider}
                                        </c:when>
                                        <c:when test="${expand == 'consumer'}">
                                            &nbsp;&nbsp;&nbsp;&nbsp; |<-- ${data.consumer}
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td>${data.success}</td>
                                <td>${data.failure}</td>
                                <td>${data.elapsed}</td>
                                <td>${data.maxElapsed}</td>
                                <td>${data.concurrent}</td>
                                <td>${data.maxConcurrent}</td>
                                <td></td>
                                <td></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript" src="${resourceUrl}/js/jquery.tablesorter.min.js"></script>
<script type="text/javascript">
    $(function () {
        <c:if test="${expand eq null or expand eq ''}">
        $("#myTable").tablesorter({sortList: [
            [2, 1]
        ]});
        $(".header").css("width", "auto").css("min-width", "50px");
        </c:if>
        $("#search").keyup(function () {
            console.log(this.value);
            var searchText = this.value;
            $("#myTable tr").each(function (index, item) {
//            if (this.value === "" || $($("#myTable tr")[2]).find("td")[0].textContent.) {
                if (searchText === "" || $(item).find("th").length > 0 ||
                        $(item).find("td").length > 0 && $(item).find("td")[0].textContent.indexOf(searchText) >= 0) {
                    $(item).show("fast");
                } else {
                    $(item).hide(1000);
                }
            });
        });
    });
</script>
</body>
</html>
