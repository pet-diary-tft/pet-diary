create table member
(
    idx                     bigint auto_increment
        primary key,
    email                   varchar(255)  not null,
    password                varchar(255)  not null,
    name                    varchar(63)   not null,
    created_date            datetime(6)   not null,
    updated_date            datetime(6)   null,
    roles                   varchar(2047) null comment '권한(USER)',
    access_token_expires_at datetime(6)   null comment '액세스 토큰의 유효기간이 이 값보다 낮으면 해당 액세스 토큰은 폐기됨(토큰 초기화용)',
    status_code             tinyint       not null comment '상태 코드(1: 정상, 2: 정지, 9: 삭제)',
    constraint member_UN_email
        unique (email),
    constraint member_UN_name
        unique (name)
)
    comment '회원';

create table member_refresh_token
(
    token_id      varchar(255) not null comment '토큰 고유값(uuid)'
        primary key,
    member_idx    bigint       null comment '회원 고유번호',
    client_ip     varchar(255) null comment 'refresh token을 생성한 client ip 정보',
    user_agent    varchar(255) null comment 'refresh token을 생성한 client agent 정보',
    refresh_token varchar(255) null comment 'refresh token 값',
    issued_date   datetime(6)  null comment 'refresh token 발급일',
    expiry_date   datetime(6)  null comment 'refresh token 만료일'
)
    comment '회원 refresh token';

create index member_refresh_token_member_idx_and_refresh_token_index
    on member_refresh_token (member_idx, refresh_token);

