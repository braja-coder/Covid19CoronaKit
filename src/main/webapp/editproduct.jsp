<%@page import="com.iiht.evaluation.coronokit.model.*"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Edit Product(Admin)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>
<h2>Edit Product</h2>
<%
		// fetch the shared data
		ProductMaster products =  (ProductMaster) request.getAttribute("product"); 
%>

<form action="admin?action=updateproduct" method="post">
		
		<div>
			
			<div><input type="text" id="pid" name="pid" value="<%=products.getId()%>" hidden="true"></div>
		</div>
		<div>
			<div><label for="pName">Enter product Name</label> </div>
			<div><input type="text" id="pName" name="pName" value="<%=products.getProductName()%>"></div>
		</div>
		<div>
			<div><label for="pCost">Enter Product Cost</label> </div>
			<div><input type="text" id="pCost" name="pCost"  value="<%=products.getCost()%>"> </div>
		</div>
		<div>
			<div><label for="pDescription">Product Description</label> </div>
			<div><input type="text" id="pDescription" name="pDescription" value="<%=products.getProductDescription()%>"> </div>
		</div>
		<div>
			<div><input type="submit" value="Update Product"> </div>
		</div>
		
	</form>

<%-- Required View Template --%>

<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>