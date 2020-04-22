import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Path("live")
public class WebService {
    MySQLConnector conn = MySQLConnector.getInstance();

    @Path("hello")
    @GET
    public String getTest(){
        return "Hello World";
    }

    @Path("json")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Hello getHelloJson(){
        return new Hello();
    }

    @Path("mysql_databases_nr")
    @GET
    public String getNrOfDatabases() throws SQLException {
        Connection connection = conn.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SHOW DATABASES ");
        int size = 0;
        while(resultSet.next()){
            size++;
        }
        return Integer.toString(size);

    }

    @Path("mysql_databases/{i}")
    @GET
    public String getDatabases(@PathParam("i") int i) throws SQLException {
        Connection connection = conn.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SHOW DATABASES ");
        int j = i;
        while(resultSet.next() && j != 0){
            j--;
        }
        return resultSet.getString(1);

    }

}
