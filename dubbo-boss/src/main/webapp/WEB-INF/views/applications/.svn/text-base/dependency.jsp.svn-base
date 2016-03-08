<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>全局应用依赖</title>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="information">
      <div class="setup">
        <h1 class="fw fleft f14">应用汇总：应用数：${totalCount} | 子系统数：${coreCount} | 前端应用数：${appCount}</h1>
      </div>
      <div class="clearer"></div>
    </div>
    <div class="result" id="_dependency" style="width:100%;height:800px;"></div>
  </div>
  <div class="clearer"></div>
  <br/>
</div>
<script type="text/javascript" src="${resourcePath}/common/component/echarts-2.0.2/echarts-plain.js"></script>
<script type="text/javascript">
  // 基于准备好的dom，初始化echarts图表
  var myChart = echarts.init(document.getElementById('_dependency'));
  var option = ${dependencyMeta};
  // 为echarts对象加载数据
  myChart.setOption(option);
</script>
</body>
</html>
