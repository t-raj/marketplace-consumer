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
<form:form method="POST" action="submitSurvey.do" modelAttribute="partnerForm" id="partnerActionForm">
<table class="tablesorter">
			<thead class="thfloat textbold">
			    <tr>
			    	<th>Partner Information</th>
			    	<th>Actions</th>			
			    </tr>
		    </thead>
			<tbody class="texttd">
			    <c:forEach items="${partnerForm.partnerTOList}" var="partnerTO" varStatus="status">
			        <tr>
			            <!-- <td><input type="hidden" name="partnerTOList[${status.index}].id" value="${partnerTO.id}"/></td> --> 			           
			            <td>
			            	${partnerTO.id} ${partnerTO.login}<br>
			            </td>
			            <td>
			                <div>
				         	    <c:forEach items="${partnerTO.linkList}" var="link">
						    		<a href="${link.url}" onclick="form.submit();"> ${link.url}</a><br>
					    		</c:forEach>
					    	</div>
						</td>
			       </tr>
			    </c:forEach>
		    </tbody>
		</table>
</form:form> 

<!-- form to add products  -->
<form method="POST" action="/LakeshoreMarketConsumer/addProduct">
<h4>Add Product</h4>
  Please enter the product number:  <input type="text" name="productNumber" value="" /><br/>    
  Please enter the product description:  <input type="text" name="description" value="" /><br/>    
  Please enter the product partner:  <input type="text" name="partner" value="" /><br/>    
  Please enter the product price:  <input type="text" name="price" value="" /><br/>    
  
  <input type="submit" value="Add"/>  
 </form>

</body>
</html>