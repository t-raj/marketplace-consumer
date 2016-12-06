<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order</title>
</head>
<body>
<!-- customer info with links to actions --> 			
<jsp:include page="/WEB-INF/jsp/customerOrder.jsp" />

<form method="POST" action="/LakeshoreMarketConsumer/processPayment">
<h4>Enter Credit Card Payment</h4>
  Please enter the credit card number:  <input type="text" name="cardNumber" value="" /><br/>    
  Please enter the name:  <input type="text" name="name" value="" /><br/>    
  Please enter the expiration date:  <input type="text" name="expiration" value="" /><br/>    
  Please enter the billing address:  <input type="text" name="billing" value="" /><br/>    
  Please enter the card type:  <input type="text" name="type" value="" /><br/>    
  
  <input type="submit" value="Submit"/>  
 </form>
</body>
</html>