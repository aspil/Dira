'use strict';
const { DiraUserClient } = require("dira-clients");
const confirm = require('inquirer-confirm');
const io_utils = require("./io_utils");
const questions = require("./questions");
const { warn } = require("./logging");


async function prompt_login_and_get_token() {
    const user_client = new DiraUserClient();
    const username = await io_utils.get_answers(questions.username);
    const password = await io_utils.get_answers(questions.password);
    const user = await user_client.login_user(username, password);
    return user ? user.token : null;
}

async function try_get_auth_token_from_fs_or_prompt_for_login() {
    var token = io_utils.get_auth_token_from_fs();
    if (!token) {
        warn("No authentication token was provided and none saved was found");

        const confirmed = await confirm("Do you want to login?").then(() => true, () => false);
        if (confirmed) {
            token = await prompt_login_and_get_token();
            io_utils.save_auth_token(token);
        }
    }

    return token;
}


module.exports = { prompt_login_and_get_token, try_get_auth_token_from_fs_or_prompt_for_login };