<%@page import="com.iiht.evaluation.coronokit.model.*"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-My Kit(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>

<%
		// fetch the shared data
		List<KitDetail> kits =  (List<KitDetail>) request.getAttribute("KitDetails");
	%>
	<table border="1" width="100%">
		<thead>
			<th>Product ID</th>
			<th>Product Name</th>
			<th>Product Quantity</th>
			<th>Product Amount</th>
		</thead>
		<tbody>
			<% for(KitDetail kit : kits) { %>
			<tr>
				<td><%=kit.getProductId()%></td>
				<td><%=kit.getPname()%></td>
				<td><%=kit.getQuantity()%></td>
				<td><%=kit.getAmount()%></td>
				
			</tr>
			<% } %>
		</tbody>
	</table>
	<br/>
  <a href="user?action=showAllProducts"><button>Show All Products</button></a>
<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>