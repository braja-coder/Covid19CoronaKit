<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Place Order(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>
<h2 style="text-align:center;"> Delivery Address</h2>
<br/>
<form action="user?action=saveorder" method="post">
		<div>
			<div><label for="pAddress">Enter Address</label> </div>
			<div><input style="height:200px;" type="text" id="pAddress" name="pAddress"> </div>
		</div> 
		<br/>
		<div>
			<div>
			<input type="submit" value="Order"> 
			</div>
		</div>
	</form>

<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>