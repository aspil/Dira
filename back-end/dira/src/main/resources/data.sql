-- Insert subscription plan static data
insert into subscription_plan(subscription_plan_id, plan) values (1, 'STANDARD');
insert into subscription_plan(subscription_plan_id, plan) values (2, 'PREMIUM');


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