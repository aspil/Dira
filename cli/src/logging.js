'use strict';
const chalk = require('chalk');
const log = console.log;
const info_tag = chalk.bold.blue("[INFO]");
const error_tag = chalk.bold.red("[ERROR]");
const warn_tag = chalk.bold.yellow("[WARN]");

function info(msg) {
    log(`${info_tag} ${msg}`);
}

function error(msg) {
    log(`${error_tag} ${msg}`);
}

function warn(msg) {
    log(`${warn_tag} ${msg}`);
}

module.exports = { info, error, warn, log };