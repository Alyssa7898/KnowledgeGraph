<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>关联关系图</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/jquery-easyui-1.5.2/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/jquery-easyui-1.5.2/themes/icon.css">
    <script charset="utf-8" type="text/javascript"
            src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/jquery.min.js"></script>
    <script charset="utf-8" type="text/javascript"
            src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/locale/easyui-lang-en.js"></script>
    <%--<script charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>--%>
    <%--<style>
        ul
        {
            list-style-type:none;
            margin:0;
            padding:0;
            overflow:hidden;
        }
        li
        {
            float:left;
        }
        a:link,a:visited
        {
            display:block;
            width:120px;
            font-weight:bold;
            color:#FFFFFF;
            background-color:#bebebe;
            text-align:center;
            padding:4px;
            text-decoration:none;
            text-transform:uppercase;
        }
        a:hover,a:active
        {
            background-color:#cc0000;
        }

    </style>--%>

    <script type="text/javascript">
        $(function () {
            if ("1" != "${currentUser.role}") {
                $("#userList").hide();
            }
        });

        var url;

        function addTab(url, text, iconCls) {
            var content = "<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='${pageContext.request.contextPath}/views/"
                + url + "'></iframe>";
            $("#tabs").tabs("add", {
                title: text,
                iconCls: iconCls,
                closable: true,
                content: content
            });
        }

        function openTab(text, url, iconCls) {
            if ($("#tabs").tabs("exists", text)) {
                $("#tabs").tabs("close", text);
                addTab(url, text, iconCls);
                $("#tabs").tabs("select", text);
            } else {
                addTab(url, text, iconCls);
            }
        }

        function openPasswordModifyDialog() {
            $("#dlg").dialog("open").dialog("setTitle", "修改密码");
            url = "${pageContext.request.contextPath}/user/modifyPassword.do?id=${currentUser.id}";
        }

        function closePasswordModifyDialog() {
            $("#dlg").dialog("close");
            $("#oldPassword").val("");
            $("#newPassword").val("");
            $("#newPassword2").val("");
        }

        function modifyPassword() {
            $("#fm").form("submit", {
                url: url,
                onSubmit: function () {
                    var newPassword = $("#newPassword").val();
                    var newPassword2 = $("#newPassword2").val();
                    if (!$(this).form("validate")) {
                        return false;
                    }
                    if (newPassword != newPassword2) {
                        $.messager.alert("系统提示", "确认密码输入错误！");
                        return false;
                    }
                    return true;
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统提示", "密码修改成功，下一次登录生效！");
                        closePasswordModifyDialog();
                    } else {
                        $.messager.alert("系统提示", "原密码不正确！");
                        return;
                    }
                }
            });
        }

        function logout() {
            $.messager
                .confirm(
                    "系统提示",
                    "您确定要退出系统吗",
                    function (r) {
                        if (r) {
                            window.location.href = "${pageContext.request.contextPath}/user/logout.do";
                        }
                    });
        }
    </script>
    <jsp:include page="login_chk.jsp"></jsp:include>
<body class="easyui-layout">
<%-- <div region="north" style="height: 78px;background-color: #ffff">
    <table width="100%">
        <tr>
            <td width="50%"></td>
            <td valign="bottom" style="font-size: 20px;color:#8B8B8B;font-family: '楷体';" align="right" width="50%"><font size="3">&nbsp;&nbsp;<strong>当前用户：</strong>${currentUser.name
                    }：${currentUser.userName
                    }</font></td>
        </tr>
    </table>
</div> --%>
<div region="center">
    <div class="easyui-tabs" fit="true" border="false" id="tabs">
        <div title="首页" data-options="iconCls:'icon-first'">
            <div align="center" style="padding-top:100px">
                <font color="grey" size="5">知识点：${resultLabels.Topic} 个</font><br/>
                <font color="grey" size="5">章节：${resultLabels.Chapter} 个</font><br/>
                <font color="grey" size="5">科目：${resultLabels.Subject} 个</font><br/>
                <br/> <font color="grey" size="5">总计：${resultLabels.Count}个</font>
            </div>
        </div>
    </div>
</div>


<div region="west" style="width: 200px;height:500px;" title="导航菜单" split="true" data-options="iconCls:'icon-nav'">
    <div class="easyui-accordion">
        <div title="关系图谱" data-options="iconCls:'icon-link'" style="padding:10px">
            <a href="javascript:openTab('图谱查询','graph.jsp','icon-graph_search')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-graph_search'" style="width: 150px;">图谱查询</a>

        </div>
        <div title="数据导入" data-options="iconCls:'icon-import_file'" style="padding:10px;border:none;">
            <a href="javascript:openTab('导入','batchimport.jsp','icon-import_child')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-import_child'"
               style="width: 150px;">导入</a>
            <!-- <a href="javascript:openTab('增量导入','incrementalimport.jsp','icon-lxr')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-lxr'" style="width: 150px;">增量导入</a> -->
        </div>
        <div title="系统管理" data-options="iconCls:'icon-manager'" style="padding:10px;border:none;">

            <a
                    href="javascript:openPasswordModifyDialog()"
                    class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-key'" style="width: 150px;">
                修改密码</a> <a
                href="javascript:logout()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-exit'"
                style="width: 150px;"> 安全退出</a>
        </div>
    </div>
</div>
<div id="dlg" class="easyui-dialog" style="width: 400px;height:250px;padding: 10px 20px" closed="true"
     buttons="#dlg-buttons">
    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>用户名：</td>
                <td><input type="text" id="userName" name="userName" value="${currentUser.userName }"
                           readonly="readonly" style="width: 200px"/>
                </td>
            </tr>
            <tr>
                <td>原密码：</td>
                <td><input type="password" id="oldPassword" name="password" class="easyui-validatebox" required="true"
                           style="width: 200px"/>
                </td>
            </tr>
            <tr>
                <td>新密码：</td>
                <td><input type="password" id="newPassword" name="newPassword" class="easyui-validatebox"
                           required="true" style="width: 200px"/>
                </td>
            </tr>
            <tr>
            <td>确认新密码：</td>
            <td><input type="password" id="newPassword2" class="easyui-validatebox" required="true"
                       style="width: 200px"/>
            </td>
        </tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:modifyPassword()" class="easyui-linkbutton" iconCls="icon-ok">保存</a> <a
        href="javascript:closePasswordModifyDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
</body>
</html>