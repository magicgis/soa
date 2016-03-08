$(document).ready(function () {
  $("a[name=configOperate]").click(function () {
    var type = $(this).attr("type");
    var configKey = $(this).attr("configKey");
    var application = $(this).attr("application");
    if (!type || !configKey || !application) {
      return;
    }
    var opName = $(this).text() + "配置项";
    MessageBoxExt.confirm("确认" + opName + "？", function () {
      MessageBoxExt.ajax({
        url : GV.ctxPath + 'configs/' + type,
        type : 'POST',
        dataType : "json",
        style : "REDIRECT",
        data : {
          "configKey" : configKey,
          "application" : application
        },
        cache : false
      });
    });
  });

  var valRules = {
    "configName" : {
      req : true,
      len : {
        max : 150
      },
      label : "配置名"
    },
    "configKey" : {
      req : true,
      len : {
        max : 150
      },
      label : "配置键"
    },
    "configValue" : {
      req : true,
      len : {
        max : 2000
      },
      label : "配置值"
    },
    "application" : {
      req : true,
      len : {
        max : 200
      },
      label : "所属应用"
    },
    "valueType" : {
      req : true,
      len : {
        max : 200
      },
      label : "数据类型"
    },
    "dataType" : {
      req : false,
      label : "数据类型"
    }
  };
  ValidateExt.listen("editConfigForm", valRules, ValidateExt.inlineError);
  $("#addConfig").click(function () {
    // 清空
    $("#editConfigForm").find(inputSelector).each(function () {
      var type = $(this).attr("type");
      if (type == "checkbox" || type == "radio") {
        $(this).attr("checked", "");
      } else {
        $(this).val("");
      }
    });
    popupForEditConfig(true);
  });
  $("a[name=editConfig]").click(function () {
    var configKey = $(this).attr("configKey");
    var application = $(this).attr("application");
    if (!configKey || !application) {
      return;
    }
    var context = $(this);
    $("#editConfigForm input[name=configKey]").val(configKey);
    MessageBoxExt.ajax({
      url : GV.ctxPath + 'configs/' + application + '/' + configKey,
      type : 'GET',
      dataType : "json",
      style : "none",
      cache : false,
      success : function (result) {
        var config = (result.data || {}).config;
        if (!config) {
          MessageBoxExt.error("配置不存在，可能已被删除！");
          return;
        }
        $("#editConfigForm input[name=configName]").val(config.name);
        $("#editConfigForm select[name=application]").val(config.domain);
        $("#editConfigForm select[name=valueType]").val(config.valueType);
        $("#editConfigForm select[name=dataType]").val(config.dataType);
        $("#editConfigForm select[name=enabled]").val(config.enabled + "");
        if (config.valueType == "VALUE") {
          $("#editConfigForm input[name=configValue]").val(config.valueStr);
        } else if (config.valueType == "LIST") {
          parseList(config);
        } else if (config.valueType == "MAP") {
          parseMap(config);
        } else if (config.valueType == "STRUCTURE") {
          parseStructure(config);
        }
        popupForEditConfig(false, context);
        $("#_valueType").change();
      }
    });
  });

  $("#_valueType").change(function () {
    var valueType = $(this).val();
    $("li[name=configValueBlock]").hide();
    var block = $("#" + valueType + "_BLOCK");
    if (block.length > 0) {
      $("#" + valueType + "_BLOCK").show();
    } else {
      $("#VALUE_BLOCK").show();
    }
    if (valueType == 'STRUCTURE') {
      $("#_dataTypeBlock").hide();
    } else {
      $("#_dataTypeBlock").show();
    }
  });
});

