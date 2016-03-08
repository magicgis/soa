<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<script type="text/javascript">
  $(document).ready(function(){
    var serviceValRule = {
      "serviceName" : {
        req : true,
        len: {max:150},
        label:"服务名称"
      },
      "serviceDesc" : {
        req : true,
        len: {max:2000},
        label:"服务描述"
      }
    };
    ValidateExt.listen("editServiceForm", serviceValRule, ValidateExt.nextlineError);
    $("#editService").click(function(){
      MessageBoxExt.popup("_editServiceBlock", {
        title : $("#editServiceForm input[name=serviceInterface]").val() || "修改服务信息",
        width : 745,
        height : "auto",
        buttons: {
          "确定":function(){
            if (ValidateExt.val("editServiceForm")) {
              MessageBoxExt.ajax({
                url : GV.ctxPath + 'services/save',
                type : 'POST',
                dataType : "json",
                style : "REDIRECT",
                data : $("#editServiceForm").serialize(),
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
<div id="_editServiceBlock" style="display:none">
  <div class="information">
  <div class="clear"></div>
    <form name="editServiceForm" id="editServiceForm" method="post">
      <div class="input_cont">
        <ul>
          <li>
            <label class="text_tit">服务名：</label>
            <input type="hidden" name="serviceId" value="${service.id}">
            <input type="hidden" name="serviceInterface" value="${service.serviceInterface}">
            <input class="input_text" type="text" style="width:600px;" name="serviceName" value="${service.serviceName}">
          </li>
          <li>
            <label class="text_tit">服务描述：</label>
            <p>
              <textarea class="textfield" style="width:600px;min-height:200px;" name="serviceDesc">${service.serviceDesc}</textarea>
            </p>
          </li>
        </ul>
      </div>
    </form>
  </div>
</div>
