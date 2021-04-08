const { Command, flags } = require('@oclif/command');
var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

const API = {
    users: {
        get_all: "http://localhost:8080/user/all",
        get_by_id: "http://localhost:8080/user/",
        create: "http://localhost:8080/user/all",
    }
};

function get_all_users(callback) {
    var request = new XMLHttpRequest();
    request.open("GET", API.users.get_all, true);
    request.onload = function (e) {
        if (request.readyState == 4) {
            if (request.status == 200) {
                callback(request.responseText);
            } else {
                console.error(request.statusText);
            }
        }
    }

    request.send();
}

function get_user_by_id(id, callback) {
    var request = new XMLHttpRequest();
    const url = `${API.users.get_by_id}/${id}`;
    request.open("GET", url, true);
    request.onload = function (e) {
        if (request.readyState == 4) {
            if (request.status == 200) {
                callback(request.responseText);
            } else {
                console.error(request.statusText);
            }
        }
    }

    request.send();
}

class GetUserCommand extends Command {
    async run() {
        const { flags } = this.parse(GetUserCommand);
        if (flags.all) {
            get_all_users(res => {
                const users = JSON.parse(res);
                for (var user of users) {
                    this.log(user);
                }
            });
        } else {
            if (flags.key === "id") {
                get_user_by_id(flags.value, res => {
                    const user = JSON.parse(res);
                    this.log(JSON.stringify(user));
                });
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