<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>关系操作</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.5.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.5.2/themes/icon.css">
<script charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/jquery.min.js"></script>
<script charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/locale/easyui-lang-en.js"></script>
	<%--<script charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>--%>
<script type="text/javascript">
	var url;

	function searchNode(type) {
		$("#dg").datagrid('load', {
			"nodeNames" : $("#s_nodeName").val(),
			"label" : $("#s_label").val()
		});
	}
	function searchNode2(type) {
		$("#dg2").datagrid('load', {
			"nodeNames" : $("#s_nodeName2").val(),
			"label" : $("#s_label2").val()
		});
	}

	function deleteOrAdd(type) {
		var selectedRows = $("#dg").datagrid('getSelections');
		var selectedRows2 = $("#dg2").datagrid('getSelections');
		if (selectedRows.length == 0 || selectedRows2.length == 0) {
			$.messager.alert("系统提示", "请选择要操作关系的数据！");
			return;
		}
		var strIds = [];
		var strIds2 = [];
		for ( var i = 0; i < selectedRows.length; i++) {
			strIds.push(selectedRows[i].id + "-" + selectedRows[i].label);
		}
		for ( var i = 0; i < selectedRows2.length; i++) {
			strIds2.push(selectedRows2[i].id + "-" + selectedRows2[i].label);
		}
		var ids = strIds.join(",");
		var ids2 = strIds2.join(",");
		$.messager
				.confirm(
						"系统提示",
						"您确认要操作这<font color=red>" + selectedRows.length + "->"
								+ selectedRows2.length + "</font>条数据的关系吗？",
						function(r) {
							if (r) {
								$
										.post(
												"${pageContext.request.contextPath}/line/deleteOrAdd.do",
												{
													type : type,
													ids : ids,
													ids2 : ids2
												}, function(result) {
													if (result.success) {
														$.messager.alert(
																"系统提示",
																result.message,
																"info");
														$("#dg").datagrid(
																"reload");
														$("#dg2").datagrid(
																"reload");
													} else {
														$.messager.alert(
																"系统提示",
																"关系操作失败！",
																"error");
													}
												}, "json");
							}
						});
	}
</script>
</head>
<body style="margin:1px;clear:both;">
	<div style="width: 42%;margin-left: 5px;float:left">
		<table id="dg" title="关系操作左" style="height:616px;" class="easyui-datagrid" fitColumns="true" PageSize="20" pagination="true"
			rownumbers="true" url="${pageContext.request.contextPath}/line/list.do" fit="false" toolbar="#tb">
			<thead>
				<tr>
					<th field="cb" checkbox="true" align="center"></th>
					<th field="id" width="15" align="center">编号</th>
					<th field="label" width="15" align="center">节点类型</th>
					<th field="nodeNames" width="70" align="center">节点名称</th>
				</tr>
			</thead>
		</table>
	</div>
	<div style="width: 14%;float:left;">
		<!-- <button style="width:120px;height:40px;margin:20px 50px 0px 50px;">左右建立关系</button> -->
		<a href="javascript:deleteOrAdd(1)" class="easyui-linkbutton" style="width:120px;height:40px;margin:30px 20px 0px 20px;">建立关系</a> <a
			href="javascript:deleteOrAdd(2)" class="easyui-linkbutton" style="width:120px;height:40px;margin:30px 20px 0px 20px;">解除关系</a>
	</div>
	<div style="width: 42%;float:left;" style="margin-right:10px;">
		<table id="dg2" title="关系操作右" style="height:616px;" class="easyui-datagrid" fitColumns="true" PageSize="20" pagination="true"
			rownumbers="true" url="${pageContext.request.contextPath}/line/list.do" fit="false" toolbar="#tb2">
			<thead>
				<tr>
					<th field="cb" checkbox="true" align="center"></th>
					<th field="id" width="15" align="center">编号</th>
					<th field="label" width="15" align="center">节点类型</th>
					<th field="nodeNames" width="70" align="center">节点名称</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="tb">
		<div>
			&nbsp;节点类型： <select class="easyui-combobox" panelHeight="auto" id="s_label" name="s_label" style="width:100px">
				<option value="">&nbsp;&nbsp;--请选择--</option>
				<option value="Topic">知识点</option>
				<option value="Chapter">章节</option>
				<option value="Subject">科目</option>
			</select>&nbsp;节点名称：<input type="text" id="s_nodeName" size="15" onkeydown="if(event.keyCode==13) searchNode()" /> <a
				href="javascript:searchNode()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		</div>
	</div>
	<div id="tb2">
		<div>
			&nbsp;节点类型： <select class="easyui-combobox" panelHeight="auto" id="s_label2" name="s_label2" style="width:100px">
				<option value="">&nbsp;&nbsp;--请选择--</option>
				<option value="Topic">知识点</option>
				<option value="Chapter">章节</option>
				<option value="Subject">科目</option>
			</select>&nbsp;节点名称：<input type="text" id="s_nodeName2" size="15" onkeydown="if(event.keyCode==13) searchNode2()" /> <a
				href="javascript:searchNode2()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		</div>
	</div>
</body>
</html>