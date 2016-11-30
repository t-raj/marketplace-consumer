<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
</head>
<body>
Welcome to the Lakeshore Market. Please register or login to continue:

<form action="/LakeshoreMarketConsumer/register">  
    <input type="text" name="uname" value="Name..." onclick="this.value=''"/><br/>    
    <input type="text" name="uemail"  value="Email ID..." onclick="this.value=''"/><br/>  
    <input type="password" name="upass"  value="Password..." onclick="this.value=''"/><br/>  
    <input type="submit" value="register"/>  
</form>  

<br/>
<br/>

<form action="/LakeshoreMarketConsumer/login">  
    <input type="text" name="uname" value="Name..." onclick="this.value=''"/><br/>    
    <input type="password" name="upass"  value="Password..." onclick="this.value=''"/><br/>  
    <input type="submit" value="login"/>  
</form>  

</body>
</html>