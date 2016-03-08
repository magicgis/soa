<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
        <div class="result">
            <h2 class="fw">查询结果</h2>
            <table class="list">
                <tbody>
                <tr>
                    <th>服务</th>
                    <th width="80px">角色</th>
                    <th width="80px">可用操作</th>
                </tr>
                <c:forEach items="${addresses}" var="address">
                    <c:set var="isProvider" value="${providerAddresses.contains(address)}"/>
                    <tr <c:if test="${isProvider}">class="success"</c:if>>
                        <c:choose>
                            <c:when test="${isProvider}">
                                <td><a href="${ctxPath}addresses/${address}/providers">${address}</a>
                                </td>
                                <td>服务提供者</td>
                            </c:when>
                            <c:otherwise>
                                <td><a href="${ctxPath}addresses/${address}/customers">${address}</a>
                                </td>
                                <td>服务消费者</td>
                            </c:otherwise>
                        </c:choose>
                        <td><a href="javascript:void(0)" onclick="enableAllByAddress('${address}')">启用</a> <a
                                href="javascript:void(0)" onclick="disableAllByAddress('${address}')">禁用</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    <div class="clearer"></div>
<script type="text/javascript">
    $(function () {
        $("table.list tr").removeClass("even");
    });
    function queryAddresses() {
        var application = $("#application").val();
        if (application != '') {
            window.location.href = GV.ctxPath + "addresses/application/" + application + "?" + $("#addresseQueryForm").serialize();
        } else {
            window.location.href = GV.ctxPath + "addresses?" + $("#addresseQueryForm").serialize();
        }
    }
    function enableAllByAddress(address) {
        MessageBoxExt.confirm("确认启用该 " + address + " 的所有服务嘛？", function () {
            MessageBoxExt.ajax({
                url : GV.ctxPath + "providers/address/" + encodeURIComponent(address) + "/enable"
//                success : function(data){
//                    MessageBoxExt
//                }
            });
        });
    }
    function disableAllByAddress(address) {
        MessageBoxExt.confirm("确认禁用该 " + address + " 的所有服务嘛？", function () {
            MessageBoxExt.ajax({
                url : GV.ctxPath + "providers/address/" + encodeURIComponent(address) + "/disable"
//                success : function(data){
//                    MessageBoxExt
//                }
            });
        });
    }
</script>
