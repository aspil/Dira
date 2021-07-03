-- Insert subscription plan static data
INSERT INTO subscription_plan(
    subscription_plan_id,
    plan
)
SELECT 1, 'STANDARD'
WHERE NOT EXISTS (
    SELECT 1
    FROM subscription_plan
    WHERE plan = 'STANDARD'
);

INSERT INTO subscription_plan(
    subscription_plan_id,
    plan
)
SELECT 2, 'PREMIUM'
WHERE NOT EXISTS (
    SELECT 1
    FROM subscription_plan
    WHERE plan = 'PREMIUM'
);

-- Insert customer static data
INSERT INTO customer(
    username,
    name,
    surname,
    email,
    password,
    subscription_plan_id
)
SELECT
    'tester',
    'Tester',
    'Mc Tester',
    'test@otenet.gr',
    '$2a$10$bb/RXy.KPolaCeW14ADg0eC9.kchxyHCu5T5m.C1GBHkA.vdK5ST.', -- 12345678
    1
WHERE NOT EXISTS (
    SELECT 1
    FROM customer
    WHERE username = 'tester'
);

INSERT INTO customer(
    username,
    name,
    surname,
    email,
    password,
    subscription_plan_id
)
SELECT
    'tester2',
    'Tester2',
    'Mc Tester Cousin',
    'test2@otenet.gr',
    '$2a$10$bb/RXy.KPolaCeW14ADg0eC9.kchxyHCu5T5m.C1GBHkA.vdK5ST.', -- 12345678
    1
    WHERE NOT EXISTS (
    SELECT 1
    FROM customer
    WHERE username = 'tester2'
);