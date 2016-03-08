$(document).ready(function () {
  $(".tag[name=tabSwitchOp] a").click(function () {
    $(this).siblings("a").removeClass("on");
    $(this).addClass("on");
    fethTabContent({
      url : $(this).attr("url"),
      context : $(this).attr("context"),
      ref : $(this).attr("ref")
    });
  });
  $(".tag[name=tabSwitchOp] a:eq(0)").click();
});

function fethTabContent (H) {
  var blockId = H.ref || "_tabContent";
  if (!H.url) {
    showTabView(blockId);
    return;
  }
  var url = H.url;
  $.ajax({
    url : H.url,
    type : "GET",
    data : H.context + "&blockModel=true",
    success : function (result, status) {
      $("#" + blockId).html(result);
      showTabView(blockId);
      if (blockId == '_services' && initForServiceMgr) {
        initForServiceMgr();
      } else if (blockId == '_applications' && initForAppMgr) {
        initForAppMgr();
      }
    }
  });
}

function showTabView (id) {
  $("div[name=tabSwitchView]>div").hide();
  $("#" + id).show();
}