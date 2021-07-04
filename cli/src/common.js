'use strict';
const { DiraUserClient } = require("dira-clients");
const { flags } = require('@oclif/command');
const confirm = require('inquirer-confirm');
const io_utils = require("./io_utils");
const questions = require("./questions");
const { warn } = require("./logging");


async function prompt_login_and_get_token() {
    const user_client = new DiraUserClient();
    const username = await io_utils.get_answers(questions.username);
    const password = await io_utils.get_answers(questions.password);
    return await user_client.login_user(username, password)
        .then(user => {
            io_utils.save_auth_token(user.token);
            return user.token;
        }).catch(_err => null);
}

async function try_get_auth_token_from_fs_or_prompt_for_login() {
    var token = io_utils.get_auth_token_from_fs();
    if (!token) {
        warn("No authentication token was provided and none saved was found");

        const confirmed = await confirm("Do you want to login?").then(() => true, () => false);
        if (confirmed) {
            token = await prompt_login_and_get_token()
                .then(token => {
                    if (token) {
                        io_utils.save_auth_token(token);
                    }
                    return token;
                });
        }
    }

    return token;
}

const common_flags = {
    "auth-token": flags.string({ char: 't', description: 'The authentication token to use for user' }),
    login: flags.boolean({ char: 'l', description: 'Force login of user before sending the request' }),
};

async function get_token (parsed_flags) {
    return parsed_flags.login
        ? await prompt_login_and_get_token()
        : parsed_flags['auth-token'] || await try_get_auth_token_from_fs_or_prompt_for_login();
}


module.exports = {
    prompt_login_and_get_token,
    try_get_auth_token_from_fs_or_prompt_for_login,
    get_token,
    common_flags
};
