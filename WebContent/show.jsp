<%@ page  language="java" import="net.sf.json.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WeSearch</title>
<script type="text/javascript" src="jquery-1.9.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="style.css" />
<script type="text/javascript">  
        function getjson()
        {
        	
        	$("#main-content").empty();
        	$("#sidebar").empty();
        	$("#head-content").empty();
        	var q = $("#query")[0].value;
        	var inp = {name:q};
        	$("#head-content").append("<font color=\"green\">");
        	$("#head-content").append(q+"</font>&nbsp&nbsp");
            $.getJSON("/WeiboSearch/hello",inp,function(data){
            	$("#sidebar").empty();
            	var t;
            	d = new Array();
            	for(var i = 0;i < data.length;i++)
            	{
            		d[i] = new Array();
            		for(var j = 0;j<data[i].docs.length;j++)
            		{
            			d[i][j]=data[i].docs[j];
            		}
            	}
            	$("#head-content").append(data.length);
            	for(var i = 0;i < data.length;i++)
            	{
            		t="";
            		for(var j = 0;j<data[i].terms.length;j++)
            		{
            			t+=(data[i].terms[j]+"/");
            		}
            		
            		//document.getElementById(s).innerHTML=terms[i];
            		$("#sidebar").append("<li><a href="+"javascript:show("+i+");"+">"+t+"</a></li>");
            		//$("#sidebar").append("<li>skjflsakjdflaksjfksjfkjksf</li>");
            	}
            	for(var i = 0;i < data.length;i++)
            	{
            		delete data[i];
            	}
            });  
        	
        }  
        function show(n)
        {
        	$("#main-content").empty();
        	for(var i =0;i<d[n].length;i++)
        	{
        		$("#main-content").append("<p><font size="+3+">"+d[n][i]+"</font></p><br>");
        		//document.getElementById("main-content").innerHTML=docs[n][i];
        	}
        }
    </script>  
</head>
<body>
<div id="header">
<br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input id = "query" type="text" style="width:400px;height:26px" query="fquery" name ="name" />
    <input type="button" style="width:85px;height:30px" value="WeSearch" onclick= "getjson()"/> 
  	
</form>
</div>

<div id="content">
		<div id="sidebar">
			
		</div>
		<div id="head-content">
			<p></p>
			
			<br />
		</div>
		<div id="main-content">
			<p></p>
			
			<br />
		</div>
		
		<div class="clear"></div>
	</div>


</body>
</html>