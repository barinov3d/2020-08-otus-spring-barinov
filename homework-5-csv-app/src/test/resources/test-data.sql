insert into authors (id, `name`)
values (1, 'Bruce Eckel');
insert into authors (id, `name`)
values (2, 'Zed A. Shaw');
insert into authors (id, `name`)
values (3, 'Alfred Van Vogt');
insert into authors (id, `name`)
values (4, 'Super Author');

insert into genres (id, `name`)
values (1, 'Сomputer science');
insert into genres (id, `name`)
values (2, 'Other');

insert into books (id, `title`, author_id, genre_id)
values (1, 'Thinking in java', 1, 1);
insert into books (id, `title`, author_id, genre_id)
values (2, 'Learn Python the Hard Way', 2, 1);
insert into books (id, `title`, author_id, genre_id)
values (3, 'The Monster', 3, 2);