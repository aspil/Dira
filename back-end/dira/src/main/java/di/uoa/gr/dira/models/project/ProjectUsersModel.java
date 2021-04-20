package di.uoa.gr.dira.models.project;

import di.uoa.gr.dira.models.customer.CustomerModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ProjectUsersModel {
    @NotNull
    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
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
