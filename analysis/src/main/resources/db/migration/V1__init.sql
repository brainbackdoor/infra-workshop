create table latest_lecture
(
    lecture_id                  varchar(8),
    name                        varchar(50)
) engine = InnoDB;

create table member
(
    member_id                  varchar(8),
    name                       integer
) engine = InnoDB;

create table survey
(
    id                         bigint,
    hobby                      varchar(50),
    dev_type                   longtext,
    years_coding               longtext,
    employment                 longtext,
    company_size               longtext,
    ide                        longtext,
    operating_system           longtext,
    version_control            longtext
) engine = InnoDB;

create table participant
(
    id                         bigint,
    member_id                  varchar(8),
    survey_id                  bigint,
    lecture_id                 varchar(8)
) engine = InnoDB;

