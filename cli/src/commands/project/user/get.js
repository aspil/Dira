'use strict';
const { Command, flags } = require('@oclif/command');
const { DiraProjectClient } = require("dira-clients");
const confirm = require('inquirer-confirm');
const io_utils = require("../../../io_utils");
const questions = require('../../../questions');

async function login_and_get_token() {
    const user_client = new DiraUserClient();
    const username = await io_utils.get_answers(questions.username);
    const password = await io_utils.get_answers(questions.password);
    return await user_client.login_user(username, password);
}

class GetProjectUserCommand extends Command {
    async run() {
        const client = new DiraProjectClient();
        const { flags } = this.parse(GetProjectUserCommand);

        const projectId = flags['project-id'];
        var token;

        if (flags.login) {
            token = await login_and_get_token();
        } else {
            var token = flags['auth-token'] || io_utils.get_auth_token_from_fs();
            if (!token) {
                this.log("No authentication token was provided and none saved was found");

                const confirmed = await confirm("Do you want to login?").then(() => true, () => false);
                if (!confirmed) {
                    return;
                }

                token = await login_and_get_token();

                // TODO: Save token per user
                io_utils.save_auth_token(token);
            }
        }

        client.set_authorization_token(token);

        if (flags.all) {
            const projectUsers = await client.get_all_users_in_project_by_id(projectId);

            this.log(`Project: ${projectUsers.key}`);

            for (var user of projectUsers.users) {
                this.log(user);
            }
        }
    }
}

GetProjectUserCommand.description = `Retrieve project user information
...
`;

GetProjectUserCommand.flags = {
    "auth-token": flags.string({ char: 't', description: 'The authentication token to use for user' }),
    login: flags.boolean({ char: 'l', description: 'Force login of user before sending the request' }),
    all: flags.boolean({ char: 'a', description: 'Get all project users', default: true }),
    "project-id": flags.integer({ char: 'p', description: 'The id of project whose users to retrieve', required: true })
};

module.exports = GetProjectUserCommand;