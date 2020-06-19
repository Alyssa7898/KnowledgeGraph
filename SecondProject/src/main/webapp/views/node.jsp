<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>节点操作</title>
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
			"label" : $("#s_label").val(),
			"acnode" : type
		});
	}

	function deleteNode() {
		var relationshipCounts = 0;
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的数据！");
			return;
		}
		var strIds = [];
		var rss = [];
		for ( var i = 0; i < selectedRows.length; i++) {
			relationshipCounts += selectedRows[i].relationshipCount;
			strIds.push(selectedRows[i].id);
			rss.push(selectedRows[i].relationshipCount);
		}
		var ids = strIds.join(",");
		var rs = rss.join(",");
		$.messager.confirm("系统提示", "您确认要删除这<font color=red>"
				+ selectedRows.length + "</font>个节点<font color=red>"
				+ relationshipCounts + "</font>条关系吗？", function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/node/delete.do", {
					ids : ids,
					rss : rs
				}, function(result) {
					if (result.success) {
						$.messager.alert("系统提示", "数据已删除成功！");
						$("#dg").datagrid("reload");
					} else {
						$.messager.alert("系统提示", "数据删除失败！");
					}
				}, "json");
			}
		});

	}

	function openNodeAddDialog() {
		resetValue();
		$("#dlg").dialog("open").dialog("setTitle", "添加用户信息");
		url = "${pageContext.request.contextPath}/node/save.do";
	}

	function saveNode() {
		$("#fm").form("submit", {
			url : url,
			onSubmit : function() {
				return $(this).form("validate");
			},
			success : function(result) {
				$.messager.alert("系统提示", result);
				resetValue();
				$("#dlg").dialog("close");
				$("#dg").datagrid("reload");
			}
		});
	}

	function openNodeModifyDialog() {
		$("#label").combobox({
			disabled : true
		});
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length != 1) {
			$.messager.alert("系统提示", "请选择一条要编辑的数据！");
			return;
		}
		var row = selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle", "编辑节点信息");
		$('#fm').form('load', row);
		url = "${pageContext.request.contextPath}/node/save.do?id=" + row.id;
	}

	function resetValue() {
		$("#label").combobox({
			disabled : false
		});
		$("#nodeNames").val("");
		$("#symbolSize").val("");
	}

	function closeNodeDialog() {
		$("#dlg").dialog("close");
		resetValue();
	}
</script>
</head>
<body style="margin:1px;">
	<table id="dg" class="easyui-datagrid" fitColumns="true" PageSize="20" pagination="true" rownumbers="true"
		url="${pageContext.request.contextPath}/node/list.do" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true" align="center"></th>
				<th field="id" width="10" align="center">编号</th>
				<th field="label" width="10" align="center">节点类型</th>
				<th field="nodeNames" width="90" align="center">节点名称</th>
				<!-- <th field="symbolSize" width="100" align="center">节点权重</th> -->
				<th field="relationshipCount" width="10" align="center">关系数量</th>
			</tr>
		</thead>
	</table>
	<div id="tb">
		<div>
			<a href="javascript:openNodeAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> <a
				href="javascript:openNodeModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a> <a
				href="javascript:deleteNode()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>
			&nbsp;节点类型： <select class="easyui-combobox" panelHeight="auto" id="s_label" name="s_label" style="width:100px">
				<option value="">&nbsp;&nbsp;--请选择--</option>
				<option value="Topic">知识点</option>
				<option value="Chapter">章节</option>
				<option value="Subject">科目</option>
			</select>&nbsp; &nbsp;节点名称：<input type="text" id="s_nodeName" size="20" onkeydown="if(event.keyCode==13) searchNode()" /> <a
				href="javascript:searchNode()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a> <a
				href="javascript:searchNode('1')" class="easyui-linkbutton easyui-tooltip" position="top" title="查询不存在关系的节点" iconCls="icon-search"
				plain="true">孤点查询</a>
		</div>
	</div>
	<div id="dlg" class="easyui-dialog" style="width: 350px;height:180px;padding: 10px 20px" closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="8px">
				<tr>
					<td>节点类型：</td>
					<td><select class="easyui-combobox" id="label" name="label" panelHeight="auto" style="width:173px">
							<option value="Topic">知识点</option>
							<option value="Chapter">章节</option>
							<option value="Subject">科目</option>
					</select>&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>节点名称：</td>
					<td><input type="text" id="nodeNames" name="nodeNames" class="easyui-validatebox" required="true" />&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<!-- <tr>
					<td>节点权重：</td>
					<td><input type="text" id="symbolSize" name="symbolSize" class="easyui-validatebox" />
					</td>
				</tr> -->
			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:saveNode()" class="easyui-linkbutton" iconCls="icon-ok">保存</a> <a href="javascript:closeNodeDialog()"
			class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>