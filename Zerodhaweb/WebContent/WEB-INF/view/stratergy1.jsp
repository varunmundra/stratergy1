<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="kiteconnect.Model.Stock" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Strategy1</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">
 
var webSocket = new WebSocket('ws://localhost:8080/Zerodhaweb/Ticker');


webSocket.onerror = function(event) {
    console.log("Socket Error Occured");
};

webSocket.onopen = function(event) {
	console.log("Web Socket Opened");
};

webSocket.onmessage = function(msg) {
   /*  onMessage(event) */
   
   console.log(msg.data);
   
   var stock = JSON.parse(msg.data);
   
  /*  $("#"+stock.instrument_token).remove(); */
   if(stock.flag=='L')
	{
	   /* $("#low_heading").remove(); */
	   /* $("#low_"+stock.instrument_token).remove(); */
	   var perc_change = ((stock.LTP - stock.prev_close)/(stock.prev_close))*100;
	   var avg_vol_perc = ((stock.current_volume / stock.avg_volume)*100);
	   
	   if(parseInt(avg_vol_perc) > 50) 
		   {
		     var tab_row = "<tr id='low_"+stock.instrument_token+"'><td>"+stock.time_stamp+"</td><td>"+stock.stock_name+"</td><td>"+stock.LTP+"</td><td>"+perc_change.toFixed(2)+"</td><td>"+stock.low_counter+"</td><td>"+stock.prev_low+"</td><td>"+stock.current_volume+"</td><td>"+avg_vol_perc.toFixed(2)+"</td></tr>";	   
		   /* $("#low_table").prepend(tab_row); */
		      $("#low_heading").after(tab_row);
		   }
	
	}
  
   else if(stock.flag=='H')
	{
	  /*  $("#high_heading").remove(); */
	   /* $("#high_"+stock.instrument_token).remove(); */
	   var avg_vol_perc = ((stock.current_volume / stock.avg_volume)*100);
	   var perc_change = ((stock.LTP - stock.prev_close)/(stock.prev_close))*100;
	   
	   if(parseInt(avg_vol_perc) > 50) 
	   { 
		   var tab_row1 = "<tr id='high_"+stock.instrument_token+"'><td>"+stock.time_stamp+"</td><td>"+stock.stock_name+"</td><td>"+stock.LTP+"</td><td>"+perc_change.toFixed(2)+"</td><td>"+stock.high_counter+"</td><td>"+stock.prev_high+"</td><td>"+stock.current_volume+"</td><td>"+avg_vol_perc.toFixed(2)+"</td></tr>";	   
		  /*  $("#high_table").prepend(tab_row1); */
		   $("#high_heading").after(tab_row1);
	   }
	}
  /*  $("#"+stock.instrument_token+"_ltp").text(stock.LTP);
   $("#"+stock.instrument_token+"_high").text(stock.high);
   $("#"+stock.instrument_token+"_low").text(stock.low);
   $("#"+stock.instrument_token+"_high_counter").text(stock.high_counter);
   $("#"+stock.instrument_token+"_low_counter").text(stock.low_counter);
   
   $("#"+stock.instrument_token+"_prev_low").text(stock.prev_low);
   $("#"+stock.instrument_token+"_prev_high").text(stock.prev_high);
   $("#"+stock.instrument_token+"_prev_close").text(stock.prev_close);
   $("#"+stock.instrument_token+"_avg_volume").text(stock.avg_volume); */
  
  
  /*  console.log("#"+stock.instrument_token);*/
   
  
   
   
  /*   console.log("tick Rec"+stock.instrument_token); */
   
   
   
};







</script>
<style>
table, th, td {
  border: 1px solid black;
}
table.one {
    position: relative;
    float: left;
}
table.two {
    position: relative;
    float: right;
}
</style>

</head>
<body>
<center><h1>Statergy 1</h1></center> 
<%-- <h4><c:out value="${margin.cash}" /></h4>  --%>

<table id="high_table" class="one">
<caption>High Table</caption>
<tr id='high_heading'><td>Time</td><td>Token Name</td><td>LTP</td><td>Prcnt Change (%)</td><td>High Counter</td><td>PrevDay High</td><td>Volume</td><td>Volume Change (%)</td></tr>

<%--    <td><%=ob.getInstrument_token() %></td>
    <td> <%=Stock.name_list.get(ob.getInstrument_token())%> </td>
   <td id="high_<%=entry.getKey()%>_ltp"><%=ob.getLTP() %></td>
   <td id="high_<%=entry.getKey()%>_high"><%= ob.getHigh()%></td>
   <td id="high_<%=entry.getKey()%>_low"><%= ob.getLow()%></td>
   <td id="high_<%=entry.getKey()%>_high_counter"><%= ob.getHigh_counter()%></td>
   <td id="high_<%=entry.getKey()%>_low_counter"><%= ob.getLow_counter()%></td>
   <td id="high_<%=entry.getKey()%>_prev_low"><%= ob.getPrev_low()%></td>
   <td id="high_<%=entry.getKey()%>_prev_high"><%= ob.getPrev_high()%></td>
   <td id="high_<%=entry.getKey()%>_prev_close"><%= ob.getPrev_close()%></td>
   <td id="high_<%=entry.getKey()%>_avg_volume"><%= ob.getAvg_volume()%></td> --%>
   </tr>
</table>


<table id="low_table" class="two">
<caption>Low Table</caption>
<tr id='low_heading'><td>Time</td><td>Token Name</td><td>LTP</td><td>Prcnt Change (%)</td><td>Low Counter</td> <td>PrevDay Low</td><td>Volume</td><td>Volume Change (%)</td></tr>

<%--    <tr id="<%=entry.getKey()%>">
   <td><%=ob.getInstrument_token() %></td>
    <td> <%=Stock.name_list.get(ob.getInstrument_token())%> </td>
   <td id="low_<%=entry.getKey()%>_ltp"><%=ob.getLTP() %></td>
   <td id="low_<%=entry.getKey()%>_high"><%= ob.getHigh()%></td>
   <td id="low_<%=entry.getKey()%>_low"><%= ob.getLow()%></td>
   <td id="low_<%=entry.getKey()%>_high_counter"><%= ob.getHigh_counter()%></td>
   <td id="low_<%=entry.getKey()%>_low_counter"><%= ob.getLow_counter()%></td>
   <td id="low_<%=entry.getKey()%>_prev_low"><%= ob.getPrev_low()%></td>
   <td id="low_<%=entry.getKey()%>_prev_high"><%= ob.getPrev_high()%></td>
   <td id="low_<%=entry.getKey()%>_prev_close"><%= ob.getPrev_close()%></td>
   <td id="low_<%=entry.getKey()%>_avg_volume"><%= ob.getAvg_volume()%></td> --%>
   </tr>
</table>


<%-- <tr><td>Token No</td><td>LTP</td><td>High</td><td>Low</td><td>High Counter</td> <td>Low Counter</td></tr>
 <c:forEach items="${Stock_List}" var="usersMap" varStatus="usersMapStatus">  
        <tr id='${usersMap.key}'>
        <td></td>
        <td>${usersMap.key}</td>
        <c:forEach items="${usersMap.value}" var="currentLoggedInUserValue" varStatus="valueStatus">
            </tr> Value: ${currentLoggedInUserValue.LTP}
        </c:forEach>        
    </c:forEach>
</table> --%>


</body>
</html>