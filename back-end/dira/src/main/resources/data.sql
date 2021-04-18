-- Insert subscription plan static data
insert into subscription_plan(subscription_plan_id, plan) values (1, 'STANDARD');
insert into subscription_plan(subscription_plan_id, plan) values (2, 'PREMIUM');

-- Insert issue type static data
insert into issue_type(issue_type_id, type) values(1, 'Epic');
insert into issue_type(issue_type_id, type) values(2, 'Story');
insert into issue_type(issue_type_id, type) values(3, 'Defect');