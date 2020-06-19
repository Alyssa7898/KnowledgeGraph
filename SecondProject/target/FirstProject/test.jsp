<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>查询知识</title>
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
    <script charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/conNode.js"></script>
</head>



<body style="margin:1px;width:100%;height:100%; " >
<div id="test">
    <div>&nbsp
        <div style="visibility: hidden" id="node_type" value="<%=session.getAttribute("nodeType")%>"/>
        <div style="visibility: hidden" id="node_id" value="<%=session.getAttribute("nodeId")%>"/>
        <a href="" onclick="loadSecondGraph()" class="easyui-linkbutton" iconCls="icon-search" plain="true" >查看</a>
    </div>
</div>

<fieldset class="mydorder">
    <legend ><%=session.getAttribute("PreChapter")%></legend>
    <div >
        <%=session.getAttribute("knowledge")%>
    </div>
</fieldset>

<fieldset class="mydorder" style="height: 300px">
    <legend >基本介绍</legend>
    <div >
        <%=session.getAttribute("ContenText")%>
    </div>
</fieldset>

<fieldset class="mydorder" style="height: 300px">
    <legend >学习资源</legend>
    <fieldset class="mydorder">
        <legend>网站资源</legend>
        <div >
            <a href="<%=session.getAttribute("Link")%>" target="_blank" style="text-decoration:underline;"><%=session.getAttribute("knowledge")%></a>
        </div>
    </fieldset>
    <fieldset class="mydorder">
        <legend>视频介绍</legend>
        <div >
            <a href="<%=session.getAttribute("Video")%>" target="_blank" style="text-decoration:underline;"><%=session.getAttribute("knowledge")%></a>
        </div>
    </fieldset>


</fieldset>



<div style="width:100%;height:100%;">
    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="node_show" style="width: 100%;height:100%;"></div>
</div>
</body>
</html>
<%--onload="loadSecondGraph(<%=session.getAttribute("nodeType")%>,<%=session.getAttribute("nodeId")%>)"--%>