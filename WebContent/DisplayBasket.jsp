<%@ page import="java.util.*"%>

<h2>Current contents of basket:</h2>

<table width='50%' bgColor='lemonchiffon' border='1'>

<%
Hashtable<Integer,Integer> basket = (Hashtable<Integer,Integer>)session.getAttribute("Basket");

Enumeration productIDs = basket.keys();

int row = 0;
while (productIDs.hasMoreElements())
{
	Object productID = productIDs.nextElement();
	Object quantity  = basket.get(productID);
%>

	<tr bgColor= <%= (row++ % 2 == 0) ? "white" : "lemonchiffon" %> >
		<td>Product ID: <%= productID %> </td>
		<td>Quantity:   <%= quantity %>  </td>
 	</tr>
<%
}
%>

</table>