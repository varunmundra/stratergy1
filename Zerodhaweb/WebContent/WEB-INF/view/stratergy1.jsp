<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="kiteconnect.Model.Stock" %>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>we are in statergy 1</h1> 
<%-- <h4><c:out value="${margin.cash}" /></h4>  --%>
<%
HashMap<Long, String> name_list =  (HashMap<Long, String> ) request.getAttribute("Stock_Name_List");

%> 

<table>
 <c:forEach items="${Stock_Name_List}" var="usersMap" varStatus="usersMapStatus">  
        <tr>
        <td></td>
        <td>Key: ${usersMap.key}</td>
        <c:forEach items="${usersMap.value}" var="currentLoggedInUserValue" varStatus="valueStatus">
            <td>Value: ${currentLoggedInUserValue}</td></tr>
        </c:forEach>        
    </c:forEach>
</table>


</body>
</html>