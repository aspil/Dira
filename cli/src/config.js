'use strict';
const os = require('os');

const Config = {
    authTokenLocation: `${os.homedir()}/.dira_cli_auth_token`
};

module.exports = Config;