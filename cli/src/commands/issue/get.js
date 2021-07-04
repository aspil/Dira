'use strict';
const { Command, flags } = require('@oclif/command');
const { DiraIssueClient } = require("dira-clients");
const logging = require("../../logging");
const common = require('../../common');

class GetIssueCommand extends Command {
    async run() {
        const { flags } = this.parse(GetIssueCommand);

        if (!flags.all && !flags.id) {
            logging.fatal("You must either provide --all or --id");
        }

        const token = await common.get_token(flags);
        const client = new DiraIssueClient(flags["project-id"]);
        client.set_authorization_token(token);

        if (flags.all) {
            client.get_all_issues()
                .then(projectIssues => {
                    const issues = projectIssues.issues;
                    if (issues && issues.length) {
                        for (const issue of issues) {
                            console.table(issue);
                        }
                    } else {
                        logging.info("No issues were found");
                    }
                }).catch(err => logging.response_error(err, "Could not retrieve issues"));
        } else {
            client.get_issue(flags.id)
                .then(logging.response_success)
                .catch(logging.response_error);
        }
    }
}

GetIssueCommand.description = `Retrieve issue information`;

GetIssueCommand.flags = {
    'project-id': flags.integer({description: "The project id whose issue(s) to retrieve", required: true }),
    all: flags.boolean({char: "a", description: "Retrieve all issues" }),
    id: flags.integer({description: "The id of the issue to retrieve" }),
    ...common.common_flags
};

module.exports = GetIssueCommand;
