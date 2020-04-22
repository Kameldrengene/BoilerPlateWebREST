package dal;

import services.SQLDatabaseIO;
import dto.UserDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class UserDAOSQL implements IUserDAO {
    SQLDatabaseIO db = new SQLDatabaseIO("kamel", "dreng", "runerne.dk", 8003); //Makes new SQLDatabaseIO object.

    @Override
    public UserDTO getUser(int userId) throws DALException {
        db.connect();
        ResultSet rs = db.query("SELECT * FROM userdto where userID=" + userId); //Select all columns from userdto where userID is input

        UserDTO user = new UserDTO();
        List<String> roles = new ArrayList<>();
        //Try to insert columns into userDTO object
        try {
            rs.next();
            user.setUserId(rs.getInt("userID"));
            user.setUserName(rs.getString("userName"));
            user.setIni(rs.getString("ini"));
            user.setCpr(rs.getString("cpr"));
            user.setPassword(rs.getString("password"));
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs_roles = db.query("SELECT * FROM userdto_roles where userID = " + userId + ";"); //Get roles from userID
        try {
            while (rs_roles.next()) {
                roles.add(rs_roles.getString("role_name")); //Insert roles into roles
            }
            rs_roles.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        user.setRoles(roles);

        db.close();
        return user;
    }

    @Override
    public List<UserDTO> getData() throws DALException { //We get a list of users here
        db.connect();
        ResultSet rs = db.query("SELECT * FROM userdto"); //Select all data from userdto


        List<UserDTO> userList = new ArrayList<>();
        try {
            //We do as in getUser, except we make new user until rs is empty
            while (rs.next()) {
                UserDTO user = new UserDTO();
                user.setUserId(rs.getInt("userID"));
                user.setUserName(rs.getString("userName"));
                user.setIni(rs.getString("ini"));
                user.setCpr(rs.getString("cpr"));
                user.setPassword(rs.getString("password"));
                List<String> roles = new ArrayList<>();
                ResultSet rs_roles = db.query("SELECT * FROM userdto_roles where userID = " + user.getUserId() + ";");
                try {
                    while (rs_roles.next()) {
                        roles.add(rs_roles.getString("role_name"));
                    }
                    rs_roles.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                user.setRoles(roles);
                userList.add(user);
            }
            rs.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        db.close();
        return userList;
    }

    @Override
    public void createUser(UserDTO user) throws DALException { //We make a new user
        db.connect();
        db.update("insert into userdto (userID, userName, ini, cpr, password) VALUE ('" + user.getUserId() + "','" + user.getUserName() + "','" + user.getIni() + "','" + user.getCpr() + "','" + user.getPassword() + "')");
        for(int i = 0; i < user.getRoles().size();i++){
            db.update("insert into userdto_roles (userID,role_name) VALUE('"+user.getUserId()+"','"+user.getRoles().get(i)+"');");
        }
        db.close();
    }

    @Override
    public void updateUser(UserDTO user) throws DALException { //We update user with a new user
        db.connect();
        try {
            ResultSet rs = db.query("SELECT * FROM userdto where userID=" + user.getUserId());
            rs.next();
            if (rs.getInt("userID") == user.getUserId()) {
                db.update("UPDATE userdto SET userID = '" + user.getUserId() + "' WHERE (userID = '" + user.getUserId() + "');");
                db.update("UPDATE userdto SET userName = '" + user.getUserName() + "' WHERE (userID = '" + user.getUserId() + "');");
                db.update("UPDATE userdto SET ini = '" + user.getIni() + "' WHERE (userID = '" + user.getUserId() + "');");
                db.update("UPDATE userdto SET cpr = '" + user.getCpr() + "' WHERE (userID = '" + user.getUserId() + "');");
                db.update("UPDATE userdto SET password = '" + user.getPassword() + "' WHERE (userID = '" + user.getUserId() + "');");
            }
            rs.close();
            db.update("delete from userdto_roles where userID="+user.getUserId());
            for(int i = 0; i < user.getRoles().size();i++){
                db.update("insert into userdto_roles (userID,role_name) VALUE('"+user.getUserId()+"','"+user.getRoles().get(i)+"');");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();

    }

    @Override
    public void deleteUser(int userId) throws DALException { //We delete user
        db.connect();
        db.update("delete from userdto where userID=" + userId);
        db.update("delete from userdto_roles where userID="+userId);
        db.close();
    }
}
