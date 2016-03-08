<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
    <title>实时应用查询</title>
    <%@ include file="/WEB-INF/views/common/metas.jsp" %>
</head>
<body>
<div class="Container">
    <div class="Content fontText">
        <!--search star-->
        <div class="information">
            <form id="applicationQueryForm" method="get" action="">
                <div class="search">
                    <div class="search_tit">
                        <h2 class="fw fleft f14">应用查询</h2>
                    </div>
                    <div class="search_con">
                        <ul class="fix">
                            <li>
                                <label class="text_tit">服务名：</label>
                                <input type="text" id="service" name="service" class="input_text"/>
                            </li>
                            <li>
                                <label class="text_tit">应用名：</label>
                                <input type="text" id="application" name="application" class="input_text"/>
                            </li>
                            <li>
                                <label class="text_tit">机器IP：</label>
                                <input type="text" id="address" name="address" class="input_text"/>
                            </li>
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
                            <input type="submit" class="btn_sure fw" value="查询"/>
                            <input type="button" onclick="$('#applicationQueryForm')[0].reset();" class="btn_cancel fw"
                                   value="清空"/>
                        </div>
                        <div class="clearer"></div>
                    </div>
                </div>
            </form>
        </div>
        <div class="clearer"></div>
        <div class="result">
            <h2 class="fw">查询结果</h2>
            <table class="list">
                <thead>
                <tr>
                    <th>应用</th>
                    <th>角色</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${applications}" var="application">
                    <c:set var="isProvider" value="${providerApplications.contains(application)}"/>
                    <tr <c:if test="${isProvider}">class="success"</c:if>>
                        <td><a href="${ctxPath}applications/${application}/services">${application}</a></td>
                        <c:choose>
                            <c:when test="${isProvider}">
                                <td>服务提供者</td>
                            </c:when>
                            <c:otherwise>
                                <td>服务消费者</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="clearer"></div>
        <br/>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        $("table.list tr").removeClass("even");
    });
</script>
</body>
</html>
