$(function() {
	var url = encodeURI($("#path").val()
			+ '/TcgnicalSupervisionController/select.do');
	var datas;
	$.ajax({
		async : false,
		type : 'POST',
		url : url,
		dataType : 'json',
		success : function(data) {
			datas = data;
			for ( var i = 0; i < data.Chapter.length; i++) {
				$("#chapter").append(
						"<option value='" + data.Chapter[i].value + "'>"
								+ data.Chapter[i].name + "</option>");
			}
			for ( var i = 0; i < data.Topic.length; i++) {
				$("#topic").append(
						"<option value='" + data.Topic[i].value + "'>"
								+ data.Topic[i].name + "</option>");
			}
			for ( var i = 0; i < data.Subject.length; i++) {
				$("#subject").append(
						"<option value='" + data.Subject[i].value + "'>"
								+ data.Subject[i].name + "</option>");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// alert(XMLHttpRequest.status);
			// alert(XMLHttpRequest.readyState);
			// alert(textStatus);
		}
	});
	$('#chapter').chosen({});
	$('#topic').chosen({});
	$('#subject').chosen({});
});

function association() {
	if ($("#chapter").val().length == 0 && $("#topic").val().length == 0
			&& $("#subject").val().length == 0) {
		$.messager.alert("系统提示", "请至少选择一项！");
		return;
	}

	var result = "";
	var datalist = [];
	var datas = [];
	var link = [];
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : encodeURI($("#path").val()
				+ '/TcgnicalSupervisionController/query.do'),
		async : false, // 同步，这个请求得到相应以后在进行后面的操作
		data : {
			"Chapter" : $("#chapter").val(),
			"Topic" : $("#topic").val(),
			"Subject" : $("#subject").val(),
		},
		success : function(data) {
			result = data;
			datalist.push(result);
			for ( var i = 0; i < datalist.length; i++) {
				datas = datalist[i].data;
				link = datalist[i].links;
			}
			if (datas.length == 0) {
				$.messager.alert("提示", "当前查询条件下没有数据！", "info");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// alert(XMLHttpRequest.status);
			// alert(XMLHttpRequest.readyState);
			// alert(textStatus);
		}
	});
	glgxcharts(datas, link);
}

function glgxcharts(datas, link) {
	// alert(link);

	// 基于准备好的dom，初始化ECharts实例
	var myChart = echarts.init(document.getElementById('main'));
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
			formatter : function(params, ticket, callback) {
				if ('node' == params.dataType) {
					var str = '';
					var arr = params.value.split('');
					var j = 1;
					var temp='';
					for ( var i = 0; i < arr.length; i++) {
						if (j % 29 == 0 || '；' == arr[i] || '。' == arr[i]) {
							str += arr[i] + '<br/>';
							temp+=arr[i];
							j = 1;
						} else {
							j++;
							str += arr[i];
							temp+=arr[i];
						}
					}
					var temp_id=params.data.id;
					var nodeType = myChart.getOption().series[params.seriesIndex].categories[params.data.category].name;
					var houzhui='';
					if(nodeType.toString()==="知识点")/*!//节点是不是知识点*/
						houzhui='<button style="background: #00bbee" onclick="jump(\''+ str + ' '+ params.data.category + ' '+ temp_id + '\')">查看详情</button>';
					else
						houzhui='';
					return nodeType+':'+str+houzhui;
				}
			}
		},
		// toolbox: {
		// show : true,
		// feature : {//启用功能
		// //dataView数据视图，打开数据视图，可设置更多属性,readOnly
		// 默认数据视图为只读(即值为true)，可指定readOnly为false打开编辑功能
		// dataView: {show: true, readOnly: false},
		// restore : {show: true},//restore，还原，复位原始图表
		// saveAsImage : {show: true}//saveAsImage，保存图片
		// }
		// },
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
			}/*, {
				name : 'comment',
				icon : 'circle'
			} */]
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
			// 如果需要每个数据的图形大小不一样，可以设置为如下格式的回调函数：(value:
			// Array|number, params: Object) => number|Array
			// symbolRotate:,//关系图节点标记的旋转角度。注意在 markLine 中当 symbol 为 'arrow'
			// 时会忽略 symbolRotate 强制设置为切线的角度。
			// symbolOffset:[0,0],//关系图节点标记相对于原本位置的偏移。[0, '50%']
			// edgeSymbol: ['none',
			// 'arrow'],//边两端的标记类型，可以是一个数组分别指定两端，也可以是单个统一指定。默认不显示标记，常见的可以设置为箭头，如下：edgeSymbol:
			// ['circle', 'arrow']

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
			}/*, {
				name : 'comment',
				symbol : 'circle'
			} */],
			// 别名为nodes
			// name:影响图形标签显示,value:影响选中后值得显示,category:所在类目的index,symbol:类目节点标记图形,symbolSize:10图形大小
			// label:标签样式。
			data : datas,
			links : link
		} ]
	};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option,true);

}
function jump(temp,temp_data,temp_idd){
	var name=temp.toString().split(" ")[0];
	var type=temp.toString().split(" ")[1];
	var id=temp.toString().split(" ")[2];
	var jumpUrl = window.location.protocol + "//"  + window.location.host + "/test.jsp";

	$.ajax({
		type : "POST",
		dataType : 'json',
		url : encodeURI($("#path").val()
			+ '/KnowledgeController/knoInfo.do'),
		async : false, // 同步，这个请求得到相应以后在进行后面的操作
		data : {
			"node_name":name,
			"node_type":type,
			"node_id":id
		},
		success : function(data) {
			window.location.href=jumpUrl;
			/*window.open(jumpUrl);*/
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// alert(XMLHttpRequest.status);
			// alert(XMLHttpRequest.readyState);
			// alert(textStatus);
		}
	});

}
/*TcgnicalSupervisionController/jump.do*/
/*KnowledgeController/knoInfo.do*/

