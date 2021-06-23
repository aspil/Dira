'use strict';
const { Command, flags } = require('@oclif/command');
const { DiraProjectClient } = require("dira-clients");
const logging = require("../../../logging");
const common = require("../../../common");

class GetProjectUserPermissionsCommand extends Command {
    async run() {
        const client = new DiraProjectClient();
        const { flags } = this.parse(GetProjectUserPermissionsCommand);

        if (!flags.all && !flags.id) {
            logging.fatal("You must provide either --all or --id");
        }

        const projectId = flags['project-id'];

        var token = flags.login
            ? await common.prompt_login_and_get_token()
            : flags['auth-token'] || await common.try_get_auth_token_from_fs_or_prompt_for_login();

        client.set_authorization_token(token);

        if (flags.all) {
            client.get_project_permissions_for_all_users(projectId)
                .then(console.table).catch(err => {
                    logging.error("Could not retrieve project user permissions");
                    logging.info(`Reason: ${err.error.message}`);
                });
        } else if (flags.id) {
            // TODO: Implement this once backend is ready for it!!!
        }
    }
}

GetProjectUserPermissionsCommand.description = `Retrieve project user permissions information
...
`;

GetProjectUserPermissionsCommand.flags = {
    "auth-token": flags.string({ char: 't', description: 'The authentication token to use for user' }),
    login: flags.boolean({ char: 'l', description: 'Force login of user before sending the request' }),
    all: flags.boolean({ char: 'a', description: 'Get all project user permissions' }),
    id: flags.integer({ description: 'Get the project user permission with the given id'}),
    "project-id": flags.integer({ char: 'p', description: 'The id of project whose user permissions to retrieve', required: true })
};

module.exports = GetProjectUserPermissionsCommand;