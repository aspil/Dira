'use strict';
const { DiraClient } = require("./dira_client");

class DiraIssueClient extends DiraClient {
    constructor(base_url, project_id) {
        super(base_url);
        this.base_url = `${this.base_url}/projects/${project_id}/issues`;
    }

    get_all_issues() {
        return this.get({
            url: this.base_url
        });
    }

    create_issue(issue) {
        return this.post({
            url: this.base_url,
            data: issue
        });
    }

    get_issue(id) {
        return this.get({
            url: `${this.base_url}/${id}`
        });
    }

    update_issue(issue_id, issue) {
        return this.put({
            url: `${this.base_url}/${id}`,
            data: issue
        });
    }

    delete_issue(id) {
        return this.delete({
            url:`${this.base_url}/${id}`
        });
    }
}

module.exports = DiraIssueClient;