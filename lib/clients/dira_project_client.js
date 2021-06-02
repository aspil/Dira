'use strict';
const { DiraClient } = require("./dira_client");

class DiraProjectClient extends DiraClient {
    constructor(base_url) {
        super(base_url);
        this.base_url = `${this.base_url}/projects`;
    }

    get_all_projects() {
        return this.get({ url: this.base_url });
    }

    create_project(project) {
        return this.post({ url: this.base_url, data: project });
    }

    get_project_by_id(id) {
        return this.get({ url: `${this.base_url}/${id}` });
    }

    update_project_with_id(id, project) {
        return this.put({ url: `${this.base_url}/${id}`, data: project });
    }

    delete_project_by_id(id) {
        return this.delete({ url: `${this.base_url}/${id}` });
    }

    get_all_users_in_project_by_id(id) {
        return this.get({ url: `${this.base_url}/${id}/users` });
    }
}

module.exports = DiraProjectClient;