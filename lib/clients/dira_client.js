'use strict';
const DiraHttpClient = require("./dira_http_client");

class DiraClient {
    constructor(base_url) {
        base_url = base_url || "http://localhost:8080/dira";
        this.original_base_url = base_url;
        this.base_url = base_url;
        this.http_client = new DiraHttpClient();
        this.headers = {
            "Content-Type": "application/json"
        };
    }

    __handle_success(resp) {
        if (resp.body && resp.body.length) {
            return JSON.parse(resp.body);
        }
    }

    __handle_error(resp) {
        if (resp.body && resp.body.length) {
            resp.body = JSON.parse(resp.body);
        }
        return resp;
    }

    __send_request(options, method) {
        options.headers = this.headers;
        return method(options).catch(this.__handle_error);
    }

    set_authorization_token(token) {
        this.headers['Authorization'] = token;
    }

    get(options) {
        return this.__send_request(options, this.http_client.get);
    }

    post(options) {
        return this.__send_request(options, this.http_client.post);
    }

    put(options) {
        return this.__send_request(options, this.http_client.put);
    }

    delete(options) {
        return this.__send_request(options, this.http_client.delete);
    }
}

module.exports = DiraClient;
