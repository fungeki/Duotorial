package productions.ranuskin.meow.duotorial;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Ran on 4/19/2018.
 */

public class User {

    private String userName;
    private String email;
    private int duotorialAmount;
    private String lastLogin;

    public User() {
    }

    public User(String userName, String email, int duotorialAmount, String lastLogin) {

        this.userName = userName;
        this.email = email;
        this.duotorialAmount = duotorialAmount;
        this.lastLogin = lastLogin;
    }


    @Override
    public String toString() {
        return "User{" +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", duotorialAmount=" + duotorialAmount +
                ", lastLogin=" + lastLogin +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDuotorialAmount() {
        return duotorialAmount;
    }

    public void setDuotorialAmount(int duotorialAmount) {
        this.duotorialAmount = duotorialAmount;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
