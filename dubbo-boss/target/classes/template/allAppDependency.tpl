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
      magicType: {
          show: true,
          type: ['force', 'chord'],
          option: {
              chord: {
                  minRadius : 2,
                  maxRadius : 10,
                  ribbonType: false,
                  itemStyle: {
                      normal: {
                          label: {
                              show: true,
                              rotate: true
                          },
                          chordStyle: {
                              opacity: 0.2
                          }
                      }
                  }
              },
              force: {
                  minRadius : 5,
                  maxRadius : 8,
                  itemStyle : {
                      normal : {
                          label: {
                              show: false
                          },
                          linkStyle : {
                              opacity : 0.5
                          }
                      }
                  }
              }
          }
      },
      saveAsImage : {show: true}
    }
  },
  legend: {
    x: 'left',
    data:['消费者','提供者','Both']
  },
  series : [
    {
      type:'force',
      name : '依赖关系',
      categories : [
        {
          name: '消费者'
        },
        {
          name: '提供者'
        },
        {
          name: 'Both'
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
            show: true
          },
          nodeStyle : {
            //draggable: false
          },
          linkStyle : {}
        }
      },
      nodes:[
        #_nodes_#
      ],
      links : [
        #_links_#
      ]
    }
  ]
}