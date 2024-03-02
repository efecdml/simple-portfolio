insert into locales (id, name)
values (1, 'ENGLISH');
insert into locales (id, name)
values (2, 'TURKISH');
select setval('locales_seq', 2);

insert into home_pages (id, title, text, second_title, second_text, locale_id)
values (1, 'title', 'text', 'second title', 'second text', 1);
insert into home_pages (id, title, text, second_title, second_text, locale_id)
values (2, 'baslik', 'metin', 'ikinci baslik', 'ikinci metin', 2);
select setval('home_pages_seq', 2);