package di.uoa.gr.dira.entities;

import javax.persistence.*;

@Entity
public class User {
    @Id
    private String id;

    //    @ManyToOne
//    @JoinColumn(name = "subscription_plan_id")
//    private SubscriptionPlan subscriptionPlan;
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String salt;

    public User() {
    }

    public User(String name, String surname, String email, String username, String password) {
//        this.subscriptionPlan = subscriptionPlan;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.salt = null;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public SubscriptionPlan getSubscriptionPlan() {
//        return subscriptionPlan;
//    }
//
//    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
//        this.subscriptionPlan = subscriptionPlan;
//    }
}