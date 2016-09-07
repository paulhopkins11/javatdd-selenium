<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>

    <h2>Hello stranger, please enter your details</h2>

    <form action='EnrolServlet' method='get'>

        <table width='50%' bgColor='lemonchiffon' border='1'>
            <tr>
                <td>ID</td>      <td><input type='text' name='CustomerID' size='3'></td>
            </tr><tr>
                <td>Name</td>    <td><input type='text' name='Name'       size='20'></td>
            </tr><tr>
                <td>Address</td> <td><input type='text' name='Address'    size='50'></td>
        </table>

        <input type='submit' value='Submit'>

    </form><br><br>

    <hr>
    <i>This page was auto-generated at <%= new java.util.Date() %> </i>
    
    </body>
</html>
