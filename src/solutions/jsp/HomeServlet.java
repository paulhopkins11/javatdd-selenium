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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HomeServlet", urlPatterns = { "/HomeServlet" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public HomeServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		boolean isCustomerKnown = false;
		Connection con = null;

		try {
			Cookie[] rd = request.getCookies();
			if (rd != null) {
				for (int i = 0; i < rd.length; ++i) {
					if (rd[i].getName().equals("CustomerID")) {
						String id = rd[i].getValue();
						String jdbcDriver = System.getProperty("jdbc.driver", "org.apache.derby.jdbc.ClientDriver");
						Class.forName(jdbcDriver);
						String jdbcUrl = System.getProperty("jdbc.url", "jdbc:derby:sample");
						con = DriverManager.getConnection(jdbcUrl);
						Statement stmt = con.createStatement();
						ResultSet rs = stmt
								.executeQuery("SELECT Name, Address FROM MySchema.Customers WHERE CustomerID=" + id);
						rs.next();
						out.println(
								"<h1>Welcome " + rs.getString("Name") + " from " + rs.getString("Address") + "</h1>");
						out.println("<a href=\'CatalogServlet\'>View catalog page.</a>");
						request.getSession().setAttribute("CustomerID", id);
						isCustomerKnown = true;
						break;
					}
				}
			}
		} catch (Exception var19) {
			out.println("<h1>Error: + " + var19.getMessage() + "</h1>");
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception var18) {
				;
			}

		}

		if (!isCustomerKnown) {
			RequestDispatcher var21 = this.getServletContext().getRequestDispatcher("/Login.jsp");
			var21.include(request, response);
		}

		out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
