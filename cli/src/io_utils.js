'use strict';
var inquirer = require("inquirer");
var config = require("./config");
var fs = require('fs');

function get_answers_serialized(questions) {
    return inquirer.prompt(questions)
        .then(answers => JSON.stringify(answers));
}

function get_answers(questions) {
    return inquirer.prompt(questions).then(answers => {
        const keys = Object.keys(answers);
        if (keys.length === 1) {
            return answers[keys[0]];
        }
    });
}

function save_auth_token(token) {
    fs.writeFile(config.authTokenLocation, token, () => {});
}

function get_auth_token_from_fs() {
    try {
        return fs.readFileSync(config.authTokenLocation, 'utf8');
    } catch (_) {
        return null;
    }
}



module.exports = { get_answers, get_answers_serialized, save_auth_token, get_auth_token_from_fs };