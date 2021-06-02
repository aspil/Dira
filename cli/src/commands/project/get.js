'use strict';
const { Command, flags } = require('@oclif/command');
const { DiraProjectClient } = require("dira-clients");
const common = require('../../common');
const logging = require("../../logging");

class GetProjectCommand extends Command {
    async run() {
        const client = new DiraProjectClient();
        const { flags } = this.parse(GetProjectCommand);

        if (!flags.all && !flags.id) {
            logging.fatal("You must provide either --all or --id");
        }

        var token = flags.login
            ? await common.prompt_login_and_get_token()
            : flags['auth-token'] || await common.try_get_auth_token_from_fs_or_prompt_for_login();

        client.set_authorization_token(token);

        if (flags.all) {
            client.get_all_projects()
                .then(projects => {
                    if (projects && projects.length) {
                        console.table(projects);
                    } else {
                        logging.info("No projects were found");
                    }
                }).catch(err => {
                    logging.error("Something went wrong");
                    logging.info(`Reason: ${err.error.message}`);
                });
        } else if (flags.id) {
            client.get_project_by_id(flags.id)
                .then(console.table)
                .catch(err => logging.info(err.error.message));
        }
    }
}

GetProjectCommand.description = `Retrieve project information
...
`;

GetProjectCommand.flags = {
    "auth-token": flags.string({ char: 't', description: 'The authentication token to use for user' }),
    login: flags.boolean({ char: 'l', description: 'Force login of user before sending the request' }),
    all: flags.boolean({ char: 'a', description: 'Get all projects' }),
    id: flags.integer({ description: 'Get project by id' }),
};

module.exports = GetProjectCommand;