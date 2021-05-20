'use strict';
const { DiraHttpClient, Response } = require("./dira_http_client");

function parse_response(resp) {
    return JSON.parse(resp.get_body());
}

class DiraClient {
    constructor(base_url = "http://localhost:8080/dira") {
        this.original_base_url = base_url;
        this.base_url = base_url;
        this.http_client = new DiraHttpClient();
        this.headers = {
            "Content-Type": "application/json"
        };
    }
}

module.exports = { DiraClient, Response, parse_response };
