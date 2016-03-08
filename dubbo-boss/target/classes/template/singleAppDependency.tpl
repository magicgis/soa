{
  title : {
    text: '应用依赖关系',
    x:'right',
    y:'bottom'
  },
  tooltip : {
    trigger: 'item',
    formatter: '{a} : {b}'
  },
  toolbox: {
    show : true,
    feature : {
      restore : {show: true},
      saveAsImage : {show: true}
    }
  },
  legend: {
    x: 'left',
    data:['被依赖','依赖','应用']
  },
  series : [
    {
      type:'force',
      name : '依赖关系',
      categories : [
        {
          name: '被依赖'
        },
        {
          name: '依赖'
        },
        {
          name: '应用'
        }
      ],
      itemStyle: {
        normal: {
          label: {
            show: true,
            textStyle: {
              color: '#333'
            }
          },
          nodeStyle : {
            brushType : 'both',
            strokeColor : 'rgba(255,215,0,0.4)',
            lineWidth : 1
          }
        },
        emphasis: {
          label: {
            show: false
          },
          nodeStyle : {
          },
          linkStyle : {}
        }
      },
      useWorker: false,
      minRadius : 15,
      maxRadius : 25,
      gravity: 1.1,
      scaling: 1.1,
      linkSymbol: 'arrow',
      nodes:[
        #_nodes_#
      ],
      links : [
        #_links_#
      ]
    }
  ]
}