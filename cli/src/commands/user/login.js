'use strict';
const { Command, flags } = require('@oclif/command');
const get_answers = require('../../io_utils').get_answers;
const DiraUserClient = require("../../lib/clients/dira_user_client");
var questions = require('../../questions');
const client = new DiraUserClient();

class LoginUserCommand extends Command {
    async run() {
        const { flags } = this.parse(LoginUserCommand);
        var username = flags.username || await get_answers(questions.username);
        var password = flags.password || await get_answers(questions.password);
        const jwtToken = await client.login_user(username, password);
        this.log(`Logged in successfully. JWT token ${jwtToken}`);
    }
}

LoginUserCommand.description = `Perform a user login
...
`;

LoginUserCommand.flags = {
    username: flags.string({char: 'u', description: 'The username of the user to login' }),
    password: flags.string({char: 'p', description: 'The password of the user to login' })
};

module.exports = LoginUserCommand; 