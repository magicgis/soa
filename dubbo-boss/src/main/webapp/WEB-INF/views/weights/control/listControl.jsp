<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="result">
  <h2 class="fw">查询结果</h2>
  <table class="list">
    <tbody>
    <tr>
      <th width="15px"><input type="checkbox" onclick="checkAll('queryTable', 'recordId', this.checked)"/></th>
      <th width="150px">消费者地址</th>
      <th>服务名</th>
      <th width="60px">权重</th>
      <c:if test="${viewLogicHelper.isSoaAdmin()}">
        <th width="80px">可用操作</th>
      </c:if>
    </tr>
    <c:forEach items="${weights}" var="weight">
      <tr>
        <td><input type="checkbox" name="recordId" value="${weight.id}"/></td>
        <td><a href="${ctxPath}addresses/${weight.address}">${weight.address}</a></td>
        <td><a href="${ctxPath}services/detail/${weight.service}">${weight.service}</a></td>
        <td>${weight.weight}</td>
        <c:if test="${viewLogicHelper.isSoaAdmin()}">
          <td><a href="${ctxPath}weights/edit?id=${weight.id}">编辑</a> <a
                  href="javascript:void(0)" onclick="deleteWeight('${weight.id}')">删除</a>
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
                    <a href="javascript:void(0)" onclick="batchDelete()" class="btn_sure_a fw"><b>批量删除</b><em></em></a>
                </span>
      </li>
    </ul>
  </div>
  <script type="text/javascript">
    function deleteWeight(id) {
      MessageBoxExt.confirm("确认删除 #" + id + " 的权重调节规则嘛？", function () {
        MessageBoxExt.ajax({
          url: GV.ctxPath + "weights/delete?id=" + id,
          style: "REDIRECT"
        });
      });
    }
    function batchDelete() {
      var ids = "id=";
      $(":checkbox[name='recordId']:checked").map(function () {
        ids += this.value + "&id=";
      });
      ids = ids.substr(0, ids.length - 4);
      console.debug("ids:" + ids);

      if (ids === "") {
        MessageBoxExt.alert("请选择要操作的项");
      } else {
        MessageBoxExt.confirm("确认删除 #" + ids + " 的权重调节规则嘛？", function () {
          MessageBoxExt.ajax({
            url: GV.ctxPath + "weights/delete?" + ids,
            style: "REDIRECT"
          });
        });
      }
    }
  </script>
</c:if>