drop table if exists authors_composite;

create table authors_composite
(
    first_name varchar(255),
    last_name varchar(255),
    country varchar(255),
    primary key (first_name, last_name)
) engine = InnoDB;