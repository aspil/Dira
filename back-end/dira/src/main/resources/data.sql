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
    'admin',
    'Admin',
    'Mc Admin',
    'admin@otenet.gr',
    '$2a$10$btQaiUjTL6z03Gk4oYxwvu1RSJXgwc2AKOlqJBZaqihVzk8PjSnmy', -- @adm!n1A
    1
WHERE NOT EXISTS (
    SELECT 1
    FROM customer
    WHERE username = 'tester'
);