function popupForEditConfig (isAdd, context) {
  if (isAdd) {
    $("#editConfigForm input[name=configKey]").removeAttr("readonly");
    $("#editConfigForm input[name=isAdd]").val("true");
  } else {
    $("#editConfigForm input[name=configKey]").attr("readonly", "readonly");
    $("#editConfigForm input[name=isAdd]").val("false");
  }
  MessageBoxExt.popup("_editConfigBlock", {
    title : isAdd ? "新增配置信息" : "修改配置信息",
    width : 650,
    height : "auto",
    buttons : {
      "确定" : function () {
        var valueType = $("#editConfigForm select[name=valueType]").val() || "";
        var dataType = $("#editConfigForm select[name=dataType]").val() || "";
        if (valueType != "STRUCTURE" && !dataType) {
          MessageBoxExt.error("数据类型必选");
          return;
        }
        var configValue = $("#_configValue").val();
        if (valueType == "VALUE") {
          if (!ValidateExt.checkValue(configValue, dataTypeRules[$("#_dataType").val()])) {
            MessageBoxExt.error("配置值与数据类型不匹配！");
            return;
          }
        } else if (valueType == "LIST") {
          configValue = buildListValue();
        } else if (valueType == "MAP") {
          configValue = buildMapValue();
        } else if (valueType == "STRUCTURE") {
          configValue = buildStructureValue();
        }
        if (valueType != "VALUE" && !configValue) {
          return;
        } else {
          $("#_configValue").val(configValue);
        }
        if (ValidateExt.val("editConfigForm")) {
          MessageBoxExt.ajax({
            url : GV.ctxPath + 'configs/save',
            type : 'POST',
            dataType : "json",
            style : "REDIRECT",
            data : $("#editConfigForm").serialize(),
            cache : false
          });
        }
      },
      "关闭" : function () {
        $(this).dialog("close");
      }
    }
  });
}

function addRow (context, type) {
  if (!context || !type) {
    return;
  }
  $(context).parents("table").eq(0).append("<tr>" + $("#_TPL_" + type).html() + "</tr>");
}

function deleteRow (context) {
  if (!context) {
    return;
  }
  $(context).parents("tr").eq(0).remove();
}

var dataTypeRules = {
  "_KEY_" : "^.+$",
  "STRING" : "^.*$",
  "INTEGER" : "int",
  "BOOLEAN" : "^(true|false)$",
  "DOUBLE" : "decimal",
  "DATE" : "date",
  "DATETIME" : "datetime"
}
function buildListValue () {
  var values = $("#editConfigForm input[name=_listValue]");
  var valueStr = "";
  for (var i = 0; i < values.length; i++) {
    var value = $(values[i]).val() + "";
    if (!value) {
      MessageBoxExt.error("请输入配置值！");
      return null;
    }
    if (!ValidateExt.checkValue(value, dataTypeRules[$("#_dataType").val()])) {
      MessageBoxExt.error("配置值与数据类型不匹配！");
      return null;
    }
    if (i > 0) {
      valueStr += "_&_";
    }
    valueStr += value;
  }
  return encodeURIComponent(valueStr);
}
function buildMapValue () {
  var keys = $("#editConfigForm input[name=_mapKey]");
  var values = $("#editConfigForm input[name=_mapValue]");
  var valueStr = "";
  for (var i = 0; i < values.length; i++) {
    var key = $(keys[i]).val() + "";
    var value = $(values[i]).val() + "";
    if (!key) {
      MessageBoxExt.error("请输入Map配置键！行数:" + (i + 1));
      return null;
    }
    if (!ValidateExt.checkValue(key, dataTypeRules["_KEY_"])) {
      MessageBoxExt.error("Map配置键包含非法字符！行数:" + (i + 1) + " key:" + key);
      return null;
    }
    if (!value) {
      MessageBoxExt.error("请输入Map配置值！行数:" + (i + 1));
      return null;
    }
    if (!ValidateExt.checkValue(value, dataTypeRules[$("#_dataType").val()])) {
      MessageBoxExt.error("Map配置值与数据类型不匹配！行数:" + (i + 1) + " value:" + value);
      return null;
    }
    if (i > 0) {
      valueStr += "&";
    }
    valueStr += key + "=" + value;
  }
  return encodeURIComponent(valueStr);
}

