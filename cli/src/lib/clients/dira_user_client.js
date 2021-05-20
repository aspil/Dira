'use strict';
const dira_client = require("./dira_client");
const DiraClient = dira_client.DiraClient;
var parse_response = dira_client.parse_response;

class DiraUserClient extends DiraClient {
    constructor() {
        super();
        this.base_url = `${this.base_url}/users`;
    }

    get_all_users() {
        return this.http_client.get({
            url: this.base_url,
            headers: this.headers
        }).then(parse_response).catch(console.error);
    }

    delete_all_users() {
        return this.http_client.delete({
            url: this.base_url,
            headers: this.headers
        }).catch(console.error);
    }

    get_user_by_id(id) {
        return this.http_client.get({
            url: `${this.base_url}/${id}`,
            headers: this.headers,
        }).then(parse_response).catch(console.error);
    }

    delete_user_by_id(id) {
        return this.http_client.delete({
            url: `${this.base_url}/${id}`,
            headers: this.headers
        }).catch(console.error);
    }

    register_user(user) {
        return this.http_client.post({
            url: `${this.original_base_url}/register`,
            headers: this.headers,
            data: user
        }).then(parse_response).catch(console.error);
    }

    login_user(username, password) {
        return this.http_client.post({
            url: `${this.original_base_url}/login`,
            headers: this.headers,
            data: {
                username: username,
                password: password
            }
        }).then(resp => {
            return resp.get_headers().authorization;
        }).catch(console.error);
    }
}

module.exports = DiraUserClient;
