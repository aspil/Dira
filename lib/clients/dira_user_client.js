'use strict';
const DiraClient = require("./dira_client");

class DiraUserClient extends DiraClient {
    constructor(base_url) {
        super(base_url);
        this.base_url = `${this.base_url}/users`;
    }

    get_all_users() {
        return this.get({ url: this.base_url })
            .then(this.__handle_success);
    }

    delete_all_users() {
        return this.delete({ url: this.base_url });
    }

    get_user_by_id(id) {
        return this.get({ url: `${this.base_url}/${id}` })
            .then(this.__handle_success);
    }

    delete_user_by_id(id) {
        return this.delete({ url: `${this.base_url}/${id}` });
    }

    register_user(user) {
        return this.post({ url: `${this.original_base_url}/register`, data: user })
            .then(this.__handle_success);
    }

    login_user(username, password) {
        return this.post({
            url: `${this.original_base_url}/login`,
            data: {
                username: username,
                password: password
            }
        }).then(resp => {
            var res = JSON.parse(resp.body);
            res.token = resp.headers['Authorization'];
        });
    }
}

module.exports = DiraUserClient;
