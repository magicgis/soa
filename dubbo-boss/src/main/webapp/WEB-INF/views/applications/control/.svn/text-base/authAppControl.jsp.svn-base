<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<script type="text/javascript">
  $(document).ready(function(){
    $("a[name=authApp]").click(function(){
      var toAppName = $(this).attr("appName") || "";
      if (!toAppName) {
        MessageBoxExt.popup("_authAppBlock", {
          title : "新增应用授权",
          width : "auto",
          height : "auto",
          buttons: {
            "确定":function(){
              if (ValidateExt.val("authAppForm")) {
                MessageBoxExt.ajax({
                  url : GV.ctxPath + 'applications/auth',
                  type : 'POST',
                  dataType : "json",
                  style : "REDIRECT",
                  data : $("#authAppForm").serialize(),
                  cache : false
                });
              }
            },
            "关闭": function() {
              $(this).dialog("close");
            }
          }
        });
      } else{
        var type = $(this).attr("type");
        var appName = $("#_currentAppName").val() || "";
        if (!type || !appName) {
          return;
        }
        var opName = $(this).text() + "应用";
        MessageBoxExt.confirm("确认" + opName + "？", function () {
          MessageBoxExt.ajax({
            url : GV.ctxPath + 'applications/' + type,
            type : 'POST',
            dataType : "json",
            style : "REDIRECT",
            data : {
              "appName" : appName,
              "toAppName" : toAppName
            },
            cache : false
          });
        });
      }
    });
  });
</script>
<div id="_authAppBlock" style="display:none">
  <div class="information">
  <div class="clear"></div>
    <form name="authAppForm" id="authAppForm" owner="post">
      <div class="input_cont">
        <ul>
          <li>
            <label class="text_tit">授权给：</label>
            <input type="hidden" name="appName" value="${app.appName}"/>
            <input class="input_text" type="text" name="toAppName" value=""/>
          </li>
        </ul>
      </div>
    </form>
  </div>
</div>
