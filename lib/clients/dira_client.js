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
            console.log(resp.body);
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

    default_get(options) {
        return this.__send_request(options, this.http_client.get);
    }

    default_post(options) {
        return this.__send_request(options, this.http_client.post);
    }

    default_put(options) {
        return this.__send_request(options, this.http_client.put);
    }

    default_delete(options) {
        return this.__send_request(options, this.http_client.delete);
    }

    get(options) {
        return this.default_get(options).then(this.__handle_success);
    }

    post(options) {
        return this.default_post(options).then(this.__handle_success);
    }

    put(options) {
        return this.default_put(options).then(this.__handle_success);
    }

    delete(options) {
        return this.default_delete(options);
    }
}

module.exports = DiraClient;
