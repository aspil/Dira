'use strict';
const { Command, flags } = require('@oclif/command');
const { DiraSprintClient } = require("dira-clients");
const logging = require("../../logging");
const common = require('../../common');

class GetSprintCommand extends Command {
    async run() {
        const { flags } = this.parse(GetSprintCommand);

        if (!flags.all && !flags.id) {
            logging.fatal("You must either provide --all or --id");
        }

        const token = await common.get_token(flags);
        const client = new DiraSprintClient(flags["project-id"]);
        client.set_authorization_token(token);

        if (flags.all) {
            client.get_all_sprints()
                .then(projectSprints => {
                    const sprints = projectSprints.sprints;
                    if (sprints && sprints.length) {
                        for (const sprint of sprints) {
                            const sprint_issues = sprint.issueModels.map(issue => {
                                return {
                                    key: issue.key,
                                    title: issue.title,
                                    type: issue.type,
                                };
                            });
                            const data = {
                                id: sprint.id,
                                startDate: sprint.startDate,
                                dueDate: sprint.dueDate,
                                active: sprint.active,
                            };
                            console.table(data);
                            logging.info("Issues of sprint");
                            logging.log();
                            console.table(sprint_issues);
                        }
                    } else {
                        logging.info("No sprints were found");
                    }
                }).catch(err => logging.response_error(err, "Could not retrieve sprints"));
        } else {
            client.get_sprint_with_id(flags.id)
                .then(logging.response_success)
                .catch(logging.response_error);
        }
    }
}

GetSprintCommand.description = `Retrieve sprint information`;

GetSprintCommand.flags = {
    'project-id': flags.integer({description: "The project id whose sprint(s) to retrieve", required: true }),
    all: flags.boolean({char: "a", description: "Retrieve all sprints" }),
    id: flags.integer({description: "The id of the sprint to retrieve" }),
    ...common.common_flags
};

module.exports = GetSprintCommand;
