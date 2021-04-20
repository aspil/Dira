package di.uoa.gr.dira.models.project;


import di.uoa.gr.dira.models.issue.IssueModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;

public class ProjectIssuesModel {
    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    List<IssueModel> issues;

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

    public List<IssueModel> getIssues() {
        return issues;
    }

    public void setIssues(List<IssueModel> issues) {
        this.issues = issues;
    }

}
