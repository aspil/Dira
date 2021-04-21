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

module.exports = DiraClient;
