'use strict';

var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
const GET = "GET";
const POST = "POST";
const PUT = "PUT";
const DELETE = "DELETE";

function format_query_params(params) {
    if (params && typeof (params) === 'object') {
        params = Object.keys(params).map((key) => {
            return `${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`;
        }).join('&');
    }

    return params;
}

function prepare_body_data(data) {
    if (data && typeof (data) === 'object') {
        data = JSON.stringify(data);
    }

    return data;
}

function make_response_headers(request) {
    var headers = request.getAllResponseHeaders();
    var headersArray = headers.trim().split(/[\r\n]+/);

    var headerMap = {};
    headersArray.forEach(function (line) {
        var parts = line.split(': ');
        const header = parts.shift();
        const value = parts.join(': ');
        headerMap[header] = value;
    });

    return headerMap;
}

function make_request(options) {
    return new Promise((resolve, reject) => {
        var request = new XMLHttpRequest();
        var params = format_query_params(options.params);
        var url = params ? `${options.url}?${params}` : options.url;

        request.open(options.method, url);

        request.onload = () => {
            if (request.readyState === 4) {
                if (request.status >= 200 && request.status < 300) {
                    const headers = make_response_headers(request);
                    const response = new Response(headers, request.responseText);
                    resolve(response);
                } else {
                    reject({
                        status: request.status,
                        statusText: request.statusText
                    });
                }
            }
        };

        request.onerror = () => {
            reject({
                status: request.status,
                statusText: request.statusText
            });
        };

        if (options.headers) {
            Object.keys(options.headers).forEach(key => {
                request.setRequestHeader(key, options.headers[key]);
            });
        }

        if (options.data) {
            const data = prepare_body_data(options.data);
            request.send(data);
        } else {
            request.send();
        }
    });
}

class Response {
    constructor(headers, body) {
        this.headers = headers;
        this.body = body;
    }

    get_headers() {
        return this.headers;
    }

    get_body() {
        return this.body;
    }
}

class DiraHttpClient {
    constructor() { }

    get(options) {
        options.method = GET;
        return make_request(options);
    }

    post(options) {
        options.method = POST;
        return make_request(options);
    }

    put(options) {
        options.method = PUT;
        return make_request(options);
    }

    delete(options) {
        options.method = DELETE;
        return make_request(options);
    }
}

module.exports = { DiraHttpClient, Response };