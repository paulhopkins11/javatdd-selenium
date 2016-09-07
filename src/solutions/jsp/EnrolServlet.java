//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package solutions.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "EnrolServlet", urlPatterns = { "/EnrolServlet" })
public class EnrolServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EnrolServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Connection con = null;

		try {
			String ex = request.getParameter("CustomerID");
			String name = request.getParameter("Name");
			String address = request.getParameter("Address");
			String jdbcDriver = System.getProperty("jdbc.driver", "org.apache.derby.jdbc.ClientDriver");
			Class.forName(jdbcDriver);
			String jdbcUrl = System.getProperty("jdbc.url", "jdbc:derby:sample");
			con = DriverManager.getConnection(jdbcUrl);
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO MySchema.Customers VALUES (" + ex + ",  " + "\'" + name + "\', " + "\'"
					+ address + "\')");
			request.getSession().setAttribute("CustomerID", ex);
			Cookie cookie = new Cookie("CustomerID", ex);
			cookie.setMaxAge(2419200);
			response.addCookie(cookie);
			out.println("<h1>Successfully enrolled!</h1>");
			out.println("<a href=\'CatalogServlet\'>View catalog page.</a>");
		} catch (Exception var18) {
			out.println("<h1>Error: " + var18.getMessage() + "</h1>");
			out.println("<a href=\'HomeServlet\'>Re-enter data.</a>");
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception var17) {
				;
			}

		}

		out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
