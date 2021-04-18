package di.uoa.gr.dira.models.issue;


import java.util.Objects;

public class IssueModel {
    private Long id;
    private String key;
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        IssueModel that = (IssueModel) obj;
        return Objects.equals(id, that.id) && Objects.equals(key, that.key) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, key, name);
    }
}
