<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<div class="result">
  <h2 class="fw">查询结果</h2>
  <table class="list">
    <tbody>
    <tr>
      <th width="15px"><input type="checkbox" onclick="checkAll('queryTable', 'recordId', this.checked)"/></th>
      <th width="110px">消费者地址</th>
      <th>服务名</th>
      <th>应用名</th>
      <th width="45px">访问</th>
      <th width="45px">降级</th>
      <th width="75px">路由</th>
      <th width="75px">通知</th>
      <th width="140px">可用操作</th>
    </tr>
    <c:forEach items="${consumers}" var="consumer">
      <tr>
        <td><input type="checkbox" name="recordId" value="${consumer.id}"/></td>
        <td><a href="${ctxPath}consumers/${consumer.id}" title="查看详情">${consumer.address}</a></td>
        <td><a href="${ctxPath}consumers/list?service=${consumer.service}" title="按服务名查询">${consumer.service}</a></td>
        <td><a href="${ctxPath}consumers/list?application=${consumer.application}"
               title="按应用名查询">${consumer.application}</a></td>
        <c:choose>
          <c:when test="${consumer.forbid}">
            <td class="danger">已禁止</td>
          </c:when>
          <c:otherwise>
            <td class="success">允许</td>
          </c:otherwise>
        </c:choose>
        <c:choose>
          <c:when test="${consumer.mock eq 'force%3Areturn+null'}">
            <td class="danger">已屏蔽</td>
          </c:when>
          <c:when test="${consumer.mock eq 'fail%3Areturn+null'}">
            <td class="warning">已容错</td>
          </c:when>
          <c:otherwise>
            <td>未降级</td>
          </c:otherwise>
        </c:choose>
        <td>
          <c:choose>
            <c:when test="${null != consumer.routes && fn:length(consumer.routes) > 0}">
              <a href="${ctxPath}consumers/${consumer.id}#routed">已路由(${fn:length(consumer.routes)})</a>
            </c:when>
            <c:otherwise>
              未路由
            </c:otherwise>
          </c:choose>
        </td>
        <td>
          <c:choose>
            <c:when test="${null != consumer.providers && fn:length(consumer.providers) > 0}">
              <a href="${ctxPath}consumers/${consumer.id}#notified">已通知(${fn:length(consumer.providers)})</a>
            </c:when>
            <c:otherwise>
              未通知
            </c:otherwise>
          </c:choose>
        </td>
        <c:if test="${viewLogicHelper.isSoaAdmin()}">
          <td>
              <%--<a href="${ctxPath}consumers/edit?id=${consumer.id}">编辑</a>--%>
            <c:choose>
              <c:when test="${consumer.forbid}">
                <a href="javascript:void(0)" onclick="deal('${consumer.id}', 'allow')">启用</a>
              </c:when>
              <c:otherwise>
                <a href="javascript:void(0)" onclick="deal('${consumer.id}', 'forbid')">禁用</a>
              </c:otherwise>
            </c:choose>
            <c:choose>
              <c:when test="${consumer.mock eq 'force%3Areturn+null'}">
                <a href="javascript:void(0)" onclick="deal('${consumer.id}', 'recover')">恢复</a>
                <a href="javascript:void(0)" onclick="deal('${consumer.id}', 'tolerant')">容错</a>
              </c:when>
              <c:when test="${consumer.mock eq 'fail%3Areturn+null'}">
                <a href="javascript:void(0)" onclick="deal('${consumer.id}', 'shield')">屏蔽</a>
                <a href="javascript:void(0)" onclick="deal('${consumer.id}', 'recover')">恢复</a>
              </c:when>
              <c:otherwise>
                <a href="javascript:void(0)" onclick="deal('${consumer.id}', 'shield')">屏蔽</a>
                <a href="javascript:void(0)" onclick="deal('${consumer.id}', 'tolerant')">容错</a>
              </c:otherwise>
            </c:choose>
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
                    <a href="javascript:void(0)" onclick="batchDeal('forbid', '被禁止的客户端将收到禁止访问异常。')"
                       class="btn_sure_a fw"><b>批量禁止</b><em></em></a>
                </span>
      </li>
      <li>
                <span>
                    <a href="javascript:void(0)" onclick="batchDeal('allow', '')"
                       class="btn_sure_a fw"><b>批量允许</b><em></em></a>
                </span>
      </li>
      <li>
                <span>
                    <a href="javascript:void(0)"
                       onclick="batchDeal('onlyForbid', '其它未选中项及以后新增项将全部变为允许访问。\n被禁止的客户端将收到禁止访问异常。')"
                       class="btn_sure_a fw"><b>只禁止</b><em></em></a>
                </span>
      </li>
      <li>
                <span>
                    <a href="javascript:void(0)"
                       onclick="batchDeal('onlyAllow', '其它未选中项及以后新增项将全部变为禁止访问。\n被禁止的客户端将收到禁止访问异常。')"
                       class="btn_sure_a fw"><b>只允许</b><em></em></a>
                </span>
      </li>
      <li>
                <span>
                    <a href="javascript:void(0)" onclick="batchDeal('shield', '屏蔽后，将不发起远程调用，直接在客户端返回空对象。')"
                       class="btn_sure_a fw"><b>批量屏蔽</b><em></em></a>
                </span>
      </li>
      <li>
                <span>
                    <a href="javascript:void(0)" onclick="batchDeal('tolerant', '容错后，当远程调用失败时，返回空对象。')"
                       class="btn_sure_a fw"><b>批量容错</b><em></em></a>
                </span>
      </li>
      <li>
                <span>
                    <a href="javascript:void(0)" onclick="batchDeal('recover', '将取消服务的屏蔽和容错行为。')"
                       class="btn_sure_a fw"><b>批量恢复</b><em></em></a>
                </span>
      </li>
    </ul>
  </div>
  <script type="text/javascript">
    function deal(id, operation) {
      MessageBoxExt.confirm("确认操作 #" + id + " 嘛？", function () {
        MessageBoxExt.ajax({
          url: GV.ctxPath + "consumers/" + operation + "?id=" + id,
          style: "REDIRECT"
        });
      });
    }
    function batchDeal(operation, message) {
      var ids = "id=";
      $(":checkbox[name='recordId']:checked").map(function () {
        ids += this.value + "&id=";
      });
      ids = ids.substr(0, ids.length - 4);
      console.debug("ids:" + ids);

      if (ids === "") {
        MessageBoxExt.alert("请选择要操作的项");
      } else {
        MessageBoxExt.confirm(message + "\n\n确认操作 #" + ids + " 嘛？", function () {
          MessageBoxExt.ajax({
            url: GV.ctxPath + "consumers/" + operation + "?" + ids,
            style: "REDIRECT"
          });
        });
      }
    }
    //  function batchDeal2(operation) {
    //    var services = "service=";
    //    $(":checkbox[name='recordId']:checked").map(function () {
    //      services += $(this).parents('tr').find('td')[2].innerText + "&service=";
    //    });
    //    services = services.substr(0, services.length - 9);
    //    console.debug("services:" + services);
    //
    //    MessageBoxExt.confirm("确认操作 #" + services + " 嘛？", function () {
    //      MessageBoxExt.ajax({
    //        url: GV.ctxPath + "consumers/" + operation + "?" + services,
    //        style: "REDIRECT"
    //      });
    //    });
    //  }
  </script>
</c:if>