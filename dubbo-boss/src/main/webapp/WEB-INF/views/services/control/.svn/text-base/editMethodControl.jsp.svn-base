<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<script type="text/javascript">
  $(document).ready(function(){
    var methodValRule = {
      "methodDesc" : {
        req : true,
        len: {max:2000},
        label:"方法描述"
      }
    };
    ValidateExt.listen("editMethodForm", methodValRule, ValidateExt.nextlineError);
    $("a[name=editMethod]").click(function(){
      var methodId = $(this).attr("methodId");
      var methodName = $.trim($(this).parent().siblings().eq(0).text());
      var methodDesc = $.trim($(this).parent().siblings().eq(1).html().replace(/<br\/?>/,"\n"));
      $("#editMethodForm input[name=methodId]").val(methodId);
      $("#editMethodForm input[name=methodName]").val(methodName);
      $("#editMethodForm textarea[name=methodDesc]").val(methodDesc);
      MessageBoxExt.popup("_editMethodBlock", {
        title : methodName || "修改方法信息",
        width : 745,
        height : "auto",
        buttons: {
          "确定":function(){
            if (ValidateExt.val("editMethodForm")) {
              MessageBoxExt.ajax({
                url : GV.ctxPath + 'services/method/save',
                type : 'POST',
                dataType : "json",
                style : "REDIRECT",
                data : $("#editMethodForm").serialize(),
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
<div id="_editMethodBlock" style="display:none">
  <div class="information">
  <div class="clear"></div>
    <form name="editMethodForm" id="editMethodForm" method="post">
      <div class="input_cont">
        <ul>
          <li>
            <label class="text_tit">方法描述：</label>
            <p>
              <input type="hidden" name="methodId">
              <input type="hidden" name="methodName">
              <textarea class="textfield" style="width:600px;min-height:200px;" name="methodDesc"></textarea>
            </p>
          </li>
        </ul>
      </div>
    </form>
  </div>
</div>
