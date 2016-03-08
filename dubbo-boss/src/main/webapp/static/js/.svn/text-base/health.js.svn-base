$(document).ready(function () {
  sendCheckRequest();
  setInterval(sendCheckRequest, 120000);
  $("#serviceQueryForm select[name=side]").change(function () {
    $("#serviceQueryForm").submit();
  });
  $("#serviceQueryForm :input, #serviceQueryForm select[name=status]").change(doSearch);
  $("#health td a[name=detail]").click(function () {
    var txt = $(this).text().trim();
    if (txt == '详情') {
      $(this).text("收起")
      $(this).siblings("div").eq(0).hide();
      $(this).siblings("div").eq(1).show();
    } else {
      $(this).text("详情")
      $(this).siblings("div").eq(0).show();
      $(this).siblings("div").eq(1).hide();
    }
  });
});
function sendCheckRequest () {
  $("#health tr").each(function (index, element) {
    var tr = $(this);
    // 间隔2秒提交一批，避免ajax超时
    var delay = Math.floor(index/10) * 2000;
    setTimeout(doCheck(tr), delay);
  });
}
function doCheck (tr) {
  var address = tr.attr("address");
  var appName = tr.attr("appName");
  var status = tr.attr("status") || "";
  if (!address || !appName) {
    return;
  }
  $.ajax({
    url : GV.ctxPath + 'monitor/health/' + address + '/' + appName,
    type : 'GET',
    dataType : "json",
    timeout : 60000,
    cache : false,
    success : function (result) {
      var tds = tr.find("td");
      if (result.data && result.data.summary) {
        if (status == result.data.summary.level) {
          return;
        }
        tr.attr("status", result.data.summary.level);
        $(tds[2]).children("img").hide();
        $(tds[2]).children("img[name=" + result.data.summary.level + "]").show();
        $(tds[3]).children("div:first").html(result.data.summary.message);
        $(tds[3]).find("textarea").val(formatJson(result.data).trim());
      } else {
        if (status == "UNKNOW") {
          return;
        }
        tr.attr("status", "UNKNOW");
        $(tds[2]).children("img").hide();
        $(tds[2]).children("img[name=UNKNOW]").show();
        $(tds[3]).children("div").hide();
      }
    },
    error : function (result) {
      if (status == "UNKNOW") {
        return;
      }
      var tds = tr.find("td");
      tr.attr("status", "UNKNOW");
      $(tds[2]).children("img").hide();
      $(tds[2]).children("img[name=UNKNOW]").show();
      $(tds[3]).children("div").hide();
    }
  });
}

function doSearch () {
  var address = $("#serviceQueryForm input:eq(0)").val();
  var appName = $("#serviceQueryForm input:eq(1)").val();
  var status = $("#serviceQueryForm select:eq(0)").val();
  $("#health tr:gt(0)").show();
  if (address) {
    // $("#health tbody tr:not(:contains('" + address + "'))").hide();
    $("#health tbody tr:visible").each(function () {
      var addr = $(this).children(":first").text() || "";
      if (addr.indexOf(address) < 0) {
        $(this).hide();
      }
    });
  }
  if (appName) {
    // $("#health tbody tr:not(:contains('" + appName + "'))").hide();
    $("#health tbody tr:visible").each(function () {
      var addr = $(this).children(":eq(1)").text() || "";
      if (addr.indexOf(appName) < 0) {
        $(this).hide();
      }
    });
  }
  if (status) {
    $("#health tbody tr[status!=" + status + "]").hide();
  }
}

JSON.stringify = JSON.stringify || function (obj) {
  var t = typeof (obj);
  if (t != "object" || obj === null) {
    // simple data type
    if (t == "string")
      obj = '"' + obj + '"';
    return String(obj);
  } else {
    // recurse array or object
    var n, v, json = [], arr = (obj && obj.constructor == Array);
    for (n in obj) {
      v = obj[n];
      t = typeof (v);
      if (t == "string")
        v = '"' + v + '"';
      else if (t == "object" && v !== null)
        v = JSON.stringify(v);
      json.push((arr ? "" : '"' + n + '":') + String(v));
    }
    return (arr ? "[" : "{") + String(json) + (arr ? "]" : "}");
  }
};

// Notes:
// - json2.js is not needed if browser supports JSON.stringify and JSON.parse
// natively
// - jQuery is only used to place the results
var formatJson = function (json, options) {
  var reg = null, formatted = '', pad = 0, PADDING = '    '; // one can also
  // use '\t' or a
  // different
  // number of
  // spaces

  // optional settings
  options = options || {};
  // remove newline where '{' or '[' follows ':'
  options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true)
      ? true : false;
  // use a space after a colon
  options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;

  // begin formatting...

  // make sure we start with the JSON as a string
  if (typeof json !== 'string') {
    json = JSON.stringify(json);
  }
  // parse and stringify in order to remove extra whitespace
  json = JSON.parse(json);
  json = JSON.stringify(json);

  // add newline before and after curly braces
  reg = /([\{\}])/g;
  json = json.replace(reg, '\r\n$1\r\n');

  // add newline before and after square brackets
  reg = /([\[\]])/g;
  json = json.replace(reg, '\r\n$1\r\n');

  // add newline after comma
  reg = /(\,)/g;
  json = json.replace(reg, '$1\r\n');

  // remove multiple newlines
  reg = /(\r\n\r\n)/g;
  json = json.replace(reg, '\r\n');

  // remove newlines before commas
  reg = /\r\n\,/g;
  json = json.replace(reg, ',');

  // optional formatting...
  if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
    reg = /\:\r\n\{/g;
    json = json.replace(reg, ':{');
    reg = /\:\r\n\[/g;
    json = json.replace(reg, ':[');
  }
  if (options.spaceAfterColon) {
    reg = /\:/g;
    json = json.replace(reg, ': ');
  }

  $.each(json.split('\r\n'), function (index, node) {
    var i = 0, indent = 0, padding = '';

    if (node.match(/\{$/) || node.match(/\[$/)) {
      indent = 1;
    } else if (node.match(/\}/) || node.match(/\]/)) {
      if (pad !== 0) {
        pad -= 1;
      }
    } else {
      indent = 0;
    }

    for (i = 0; i < pad; i++) {
      padding += PADDING;
    }

    formatted += padding + node + '\r\n';
    pad += indent;
  });

  return formatted;
};
