<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Login.jsp' starting page</title>
    
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
  	var loginAndRedDialog;
  	$(function(){
  		loginAndRedDialog = $("#loginAndRedDialog").dialog({
  		closable:false,modal:true,
  		buttons:[{text:'注册',handler:function(){
  		}},{text:'登录',handler:function(){
  		
  			//console.info('wodianji');
  			$.ajax({url:'userController.do?login',
  				data:{
  				name:$('#loginInputForm input[name=name]').val(),
  				password:$('#loginInputForm input[name=password]').val()},
  				dataType:'json',
  				success:function(r){
  					console.info(r.msg);
  				
  				}
  			});
  		}}]
  		});
  	});
  
  </script>
  </head>
  <body>
    <div id="loginAndRedDialog" title="用户登录" style="width:250px;height:200px;">
    	<form id="loginInputForm" method="post">
    	<table>
    		<tr>
    			<th align="right">用户名</th>
    			<td ><input name="name"/></td>
    		</tr>
    		<tr>
    			<th align="right">密码</th>
    			<td><input name="password" type="password"/></td>
    		</tr>
    	</table>
    	</form>
    	
    </div>
  </body>
</html>