function buildStructureValue () {
  var keys = $("#editConfigForm input[name=_structureKey]");
  var values = $("#editConfigForm input[name=_structureValue]");
  var types = $("#editConfigForm select[name=_structureDataType]");
  var valueStr = "";
  for (var i = 0; i < values.length; i++) {
    var key = $(keys[i]).val() + "";
    var value = $(values[i]).val() + "";
    var type = $(types[i]).val() + "";
    if (!key) {
      MessageBoxExt.error("请输入Map配置键！行数:" + (i + 1));
      return null;
    }
    if (!ValidateExt.checkValue(key, dataTypeRules["_KEY_"])) {
      MessageBoxExt.error("Map配置键包含非法字符！行数:" + (i + 1) + " key:" + key);
      return null;
    }
    if (!value) {
      MessageBoxExt.error("请输入Map配置值！行数:" + (i + 1));
      return null;
    }
    if (!ValidateExt.checkValue(value, dataTypeRules[type])) {
      MessageBoxExt.error("Map配置值与数据类型不匹配！行数:" + (i + 1) + " value:" + value);
      return null;
    }
    if (i > 0) {
      valueStr += "&";
    }
    valueStr += key + "=" + value + "=" + type;
  }
  return encodeURIComponent(valueStr);
}

function parseList (config) {
  $("#LIST_BLOCK table tr:gt(1)").remove();
  $("#LIST_BLOCK input[name=_listValue]").val(config.value[0]);
  for (var i = 1; i < config.value.length; i++) {
    var $tr = $("<tr>" + $("#_TPL_LIST").html() + "</tr>");
    $tr.find("input[name=_listValue]").val(config.value[i]);
    $("#LIST_BLOCK table").append($tr);
  }
}
function parseMap (config) {
  $("#MAP_BLOCK table tr:gt(1)").remove();
  var i = 0;
  for ( var key in config.value) {
    if (i == 0) {
      $("#MAP_BLOCK input[name=_mapKey]").val(key);
      $("#MAP_BLOCK input[name=_mapValue]").val(config.value[key]);
    } else {
      var $tr = $("<tr>" + $("#_TPL_MAP").html() + "</tr>");
      $tr.find("input[name=_mapKey]").val(key);
      $tr.find("input[name=_mapValue]").val(config.value[key]);
      $("#MAP_BLOCK table").append($tr);
    }
    i++;
  }
}
function parseStructure (config) {
  $("#STRUCTURE_BLOCK table tr:gt(1)").remove();
  var i = 0;
  var detectDataType = function (value) {
    var type = typeof(value);
    var strValue = "" + value;
    if (type=="number") {
      if (strValue.indexOf(".")>0) {
        return "DOUBLE";
      } else {
        return "INTEGER";
      }
    }
    if (type=="boolean") {
      return "BOOLEAN";
    }
    if (ValidateExt.checkValue(strValue, "date")) {
      if (strValue.indexOf(":")>0) {
        return "DATETIME";
      } else {
        return "DATE";
      }
    }
    return "STRING";
  }
  for ( var key in config.value) {
    if (i == 0) {
      $("#STRUCTURE_BLOCK input[name=_structureKey]").val(key);
      $("#STRUCTURE_BLOCK input[name=_structureValue]").val(config.value[key]);
      $("#STRUCTURE_BLOCK select[name=_structureDataType]").val(detectDataType(config.value[key]));
    } else {
      var $tr = $("<tr>" + $("#_TPL_STRUCTURE").html() + "</tr>");
      $tr.find("input[name=_structureKey]").val(key);
      $tr.find("input[name=_structureValue]").val(config.value[key]);
      $tr.find("select[name=_structureDataType]").val(detectDataType(config.value[key]));
      $("#STRUCTURE_BLOCK table").append($tr);
    }
    i++;
  }
}