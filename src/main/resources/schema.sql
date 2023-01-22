
create table if not exists genres (
    genre_id int generated by default as identity primary key,
    name varchar(20) not null
);

create table if not exists ratings (
    rating_id int generated by default as identity primary key,
    name varchar(30)
);

create table if not exists films (
    id int generated by default as identity primary key,
    name varchar(50) not null,
    description varchar(500) default '-',
    releace_date date,
    duration int,
    rating_id int references ratings
);

create table if not exists films_genres (
    genre_id int references genres,
    film_id int references films,
    unique (genre_id, film_id)
);

create unique index if not exists film_id_uindex
    on films (id);

create table if not exists subscribers (
    user_id int,
    sub_id int
);

create table if not exists friends (
    user_id int,
    friend_id int
);

create table if not exists users (
    id int generated by default as identity primary key,
    name varchar(30) not null ,
    email varchar(30) not null,
    login varchar(20) not null,
    birthday date
);

create unique index if not exists USER_EMAIL_UINDEX on USERS (email);

create unique index if not exists USER_LOGIN_UINDEX on USERS (login);

create unique index if not exists user_id_uindex
    on users (id);

create table if not exists likes (
    user_id int,
    film_id int
);