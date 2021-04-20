package di.uoa.gr.dira.models.project;

import di.uoa.gr.dira.models.IModel;

public class ProjectModel implements IModel<Long> {
    private Long id;
    private String key;
    private String name;
    private String description;

    public ProjectModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
