'use strict';
const { DiraClient } = require("./dira_client");

class DiraProjectClient extends DiraClient {
    constructor() {
        super();
        this.base_url = `${this.base_url}/projects`;
    }

    get_all_projects() {
        return this.get({
            url: this.base_url
        });
    }

    create_project(project) {
        return this.post({
            url: this.base_url,
            data: project
        });
    }

    get_project_by_id(id) {
        return this.get({
            url: `${this.base_url}/${id}`
        });
    }

    update_project_with_id(id, project) {
        return this.put({
            url: `${this.base_url}/${id}`,
            data: project
        });
    }

    delete_project_by_id(id) {
        return this.delete({
            url: `${this.base_url}/${id}`
        });
    }

    get_all_users_in_project_by_id(id) {
        return this.get({
            url: `${this.base_url}/${id}/users`
        });
    }

    add_user_to_project_with_id(project_id, user_id) {
        return this.post({
            url: `${this.base_url}/${project_id}/users/${user_id}`
        });
    }

    add_user_to_project_with_email(project_id, email) {
        return this.post({
	        url: `${this.base_url}/${project_id}/users`,
	        params: {
	            email: email
	        }
	    });
    }

    delete_user_from_project_with_id(project_id, user_id) {
        return this.delete({
            url: `${this.base_url}/${project_id}/users/${user_id}`
        });
    }

    get_project_permissions_for_all_users(id) {
        return this.get({
            url: `${this.base_url}/${id}/users/permissions`
        });
    }

    create_project_user_permission(project_id, permission) {
        return this.post({
            url: `${this.base_url}/${project_id}/users/permissions`,
            data: permission
        });
    }

    update_project_user_permission(project_id, permission_id, permission) {
        return this.put({
            url: `${this.base_url}/${project_id}/users/permissions/${permission_id}`,
            data: permission
        });
    }

    delete_project_user_permission(project_id, permission_id) {
        return this.delete({
            url: `${this.base_url}/${project_id}/users/permissions/${permission_id}`
        });
    }

    get_project_user_permission_by_id(project_id, permission_id) {
        return this.get({
            url: `${this.base_url}/${project_id}/users/permissions/${permission_id}`
        });
    }
}

module.exports = DiraProjectClient;
