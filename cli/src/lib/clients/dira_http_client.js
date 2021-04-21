'use stict';

var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
const GET = "GET";
const POST = "POST";

function make_request(options) {
    return new Promise((resolve, reject) => {
        var request = new XMLHttpRequest();
        request.open(options.method, options.url);

        request.onload = () => {
            if (request.readyState === 4) {
                if (request.status >= 200 && request.status < 300) {
                    resolve(request.responseText);
                } else {
                    reject({
                        status: this.status,
                        statusText: request.statusText
                    });
                }
            }
        };

        request.onerror = () => {
            reject({
                status: this.status,
                statusText: request.statusText
            });
        };

        if (options.headers) {
            Object.keys(options.headers).forEach(key => {
                request.setRequestHeader(key, options.headers[key]);
            });
        }

        var params = options.params;
        if (params && typeof params === 'object') {
            params = Object.keys(params).map(function (key) {
                return encodeURIComponent(key) + '=' + encodeURIComponent(params[key]);
            }).join('&');
        }

        request.send(params);
    });
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
}

module.exports = DiraHttpClient;
