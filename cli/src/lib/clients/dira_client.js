'use strict';
const DiraHttpClient = require("./dira_http_client");

class DiraClient {
    constructor(base_url = "http://localhost:8080") {
        this.base_url = base_url;
        this.http_client = new DiraHttpClient();
        this.headers = {
            "Content-Type": "application/json"
        };
    }
}

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
}

module.exports = { DiraClient, DiraUserClient };
