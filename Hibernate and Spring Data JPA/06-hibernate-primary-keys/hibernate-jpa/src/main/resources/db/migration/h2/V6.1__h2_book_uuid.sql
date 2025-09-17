-- may be required for older version of spring boot & h2
alter table books_uuid change id id varbinary(16);