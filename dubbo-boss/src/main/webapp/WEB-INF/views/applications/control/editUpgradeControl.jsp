<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<script type="text/javascript">
  $(document).ready(function () {
    $("#_latestUpgrade tr:eq(0) td").css("border-top", "none");
    var upgradeValRule = {
      "upgradeInfo": {
        req: true,
        len: {max: 1000},
        label: "升级描述"
      }
    };
    ValidateExt.listen("editUpgradeForm", upgradeValRule, ValidateExt.nextlineError);
    $("a[name=addUpgrade]").click(function () {
      $("#editUpgradeForm input[name=upgradeId]").val("");
      $("#editUpgradeForm textarea[name=upgradeInfo]").val("");
      popupForEditUpgradeInfo("添加升级信息");
    });
    $("a[name=editUpgrade]").click(function () {
      var upgradeId = $(this).attr("upgradeId");
      var upgradeInfo = $.trim($(this).parent().siblings().eq(0).html().replace(/<br\/?>/, "\n"));
      $("#editUpgradeForm input[name=upgradeId]").val(upgradeId);
      $("#editUpgradeForm textarea[name=upgradeInfo]").val(upgradeInfo);
      popupForEditUpgradeInfo();
    });
    $("a[name=delUpgrade]").click(function () {
      var upgradeId = $(this).attr("upgradeId");
      MessageBoxExt.confirm("确认删除此条升级信息？", function () {
        MessageBoxExt.ajax({
          url: GV.ctxPath + 'upgrades/delete',
          type: 'POST',
          dataType: "json",
          style: "REDIRECT",
          data: {
            "upgradeId": upgradeId
          },
          cache: false
        });
      });
    });
  });
  function popupForEditUpgradeInfo(title) {
    MessageBoxExt.popup("_editUpgradeBlock", {
      title: title || "修改升级信息",
      width: 745,
      height: "auto",
      buttons: {
        "确定": function () {
          if (ValidateExt.val("editUpgradeForm")) {
            MessageBoxExt.ajax({
              url: GV.ctxPath + 'upgrades/save',
              type: 'POST',
              dataType: "json",
              style: "REDIRECT",
              data: $("#editUpgradeForm").serialize(),
              cache: false
            });
          }
        },
        "关闭": function () {
          $(this).dialog("close");
        }
      }
    });
  }
</script>
<div id="_editUpgradeBlock" style="display:none">
  <div class="information">
    <div class="clear"></div>
    <form name="editUpgradeForm" id="editUpgradeForm" upgrade="post">
      <div class="input_cont">
        <ul>
          <li>
            <label class="text_tit">升级描述：</label>

            <p>
              <input type="hidden" name="upgradeId">
              <input type="hidden" name="appName" value="${app.appName}"/>
              <textarea class="textfield" style="width:600px;min-height:200px;" name="upgradeInfo"></textarea>
            </p>
          </li>
          <li>
            <label class="text_tit">发送邮件：</label>
            <input type="checkbox" name="mail"/>
          </li>
        </ul>
      </div>
    </form>
  </div>
</div>
