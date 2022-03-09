package Models;

/**
 * Model for Users
 */
public class User {

    private static Integer userId;
    private static String userName, password;

    public User(){}

    public User(Integer userId, String userName, String password){
        User.userId = userId;
        User.userName = userName;
        User.password = password;
    }

    //For populating Dropdowns
    public User(Integer userId){
        User.userId = userId;
    }

    //getters
    public static Integer getUserId(){
        return userId;
    }
    public static String getUserName(){
        return userName;
    }
    public static String getPassword(){
        return password;
    }

    //setters
    public static void setUserId(Integer userId){
        User.userId = userId;
    }
    public static void setUserName(String userName){
        User.userName = userName;
    }
    public static void setPassword(String password) { User.password = password; }

    @Override
    public String toString() {return userId.toString();}

}
