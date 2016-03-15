# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table book (
  id                        varchar(255) not null,
  isbn                      varchar(255),
  title                     varchar(255),
  author                    varchar(255),
  cover                     integer,
  constraint ck_book_cover check (cover in (0,1)),
  constraint uq_book_isbn unique (isbn),
  constraint pk_book primary key (id))
;

create sequence book_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists book;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists book_seq;

