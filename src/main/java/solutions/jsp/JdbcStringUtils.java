package solutions.jsp;

public class JdbcStringUtils {

	public static String getJdbcUrl() {
		String jdbcUrl = System.getProperty("jdbc.url", "jdbc:derby:sample;create=true");
		return jdbcUrl;
	}

	public static String getJdbcDriverClass() {
		// String jdbcDriver = System.getProperty("jdbc.driver",
		// "org.apache.derby.jdbc.ClientDriver");
		String jdbcDriver = System.getProperty("jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver");
		return jdbcDriver;
	}
}
