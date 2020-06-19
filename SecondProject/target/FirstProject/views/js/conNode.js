function loadSecondGraph() {
    /*var type=temp.toString().split(" ")[1];
    var idd=temp.toString().split(" ")[2];*/
    var type2=$("#node_type").val().toString();
    var id2=$("#node_id").val().toString();
    var tempList={
        "Chapter" : ["all"],
        "Topic" : ["all"],
        "Subject" : ["all"]
    };
    switch (type2) {
        case "0":
            tempList.Chapter=[id2];
            break;
        case "1":
            tempList.Topic=[id2];
            break;
        case "2":
            tempList.Subject=[id2];
            break;
    }

    //跳转到具体页面的图谱
    var result2 = "";
    var datalist2 = [];
    var datas2 = [];
    var link2 = [];
    $.ajax({
        type : "POST",
        dataType : 'json',
        url : encodeURI($("#path").val()
            + '/TcgnicalSupervisionController/query.do'),
        async : false, // 同步，这个请求得到相应以后在进行后面的操作
        data : tempList,
        success : function(data) {
            result2 = data;
            datalist2.push(result2);
            for ( var i = 0; i < datalist2.length; i++) {
                datas2 = datalist2[i].data;
                link2 = datalist2[i].links;
            }
            if (datas2.length == 0) {
                $.messager.alert("提示", "当前查询条件下没有数据！", "info");
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            // alert(XMLHttpRequest.status);
            // alert(XMLHttpRequest.readyState);
            // alert(textStatus);
            $.messager.alert("提示","graph2请求失败啦！");

        }
    });
    glgxcharts2(datas2, link2);
}
function glgxcharts2(datas2, link2) {


    // 基于准备好的dom，初始化ECharts实例
    var myChart2 = echarts.init(document.getElementById('node_show'));
    // 指定图表的配置项和数据
    var option = {
        tooltip : {
            show : true, // 默认显示
            showContent : true, // 是否显示提示框浮层
            trigger : 'item',// 触发类型，默认数据项触发
            /*triggerOn : 'mousemove',// 提示触发条件，mousemove鼠标移至触发，还有click点击触发
            */
            triggerOn : 'mousemove',//鼠标移至触发
            alwaysShowContent : false, // 默认离开提示框区域隐藏，true为一直显示
            showDelay : 0,// 浮层显示的延迟，单位为 ms，默认没有延迟，也不建议设置。在 triggerOn 为
            // 'mousemove' 时有效。
            hideDelay : 200,// 浮层隐藏的延迟，单位为 ms，在 alwaysShowContent 为 true 的时候无效。
            enterable : true,// 鼠标是否可进入提示框浮层中，默认为false，如需详情内交互，如添加链接，按钮，可设置为
            // true。
            position : 'right',// 提示框浮层的位置，默认不设置时位置会跟随鼠标的位置。只在 trigger
            // 为'item'的时候有效。
            confine : false,// 是否将 tooltip 框限制在图表的区域内。外层的 dom 被设置为 'overflow:
                            // hidden'，或者移动端窄屏，导致 tooltip 超出外界被截断时，此配置比较有用。
            transitionDuration : 0.4,// 提示框浮层的移动动画过渡时间，单位是 s，设置为 0
            // 的时候会紧跟着鼠标移动。

            formatter : function(params2, ticket2, callback2) {

                if ('node' == params2.dataType) {
                    var str2 = '';
                    var arr2 = params2.value.split('');
                    var j2 = 1;
                    var temp2='';
                    for ( var i = 0; i < arr2.length; i++) {
                        if (j2 % 29 == 0 || '；' == arr2[i] || '。' == arr2[i]) {
                            str2 += arr2[i] + '<br/>';
                            temp2+=arr2[i];
                            j2 = 1;
                        } else {
                            j2++;
                            str2 += arr2[i];
                            temp2+=arr2[i];
                        }
                    }
                    var temp_id2=params2.data.id;
                    return myChart2.getOption().series[params2.seriesIndex].categories[params2.data.category].name
                        + ':' + str2+'<button style="background: #00bbee;border-radius: 5" onclick="jump(\''+ str2 + ' '+ params2.data.category + ' '+ temp_id2 + '\')">click</button>';
                }
            }
        },
        color : [ "#FF756E", "#68BDF6", "#FFD86E" ],
        legend : {
            top : '5%',
            x : 'left',
            orient : 'vertical',
            show : true,
            data : [ {
                name : '章节',
                icon : 'circle'
            },{
                name : '知识点',
                icon : 'circle'
            },  {
                name : '科目',
                icon : 'circle'
            }]
        },
        series : [ {
            type : 'graph', // 关系图
            name : "关联关系图", // 系列名称，用于tooltip的显示，legend 的图例筛选，在 setOption
                            // 更新数据和配置项时用于指定对应的系列。
            layout : 'force', // 图的布局，类型为力导图，'circular' 采用环形布局，见示例
            // LesMiserables
            clickable : true,//允许点击事件
            legendHoverLink : false,// 是否启用图例 hover(悬停) 时的联动高亮。
            hoverAnimation : true,// 是否开启鼠标悬停节点的显示动画
            coordinateSystem : null,// 坐标系可选
            xAxisIndex : 0, // x轴坐标 有多种坐标系轴坐标选项
            yAxisIndex : 0, // y轴坐标
            force : { // 力引导图基本配置
                initLayout : 'circular',// 力引导的初始化布局，默认使用xy轴的标点
                repulsion : 1500,// 节点之间的斥力因子。支持数组表达斥力范围，值越大斥力越大。
                gravity : 0.2,// 节点受到的向中心的引力因子。该值越大节点越往中心点靠拢。
                edgeLength : 150,// 边的两个节点之间的距离，这个距离也会受 repulsion。[10,
                // 50]。值越小则长度越长
                layoutAnimation : true
                // 因为力引导布局会在多次迭代后才会稳定，这个参数决定是否显示布局的迭代动画，在浏览器端节点数据较多（>100）的时候不建议关闭，布局过程会造成浏览器假死。
            },
            roam : true,// 是否开启鼠标缩放和平移漫游。默认不开启。如果只想要开启缩放或者平移，可以设置成 'scale' 或者
                        // 'move'。设置成 true 为都开启
            nodeScaleRatio : 0.6,// 鼠标漫游缩放时节点的相应缩放比例，当设为0时节点不随着鼠标的缩放而缩放
            draggable : true,// 节点是否可拖拽，只在使用力引导布局的时候有用。
            focusNodeAdjacency : true,// 是否在鼠标移到节点上的时候突出显示节点以及节点的边和邻接节点。
            // symbol:'roundRect',//关系图节点标记的图形。ECharts 提供的标记类型包括 'circle'(圆形),
            // 'rect'（矩形）, 'roundRect'（圆角矩形）, 'triangle'（三角形）, 'diamond'（菱形）,
            // 'pin'（大头针）, 'arrow'（箭头） 也可以通过 'image://url' 设置为图片，其中 url
            // 为图片的链接。'path:// 这种方式可以任意改变颜色并且抗锯齿
            symbolSize : 40,// 也可以用数组分开表示宽和高，例如 [20, 10]

            edgeSymbol : [ 'circle', 'arrow' ],
            edgeSymbolSize : [ 1, 4 ],

            itemStyle : {// ===============图形样式，有 normal 和 emphasis
                // 两个状态。normal 是图形在默认状态下的样式；emphasis
                // 是图形在高亮状态下的样式，比如在鼠标悬浮或者图例联动高亮时。
                normal : { // 默认样式
                    label : {
                        show : true
                    },
                    borderType : 'solid', // 图形描边类型，默认为实线，支持 'solid'（实线）,
                    // 'dashed'(虚线), 'dotted'（点线）。
                    borderColor : 'rgba(0,0,0,0.4)', // 设置图形边框为淡金色,透明度为0.4
                    borderWidth : 0.5, // 图形的描边线宽。为 0 时无描边。
                    opacity : 1
                    // 图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。默认0.5
                },
                emphasis : {// 高亮状态
                }
            },
            lineStyle : { // ==========关系边的公用线条样式。
                normal : {
                    /* color : 'rgba(255,0,255,0.4)', */
                    width : '0.5',
                    type : 'solid', // 线的类型 'solid'（实线）'dashed'（虚线）'dotted'（点线）
                    curveness : 0, // 线条的曲线程度，从0到1
                    color : 'source',
                    opacity : 1
                    // 图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。默认0.5
                },
                emphasis : {// 高亮状态
                }
            },
            label : { // =============图形上的文本标签
                normal : {
                    show : true,// 是否显示标签。
                    // position: 'right',//相对于节点标签的位置
                    // formatter: '{b}',//标签文字
                    textStyle : { // 标签的字体样式
                        color : '#000', // 字体颜色
                        fontSize : 16
                        // 字体大小
                    }
                },
                emphasis : {// 高亮状态
                }
            },

            categories : [ // symbol name：用于和 legend 对应以及格式化 tooltip 的内容。
                // label有效
                {
                    name : '章节',
                    symbol : 'circle'
                }, {
                    name : '知识点',
                    symbol : 'circle'
                }, {
                    name : '科目',
                    symbol : 'circle'
                }],
            // 别名为nodes
            // name:影响图形标签显示,value:影响选中后值得显示,category:所在类目的index,symbol:类目节点标记图形,symbolSize:10图形大小
            // label:标签样式。
            data : datas2,
            links : link2
        } ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart2.setOption(option,true);/*不会合并之前的数据*/

}