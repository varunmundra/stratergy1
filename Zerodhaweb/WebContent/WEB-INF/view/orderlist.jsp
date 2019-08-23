<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="kiteconnect.Model.Stock" %>
<%@ page import="kiteconnect.Model.Examples" %>
<%@ page import="com.zerodhatech.models.Trade" %>
<%@ page import="java.util.*" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OrderList</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://momentjs.com/downloads/moment-with-locales.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>

          <p><h4>Order List</h4></p>
          <table id="order_tab" class="one table table-bordered table-sm">
          <% List<Trade> list = (List<Trade>) request.getAttribute("order_list"); %>
          <tr><td>Trading Symbol</td><td>Order Id</td><td>Product</td><td>Quantity</td><td>Average Price</td> <td>Time Stamp</td></tr>
			<% for(Trade td : list) {%>
			<tr><td><%=td.tradingSymbol %></td><td><%=td.orderId %></td><td><%=td.product%></td><td><%=td.quantity%></td><td><%=td.averagePrice%></td><td><%=td.fillTimestamp%></td></tr>
		    <%} %>
	
	
	    </table>
</body>
</html>