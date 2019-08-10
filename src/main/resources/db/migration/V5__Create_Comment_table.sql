create table comment
(
    id bigint auto_increment,
    parent_id BIGINT not null,
    type int not null,
    commentator int not null,
    gmt_create BIGINT not null,
    gmt_modified BIGINT not null,
    like_count int default 0,
    constraint comment_pk
        primary key (id)
);