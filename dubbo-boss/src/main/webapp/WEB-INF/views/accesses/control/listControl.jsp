<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="result">
  <h2 class="fw">查询结果</h2>
  <table class="list">
    <tbody>
    <tr>
      <th width="15px"><input type="checkbox" onclick="checkAll('queryTable', 'recordId', this.checked)"/></th>
      <th width="115px">消费者地址</th>
      <th>服务名</th>
      <th width="60px">类型</th>
      <c:if test="${viewLogicHelper.isSoaAdmin()}">
        <th width="60px">可用操作</th>
      </c:if>
    </tr>
    <c:forEach items="${accesses}" var="access">
      <tr
              <c:choose>
                <c:when test="${access.allow}">class="success" </c:when>
                <c:otherwise>class="danger"</c:otherwise>
              </c:choose>>
        <td><input type="checkbox" name="recordId" value="${service}"/></td>
        <td><a href="${ctxPath}addresses/${access.address}">${access.address}</a></td>
        <td><a href="${ctxPath}services/detail/${access.service}">${access.service}</a></td>
        <c:choose>
          <c:when test="${access.allow}">
            <td>允许</td>
          </c:when>
          <c:otherwise>
            <td>禁止</td>
          </c:otherwise>
        </c:choose>
        <c:if test="${viewLogicHelper.isSoaAdmin()}">
          <td><a href="javascript:void(0)" onclick="deleteAccess('${access.service}','${access.address}')">删除</a></td>
        </c:if>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
<c:if test="${viewLogicHelper.isSoaAdmin()}">
  <div style="margin:20px auto auto 20px;height:30px;">
    <ul>
      <li>
                <span>
                    <a href="javascript:void(0)" onclick="batchDelete()" class="btn_sure_a fw"><b>批量删除</b><em></em></a>
                </span>
      </li>
    </ul>
  </div>
</c:if>
<script type="text/javascript">
  $(function () {
    $("tr").removeClass("even");
    var allow = $(".allow").val();
    console.log("allow:" + allow);
    if (allow === '1') {
      $("tr.danger").hide();
    } else if (allow === '0') {
      $("tr.success").hide();
    }
  });
  <c:if test="${viewLogicHelper.isSoaAdmin()}">
  function deleteAccess(service, address) {
    MessageBoxExt.confirm("确认删除 " + address + " 对 " + service + " 服务的访问控制规则嘛？", function () {
      MessageBoxExt.ajax({
        url: GV.ctxPath + "accesses/delete?address=" + encodeURIComponent(address) + "&service=" + encodeURIComponent(service),
        style: "REDIRECT"
      });
    });
  }
  function batchDelete() {
    var address = "";
    var service = "";
    $(":checkbox[name='recordId']:checked").map(function () {
      var tds = $(this).parents("tr").find("td");
      address += tds[1].outerText + ",";
      service += tds[2].outerText + ",";
    });
    address = address.substr(0, address.length - 1);
    service = service.substr(0, service.length - 1);
    console.debug("address:" + address);
    console.debug("service:" + service);

    if (address === "" || service === "") {
      MessageBoxExt.alert("请选择要操作的项");
    } else {
      MessageBoxExt.confirm("确认删除 " + address + " 对 " + service + " 服务的访问控制规则嘛？", function () {
        MessageBoxExt.ajax({
          url: GV.ctxPath + "accesses/delete?address=" + encodeURIComponent(address) + "&service=" + encodeURIComponent(service),
          style: "REDIRECT"
        });
      });
    }
    </c:if>
  }
</script>