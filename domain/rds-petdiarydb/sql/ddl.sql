create table pet
(
    idx          bigint auto_increment comment '고유번호'
        primary key,
    name         varchar(255) not null comment '이름',
    kind         varchar(255) null comment '종 이름',
    birthday     date         null comment '생일',
    created_date datetime(6)  not null comment '데이터 생성일',
    updated_date datetime(6)  null comment '데이터 마지막 수정일'
);

