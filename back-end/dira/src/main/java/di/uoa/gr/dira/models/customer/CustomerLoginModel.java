package di.uoa.gr.dira.models.customer;

public class CustomerLoginModel {
    private String username;
    private String password;

    public CustomerLoginModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
