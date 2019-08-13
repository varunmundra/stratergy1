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
   
   $("#"+stock.instrument_token+"_ltp").text(stock.LTP);
   $("#"+stock.instrument_token+"_high").text(stock.high);
   $("#"+stock.instrument_token+"_low").text(stock.low);
   $("#"+stock.instrument_token+"_high_counter").text(stock.high_counter);
   $("#"+stock.instrument_token+"_low_counter").text(stock.low_counter);
  
  
  /*  console.log("#"+stock.instrument_token);*/
   
  
   
   
  /*   console.log("tick Rec"+stock.instrument_token); */
   
   
   
};







</script>

</head>
<body>
<center><h1>Statergy 1</h1></center> 
<%-- <h4><c:out value="${margin.cash}" /></h4>  --%>

<table>
<tr><td>Token No</td><td>LTP</td><td>High</td><td>Low</td><td>High Counter</td> <td>Low Counter</td></tr>

<% HashMap<Long, Stock> stock_list = (HashMap<Long, Stock>) request.getAttribute("Stock_List");%>
 
 <%  for (Map.Entry<Long,Stock> entry : stock_list.entrySet())  { %>
   <tr id="<%=entry.getKey()%>">
   <% Stock ob = entry.getValue(); %>
   <td><%=ob.getInstrument_token() %></td>
   <td id="<%=entry.getKey()%>_ltp"><%=ob.getLTP() %></td>
   <td id="<%=entry.getKey()%>_high"><%= ob.getHigh()%></td>
   <td id="<%=entry.getKey()%>_low"><%= ob.getLow()%></td>
   <td id="<%=entry.getKey()%>_high_counter"><%= ob.getHigh_counter()%></td>
   <td id="<%=entry.getKey()%>_low_counter"><%= ob.getLow_counter()%></td>
   </tr>
<%} %>

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