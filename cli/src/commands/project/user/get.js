'use strict';
const { Command, flags } = require('@oclif/command');
const { DiraProjectClient } = require("dira-clients");
const logging = require("../../../logging");
const common = require("../../../common");

class GetProjectUserCommand extends Command {
    async run() {
        const client = new DiraProjectClient();
        const { flags } = this.parse(GetProjectUserCommand);

        if (!flags.all && !flags.id) {
            logging.fatal("You must provide either --all or --id");
        }

        const projectId = flags['project-id'];

        var token = flags.login
            ? await common.prompt_login_and_get_token()
            : flags['auth-token'] || await common.try_get_auth_token_from_fs_or_prompt_for_login();

        client.set_authorization_token(token);

        if (flags.all) {
            const projectUsers = await client.get_all_users_in_project_by_id(projectId);
            if (projectUsers && projectUsers.users.length) {
                const data = projectUsers.users.map(user => Object.assign({
                    projectId: projectUsers.id,
                    projectKey: projectUsers.key
                }, user));

                console.table(data);
            } else {
                logging.info(`No users were found for project with id '${projectId}'`)
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
    all: flags.boolean({ char: 'a', description: 'Get all project users' }),
    "project-id": flags.integer({ char: 'p', description: 'The id of project whose users to retrieve', required: true })
};

module.exports = GetProjectUserCommand;