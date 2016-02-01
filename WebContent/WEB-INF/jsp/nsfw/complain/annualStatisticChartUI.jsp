<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//获取当前年分
	Calendar cal = Calendar.getInstance();
	int curYear = cal.get(Calendar.YEAR);
	request.setAttribute("curYear", curYear);
	
	List yearList = new ArrayList();
	for(int i = curYear; i > curYear-5; i--){
		yearList.add(i);
	}
	request.setAttribute("yearList", yearList);
%>

<!DOCTYPE HTML>
<html>
  <head>
    <%@include file="/common/header.jsp"%>
    <title>年度投诉统计图</title>
  </head>
  <script type="text/javascript" src="${bathPath }js/fusioncharts/fusioncharts.js"></script>
  <script type="text/javascript" src="${bathPath }js/fusioncharts/fusioncharts.charts.js"></script>
  <script type="text/javascript" src="${bathPath }js/fusioncharts/themes/fint.js"></script>
  <script type="text/javascript">
  	$(document).ready(doAnnualStatistic())
  
  	//根据年份统计数
  	function doAnnualStatistic(){
  		var year = $("#year option:selected").val();
  		if(year == "" || year == undefined){
  			year = "${curYear}";//默认当前年份
  		}
  		$.ajax({
  			url:"${basePath}nsfw/complain_getAnnualStatisticData.action",
  			data:{"year":year},
  			type:"post",
  			dataType:"json",
  			success: function(data){
  				if(data != null && data != "" && data != undefined){
  					var revenueChart = new FusionCharts({
  						"type":"line",
  						"renderAt":"chartContainer",
  						"width":"600",
  						"heigh":"400",
  						"dataFormat":"json",
  						"dataSource":{
  							"chart":{
  								"caption": year + " 年度投诉数统计图",
  					            "xAxi	sName": "月  份",
  					            "yAxisName": "投  诉  数",
  					            "theme": "fint"
  							},
  						"data":data.chartData
  						}
  						
  					});
  					revenueChart.render();
  				}else{alert("统计投诉数失败！");}
  			},
  			error: function(){alert("统计投诉数失败！");}
  		});
  	}
  	
  	
  </script>
  <body>
  	<br>
    <s:select id="year" list="#request.yearList" onchange="doAnnualStatistic()"></s:select>
    <br>
    <div id="chartContainer"></div>
  </body>
</html>
