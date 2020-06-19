<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.5.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.5.2/themes/icon.css">
<script charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/jquery.min.js"></script>
<script charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/locale/easyui-lang-en.js"></script>
	<%--<script charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>--%>
<script type="text/javascript">
	function uploadExcelf(type) {
		//得到上传文件的全路径  
		var fileName = $("#uploadExcelid").filebox("getValue");
		//进行基本校验  
		if (fileName == "") {
			$.messager.alert("提示", "请选择上传文件！", "info");
		} else {
			//对文件格式进行校验  
			var d1 = /\.[^\.]+$/.exec(fileName);
			if (d1 == ".xls") {
				$("#fm")
						.form(
								"submit",
								{
									url : "${pageContext.request.contextPath}/excel/import.do?type="
											+ type,
									dataType : "json",
									onSubmit : function() {
										return true;
									},
									success : function(result) {
										var result = eval('(' + result + ')');
										if (result.succ) {
											$.messager.alert("提示",
													result.message, "info");
										} else {
											$.messager.alert("错误",
													result.message, "error");
										}
									}
								});
			} else {
				$.messager.alert("提示", "请选择xls格式文件！", "info");
				//$("#uploadExcelid").val("");
				//$("#uploadExcelid").filebox("setValue","");
				$("#uploadExcelid").filebox("setText", "");
			}
		}
	}
</script>
</head>
<body>
	<div style="margin: 30px 20px 20px 20px;">
		<form id="fm" name="fm" method="post" enctype="multipart/form-data">
			<input id="uploadExcelid" name="uploadExcel" class="easyui-filebox" style="width:500px;height:30px;"
				data-options="buttonText:'&nbsp;浏览...&nbsp;', prompt:'请选择文件...'"> <br />
			<a href="${pageContext.request.contextPath}/excel/download.do" class="easyui-linkbutton"
			   style="margin: 20px 10px 20px 20px;width:122px;">下载模板</a>
			<a href="javascript:uploadExcelf(1)" position="top" title="注意：该操作会删除所有数据！" class="easyui-linkbutton easyui-tooltip"
				style="margin: 20px 10px 20px 20px;width:122px;">批量导入</a>
			<a href="javascript:uploadExcelf(2)" class="easyui-linkbutton"
				style="margin: 20px 10px 20px 20px;width:122px;">增量导入</a>
		</form>
	</div>

</body>
</html>