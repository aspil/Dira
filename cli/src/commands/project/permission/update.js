'use strict';
const { Command, flags } = require('@oclif/command');
const { DiraProjectClient } = require("dira-clients");
const logging = require("../../../logging");
const common = require("../../../common");
const io_utils = require("../../../io_utils");

const update_project_user_permission_questions = [
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
   }
];

class UpdateProjectUserPermissionsCommand extends Command {
    async run() {
        const client = new DiraProjectClient();
        const { flags } = this.parse(UpdateProjectUserPermissionsCommand);

        const projectId = flags['project-id'];

        var token = flags.login
            ? await common.prompt_login_and_get_token()
            : flags['auth-token'] || await common.try_get_auth_token_from_fs_or_prompt_for_login();

        client.set_authorization_token(token);

        const permission = await client.get_project_user_permission_by_id(projectId, flags.id)
            .catch(() => logging.fatal(`Could not retrieve permission with id '${flags.id}'`));

        var data;
        if (flags.data) {
            try {
                data = JSON.parse(flags.data);
            }
            catch {
                logging.fatal("The project data that you provided are an invalid JSON");
            }
        } else {
            for (var question of update_project_user_permission_questions) {
                question.default = permission[question.name];
            }

            data = await io_utils.get_answers(update_project_user_permission_questions);
            data = {
                id: flags.id,
                customerId: permission.customerId,
                permissions: data
            };
        }

        client.update_project_user_permission(projectId, flags.id, data)
            .then(perm => {
                logging.log();
                logging.info("Permission was updated succesfully");
                logging.log();
                console.table(perm);
            }).catch(err => {
                logging.error("Could not update permission");
                logging.info(`Reason: ${err.error.message}`);
            });
    }
}

UpdateProjectUserPermissionsCommand.description = `Update project user permissions information

Available information when updating a project permission:
- customerId  (integer)
- permissions (list of distinct strings. Available Values: ADMIN, READ, WRITE, DELETE)

If those are not provided via the --data flag,
then the user will be automatically prompted
to enter this kind of infromation.
`;

UpdateProjectUserPermissionsCommand.flags = {
    "auth-token": flags.string({ char: 't', description: 'The authentication token to use for user' }),
    login: flags.boolean({ char: 'l', description: 'Force login of user before sending the request' }),
    id: flags.integer({ description: 'Update the project user permission with the given id', required: true }),
    data: flags.string({ char: 'd', description: 'The data of the project user permission to update in JSON format' }),
    "project-id": flags.integer({ char: 'p', description: 'The id of project whose user permissions to update', required: true })
};

module.exports = UpdateProjectUserPermissionsCommand;
