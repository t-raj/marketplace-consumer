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

<!-- form to get products -->

<form action="products">
  Please enter the product number to find a product:
  <input type="text" name="productNumber" value="" /><br/>    
  <input type="submit" value="Search"/>  
	
</form>

<!-- dynamic links -->
		<form:form method="POST" action="submitSurvey.do" modelAttribute="partnerForm">

<table class="tablesorter">
		
			<thead class="thfloat textbold">
			    <tr>
			    	<th></th>
			    	<th></th>			
			    </tr>
		    </thead>
			<tbody class="texttd">
			    <c:forEach items="${partnerForm.partnerTOList}" var="partnerTO" varStatus="status">
			        <tr>
			            <td><input type="hidden" name="partnerTOList[${status.index}].id" value="${partnerTO.id}"/></td> 			           
			            <td>
			            	${partnerTO.id}) ${partnerTO.lastName}<br>
			            </td>
			       </tr>
			    </c:forEach>
		    </tbody>
		</table>
</form:form>
</body>
</html>