package example.com.br.util;

import java.sql.*;

/**
 * @author matheus
 */
public class ConnectionFactory {
    private static final String DB_NAME = "dbProducts";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postdba";

    public static Connection getConnection() throws Exception {
        return getConnection(DB_NAME, DB_USER, DB_PASSWORD);
    }

    private static Connection getConnection(String dbName, String dbUser, String dbPassword) throws Exception {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(String.format("jdbc:postgresql://localhost:5432/%s", dbName), dbUser, dbPassword);
    }

    public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
        if (rs != null)
            rs.close();
        if (stmt != null)
            stmt.close();
        if (conn != null)
            conn.close();
    }

}
