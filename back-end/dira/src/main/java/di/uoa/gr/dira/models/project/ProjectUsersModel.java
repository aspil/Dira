package di.uoa.gr.dira.models.project;

import di.uoa.gr.dira.models.customer.CustomerModel;

import java.util.List;

public class ProjectUsersModel {
    private Long id;
    private String key;
    private List<CustomerModel> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<CustomerModel> getUsers() {
        return users;
    }

    public void setUsers(List<CustomerModel> users) {
        this.users = users;
    }
}
