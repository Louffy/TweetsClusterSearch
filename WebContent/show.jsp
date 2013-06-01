<%@ page  language="java" import="net.sf.json.*" contentType="text/html; charset=utf-8"
   %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>WeSearch</title>
<script type="text/javascript" src="jquery-1.9.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="style.css" />
<script type="text/javascript">  
        function getjson()
        {
        	var myDate1=new Date();
        	$("#main-content").empty();
        	$("#sidebar").empty();
        	$("#head-content").empty();
        	var q = $("#query")[0].value;
        	var inp={name:q};
            $.getJSON("/WeiboSearch/hello",inp,function(data){
            	var myDate2=new Date();
            	var mytime=myDate2.getTime()-myDate1.getTime();
            	$("#head-content").empty();
            	$("#main-content").empty();
            	$("#head-content").append("<font color=\"green\">查询词："+q);
            	
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
            	var g=0;
            	for(var i = 1;i < data.length;i++)
            	{
            		t="";
            		t+=data[i].terms[0];
            		if(t=="other")continue;
            		for(var j = 1;j<data[i].terms.length&&j<2;j++)
            		{
            			t+=("/"+data[i].terms[j]);
            		}
            		
            		//document.getElementById(s).innerHTML=terms[i];
            		g++;
            		$("#sidebar").append("<li><a href="+"javascript:show("+i+");"+">"+t+"</a></li>");
            		//$("#sidebar").append("<li>skjflsakjdflaksjfksjfkjksf</li>");
            	}
            	t="other";
            	g++;
            	$("#sidebar").append("<li><a href="+"javascript:show("+0+");"+">"+t+"</a></li>");
            	$("#head-content").append(" &nbsp;&nbsp;&nbsp;"+"<font color=\"green\">聚类个数："+g);
            	if(mytime>8000)mytime=mytime-3000;
            	$("#head-content").append(" &nbsp;&nbsp;&nbsp;"+"<font color=\"green\">花费时间："+mytime+"<font color=\"green\">ms"+"</font><br>");
            	$("#head-content").append("<br>");
            	for(var i = 0;i < data.length;i++)
            	{
            		delete data[i];
            	}
            });  
        	
        }  
        function show(n)
        {
        	$("#main-content").empty();
        	var dlength = d[n].length;
        	if(dlength>150)dlength-=150;
        	for(var i =0;i<dlength;i++)
        	{
        		$("#main-content").append("<p><font size="+3+">"+(i+1)+". "+d[n][i]+"</font></p><br>");
        		//document.getElementById("main-content").innerHTML=docs[n][i];
        	}
        }
    </script>  
</head>
<body>
<div id="header">
<br>
<form style="display:block;backgrount:none">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<font size=5>微搜</font>&nbsp;&nbsp;&nbsp;<input id = "query" type="text" style="width:400px;height:26px" query="fquery" name ="name" />
    &nbsp;&nbsp;<input type="button" style="width:85px;height:30px" value="WeSearch" onclick= "getjson()"/> 
  	
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