$(document).ready(function () {
  initForServiceMgr();
});

function initForServiceMgr() {
  $(".list tr:contains('宕机')").addClass('error');
  $("a[name=serviceOperate]").click(function () {
    var context = $(this);
    var type = $(this).attr("type");
    var serviceId = $(this).attr("serviceId");
    if (!type || !serviceId) {
      return;
    }
    var opName = $(this).text() + "应用";
    MessageBoxExt.confirm("确认" + opName + "？", function () {
      MessageBoxExt.ajax({
        url : GV.ctxPath + 'services/' + type,
        type : 'POST',
        dataType : "json",
        style : "callback",
        data : {
          "serviceId" : serviceId
        },
        cache : false,
        callback : function(result, status) {
          MessageBoxExt.closeAll();
          if (context.parents("div[name=tabSwitchView]").length > 0) {
            context.unbind();
            context.addClass("nonOperate");
            context.parent().siblings().eq(8).text(context.text());
          } else {
            self.location.reload();
          }
        }
      });
    });
  });
}