create table INDIVIDUAL_USER
(
    ID NUMBER(20) not null
        constraint INDIVIDUAL_USER_PK
        primary key,
    USERNAME VARCHAR2(45) not null,
    PASSWORD VARCHAR2(45) not null,
    MOBILE_PHONE NUMBER(10) default 0 not null,
    EMAIL VARCHAR2(45) default '' not null,
    STATUS NUMBER(3) default 1 not null,
    CREATE_TIME TIMESTAMP(6) default current_timestamp not null,
    CREATE_USER NUMBER(20) not null,
    LAST_MODIFY_TIME TIMESTAMP(6) default current_timestamp,
    LAST_MODIFY_USER NUMBER(20) not null,
    IS_DELETED NUMBER(3) default 0 not null
)
    /

create unique index IDX_MOBILE_PHONE
    on INDIVIDUAL_USER (MOBILE_PHONE)
    /

create unique index IDX_EMAIL
    on INDIVIDUAL_USER (EMAIL)
    /

create unique index IDX_USERNAME
    on INDIVIDUAL_USER (USERNAME)
    /



create table shop
(
    ID NUMBER(20) not null
        constraint SHOP_PK
        primary key,
    user_id NUMBER(20) not null,
    shop_name VARCHAR2(45) not null,
    CREATE_TIME TIMESTAMP(6) default current_timestamp not null,
    CREATE_USER NUMBER(20) not null,
    LAST_MODIFY_TIME TIMESTAMP(6) default current_timestamp,
    LAST_MODIFY_USER NUMBER(20) not null,
    IS_DELETED NUMBER(3) default 0 not null
)
    /

create index fk_user_id_idx
    on shop (user_id)
    /


create table item
(
    ID NUMBER(20) not null
        constraint ITEM_PK
        primary key,
    shop_id NUMBER(20) not null,
    item_type NUMBER(3) default 1 not null,
    item_name VARCHAR2(45) not null,
    category_one_id NUMBER(20) not null,
    category_two_id NUMBER(20) not null,
    category_three_id NUMBER(20) not null,


    CREATE_TIME TIMESTAMP(6) default current_timestamp not null,
    CREATE_USER NUMBER(20) not null,
    LAST_MODIFY_TIME TIMESTAMP(6) default current_timestamp,
    LAST_MODIFY_USER NUMBER(20) not null,
    IS_DELETED NUMBER(3) default 0 not null
)
    /

create index fk_shop_id_idx
    on item (shop_id)
    /
create index fk_category_one_id_idx
    on item (category_one_id)
    /
create index fk_category_two_id_idx
    on item (category_two_id)
    /
create index fk_category_three_id_idx
    on item (category_three_id)
    /

create table warehouse
(
    ID NUMBER(20) not null
        constraint WAREHOUSE_PK
        primary key,
    user_id NUMBER(20) not null,
    shop_id NUMBER(20) not null,
    warehouse_type NUMBER(3) default 1 not null,
    warehouse_name VARCHAR2(45) not null,
    CREATE_TIME TIMESTAMP(6) default current_timestamp not null,
    CREATE_USER NUMBER(20) not null,
    LAST_MODIFY_TIME TIMESTAMP(6) default current_timestamp,
    LAST_MODIFY_USER NUMBER(20) not null,
    IS_DELETED NUMBER(3) default 0 not null
)
    /

create index fk_individual_user_id_idx
    on warehouse (user_id)
    /
create index fk_warehouse_shop_id_idx
    on warehouse (shop_id)
    /

