'use strict';
const DiraHttpClient = require("./dira_http_client");

function handle_response(resp) {
    if (resp.body && resp.body.length) {
        resp.body = JSON.parse(resp.body);
    }

    return resp;
}

function handle_success(resp) {
    resp = handle_response(resp);
    return resolve_successfully(resp);
}

function handle_error(resp) {
    resp = handle_response(resp);
    return resolve_erroneously(resp);
}

function resolve_successfully(resp) {
    return new Promise((resolve, _) => resolve(resp));
}

function resolve_erroneously(resp) {
    return new Promise((_, reject) => reject(resp));
}

class DiraClient {
    constructor(base_url) {
        base_url = base_url || process.env.DIRA_URL || process.env.REACT_APP_DIRA_URL || "https://localhost:8080/dira";
        this.original_base_url = base_url;
        this.base_url = base_url;
        this.http_client = new DiraHttpClient();
        this.headers = {
            "Content-Type": "application/json"
        };
    }

    __send_request_base(options, method, is_default) {
        is_default = is_default || false;
        options.headers = this.headers;

        var promise = method(options)
            .then(handle_success)
            .catch(handle_error);

        const on_success = is_default
            ? resp => resolve_successfully(resp)
            : resp => resolve_successfully(resp.body);

        const on_error = is_default
            ? resp => resolve_erroneously(resp)
            : resp => resolve_erroneously(resp.body);

        return promise.then(on_success).catch(on_error);
    }

    __send_request(options, method) {
        return this.__send_request_base(options, method);
    }

    __send_request_default(options, method) {
        return this.__send_request_base(options, method, true);
    }

    set_authorization_token(token) {
        this.headers['Authorization'] = token;
    }

    get_default(options) {
        return this.__send_request_default(options, this.http_client.get);
    }

    post_default(options) {
        return this.__send_request_default(options, this.http_client.post);
    }

    put_default(options) {
        return this.__send_request_default(options, this.http_client.put);
    }

    delete_default(options) {
        return this.__send_request_default(options, this.http_client.delete);
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

module.exports = { DiraClient, resolve_successfully, resolve_erroneously };
