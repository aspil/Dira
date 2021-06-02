'use strict';
const { Command, flags } = require('@oclif/command');
const { DiraUserClient } = require("dira-clients");
const logging = require("../../logging");

class GetUserCommand extends Command {
    async run() {
        const client = new DiraUserClient();
        const { flags } = this.parse(GetUserCommand);

        if (!flags.all && !flags.id) {
            logging.fatal("You must provide either --all or --id");
        }

        if (flags.all) {
            client.get_all_users()
                .then(users => {
                    if (users && users.length) {
                        console.table(users);
                    } else {
                        logging.info("No users were found");
                    }
                }).catch(err => {
                    logging.error("Something went wrong");
                    logging.info(`Reason ${err.error.message}`);
                });
        } else if (flags.id) {
            client.get_user_by_id(flags.id)
                .then(console.table)
                .catch(err => logging.info(err.error.message));
        }
    }
}

GetUserCommand.description = `Retrieve user information
...
`;

GetUserCommand.flags = {
    all: flags.boolean({ char: 'a', description: 'Get all users [Default: true]' }),
    id: flags.integer({ description: 'Get user by id' }),
};

module.exports = GetUserCommand;