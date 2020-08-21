<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Add New Product(Admin)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>

<h3 style="text-align:center;"> Add Product Details</h3>

<form action="admin?action=insertproduct" method="post">
		<div>
			<div><label for="pName">Enter product Name</label> </div>
			<div><input type="text" id="pName" name="pName"> </div>
		</div>
		<div>
			<div><label for="pCost">Enter Product Cost</label> </div>
			<div><input type="text" id="pCost" name="pCost"> </div>
		</div>
		<div>
			<div><label for="pDescription">Product Description</label> </div>
			<div><input type="text" id="pDescription" name="pDescription"> </div>
		</div>
		<div>
			<div><input type="submit" value="Add Product"> </div>
		</div>
	</form>
	
<%-- Required View Template --%>

<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>