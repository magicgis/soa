<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="result">
  <h2 class="fw">查询结果</h2>
  <table class="list" id="queryTable">
    <tbody>
    <tr>
      <th width="15px"><input type="checkbox" onclick="checkAll('queryTable', 'recordId', this.checked)"/></th>
      <th>服务名</th>
      <th>应用名</th>
      <th width="140px">机器IP</th>
      <th>服务参数</th>
      <th width="40px">状态</th>
      <th width="140px">操作</th>
    </tr>
    <c:forEach items="${overrides}" var="override">
      <tr>
        <td><input type="checkbox" name="recordId" value="${override.id}"/></td>
        <td><a href="${ctxPath}overrides/show?id=${override.id}">${override.service}</a>
        </td>
        <td>${override.application}</td>
        <td>${override.address}</td>
        <td>${override.params}</td>
        <td <c:choose>
          <c:when test="${override.enabled}">class="success" </c:when>
          <c:otherwise>class="danger"</c:otherwise>
        </c:choose>>${override.enabled?"启用":"禁用"}</td>
        <td>
          <a href="${ctxPath}overrides/show?id=${override.id}">查看</a>
          <c:if test="${viewLogicHelper.isSoaAdmin()}">
            <a href="${ctxPath}overrides/edit?id=${override.id}">编辑</a>
            <c:choose>
              <c:when test="${override.enabled}">
                <a href="javascript:void(0)" onclick="deal('${override.id}', 'forbid')">禁用</a>
              </c:when>
              <c:otherwise>
                <a href="javascript:void(0)" onclick="deal('${override.id}', 'allow')">启用</a>
              </c:otherwise>
            </c:choose>
            <a href="javascript:void(0)" onclick="deal('${override.id}','delete')">删除</a>
          </c:if>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
<c:if test="${viewLogicHelper.isSoaAdmin()}">
  <div style="margin:20px auto auto 20px;height:30px;">
    <ul>
      <li>
                <span id="button">
                  <a href="javascript:void(0)" onclick="batchDeal('delete')"
                     class="btn_sure_a fw"><b>批量删除</b><em></em></a>
                </span>
      </li>
    </ul>
  </div>
  <script type="text/javascript">
    function deal(id, operation) {
      MessageBoxExt.confirm("确认操作 #" + id + " 嘛？", function () {
        MessageBoxExt.ajax({
          url: GV.ctxPath + "overrides/" + operation + "?id=" + id,
          style: "REDIRECT"
        });
      });
    }
    function batchDeal(operation) {
      var ids = "id=";
      $(":checkbox[name='recordId']:checked").map(function () {
        ids += this.value + "&id=";
      });
      ids = ids.substr(0, ids.length - 4);
      console.debug("ids:" + ids);

      if (ids === "") {
        MessageBoxExt.alert("请选择要操作的项");
      } else {
        MessageBoxExt.confirm("确认操作 #" + ids + " 嘛？", function () {
          MessageBoxExt.ajax({
            url: GV.ctxPath + "overrides/" + operation + "?" + ids,
            style: "REDIRECT"
          });
        });
      }
    }
  </script>
</c:if>