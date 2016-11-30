<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>
<body>
Login/Register:

Search products:

<!-- form to get products -->

<form action="searchProduct">
	
</form>

<!-- dynamic links -->

<table class="tablesorter">
		
			<thead class="thfloat textbold">
			    <tr>
			    	<th></th>
			    	<th></th>			
			    </tr>
		    </thead>
			<tbody class="texttd">
			    <c:forEach items="${questionForm.questionTOs}" var="questionTO" varStatus="status">
			        <tr>
			            <td><input type="hidden" name="questionTOs[${status.index}].questionId" value="${questionTO.questionId}"/></td> 			           
			            <td>
			            	${questionTO.questionId}) ${questionTO.question}<br>
			            	<div>
				            	<c:forEach items="${questionTO.answerTOs}" var="answerTO">
									<input type="radio" name="questionTOs[${status.index}].selectedAnswer" value="${answerTO.answerId}"> ${answerTO.answer}<br>
								</c:forEach>
							</div>
							<br>
							<div>
								<input type="radio" name="questionTOs[${status.index}].selectedWeight" value="0" checked> Not Important<br>
								<input type="radio" name="questionTOs[${status.index}].selectedWeight" value="1" checked> Very Important<br>
							</div>	
			            </td>
			       </tr>
			    </c:forEach>
		    </tbody>
		</table>
</body>
</html>