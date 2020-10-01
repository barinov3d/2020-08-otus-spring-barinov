insert into authors (id, `name`)
values (1, 'John Deer');

insert into authors (id, `name`)
values (2, 'Vasya Ivanov');

insert into genres (id, `name`)
values (1, 'Pulp fiction');
insert into genres (id, `name`)
values (2, 'Сomputer science');

insert into books (id, `title`, author_id, genre_id)
values (1, 'Java SE 7', 1, 1);
insert into books (id, `title`, author_id, genre_id)
values (2, 'Java EE 5', 2, 2);