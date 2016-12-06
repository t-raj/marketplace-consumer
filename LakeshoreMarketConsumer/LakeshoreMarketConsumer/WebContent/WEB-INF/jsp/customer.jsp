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
 <br/>
 
 <a href="/LakeshoreMarketConsumer" >Logout</a>
 <br/>

<!-- customer info with links to actions --> 			
<form:form method="POST" action="" modelAttribute="customerForm" id="customerActionForm">
<table class="tablesorter">
			<thead class="thfloat textbold">
				<tr>
				   <th>Customer Information</th>
				</tr>
			    <tr>
			    	<th>Id</th>
			    	<th>Login</th>
			    	<th>Actions</th>			
			    </tr>
		    </thead>
			<tbody class="texttd">
			    <c:forEach items="${customerForm.customerTOList}" var="customerTO" varStatus="status">
			        <tr>
			        	<td>
			        	   	${customerTO.id} <br/>
			        	</td>
			            <td>
							${customerTO.login} <br/>
			            </td>
			            <td>
			                <div>
				         	    <c:forEach items="${customerTO.linkList}" var="link" varStatus="status">
				         	    	<!-- clicking on the link goes to the relative path -->
					    			<!-- <a href="${link.rel}" onclick="submitForm()"> ${link.action}</a><br>-->
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