# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table product (
  id                        bigint auto_increment not null,
  name                      varchar(255) not null,
  price                     bigint not null,
  quantity                  varchar(255) not null,
  location                  varchar(255) not null,
  update_date               datetime not null,
  constraint pk_product primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table product;

SET FOREIGN_KEY_CHECKS=1;

