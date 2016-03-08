<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  User: dreambt
  Date: 14-9-11
  Time: 15:48
--%>
<html>
<head>
  <%@ include file="/WEB-INF/views/common/metas.jsp" %>
  <title>服务监控</title>
</head>
<body>
<div id="qos_listNotifierInfo2"></div>
<div class="Container">
  <div class="Content fontText">
    <!--search star-->
    <div class="clearer"></div>
    <div class="submenu">
      <div align="right" style="padding-right: 30px; height: 0px;">
        <a href="${ctxPath}monitor/services/statistics?service=${service}">【统计数据】</a>
        <a href="${ctxPath}monitor/services/data?service=${service}">【原始数据】</a>
        <a href="${ctxPath}monitor/services/statistics?service=${service}&expand=provider">【+提供者】</a>
        <a href="${ctxPath}monitor/services/statistics?service=${service}&expand=consumer">【+消费者】</a>
        <a href="${ctxPath}monitor/services/charts?service=${service}">【散点图】</a>
      </div>
    </div>
    <div class="clearer"></div>
    <div class="clearer"></div>
    <div class="result">
      <h2 class="fw">服务监控散点图(${service})</h2>
      <table class="list" style="border:1px">
        <c:forEach items="${chartData}" var="methodData">
          <tr>
            <td>
              <div id="qos_${methodData.key}" style="width:100%;height:360px;"></div>
              <div id="art_${methodData.key}" style="width:100%;height:360px;"></div>
            </td>
          </tr>
        </c:forEach>
      </table>
    </div>
  </div>
</div>

<script type="text/javascript" src="${resourcePath}/common/component/echarts-2.0.2/echarts-plain.js"></script>
<script type="text/javascript">
<c:forEach items="${chartData}" var="chart">
var ${chart.key}Chart = echarts.init(document.getElementById('qos_${chart.key}'));
option_${chart.key} = {
  title: {
    text: '方法${chart.key}的吞吐量',
    subtext: 'Requests per second (QPS)'
  },
  tooltip: {
    trigger: 'item',
    showDelay: 0,
    axisPointer: {
      type: 'cross',
      lineStyle: {
        type: 'dashed',
        width: 1
      }
    },
    formatter: function (value) {
      if (value[2].length > 1) {
        var intPart = Math.floor(value[2][0]);
        return value[0] + ' :<br/>'
                + value[2][1] + ' t/s';
      }
      else {
        var intPart = Math.floor(value[1]);
        return value[0] + ' :<br/>'
                + intPart + ':' + Math.floor((value[1] - intPart) * 60) + '<br/>'
                + value[2] + ' t/s';
      }
    }
  },
  legend: {
    data: ['生产者', '消费者']
  },
  xAxis: [
    {
      type: 'category',
      boundaryGap: false,
      data: [
        <c:forEach items="${viewLogicHelper.mergeDateAndSort(chart.value.data['provider'].data,chart.value.data['consumer'].data)}" var="item" >
        '<fmt:formatNumber value="${item.hours+item.minutes*1.0/60}" maxFractionDigits="2"/>',
        </c:forEach>
      ],
      axisLabel: {
        formatter: '{value}'
      }
    }
  ],
  yAxis: [
    {
      type: 'value',
      scale: true,
      axisLabel: {
        formatter: '{value} t/s'
      }
    }
  ],
  series: [
    {
      name: '生产者',
      type: 'scatter',
      data: [
        <c:forEach items="${chart.value.data['provider'].data}" var="item" >
        [<fmt:formatNumber value="${item.label.hours+item.label.minutes*1.0/60}" maxFractionDigits="2"/> , ${item.qps}],
        </c:forEach>
      ],
      markPoint: {
        data: [
          {type: 'max', name: '最大值'},
          {type: 'min', name: '最小值'}
        ]
      },
      markLine: {
        data: [
          {type: 'average', name: '平均值'}
        ]
      }
    },
    {
      name: '消费者',
      type: 'scatter',
      data: [
        <c:forEach items="${chart.value.data['consumer'].data}" var="item">
        [<fmt:formatNumber value="${item.label.hours+item.label.minutes*1.0/60}" maxFractionDigits="2"/> , ${item.qps}],
        </c:forEach>
      ],
      markPoint: {
        data: [
          {type: 'max', name: '最大值'},
          {type: 'min', name: '最小值'}
        ]
      },
      markLine: {
        data: [
          {type: 'average', name: '平均值'}
        ]
      }
    }
  ]
};
${chart.key}Chart.setOption(option_${chart.key});

var ${chart.key}Chart2 = echarts.init(document.getElementById('art_${chart.key}'));
option_${chart.key}2 = {
  title: {
    text: '方法${chart.key}的平均响应时间',
    subtext: 'Average response time (ms)'
  },
  tooltip: {
    trigger: 'axis',
    showDelay: 0,
    axisPointer: {
      type: 'cross',
      lineStyle: {
        type: 'dashed',
        width: 1
      }
    },
    formatter: function (value) {
      if (value[2].length > 1) {
        var intPart = Math.floor(value[2][0]);
        return value[0] + ' :<br/>'
                + value[2][1] + ' ms';
      }
      else {
        var intPart = Math.floor(value[1]);
        return value[0] + ' :<br/>'
                + intPart + ':' + Math.floor((value[1] - intPart) * 60) + '<br/>'
                + value[2] + ' ms';
      }
    }
  },
  legend: {
    data: ['生产者', '消费者']
  },
  xAxis: [
    {
      type: 'category',
      data: [
        <c:forEach items="${viewLogicHelper.mergeDateAndSort(chart.value.data['provider'].data,chart.value.data['consumer'].data)}" var="item" >
        '<fmt:formatNumber value="${item.hours+item.minutes*1.0/60}" maxFractionDigits="2"/>',
        </c:forEach>
      ],
      axisLabel: {
        formatter: '{value}'
      }
    }
  ],
  yAxis: [
    {
      type: 'value',
      precision: 2,
      scale: true,
      axisLabel: {
        formatter: '{value} ms'
      }
    }
  ],
  series: [
    {
      name: '生产者',
      type: 'scatter',
      data: [
        <c:forEach items="${chart.value.data['provider'].data}" var="item">
        [<fmt:formatNumber value="${item.label.hours+item.label.minutes/60}" maxFractionDigits="2"/> , ${item.art}],
        </c:forEach>
      ],
      markPoint: {
        data: [
          {type: 'max', name: '最大值'},
          {type: 'min', name: '最小值'}
        ]
      },
      markLine: {
        data: [
          {type: 'average', name: '平均值'}
        ]
      }
    },
    {
      name: '消费者',
      type: 'scatter',
      data: [
        <c:forEach items="${chart.value.data['consumer'].data}" var="item">
        [<fmt:formatNumber value="${item.label.hours+item.label.minutes/60}" maxFractionDigits="2"/>, ${item.art}],
        </c:forEach>
      ],
      markPoint: {
        data: [
          {type: 'max', name: '最大值'},
          {type: 'min', name: '最小值'}
        ]
      },
      markLine: {
        data: [
          {type: 'average', name: '平均值'}
        ]
      }
    }
  ]
};
${chart.key}Chart2.setOption(option_${chart.key}2);
</c:forEach>
</script>
</body>
</html>
