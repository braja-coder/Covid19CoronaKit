
<%@page import="com.iiht.evaluation.coronokit.model.*"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Order Summary(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>

<h2>Order Confirmed</h2>

<%
		// fetch the shared data
		CoronaKit ckit =  (CoronaKit) request.getAttribute("ckit");
		List<KitDetail> kits =  (List<KitDetail>) request.getAttribute("KitDetails");
		
		
	%>
	<table border="1" width="100%">
		<thead>
			<th>Product ID</th>
			<th>Product Name</th>
			<th>Product Quantity</th>
			<th>Product Cost</th>
		</thead>
		<tbody>
			<% for(KitDetail kit : kits) { %>
			<tr>
			    <td><%=kit.getProductId()%></td> 
				<td><%=kit.getPname()%></td>
				<td><%=kit.getQuantity()%></td>
				<td><%=kit.getAmount()%></td>
				
			</tr>
			<%  } %>
		</tbody>
	</table>
	
	<br/>
	<label><%="User Name            : "+ckit.getPersonName() %></label><br/>
	<label><%="User Email           : "+ckit.getEmail() %></label><br/>
	<label><%="User Address         : "+ckit.getDeliveryAddress() %></label><br/>
	<label><%="User Number          : "+ckit.getContactNumber()%></label><br/>
	<label><%="Total Bill Amount    : "+ckit.getTotalAmount()%></label><br/>
     <label><%="Date of Purchage    : "+ckit.getOrderDate()%></label><br/>
	
<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>