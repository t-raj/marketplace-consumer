<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Partner Portal</title>
</head>
<body>
<script>
function submitForm() {
    document.getElementById("partnerActionForm").submit();
}
</script>

<!-- 
  This page fulfills the following:
  Allowing Partners to use your site to sell their products with functionalities such as:
	a. Need to register and create profile of partners
	b. Add product or products in market place
	c. Push orders that customers made to partners
	d. Get acknowledgement of order fulfillment
 -->
<!-- partner info with links to actions --> 			
<jsp:include page="/WEB-INF/jsp/partner.jsp" />

<!-- form to add products  -->
<form method="POST" action="/LakeshoreMarketConsumer/addProductForm">
<h4>Add Product</h4>
  Please enter the product number:  <input type="text" name="productNumber" value="" /><br/>    
  Please enter the product description:  <input type="text" name="description" value="" /><br/>    
  Please enter the product price:  <input type="text" name="price" value="" /><br/>    
  
  <input type="submit" value="Add"/>  
 </form>

</body>
</html>