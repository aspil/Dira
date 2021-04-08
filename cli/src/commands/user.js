const { Command, flags } = require('@oclif/command');
var readline = require("readline");
var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

const API = {
  users: {
    get_all: "http://localhost:8080/user/all",
    create: "http://localhost:8080/user/all",
    delete: "http://localhost:8080/user/all",
  }
};


function ask(rl, questionText) {
  return new Promise((resolve, reject) => {
    rl.question(questionText, resolve);
  });
}

async function read_user_format(rl) {
  var form = {};
  form.username = await ask(rl, ">> Please enter username: ");
  form.name = await ask(rl, ">> Please enter name: ");
  form.surname = await ask(rl, ">> Please enter surname: ");
  form.email = await ask(rl, ">> Please enter email: ");
  form.password = await ask(rl, ">> Please enter password: ");
  return form;
}

function create_user(payload) {
  var request = new XMLHttpRequest();
  request.open("POST", API.users.create, true);
  request.onload = function (e) {
    if (request.readyState == 4) {
      if (request.status == 200) {
        const new_user = JSON.parse(request.responseText);
        console.log(`Created new user: ${JSON.stringify(new_user)}`);
      } else {
        console.error(request.statusText);
      }
    }
  }
  request.setRequestHeader("Content-Type", "application/json");

  request.onerror = function (e) {
    console.error(request.statusText);
  };

  request.send(payload);
}

function delete_user_by_id(id) {
  var request = new XMLHttpRequest();
  request.open("DELETE", `${API.users.delete}/${id}`, true);
  request.onload = function (e) {
    if (request.readyState == 4) {
      if (request.status == 200) {
      } else {
        console.error(request.statusText);
      }
    }
  }

  request.onerror = function (e) {
    console.error(request.statusText);
  };

  request.send();
}

class UserCommand extends Command {
  async run() {
    var rl = readline.createInterface({
      input: process.stdin,
      output: process.stdout
    });

    const { flags } = this.parse(UserCommand);
    if (flags.create) {
      const form = await read_user_format(rl);
      const payload = JSON.stringify(form);
      create_user(payload);
    } else if (flags.delete) {
      delete_user_by_id(1);
    }

    rl.close();
  }
}

UserCommand.description = `Perform user actions such as creating new users, etc.
...
`;

UserCommand.flags = {
  create: flags.boolean({ char: 'c', description: 'Create a new user' }),
  delete: flags.boolean({ char: 'd', description: 'Delete a user' }),
};


module.exports = UserCommand;