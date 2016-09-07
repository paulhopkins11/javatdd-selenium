<%@page import="java.util.*" %>

<br>

<h2>Products</h2>

<table width='50%' bgColor='lemonchiffon' border='1'>

<%
Vector<String> products = (Vector<String>)request.getAttribute("products");

int row = 0;
Enumeration e = products.elements();

while (e.hasMoreElements())
{
	String   productString = (String)e.nextElement();
	String[] productFields = parseProductString(productString);

%>

    <tr bgColor = <%= (row++ % 2 == 0) ? "lightblue" : "pink"%> >
        <td> <%= productFields[0] %> </td>
        <td> <%= productFields[1] %> </td>
        <td> <%= productFields[2] %> </td>
    </tr>
<%
}
%>

</table>


<%! 
private String[] parseProductString(String str)
{
    return str.split(",");
}
%>
