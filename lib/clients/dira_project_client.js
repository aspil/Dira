'use strict';
const dira_client = require("./dira_client");
const DiraClient = dira_client.DiraClient;
var parse_response = dira_client.parse_response;

class DiraProjectClient extends DiraClient {
    constructor(base_url) {
        super(base_url);
        this.base_url = `${this.base_url}/projects`;
    }

    set_authorization_token(token) {
        this.headers["Authorization"] = `Bearer ${token}`;
    }

    get_all_projects() {
        return this.http_client.get({
            url: this.base_url,
            headers: this.headers
        }).then(parse_response).catch(console.error);
    }

    create_project(project) {
        return this.http_client.post({
            url: this.base_url,
            headers: this.headers,
            data: project
        }).then(parse_response).catch(console.error);
    }

    get_project_by_id(id) {
        return this.http_client.get({
            url: `${this.base_url}/${id}`,
            headers: this.headers
        }).then(parse_response).catch(console.error);
    }

    update_project_with_id(id, project) {
        return this.http_client.put({
            url: `${this.base_url}/${id}`,
            headers: this.headers,
            data: project
        }).then(parse_response).catch(console.error);
    }

    delete_project_by_id(id) {
        return this.http_client.delete({
            url: `${this.base_url}/${id}`,
            headers: this.headers
        }).catch(console.error);
    }

    get_all_users_in_project_by_id(id) {
        return this.http_client.get({
            url: `${this.base_url}/${id}/users`,
            headers: this.headers
        }).then(parse_response).catch(console.error);
    }
}

module.exports = DiraProjectClient;