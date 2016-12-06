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

<h3>Customer Portal</h3>

<form action="/LakeshoreMarketConsumer/registerCustomer">  
    <input type="text" name="id" value="Customer Id..." onclick="this.value=''"/><br/>    
    <input type="text" name="login" value="Login..." onclick="this.value=''"/><br/>    
    <input type="password" name="password"  value="Password..." onclick="this.value=''"/><br/>  
    <input type="text" name="firstName" value="First Name..." onclick="this.value=''"/><br/>    
    <input type="text" name="lastName" value="Last Name..." onclick="this.value=''"/><br/>    
    <input type="text" name="streetAddress"  value="Street Address..." onclick="this.value=''"/><br/>  
    <input type="text" name="city"  value="City..." onclick="this.value=''"/><br/>  
    <input type="text" name="state"  value="State..." onclick="this.value=''"/><br/>  
    <input type="text" name="zip"  value="Zip code..." onclick="this.value=''"/><br/>  
    <input type="submit" value="register"/>  
</form>  

<br/>
<br/>

<form action="/LakeshoreMarketConsumer/login">  
    <input type="text" name="login" value="Name..." onclick="this.value=''"/><br/>    
    <input type="password" name="password"  value="Password..." onclick="this.value=''"/><br/>  
    <input type="submit" value="login"/>  
</form>  

<br/>
<br/>
<br/>
<br/>

<h3> Partner Portal </h3>

<form action="/LakeshoreMarketConsumer/partnerRegistration">  
    <input type="text" name="id" value="Partner Id..." onclick="this.value=''"/><br/>    
    <input type="text" name="login" value="Login..." onclick="this.value=''"/><br/>    
    <input type="password" name="password"  value="Password..." onclick="this.value=''"/><br/>  
    <input type="text" name="firstName" value="First Name..." onclick="this.value=''"/><br/>    
    <input type="text" name="lastName" value="Last Name..." onclick="this.value=''"/><br/>    
    <input type="text" name="streetAddress"  value="Street Address..." onclick="this.value=''"/><br/>  
    <input type="text" name="city"  value="City..." onclick="this.value=''"/><br/>  
    <input type="text" name="state"  value="State..." onclick="this.value=''"/><br/>  
    <input type="text" name="zip"  value="Zip code..." onclick="this.value=''"/><br/>  
    <input type="submit" value="register"/>  
</form>  

<br/>
<br/>

<form action="/LakeshoreMarketConsumer/partnerLogin">  
    <input type="text" name="login" value="Login..." onclick="this.value=''"/><br/>    
    <input type="password" name="password"  value="Password..." onclick="this.value=''"/><br/>  
    <input type="submit" value="login"/>  
</form> 

</body>
</html>