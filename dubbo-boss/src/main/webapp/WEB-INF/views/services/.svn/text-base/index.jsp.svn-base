<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
  <title>服务查询</title>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <style type="text/css">
  #serviceQueryForm {
    width : 600px;
    margin: 200px auto;
  }
  #serviceQueryForm .input_text {
    width : 300px;
  }
  #serviceQueryForm .select_border {
    width : 80px;
  }
  #serviceQueryForm .select {
    width : 60px;
    margin-top: 3px;
  }
  #serviceQueryForm .btn_sure {
    float : left;
  }
  </style>
</head>
<body>
<div class="Container">
  <div class="Content fontText">
    <div class="result">
      <div class="information">
        <form action="${ctxPath}services/query" method="get" id="serviceQueryForm">
          <ul>
            <li>
              <input type="text" id="keyword" name="keyword" class="input_text"/>
              <div class="select_border">
                <select id="type" name="type" class="select">
                  <option value="service">服务</option>
                  <option value="app">应用</option>
                </select>
              </div>
              <input type="submit" class="btn_sure fw" value="查询"/>
            </li>
          </ul>
        </form>
      </div>
      <div class="clearer"></div>
    </div>
  </div>
</div>
</body>
</html>
