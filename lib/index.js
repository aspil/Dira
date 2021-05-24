'use strict';
const DiraClient = require("./clients/dira_client").DiraClient;
const DiraUserClient = require("./clients/dira_user_client");
const DiraProjectClient = require("./clients/dira_project_client");

module.exports = { DiraClient, DiraUserClient, DiraProjectClient };