create table if not exists passports (
    id identity primary key,
    seria varchar(12) not null unique ,
    name varchar(255) not null ,
    surname varchar(255) not null ,
    date DATE default current_date
);