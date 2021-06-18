'use strict';
const { DiraClient, resolve_successfully, resolve_erroneously} = require("./dira_client");

class DiraUserClient extends DiraClient {
    constructor() {
        super();
        this.base_url = `${this.base_url}/users`;
    }

    get_all_users() {
        return this.get({
            url: this.base_url
        });
    }

    delete_all_users() {
        return this.delete({
            url: this.base_url
        });
    }

    get_user_by_id(id) {
        return this.get({
            url: `${this.base_url}/${id}`
        });
    }

    delete_user_by_id(id) {
        return this.delete({
            url: `${this.base_url}/${id}`
        });
    }

    register_user(user) {
        return this.post({
            url: `${this.original_base_url}/register`,
            data: user
        });
    }

    login_user(username, password) {
        return this.post_default({
            url: `${this.original_base_url}/login`,
            data: {
                username: username,
                password: password
            }
        }).then(resp => {
            var res = resp.body;
            res.token = resp.headers.authorization;
            return resolve_successfully(res);
        }).catch(err => resolve_erroneously(err.body));
    }

    get_user_projects(user_id) {
        return this.get({ url: `${this.base_url}/${user_id}/projects` });
    }
}

module.exports = DiraUserClient;
