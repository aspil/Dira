'use strict';
const DiraClient = require("./dira_client");

class DiraProjectClient extends DiraClient {
    constructor(base_url) {
        super(base_url);
        this.base_url = `${this.base_url}/projects`;
    }

    get_all_projects() {
        return this.get({ url: this.base_url })
            .then(this.__handle_success);
    }

    create_project(project) {
        return this.post({ url: this.base_url, data: project })
            .then(this.__handle_success);
    }

    get_project_by_id(id) {
        return this.get({ url: `${this.base_url}/${id}` })
            .then(this.__handle_success);
    }

    update_project_with_id(id, project) {
        return this.put({ url: `${this.base_url}/${id}`, data: project })
            .then(this.__handle_success);
    }

    delete_project_by_id(id) {
        return this.delete({ url: `${this.base_url}/${id}` })
    }

    get_all_users_in_project_by_id(id) {
        return this.get({ url: `${this.base_url}/${id}/users` })
            .then(this.__handle_success);
    }
}

module.exports = DiraProjectClient;