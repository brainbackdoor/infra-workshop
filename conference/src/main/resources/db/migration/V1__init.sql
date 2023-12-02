create table applicant
(
    id              varchar(8) not null,
    created_date    datetime not null,
    updated_date    datetime not null,
    member_id       varchar(8),
    recruitment_id  varchar(8),
    primary key (id)
) engine = InnoDB;

create table conference
(
    id              varchar(8) not null,
    created_date    datetime not null,
    updated_date    datetime not null,
    area            varchar(255),
    contents        longtext,
    fee             integer not null,
    schedule        datetime,
    recruitment_id varchar(8),
    primary key (id)
) engine = InnoDB;

create table recruitment
(
    id              varchar(8) not null,
    created_date    datetime not null,
    updated_date    datetime not null,
    limited         integer not null,
    lottery_boundary integer not null,
    period_end      datetime,
    period_start    datetime,
    status          varchar(50),
    primary key (id)
) engine = InnoDB;

alter table applicant
    add constraint fk_applicant_to_recruitment
        foreign key (recruitment_id)
            references recruitment (id);

alter table conference
    add constraint fk_conference_to_recruitment
        foreign key (recruitment_id)
            references recruitment (id);