<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:99%;height:95%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>allBooksManger</title>
<link href="${pageContext.request.contextPath}/css/base.css" type="text/css" rel="stylesheet">


<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.5.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.5.2/themes/icon.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen_v1.7.0/chosen.css"  />
<script charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js"></script>
<script charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/locale/easyui-lang-en.js"></script>
<%--<script charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>--%>
<script charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>

<script charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/chosen_v1.7.0/chosen.jquery.js"></script>
<script charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/graph.js"></script>
</head>
<body style="margin:1px;width:100%;height:100%;" id="ff">
	<input type="hidden" id="path" value="${pageContext.request.contextPath}"/>
	<div id="tb">
		<div>&nbsp;科目:
			<select id="subject" multiple name="subject" style="width:145px;height:25px;">
			</select>
			&nbsp;&nbsp;知识点:
			<select id="topic"  multiple name="topic" style="width:145px;height:25px;">
			</select>
			 &nbsp;&nbsp;章节:
			<select id="chapter" multiple name="chapter" style="width:145px;height:25px;">
			</select>
			&nbsp;&nbsp;<%--评论:
			<input type="checkbox" id="iscomments" name="iscomments" checked="checked" style="vertical-align: middle;zoom:150%"/>--%>
			<a href="javascript:association()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		</div>
	</div>
	<div id="dlg_c" class="easyui-dialog" style="width: 300px;height:450px;padding: 10px 20px; position: relative; z-index:1000;"
		closed="true" buttons="#dlg_c-buttons">
		<form id="fm_c" method="post">
			<table cellspacing="8px" id="tab">
			</table>
		</form>
	</div>
	<div style="width:100%;height:100%;">
		<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
		<div id="main" style="width: 100%;height:100%;"></div>
	</div>

</body>
</html>