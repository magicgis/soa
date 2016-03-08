$(function () {
  // 请求参数自动回填
  if (typeof query_autoFillParams === 'undefined' || query_autoFillParams) {
    revertRequestParams();
  }
  $(function () {
    $(function () {
      var url = decodeURIComponent(location.search);
      if (typeof query_autoFillDate === 'undefined' || query_autoFillDate) {
        var needCheck = (url.length === 0);
        if (!needCheck) {
          var queryString = url.substring(url.indexOf("?") + 1);
          var re = /(\._cpage)/i;
          needCheck = !re.test(queryString);
        }
        if (needCheck) {
          $("input.hasDatepicker").each(function (index, item) {
            if ("" === $(item).val()) {
              var inputName = $(item).attr("name").toLowerCase();
              if (inputName.indexOf("start") >= 0 || inputName.indexOf("from") >= 0) {
                if (typeof query_startFillDay === 'undefined') {
                  $(item).datepicker("setDate", -7);
                } else {
                  $(item).datepicker("setDate", query_startFillDay);
                }
              } else {
                $(item).datepicker("setDate", 0);
              }
            }
          });
        }
      }
    });
  });
});

// 全选/取消
function checkAll(tableId, checkboxName, checked) {
  var checkboxs = $('input[type="checkbox"][name="' + checkboxName + '"]');
  var checktable = $("#" + tableId + " tr");
  for (var i = 0; i < checkboxs.length; i++) {
    if (checktable && checktable.length > i + 1 && checktable[i + 1].style.display == 'none') {
      checkboxs[i].checked = false;
    } else {
      checkboxs[i].checked = checked;
    }
  }
}
function revertRequestParams() {
  //获取url中"?"符后的字串
  var url = decodeURIComponent(location.search);
  // 如果链接没有参数，或者链接中不存在我们要获取的参数，直接返回空
  if (url.length === 0) {
    return;
  }

  var queryString = url.substring(url.indexOf("?") + 1);

  // 分离参数对 ?key=value&key2=value2
  var parameters = queryString.split("&");

  var pos, paraName, paraValue;
  for (var i = 0; i < parameters.length; i++) {
    // 获取等号位置
    pos = parameters[i].indexOf('=');
    if (pos === -1) {
      continue;
    }

    // 获取name 和 value
    paraName = parameters[i].substring(0, pos);
    if (paraName === 'CSRFToken'
        || paraName === 'yeepay_sso_token'
        || paraName === 'belongSystem'
        || paraName === '_menuId'
        || paraName === '_firstMenuId') {
      continue;
    }
    paraValue = parameters[i].substring(pos + 1);
    paraValue = unescape(paraValue.replace(/\+/g, " "));

    // 还原选中项
    if ("" != paraValue) {
      if ($("input:radio[name='" + paraName + "'][value='" + paraValue + "']").size() > 0) {
        $("input:radio[name='" + paraName + "'][value='" + paraValue + "']").attr({
          checked: true
        });
      } else if ($("input[name='" + paraName + "']").size() > 0) {
        $("input[name='" + paraName + "']").attr("value", paraValue);
      } else if ($("select[name='" + paraName + "'] option[value='" + paraValue + "']").size() > 0) {
        $("select[name='" + paraName + "'] option[value='" + paraValue + "']").attr("selected", "selected");
      }
    }
  }
}
// 加载服务
function loadServices() {
  var application = arguments[0];
  var defaultValueForAll = arguments[1] ? arguments[1] : '';
  if (application != "") {
    MessageBoxExt.ajax({
      url: GV.ctxPath + "applications/application/" + application + "/services",
      type: "GET",
      style: "NONE",
      success: function (response) {
        console.log(response);
        if (response.status === 'success') {
          $("#service").empty().append("<option value=" + defaultValueForAll + ">所有服务</option>");
          $.each(response.data.services, function (index, item) {
            $("#service").append("<option value='" + item + "'>" + item + "</option>");
          });
          $("#service").trigger("chosen:updated");
        }
      }
    });
  }
}