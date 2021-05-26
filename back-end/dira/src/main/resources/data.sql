-- Insert subscription plan static data
insert into subscription_plan(subscription_plan_id, plan) values (1, 'STANDARD');
insert into subscription_plan(subscription_plan_id, plan) values (2, 'PREMIUM');

-- Insert issue type static data
insert into issue_type(issue_type_id, type) values(1, 'Epic');
insert into issue_type(issue_type_id, type) values(2, 'Story');
insert into issue_type(issue_type_id, type) values(3, 'Defect');

-- Insert customer static data
insert into customer(
    username,
    name,
    surname,
    email,
    password,
    subscription_plan_id
) values (
    'tester',
    'Tester',
    'Mc Tester',
    'test@otenet.gr',
    '$2a$10$bb/RXy.KPolaCeW14ADg0eC9.kchxyHCu5T5m.C1GBHkA.vdK5ST.', -- 12345678
    1
);