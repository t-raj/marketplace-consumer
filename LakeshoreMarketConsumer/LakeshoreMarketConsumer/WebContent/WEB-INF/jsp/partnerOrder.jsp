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
<jsp:include page="partner.jsp" />

<form:form method="POST" action="" modelAttribute="orderForm">
<table class="tablesorter">
			<thead class="thfloat textbold">
			    <tr>
			    	<th>Order Number</th>
			    	<th>Actions</th>			
			    </tr>
		    </thead>
			<tbody class="texttd">
			    <c:forEach items="${orderForm.orderTOList}" var="orderTO" varStatus="status">
			        <tr>
			            <td>
			            	${orderTO.id} <br>
			            </td>
			            <td>
			                <div>
				         	    <c:forEach items="${orderTO.links}" var="link" varStatus="status">
				         	    	<!-- clicking on the link goes to the relative path -->
					    			<a href="${link.rel}"> ${link.action}</a><br> 
					    		</c:forEach>
					    	</div>
						</td>
			       </tr>
			    </c:forEach>
		    </tbody>
		</table>
</form:form> 


</body>
</html>