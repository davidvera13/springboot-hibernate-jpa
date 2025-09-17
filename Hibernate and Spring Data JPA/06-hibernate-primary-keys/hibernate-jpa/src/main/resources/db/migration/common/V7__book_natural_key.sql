drop table if exists books_natural;

create table books_natural
(
  title  varchar(255) not null,
  isbn  varchar(255),
  publisher varchar(255),
  primary key (title)
) engine = InnoDB;