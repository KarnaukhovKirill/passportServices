create table if not exists passports (
    id identity primary key,
    seria varchar(4) not null ,
    number varchar(6) not null ,
    name varchar(255) not null ,
    surname varchar(255) not null ,
    date_expiry DATE default current_date
);

alter table passports add constraint uq_seria_number_constraint unique (seria, number);