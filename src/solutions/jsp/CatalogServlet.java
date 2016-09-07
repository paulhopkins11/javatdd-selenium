//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package solutions.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CatalogServlet", urlPatterns = { "/CatalogServlet" })
public class CatalogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CatalogServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		this.displayProducts(request, response);
		this.displayTextFields(request, response);
		if (request.getParameter("Add") != null) {
			this.addToBasket(request);
		} else if (request.getParameter("Empty") != null) {
			this.emptyBasket(request);
		} else if (request.getParameter("Checkout") != null) {
			out.println(this.checkoutBasket(request));
		}

		this.displayBasket(request, response);
		out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	private void displayProducts(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		Vector products = new Vector();

		try {
			String jdbcDriver = System.getProperty("jdbc.driver", "org.apache.derby.jdbc.ClientDriver");
			Class.forName(jdbcDriver);
			String jdbcUrl = System.getProperty("jdbc.url", "jdbc:derby:sample");
			con = DriverManager.getConnection(jdbcUrl);
			Statement rd = con.createStatement();
			ResultSet rs = rd.executeQuery("SELECT * FROM MySchema.Products");

			while (rs.next()) {
				String productID = rs.getString("ProductID");
				String name = rs.getString("Name");
				double unitPrice = rs.getDouble("UnitPrice");
				products.add(productID + "," + name + "," + unitPrice);
			}

			rs.close();
		} catch (Exception var19) {
			;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception var18) {
				;
			}

		}

		request.setAttribute("products", products);
		RequestDispatcher rd1 = this.getServletContext().getRequestDispatcher("/DisplayProductTable.jsp");
		rd1.include(request, response);
	}

	private String formatProducts() {
		String output = "<br><h2>Products</h2><table width=\'50%\' bgColor=\'lemonchiffon\' border=\'1\'>";
		Connection con = null;

		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			con = DriverManager.getConnection("jdbc:derby://localhost:1527/C:/JavaTdd/Databases/OnlineRetailerDb");
			Statement ex = con.createStatement();
			ResultSet rs = ex.executeQuery("SELECT * FROM MySchema.Products");

			for (int row = 0; rs.next(); output = output + "<td>" + rs.getString("ProductID") + "</td>" + "<td>"
					+ rs.getString("Name") + "</td>" + "<td>" + rs.getDouble("UnitPrice") + "</td>" + "</tr>") {
				if (row++ % 2 == 0) {
					output = output + "<tr bgcolor=\'lightblue\'>";
				} else {
					output = output + "<tr bgcolor=\'lightpink\'>";
				}
			}

			output = output + "</table>";
			rs.close();
		} catch (Exception var14) {
			output = "<h1>Error gathering product info: " + var14.getMessage() + "</h1>";
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception var13) {
				;
			}

		}

		return output;
	}

	private void displayTextFields(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/CatalogOptions.html");
		rd.include(request, response);
	}

	private void addToBasket(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Hashtable basket = (Hashtable) session.getAttribute("Basket");
		if (basket == null) {
			basket = new Hashtable();
			session.setAttribute("Basket", basket);
		}

		Integer productID = new Integer(request.getParameter("ProductID"));
		Integer quantity = new Integer(request.getParameter("Quantity"));
		basket.put(productID, quantity);
	}

	private void emptyBasket(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Hashtable basket = (Hashtable) session.getAttribute("Basket");
		if (basket != null) {
			basket.clear();
		}

	}

	private String checkoutBasket(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Hashtable basket = (Hashtable) session.getAttribute("Basket");
		String output = "";
		Connection con = null;
		if (basket != null) {
			try {
				Class.forName("org.apache.derby.jdbc.ClientDriver");
				con = DriverManager.getConnection("jdbc:derby://localhost:1527/C:/JavaTdd/Databases/OnlineRetailerDb");
				Statement ex = con.createStatement();
				String customerID = (String) session.getAttribute("CustomerID");
				Enumeration productIDs = basket.keys();

				while (productIDs.hasMoreElements()) {
					Object productID = productIDs.nextElement();
					Object quantity = basket.get(productID);
					ex.executeUpdate("INSERT INTO MySchema.Orders VALUES(" + productID + ", " + customerID + ", "
							+ quantity + ")");
				}

				basket.clear();
				output = "<h1>Your order has been succesfully recorded.</h1>";
			} catch (Exception var19) {
				output = "<h1>Error gathering product info: " + var19.getMessage() + "</h1>";
			} finally {
				try {
					if (con != null) {
						con.close();
					}
				} catch (Exception var18) {
					;
				}

			}
		}

		return output;
	}

	private void displayBasket(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Hashtable basket = (Hashtable) session.getAttribute("Basket");
		if (basket == null) {
			basket = new Hashtable();
			session.setAttribute("Basket", basket);
		}

		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/DisplayBasket.jsp");
		rd.include(request, response);
	}
}
