<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  <script type="text/javascript" src="js/skin_blue for jquery-easyui-1.2.3/jquery-1.4.4.min.js"></script>
  <script type="text/javascript" src="js/skin_blue for jquery-easyui-1.2.3/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="js/skin_blue for jquery-easyui-1.2.3/locale/easyui-lang-zh_CN.js"></script>
  <link rel="stylesheet" href="js/skin_blue for jquery-easyui-1.2.3/themes/default/easyui.css" type="text/css"></link>
  <link rel="stylesheet" href="js/skin_blue for jquery-easyui-1.2.3/themes/icon.css" type="text/css"></link>
 	<script type="text/javascript" charset="utf-8">
 		$(function(){
 			/* $("#dd").dialog({modal:true}); */
 		});
 	</script>
  </head>
  <body>
    <div  id="p" class="easyui-panel"  title="My Panel" style="width:400px;height:200px;background:#fafafa;" iconCls="icon-save" closable="true" collapsible="true" minimizable="true" fit="true" closed="true">
    Panel Content.
    <p>you are welcome.</p>
    </div>
    <input type="button" value="打开" onclick="$('#p').panel('open')"/>
    <input type="button" value="关闭" onclick="$('#p').panel('close')"/>
    <input type="button" value="关闭" onclick="$('#p').panel('destroy')"/>
    
  </body>
</html>
