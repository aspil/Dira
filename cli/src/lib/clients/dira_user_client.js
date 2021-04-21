'use strict';
const DiraClient = require("./dira_client");

class DiraUserClient extends DiraClient {
    constructor() {
        super();
        this.base_url = `${this.base_url}/users`;
    }

    get_all_users() {
        return this.http_client.get({
            url: this.base_url,
            headers: this.headers
        }).then(JSON.parse).catch(console.error);
    }

    get_user_by_id(id) {
        return this.http_client.get({
            url: this.base_url,
            headers: this.headers,
        }).then(JSON.parse).catch(console.error);
    }

    create_user(user) {
        return this.http_client.post({
            url: `${super.base_url}/register`,
            headers: this.headers,
            data: user
        }).then(JSON.parse).catch(console.error);
    }
}

module.exports = DiraUserClient;
