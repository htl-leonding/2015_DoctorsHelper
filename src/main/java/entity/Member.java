package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity
@NamedQueries({
        @NamedQuery(name = "GetUserByUsernameAndPassword", query = "select u from Member u where u.username=:USERNAME AND u.password=:PASSWORD"),
        @NamedQuery(name = "DeleteAllUsers", query = "delete from Member u"),
        @NamedQuery(name = "DeleteUser", query = "delete from Member u where u.username=:USERNAME"),
        @NamedQuery(name = "GetUserByUsername", query = "select u from Member u where u.username=:USERNAME"),
        @NamedQuery(name = "GetUsers", query = "select u from Member u"),
        @NamedQuery(name = "GetUsername", query = "select u.username from Member u")
})
public class Member implements Serializable {
    @Id
    String username;
    String password;

    public Member() {
    }

    public Member(String name, String password) {
        this.username = name;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return username;
    }
}
