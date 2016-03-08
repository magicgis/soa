$(document).ready(function () {
  $(".list tr:contains('提供者')").removeClass('even');
  $(".list tr:contains('提供者')").addClass('success');
  $(".list tr:contains('宕机')").addClass('error');
  initForAppMgr();
});
function initForAppMgr() {
  $("a[name=appOperate]").click(function () {
    var type = $(this).attr("type");
    var appName = $(this).attr("appName");
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
          "appName" : appName
        },
        cache : false
      });
    });
  });
}