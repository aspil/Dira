'use strict';
var inquirer = require("inquirer");

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

module.exports = { get_answers, get_answers_serialized };