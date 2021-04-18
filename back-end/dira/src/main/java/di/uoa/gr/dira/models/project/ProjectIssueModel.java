package di.uoa.gr.dira.models.project;


import di.uoa.gr.dira.models.issue.IssueModel;

import java.util.Set;

public class ProjectIssueModel {
    private Long id;
    private String name;
    Set<IssueModel> issues;

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

    public Set<IssueModel> getIssues() {
        return issues;
    }

    public void setIssues(Set<IssueModel> issues) {
        this.issues = issues;
    }
}
