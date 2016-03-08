<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<script type="text/javascript">
  $(document).ready(function(){
    var appValRule = {
      "appName" : {
        req : true,
        len: {max:150},
        datatype : "^[a-zA-Z0-9\-]+$",
        label:"应用名称"
      },
      "appTitle" : {
        req : true,
        len: {max:150},
        label:"应用标题"
      },
      "javaDoc" : {
        req : false,
        len: {max:200},
        datatype : "url",
        label:"javaDoc"
      },
      "docPath" : {
        req : false,
        len: {max:200},
        label:"外部文档"
      },
      "appDesc" : {
        req : true,
        len: {max:2000},
        label:"应用描述"
      }
    };
    ValidateExt.listen("editAppForm", appValRule, ValidateExt.nextlineError);
    $("#addApp").click(function(){
      $("#editAppForm").find(inputSelector).each(function () {
        var type = $(this).attr("type");
        if (type == "checkbox" || type == "radio") {
          $(this).attr("checked","");
        } else {
          $(this).val("");
        }
      });
      $("#editAppForm input[name=appName]").removeAttr("readonly");
      popupForEditApp();
    });
    $("#editApp").click(popupForEditApp);
  });
  function popupForEditApp() {
    MessageBoxExt.popup("_editAppBlock", {
      title : $("#editAppForm input[name=appName]").val() || "修改应用信息",
      width : 745,
      height : "auto",
      buttons: {
        "确定":function(){
          if (ValidateExt.val("editAppForm")) {
            var docPath = $("#editAppForm textarea[name=docPath]").val() || "";
            var docPaths = docPath.split(/[,;，；]/);
            for (var i in docPaths) {
              if (docPaths[i] && !ValidateExt.checkValue($.trim(docPaths[i]), "url")) {
                MessageBoxExt.alert("外部文档URL格式不正确，多URL请以逗号分隔");
                return;
              }
            }
            if (docPath) {
              $("#editAppForm textarea[name=docPath]").val(docPaths.join(","));
            }
            MessageBoxExt.ajax({
              url : GV.ctxPath + 'applications/save',
              type : 'POST',
              dataType : "json",
              style : "REDIRECT",
              data : $("#editAppForm").serialize(),
              cache : false
            });
          }
        },
        "关闭": function() {
          $(this).dialog("close");
        }
      }
    });
  }
</script>
<div id="_editAppBlock" style="display:none">
  <div class="information">
  <div class="clear"></div>
    <form name="editAppForm" id="editAppForm" method="post">
      <div class="input_cont">
        <ul>
          <li>
            <label class="text_tit">应用名：</label>
            <input class="input_text" type="text" style="width:600px;" id="_currentAppName" name="appName" value="${app.appName}" readonly="true">
          </li>
          <li>
            <label class="text_tit">应用标题：</label>
            <input class="input_text" type="text" style="width:600px;" name="appTitle" value="${app.appTitle}">
          </li>
          <li>
            <label class="text_tit">javaDoc：</label>
            <input class="input_text" type="text" style="width:600px;" name="javaDoc" value="${app.javaDoc}">
          </li>
          <li>
            <label class="text_tit">外部文档：</label>
            <p>
              <textarea class="textfield" style="width:600px;min-height:40px;" name="docPath">${app.docPath}</textarea><br/>
              <span style="color: gray; font-size:12px;">说明：多URL可用","分隔，最多允许输入2000个字符</span>
            </p>
          </li>
          <li>
            <label class="text_tit">应用描述：</label>
            <p>
              <textarea class="textfield" style="width:600px;min-height:150px;" name="appDesc">${app.appDesc}</textarea>
            </p>
          </li>
        </ul>
      </div>
    </form>
  </div>
</div>
