<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${result != null && result.paginator != null}">
  <div class="clearer"></div>
  <c:set var="paginator" value="${result.paginator}"/>
  <c:set var="currentPage" value="${paginator.page}"/>
  <div class="pages mt20">
    <a href="javascript:void(0);" class="a2">第${currentPage}页 共${paginator.getPages()}页 ${paginator.getTotalCount()}条</a>
    <c:choose>
    <c:when test="${currentPage > 1}">
      <a class="easyquery_paginglink" href='javascript:gotoPage(1)' >首页</a>
      <a class="easyquery_paginglink" href='javascript:gotoPage(${paginator.getPreviousPage()})' >上一页</a>
    </c:when>
    <c:otherwise>
      <a href="javascript:void(0);" class="a2">首页</a>
      <a href="javascript:void(0);" class="a2">上一页</a>
    </c:otherwise>
    </c:choose>
    <c:choose>
    <c:when test="${paginator.getNextPage() > currentPage}">
      <a class="easyquery_paginglink" href='javascript:gotoPage(${paginator.getNextPage()})' >下一页</a>
      <a class="easyquery_paginglink" href='javascript:gotoPage(${paginator.getLastPage()})' >尾页</a>
    </c:when>
    <c:otherwise>
       <a href="javascript:void(0);" class="a2">下一页</a>
       <a href="javascript:void(0);" class="a2">尾页</a>
    </c:otherwise>
    </c:choose>
      <input id="_pageSize_" type="hidden" value="${paginator.pageSize}"/>
      前往第<input size="5"  style="border:#bdb9b8 1px solid;" id="_pageNo_" type="text" onkeydown='if(event.keyCode==13) gotoPage();'/>页
      <a href="javascript:void(0);" onclick='gotoPage();return false;'>GO</a>
  </div>
  <script type="text/javascript">
  function gotoPage(pageNo) {
    pageNo = pageNo || $("#_pageNo_").val();
    var pageSize = $("#_pageSize_").val() || 50;
    if (!pageNo) {
      return;
    }
    $form = $("div.information > form > div.search").parent();
    if ($form) {
      addHiddenParam($form[0], "pageNo", pageNo);
      addHiddenParam($form[0], "pageSize", pageSize);
      $form.submit();
    }
  }

  function addHiddenParam(form, pname, pvalue){
    var inputNode=document.createElement("input");
    inputNode.setAttribute("type",'hidden');
      inputNode.setAttribute("name", pname);
      inputNode.setAttribute("value", pvalue);
      form.appendChild(inputNode);
  }
  </script>
</c:if>
