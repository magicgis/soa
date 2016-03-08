<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<div class="result">
  <h2 class="fw">查询结果</h2>
  <table class="list">
    <tbody>
    <tr>
      <th width="15px"><input type="checkbox" onclick="checkAll('queryTable', 'recordId', this.checked)"/></th>
      <th width="150px">机器</th>
      <th>服务</th>
      <th>协议</th>
      <th width="45px">权重</th>
      <th width="45px">类型</th>
      <th width="45px">状态</th>
      <th width="45px">检查</th>
      <c:if test="${viewLogicHelper.isSoaAdmin()}">
        <th width="140px">可用操作</th>
      </c:if>
    </tr>
    <c:forEach items="${providers}" var="providerList">
      <c:set var="provider" value="${providerList.value[0]}"/>
      <tr>
        <td><input type="checkbox" name="recordId" value="${provider.id}"/></td>
        <td>
          <a href="${ctxPath}providers?address=${provider.address}"
             title="查看机器">${provider.address}</a>
        </td>
        <td>${provider.service}</td>
        <td>
          <c:forEach items="${providerList.value}" var="subProvider">
            <a href="${ctxPath}providers/${subProvider.id}"
               title="查看服务提供者详情">${fn:substring(subProvider.url, 0, fn:indexOf(subProvider.url, ":"))}</a>
          </c:forEach>
        </td>
        <td>${provider.weight}</td>
        <c:choose>
          <c:when test="${provider.dynamic}">
            <td class="success">动态</td>
          </c:when>
          <c:otherwise>
            <td>静态</td>
          </c:otherwise>
        </c:choose>
        <c:choose>
          <c:when test="${provider.enabled}">
            <td class="success">已启用</td>
          </c:when>
          <c:otherwise>
            <td class="error">禁用</td>
          </c:otherwise>
        </c:choose>
        <td>${provider.alived}</td>
        <c:if test="${viewLogicHelper.isSoaAdmin()}">
          <td>
              <%--<a href="${ctxPath}providers/edit?id=${provider.id}">编辑</a>--%>
              <%--<a href="${ctxPath}providers/add?id=${provider.id}">复制</a>--%>
            <a href="javascript:void(0)" onclick="deal('${provider.id}', 'doubling')">倍权</a>
            <a href="javascript:void(0)" onclick="deal('${provider.id}', 'halving')">半权</a>
            <c:choose>
              <c:when test="${provider.enabled}">
                <a href="javascript:void(0)" onclick="deal('${provider.id}', 'forbid')">禁用</a>
              </c:when>
              <c:otherwise>
                <a href="javascript:void(0)" onclick="deal('${provider.id}', 'allow')">启用</a>
              </c:otherwise>
            </c:choose>
            <c:if test="${provider.dynamic}">
              <a href="javascript:void(0)" onclick="deal('${provider.id}', 'delete')">删除</a>
            </c:if>
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
                    <a href="javascript:void(0)" onclick="batchDeal('doubling')"
                       class="btn_sure_a fw"><b>批量倍权</b><em></em></a>
                </span>
      </li>
      <li>
                <span>
                    <a href="javascript:void(0)" onclick="batchDeal('halving')"
                       class="btn_sure_a fw"><b>批量半权</b><em></em></a>
                </span>
      </li>
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
          url: GV.ctxPath + "providers/" + operation + "?id=" + id,
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
            url: GV.ctxPath + "providers/" + operation + "?" + ids,
            style: "REDIRECT"
          });
        });
      }
    }
  </script>
</c:if>