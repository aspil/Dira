'use strict';

const { Command, flags } = require('@oclif/command');
const DiraUserClient = require("../../lib/clients/dira_client").DiraUserClient;
const client = new DiraUserClient();

class CreateUserCommand extends Command {
    async run() {
        const { flags } = this.parse(CreateUserCommand);
    }
}

CreateUserCommand.description = `Create new users
...
`;

CreateUserCommand.flags = {
};

module.exports = CreateUserCommand;
