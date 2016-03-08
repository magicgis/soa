<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<style>
  .active {
    background-color: #f5f5f5;
  }

  .success {
    background-color: #dff0d8;
  }

  .warning {
    background-color: #fcf8e3;
  }

  .danger {
    background-color: #f2dede;
  }

  .help-block {
    display: inline;
    margin-top: 5px;
    margin-bottom: 10px;
    color: #31b0d5;
  }

  .alert-danger {
    color: #a94442;
    background-color: #f2dede;
    border-color: #ebccd1;
  }

  input[disabled], textarea[disabled], select[disabled],
  input[readonly], textarea[readonly], select[readonly] {
    cursor: not-allowed;
    background-color: #eee;
    opacity: 1;
  }

  .list_info a {
    cursor: pointer;
  }

  .list_info tr td.info_tit {
    width: 200px !important;
  }

  .list_info tr td.info_con {
    width: auto !important;
  }

  .list_info th {
    font-weight: bold;
    background: #fafcff;
  }

  .inner_table {
    border: none;
  }

  .inner_table td {
    border-left: none !important;
    border-right: none !important;
    border-bottom: none !important;
  }

  .hiddenInput {
    display: none;
  }

  .top_right_edit {
    right: 80px;
    position: absolute;
    color: #0c5b97;
  }

  .nonOperate {
    color: gray !important;
    cursor: none;
  }
  a.numLink {
    display : inline-block;
    width : 100%;
  }
  a.numLink:hover {
    background : #f8f8f8 !important;
  }
</style>
<link rel="stylesheet" type="text/css" href="${resourceUrl}/css/chosen.min.css">
<script type="text/javascript" src="${resourceUrl}/js/chosen.jquery.min.js"></script>
<script type="text/javascript">
  var GV = {
    ctxPath: '${ctxPath}',
    imgPath: '${resourceUrl}/images',
    jsPath: '${resourceUrl}/js'
  };
  function queryDubbo() {
    var service = $.trim($("#service").val());
    if ("" != service) {
      window.location.href = "${ctxPath}services?keyword=" + service;
    }
    var application = $.trim($("#application").val());
    if ("" != application) {
      window.location.href = "${ctxPath}applications?keyword=" + application;
    }
    var address = $.trim($("#address").val());
    if ("" != address) {
      window.location.href = "${ctxPath}addresses?keyword=" + address;
    }
  }
  $(document).ready(function(){
    $(".list td a").each(function(){
      var text = $.trim($(this).text());
      if (/^[0-9]+$/.test(text)) {
        $(this).addClass("numLink");
      }
    })
  });
</script>
<script src="${resourceUrl}/js/dubbo.js"></script>
