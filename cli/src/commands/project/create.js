'use strict';
const { Command, flags } = require('@oclif/command');
const { DiraProjectClient } = require("dira-clients");
const common = require('../../common');
const logging = require("../../logging");
const io_utils = require('../../io_utils');

const create_project_questions = [
    {
        type: 'input',
        name: 'key',
        message: 'Enter a project key',
        validate: key => {
            const valid = key.length >= 1 && key.length <= 15;
            return valid ? true : "The project key must be 1 to 15 characters long";
        }
    },
    {
        type: 'input',
        name: 'name',
        message: 'Enter a project name',
        validate: name => {
            const valid = name.length >= 1 && name.length <= 255;
            return valid ? true : "The project name must be 1 to 255 characters long";
        }
    },
    {
        type: 'input',
        name: 'description',
        message: 'Enter a project description (optional, press Enter for empty)'
    },
    {
        type: 'list',
        name: 'visibility',
        message: 'Select a project visibility',
        choices: ['PUBLIC', 'PRIVATE']
    }
];

class CreateProjectCommand extends Command {
    async run() {
        const client = new DiraProjectClient();
        const { flags } = this.parse(CreateProjectCommand);

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
            data = await io_utils.get_answers_serialized(create_project_questions);
        }

        client.create_project(data)
            .then(project => {
                logging.log();
                logging.info("New project was created succesfully");
                logging.log();
                console.table(project);
            }).catch(err => {
                logging.error("Could not create new project");
                logging.info(`Reason: ${err.error.message}`);
            });
    }
}

CreateProjectCommand.description = `Create a new Project

Required information for registering a new user:
- key         (string)
- name        (string)
- description (string)
- visibility  (string) (Either PUBLIC or PRIVATE)

If those are not provided via the --data flag,
then the user will be automatically prompted
to enter this kind of infromation.
`;

CreateProjectCommand.flags = {
    "auth-token": flags.string({ char: 't', description: 'The authentication token to use for user' }),
    login: flags.boolean({ char: 'l', description: 'Force login of user before sending the request' }),
    data: flags.string({ char: 'd', description: 'The data of the project to create in JSON format' })
};

module.exports = CreateProjectCommand;