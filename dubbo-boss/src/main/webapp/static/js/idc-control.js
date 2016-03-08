$(document).ready(function () {
  $("#crossRateSlider").slider("enable");
  $("#crossRateSlider").slider({
    change : function (event, ui) {
      var p = $("#crossRateValue").text();
      var v = p.substring(0, p.length - 1);
      if (v == ui.value) {
        return;
      }
      MessageBoxExt.confirm("确认将跨机房流量比例调整为" + ui.value + "%?", [function () {
        $("#crossRateValue").text(ui.value + "%");
        MessageBoxExt.ajax({
          url : GV.ctxPath + 'monitor/idc/crossrate',
          type : 'POST',
          dataType : "json",
          style : "REDIRECT",
          data : {
            rate : ui.rate
          },
          cache : false
        });
      }, function () {
        $("#crossRateSlider").slider("value", v);
      }]);
    }
  });
  $("div[name=idcSwitchBlock]").each(function () {
    $(this).buttonset("enable");
  });
  $("div[name=idcSwitchBlock]>input[type=radio]").change(function () {
    var f = $(this).attr("id");
    var p = f.lastIndexOf("_on");
    if (p < 0) {
      p = f.lastIndexOf("_off");
    }
    if (p < 0) {
      return;
    }
    var idc = f.substring(0, p);
    var st = f.substring(p + 1) == "on";
    if (!st) {
      var ss = $("div[name=idcSwitchBlock]>input[id$=_off]");
      var all = true;
      for (var i = 0; i < ss.length; i++) {
        if (!ss[i].checked) {
          all = false;
          break;
        }
      }
      if (all) {
        MessageBoxExt.alert("不能禁用所有机房!");
        $("#" + idc + "_on").attr("checked", "checked");
        $("div[name=idcSwitchBlock]").each(function () {
          $(this).buttonset("refresh");
        });
        return;
      }
    }
    var msg = "确认" + (st ? "启用" : "禁用") + idc + "机房?";
    MessageBoxExt.confirm(msg, [function () {
      MessageBoxExt.ajax({
        url : GV.ctxPath + 'monitor/idc/status',
        type : 'POST',
        dataType : "json",
        style : "REDIRECT",
        data : {
          idcName : idc,
          status : st
        },
        cache : false
      });
    }, function () {
      $("#" + idc + (st ? "_off" : "_on")).attr("checked", "checked");
      $("div[name=idcSwitchBlock]").each(function () {
        $(this).buttonset("refresh");
      });
    }]);
  });

  $("input[name=ipRule]").each(function () {
    $(this).removeAttr("disabled");
    $(this).change(function () {
      var owner = $(this);
      var idc = $(this).attr("idc");
      var old = $(this).attr("old");
      var rule = $(this).val();
      if (old == rule) {
        // 修改-取消-再改回原值
        return;
      }
      MessageBoxExt.confirm("确认修改" + idc + "机房的IP规则为" + rule + "?", [function () {
        owner.attr("old", rule);
        MessageBoxExt.ajax({
          url : GV.ctxPath + 'monitor/idc/iprule',
          type : 'POST',
          dataType : "json",
          style : "REDIRECT",
          data : {
            idcName : idc,
            ipRule : rule
          },
          cache : false
        });
      }, function () {
        owner.val(old);
      }]);
    });
  });
});