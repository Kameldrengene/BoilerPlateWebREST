package controller;

import dal.MySQLConnector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Path("test")
public class TestService {
    MySQLConnector conn = MySQLConnector.getInstance();

    @Path("hello")
    @GET
    public String getTest(){
        return "controller.Hello World";
    }

    @Path("json")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Hello getHelloJson(){
        return new Hello();
    }

    @Path("mysql")
    @GET
    public String getSQL() throws SQLException {
        Connection connection = conn.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SHOW DATABASES ");
        resultSet.next();
        return resultSet.getString(1);

    }


}
