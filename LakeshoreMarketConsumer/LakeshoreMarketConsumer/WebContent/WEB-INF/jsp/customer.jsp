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
Search products:

<!-- form to search for products -->
<form action="/LakeshoreMarketConsumer/products" method='POST'>
  Please enter the product number to find a product:
  <input type="text" name="productNumber" value="" /><br/>    
  <input type="submit" value="Search"/>  
	
</form>

<!-- search results are below -->
<form:form method="POST" action="" modelAttribute="productForm">
    <table class="tablesorter">
			<thead class="thfloat textbold">
			    <tr>
			    	<th>Number</th>
			    	<th>Product Id</th>			
			    	<th>Product Description</th>
			    	<th>Action</th>			
			    </tr>
		    </thead>
			<tbody class="texttd">
			    <c:forEach items="${productForm.productTOList}" var="productTO" varStatus="status">
			        <tr>
			            <td><input type="hidden" name="productTOList[${status.index}].id" value="${productTO.id}"/></td> 			           
			            <td>
			            	${productTO.id} 
			            </td>
			            <td>
			            	${productTO.description}
			            </td>
			            <td>
			            	<!-- Link will include action to buy the product -->
			                ${productTO.link}
			            </td>
			       </tr>
			    </c:forEach>
		    </tbody>
		</table>
</form:form>

</body>

</html>