<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<script type="text/javascript">
  $(document).ready(function(){
    var ownerValRule = {
      "loginName" : {
        len: {max:20},
        datatype : "^[a-zA-Z0-9\\-\\.]+$",
        label:"登录名"
      },
      "ownerName" : {
        len: {max:20},
        datatype : "^[^<>]+$",
        label:"姓名"
      }
    };
    ValidateExt.listen("editOwnerForm", ownerValRule, ValidateExt.nextlineError);
    $("a[name=addOwner]").click(function(){
      $("#editOwnerForm input[name=ownerId]").val("");
      $("#editOwnerForm input[name=loginName]").val("");
      $("#editOwnerForm input[name=ownerName]").val("");
      popupForEditOwner();
    });

    $("a[name=editOwner]").click(function(){
      var ownerId = $(this).attr("ownerId");
      var loginName = $(this).parent().siblings().eq(0).text();
      var ownerName = $(this).parent().siblings().eq(1).text() || "";
      $("#editOwnerForm input[name=ownerId]").val(ownerId);
      $("#editOwnerForm input[name=loginName]").val(loginName);
      $("#editOwnerForm input[name=ownerName]").val(ownerName.relace("@yeepay.com",""));
      popupForEditOwner("修改负责人信息");
    });
    $("a[name=delOwner]").click(function () {
      var ownerId = $(this).attr("ownerId");
      MessageBoxExt.confirm("是否确认删除负责人？", function () {
        MessageBoxExt.ajax({
          url : GV.ctxPath + 'owners/delete',
          type : 'POST',
          dataType : "json",
          style : "REDIRECT",
          data : {
            "ownerId" : ownerId
          },
          cache : false
        });
      });
    });
  });
  function popupForEditOwner(title) {
    MessageBoxExt.popup("_editOwnerBlock", {
      title : title || "新增负责人信息",
      width : "auto",
      height : "auto",
      buttons: {
        "确定":function(){
          if (ValidateExt.val("editOwnerForm")) {
            MessageBoxExt.ajax({
              url : GV.ctxPath + 'owners/save',
              type : 'POST',
              dataType : "json",
              style : "REDIRECT",
              data : $("#editOwnerForm").serialize(),
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
<div id="_editOwnerBlock" style="display:none">
  <div class="information">
  <div class="clear"></div>
    <form name="editOwnerForm" id="editOwnerForm" method="post">
      <div class="input_cont">
        <ul>
          <li>
            <label class="text_tit">登录名：</label>
            <input type="hidden" name="appName" value="${app.appName}"/>
            <input type="hidden" name="ownerId" value=""/>
            <input class="input_text" type="text" name="loginName" value=""/>
            <span style="color: gray; font-size:12px;">@yeepay.com</span>
          </li>
          <li>
            <label class="text_tit">姓名：</label>
            <p>
              <input class="input_text" type="text" name="ownerName" value=""/>
              <span style="color: gray; font-size:12px;">说明：可不输入，根据登录名自动查找</span>
            </p>
          </li>
        </ul>
      </div>
    </form>
  </div>
</div>
