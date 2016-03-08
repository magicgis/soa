$(document).ready(function () {
  $("#syncRegistry").click(function () {
    MessageBoxExt.ajax({
      url : GV.ctxPath + 'registry/sync',
      type : 'POST',
      dataType : "json",
      style : "redirect"
    });
  });
  $("#clearCache").click(function () {
    MessageBoxExt.ajax({
      url : GV.ctxPath + 'registry/clearCache',
      type : 'POST',
      dataType : "json",
      style : "redirect"
    });
  });
  $("a[name=unregister]").click(function () {
    var objId = $(this).attr("objId");
    if (objId) {
      MessageBoxExt.confirm("是否确认取消注册？", function () {
        MessageBoxExt.ajax({
          url : GV.ctxPath + 'registry/unregister',
          type : 'POST',
          dataType : "json",
          style : "REDIRECT",
          data : {
            "id" : objId
          },
          cache : false
        });
      });
    }
  });
  $("a[name=unsubscribe]").click(function () {
    var objId = $(this).attr("objId");
    if (objId) {
      MessageBoxExt.confirm("是否确认取消订阅？", function () {
        MessageBoxExt.ajax({
          url : GV.ctxPath + 'registry/unsubscribe',
          type : 'POST',
          dataType : "json",
          style : "REDIRECT",
          data : {
            "id" : objId
          },
          cache : false
        });
      });
    }
  });
});