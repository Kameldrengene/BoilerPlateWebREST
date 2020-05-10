package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
    private static MySQLConnector connector;
    private Connection connection;
    private String host = "runerne.dk";
    private String username = "kamel";
    private String password = "dreng";
    private String database = "databaseProjekt";
    private int port = 8003;

    private MySQLConnector() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?user="+username+"&password="+password+"&characterEncoding=latin1"+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
            System.out.println("MySQL Connection established");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static MySQLConnector getInstance() {
        if (connector == null){
            connector = new MySQLConnector();
        }
        return connector;
    }

    public Connection getConnection() {
        return connection;
    }
}
