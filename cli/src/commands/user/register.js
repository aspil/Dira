'use strict';

const { Command, flags } = require('@oclif/command');
const { DiraUserClient } = require("dira-clients");
const email_validator = require("email-validator");
const io_utils = require('../../io_utils');
const questions = require('../../questions');
const logging = require("../../logging");

const register_user_questions = [
    questions.username,
    questions.password,
    {
        type: 'input',
        name: 'name',
        message: 'Enter your first name'
    },
    {
        type: 'input',
        name: 'surname',
        message: 'Enter your last name'
    },
    {
        type: 'input',
        name: 'email',
        message: 'Enter your email',
        validate: (email) => {
            return email_validator.validate(email) ? true : "Please enter a valid email";
        }
    },
    {
        type: 'list',
        name: 'subscriptionPlan',
        message: 'Select a subscription plan',
        choices: ['STANDARD', 'PREMIUM']
    }
];

class RegisterUserCommand extends Command {
    async run() {
        const client = new DiraUserClient();
        const { flags } = this.parse(RegisterUserCommand);

        if (flags.data) {
            var data = null;
            try {
                data = JSON.parse(flags.data);
            }
            catch {
                logging.fatal("The user data that you provided are an invalid JSON");
            }
        } else {
            data = await io_utils.get_answers_serialized(register_user_questions);
        }

        const user = await client.register_user(data);
        if (user) {
            logging.log();
            logging.info("New user was created succesfully");
            logging.log();
            console.table(user);
        }
    }
}

RegisterUserCommand.description = `Register new users

Required information for registering a new user:
- username          (string)
- name              (string)
- surname           (string)
- email             (string)
- password          (string)
- subscription_plan (string) (Either STANDARD or PREMIUM)

If those are not provided via the --data flag,
then the user will be automatically prompted
to enter this kind of infromation.
`;

RegisterUserCommand.flags = {
    data: flags.string({ char: 'd', description: 'The data of the user to register in JSON format' })
}

module.exports = RegisterUserCommand;
