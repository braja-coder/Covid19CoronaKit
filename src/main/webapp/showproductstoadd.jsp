<%@page import="com.coronakit.model.*"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-All Products(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>

<h2 style="text-align:center;">Products Cart</h2>
	
	<form action="user?action=showkit" method="post">
	<%
		// fetch the shared data
			List<Product> products =  (List<Product>) request.getAttribute("products");
		     int ls=products.size();
	%>
	<table border="1" width="100%">
		<thead>
			<th>Product Name</th>
			<th>Product Cost</th>
			<th>Product Description</th>
			<th>Product Quantity</th>
			<th></th>
		</thead>
		
		<tbody>
			<% for(int i=0; i<ls ; i++) { %>
			<tr>
				<div><input type="text" id="pid<%=i %>" name="pid<%=i %>" value="<%=products.get(i).getId()%>" hidden="true">
				<input type="text" id="cost<%=i %>" name="cost<%=i %>" value="<%=products.get(i).getCost()%>" hidden="true">
				<div>
				<td><%=products.get(i).getProductName()%> <input type="text" id="pname<%=i %>" name="pname<%=i %>" value="<%=products.get(i).getProductName()%>" hidden="true"> </td>
				<td><%=products.get(i).getCost()%></td>
				<td><%=products.get(i).getProductDescription()%></td>
				<td><input type="text" id="quantity<%=i %>" name="quantity<%=i %>"  value="<%=products.get(i).getQuantity()%>" ></input></td>
				<td><input type="submit" value="Add"></td>
			</tr>
			<% } %>			
		</tbody>
	</table>
	
	<input type="text" id="psize" name="psize" value="<%=products.size()%>" hidden="true">
	</form>
	<br/>
	<a href="user?action=placeorder"><button style="text-align:center;" >Place Order</button></a>
<%-- Required View Template --%>

<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>