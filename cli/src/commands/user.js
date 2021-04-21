'use strict';
const { Command, flags } = require('@oclif/command');

function ask(rl, questionText) {
  return new Promise((resolve, reject) => {
    rl.question(questionText, resolve);
  });
}

class UserCommand extends Command {
  async run() {
    const { flags } = this.parse(UserCommand);
  }
}

UserCommand.description = `Perform user actions such as creating new users, etc.
...
`;

UserCommand.flags = {
  delete: flags.boolean({ char: 'd', description: 'Delete a user' }),
};


module.exports = UserCommand;