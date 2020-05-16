package net.simplifiedlearning.simplifiedcoding;
public class User {

    private static short idUser;
    private static String UserName, password;

    public User(short idUser, String UserName, String password) {
        this.idUser = idUser;
        this.UserName = UserName;
        this.password = password;
    }

    public static short getId(){
        return idUser;
    }

    public static String getUsername() {
        return UserName;
    }

    public static String getPassword() {
        return password;
    }
}