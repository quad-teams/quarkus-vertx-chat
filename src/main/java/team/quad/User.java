package team.quad;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class User {

    @Email
    private String username;

    @NotEmpty
    private String alias;

    public User() {
    }

    public User(String username, String alias) {
        this.username = username;
        this.alias = alias;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        return alias != null ? alias.equals(user.alias) : user.alias == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}
