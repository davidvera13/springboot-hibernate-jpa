drop table if exists authors;
drop table if exists authors_seq;

create table authors
(
    id bigint not null,
    first_name varchar(255),
    last_name varchar(255),
    primary key (id)
) engine = InnoDB;

create table authors_seq
(
    next_val bigint
) engine=InnoDB;

insert into authors_seq values ( 1 );