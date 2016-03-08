<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="result">
  <h2 class="fw">查询结果</h2>
  <table class="list">
    <tbody>
    <tr>
      <th width="15px"><input type="checkbox" onclick="checkAll('queryTable', 'recordId', this.checked)"/></th>
      <th>路由名称</th>
      <th>服务名</th>
      <th width="60px">优先级</th>
      <th width="60px">状态</th>
      <c:if test="${viewLogicHelper.isSoaAdmin()}">
        <th width="140px">可用操作</th>
      </c:if>
    </tr>
    <c:forEach items="${routes}" var="route">
      <tr>
        <td><input type="checkbox" name="recordId" value="${route.id}"/></td>
        <td><a href="${ctxPath}routes/show?id=${route.id}">${route.name}</a></td>
        <td><a href="${ctxPath}services/${route.service}/routes">${route.service}</a></td>
        <td>${route.priority}</td>
        <c:choose>
          <c:when test="${route.enabled}">
            <td class="success">已启用</td>
          </c:when>
          <c:otherwise>
            <td class="error">禁用</td>
          </c:otherwise>
        </c:choose>
        <c:if test="${viewLogicHelper.isSoaAdmin()}">
          <td>
            <a href="${ctxPath}routes/edit?id=${route.id}">编辑</a>
            <a href="${ctxPath}routes/edit?id=${route.id}&flag=copy">复制</a>
            <c:choose>
              <c:when test="${route.enabled}">
                <a href="javascript:void(0)" onclick="deal('${route.id}', 'forbid')">禁用</a>
              </c:when>
              <c:otherwise>
                <a href="javascript:void(0)" onclick="deal('${route.id}', 'allow')">启用</a>
              </c:otherwise>
            </c:choose>
            <a href="javascript:void(0)" onclick="deal('${route.id}','delete')">删除</a>
          </td>
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
                    <a href="javascript:void(0)" onclick="batchDeal('forbid')"
                       class="btn_sure_a fw"><b>批量禁用</b><em></em></a>
                </span>
      </li>
      <li>
                <span>
                    <a href="javascript:void(0)" onclick="batchDeal('allow')"
                       class="btn_sure_a fw"><b>批量启用</b><em></em></a>
                </span>
      </li>
      <li>
                <span>
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
          url: GV.ctxPath + "routes/" + operation + "?id=" + id,
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
            url: GV.ctxPath + "routes/" + operation + "?" + ids,
            style: "REDIRECT"
          });
        });
      }
    }
  </script>
</c:if>