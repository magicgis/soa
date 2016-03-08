$(document).ready(function () {
  initForAddressMgr();
});

function initForAddressMgr () {
  $(".list tr:contains('提供者')").removeClass('even');
  $(".list tr:contains('提供者')").addClass('success');
  $("a[name=addressOperate]").click(function () {
    var context = $(this);
    var type = $(this).attr("type");
    var address = $(this).attr("address") || "";
    var appName = $(this).attr("appName") || "";
    if (!type || !address) {
      return;
    }
    var p = address.indexOf(":") ;
    var opName = $(this).text() + "机器" + (p > 0 ? address.substring(0, p) : address);
    MessageBoxExt.confirm("确认" + opName + "？", function () {
      MessageBoxExt.ajax({
        url : GV.ctxPath + 'addresses/' + type,
        type : 'POST',
        dataType : "json",
        style : "redirect",
        data : {
          "address" : address,
          "appName" : appName
        },
        cache : false
      });
    });
  });
}