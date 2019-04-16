INSERT INTO person (
    first_name,
    last_name,
    email_address,
    street_address,
    city,
    state,
    zip_code
) VALUES (
    'John',
    'Smith',
    'fake1@aquent.com',
    '123 Any St.',
    'Asheville',
    'NC',
    '28801'
), (
    'Jane',
    'Smith',
    'fake2@aquent.com',
    '123 Any St.',
    'Asheville',
    'NC',
    '28801'
);

INSERT INTO client (
    name,
    website_uri,
    phone_number,
    street_address,
    city,
    state,
    zip_code
) VALUES (
    'Amazon',
    'a.co',
    '8281231234',
    '123 Fake St',
    'Asheville',
    'NC',
    '28803'
), (
    'UPS',
    'ups.cmo',
    '7049879876',
    '11 Short Ave',
    'New York',
    'NY',
    '10001'
);

INSERT INTO contact (
    person_id,
    client_id
) VALUES (
    1,
    (SELECT client_id FROM client LIMIT 1)
);