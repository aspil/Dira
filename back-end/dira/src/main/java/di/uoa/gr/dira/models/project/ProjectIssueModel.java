package di.uoa.gr.dira.models.project;


import di.uoa.gr.dira.models.issue.IssueModel;

import java.util.HashMap;

public class ProjectIssueModel {
    private Long id;
    private String name;
    HashMap<IssueModel, IssueModel> issues;

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

    public HashMap<IssueModel, IssueModel> getIssues() {
        return issues;
    }

    public void setIssues(HashMap<IssueModel, IssueModel> issues) {
        this.issues = issues;
    }

}
