'use strict';
const { Command, flags } = require('@oclif/command');
const { DiraProjectClient } = require("dira-clients");
const logging = require("../../../logging");
const common = require("../../../common");
const io_utils = require("../../../io_utils");

const create_project_user_permission_questions = [
    {
        type: 'input',
        name: 'customerId',
        message: "Enter the user's id",
        validate: id => {
            const valid = id > 0;
            return valid ? true : "The user's id must be a positive number";
        }
    },
    {
        type: 'checkbox',
        name: 'permissions',
        message: 'Select permissions',
        choices: [
            { value: 'ADMIN' },
            { value: 'READ' },
            { value: 'WRITE' },
            { value: 'DELETE' }
        ],
        validate: perms => {
            const valid = perms.includes('ADMIN') && perms.length === 1;
            return valid ? true : "If you select ADMIN, you cannot select anything else";
        }
    }
];

class CreateProjectUserPermissionsCommand extends Command {
    async run() {
        const client = new DiraProjectClient();
        const { flags } = this.parse(CreateProjectUserPermissionsCommand);

        const projectId = flags['project-id'];

        var token = flags.login
            ? await common.prompt_login_and_get_token()
            : flags['auth-token'] || await common.try_get_auth_token_from_fs_or_prompt_for_login();

        client.set_authorization_token(token);

        var data;
        if (flags.data) {
            try {
                data = JSON.parse(flags.data);
            }
            catch {
                logging.fatal("The project data that you provided are an invalid JSON");
            }
        } else {
            data = await io_utils.get_answers_serialized(create_project_user_permission_questions);
        }

        client.create_project_user_permission(projectId, data)
            .then(permission => {
                logging.log();
                logging.info("Created project user permission succesfully");
                logging.log();
                console.table(permission);
            }).catch(err => {
                logging.error("Could not create project user permission");
                logging.info(`Reason: ${err.error.message}`);
            });
    }
}

CreateProjectUserPermissionsCommand.description = `Update project user permissions information

Required information when creating a project permission:
- customerId  (integer)
- permissions (list of distinct strings. Available Values: ADMIN, READ, WRITE, DELETE)

If those are not provided via the --data flag,
then the user will be automatically prompted
to enter this kind of infromation.
`;

CreateProjectUserPermissionsCommand.flags = {
    "auth-token": flags.string({ char: 't', description: 'The authentication token to use for user' }),
    login: flags.boolean({ char: 'l', description: 'Force login of user before sending the request' }),
    data: flags.string({ char: 'd', description: 'The data of the project user permission to update in JSON format' }),
    "project-id": flags.integer({ char: 'p', description: 'The id of project whose user permissions to update', required: true })
};

module.exports = CreateProjectUserPermissionsCommand;