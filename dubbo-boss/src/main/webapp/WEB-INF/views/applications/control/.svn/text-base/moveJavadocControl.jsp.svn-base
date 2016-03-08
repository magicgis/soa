<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<script type="text/javascript">
  $(document).ready(function(){
    var javadocValRule = {
      "newJavadocPath" : {
        req: true,
        len: {max:100},
        datatype : "url",
        label:"Javadoc路径"
      },
      "oldJavadocPath" : {
        len: {max:100},
        datatype : "url",
        label:"Javadoc路径"
      }
    };
    ValidateExt.listen("javadocForm", javadocValRule, ValidateExt.nextlineError);
    $("#moveJavadoc").click(function(){
      MessageBoxExt.popup("_javadocBlock", {
        title : "迁移Javadoc",
        width : "440px",
        height : "auto",
        buttons: {
          "确定":function(){
            if (ValidateExt.val("javadocForm")) {
              if ($("#javadocForm input[name=oldJavadocPath]").val() == $("#javadocForm input[name=newJavadocPath]").val()) {
                MessageBoxExt.alert("新旧javadoc路径相同！");
                return;
              }
              MessageBoxExt.ajax({
                url : GV.ctxPath + 'applications/moveJavadoc',
                type : 'POST',
                dataType : "json",
                style : "REDIRECT",
                data : $("#javadocForm").serialize(),
                cache : false
              });
            }
          },
          "关闭": function() {
            $(this).dialog("close");
          }
        }
      });
    });
  });
</script>
<div id="_javadocBlock" style="display:none">
  <div class="information">
  <div class="clear"></div>
    <form name="javadocForm" id="javadocForm" method="post">
      <div class="input_cont">
        <ul>
          <li>
            <label class="text_tit">旧路径：</label>
            <input class="input_text" type="text" style="width:300px;" name="oldJavadocPath" value="${viewLogicHelper.getJavadocCtxPath()}"/>
          </li>
          <li>
            <label class="text_tit">新路径：</label>
            <input class="input_text" type="text" style="width:300px;" name="newJavadocPath" value="${viewLogicHelper.getJavadocCtxPath()}"/>
          </li>
        </ul>
      </div>
    </form>
  </div>
</div>
