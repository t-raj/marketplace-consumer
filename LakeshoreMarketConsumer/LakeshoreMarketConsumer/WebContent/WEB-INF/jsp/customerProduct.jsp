<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>
<body>

<!-- customer info with links to actions --> 			
<jsp:include page="/WEB-INF/jsp/customerOrder.jsp" />


Search products:

<!-- form to search for products -->
<form action="/LakeshoreMarketConsumer/searchProductResult" method='POST'>
  Please enter the product number to find a product:
  <input type="text" name="productNumber" value="" /><br/>    
  <input type="submit" value="Search"/>  
	
</form>

</body>

</html>