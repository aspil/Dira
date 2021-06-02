'use strict';
const { Command, flags } = require('@oclif/command');
const { DiraUserClient } = require("dira-clients");
const logging = require('../../logging');
const io_utils = require('../../io_utils');
var questions = require('../../questions');

class LoginUserCommand extends Command {
    async run() {
        const client = new DiraUserClient();
        const { flags } = this.parse(LoginUserCommand);
        var username = flags.username || await io_utils.get_answers(questions.username);
        var password = flags.password || await io_utils.get_answers(questions.password);

        client.login_user(username, password)
            .then(user => {
                logging.log();
                logging.info("User logged in successfully");
                logging.info("Saving authentication token for future use");
                logging.log();

                io_utils.save_auth_token(user.token);

                delete user.token;
                console.table(user);
            }).catch(err => {
                logging.error("Could not log in user");
                logging.info(`Reason: ${err.error.message}`);
            });
    }
}

LoginUserCommand.description = `Perform a user login
...
`;

LoginUserCommand.flags = {
    username: flags.string({ char: 'u', description: 'The username of the user to login' }),
    password: flags.string({ char: 'p', description: 'The password of the user to login' })
};

module.exports = LoginUserCommand;