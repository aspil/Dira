'use strict';
const { Command, flags } = require('@oclif/command');
const { DiraProjectClient } = require("dira-clients");
const logging = require("../../../logging");
const common = require("../../../common");

class AddProjectUserCommand extends Command {
    async run() {
        const client = new DiraProjectClient();
        const { flags } = this.parse(AddProjectUserCommand);

        const projectId = flags['project-id'];

        var token = flags.login
            ? await common.prompt_login_and_get_token()
            : flags['auth-token'] || await common.try_get_auth_token_from_fs_or_prompt_for_login();

        client.set_authorization_token(token);

        client.add_user_to_project_with_id(projectId, flags.id)
            .then(() => {
                logging.info("User was added to project successfuly");
            }).catch(err => {
                logging.error("Couldn't add user to project");
                logging.info(`Reason: ${err.error.message}`);
            });
    }
}

AddProjectUserCommand.description = `Add a user to a project
...
`;

AddProjectUserCommand.flags = {
    "auth-token": flags.string({ char: 't', description: 'The authentication token to use for user' }),
    login: flags.boolean({ char: 'l', description: 'Force login of user before sending the request' }),
    "project-id": flags.integer({ char: 'p', description: 'The id of project where the user will be added', required: true }),
    id: flags.integer({ description: 'The id of the user to add in the project'})
};

module.exports = AddProjectUserCommand;