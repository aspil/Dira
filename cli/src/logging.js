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

function fatal(msg) {
    error(msg);
    process.exit(1);
}

function response_error(err, msg) {
    var err_msg = err.error.message;

    if (msg) {
        error(msg);
        err_msg = `Reason: ${err_msg}`;
    }

    info(err_msg);
}

function response_success(data, msg) {
    log();
    if (msg) {
        info(msg);
        log();
    }
    console.table(data);
}

module.exports = { info, error, warn, log, fatal, response_error, response_success };
