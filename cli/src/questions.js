'use strict';

const questions = {
    username: {
        type: 'input',
        name: 'username',
        message: 'Enter a username',
    },
    password: {
        type: 'password',
        name: 'password',
        mask: '*',
        message: 'Enter a password',
        validate: (pass) => {
            return pass.length >= 8 ? true : "Your password must be at least 8 characters long";
        }
    }
};

module.exports = questions;