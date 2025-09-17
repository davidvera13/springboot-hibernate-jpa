-- 2 techniques :
-- stored as string...
drop table if exists authors_uuid;

create table authors_uuid
(
    id varchar(36) not null,
    first_name varchar(255),
    last_name varchar(255),
    primary key (id)
) engine = InnoDB