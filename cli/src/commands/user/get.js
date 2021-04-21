const { Command, flags } = require('@oclif/command');
const DiraUserClient = require("../../lib/clients/dira_client").DiraUserClient;
const client = new DiraUserClient();


class GetUserCommand extends Command {
    async run() {
        const { flags } = this.parse(GetUserCommand);
        if (flags.all) {
            const users = await client.get_all_users();
            for (var user of users) {
                this.log(user);
            }
        } else {
            if (flags.key === "id") {
                const user = await client.get_user_by_id(flags.value);
                this.log(user);
            } else {
                get_all_users(res => {
                    const users = JSON.parse(res);
                    for (var user of users) {
                        if (user[flags.key] && user[flags.key] === flags.value) {
                            this.log(user);
                            break;
                        }
                    }
                });
            }
        }
    }
}

GetUserCommand.description = `Get information on users
...
`;

GetUserCommand.flags = {
    all: flags.boolean({ char: 'a', description: 'Get all users' }),
    key: flags.string({ char: 'k', description: 'The data key to use in order to find the user' }),
    value: flags.string({ char: 'v', description: 'The data to match against the key' })
};

module.exports = GetUserCommand;