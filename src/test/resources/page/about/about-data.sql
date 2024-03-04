insert into locales (id, name)
values (1, 'ENGLISH');
insert into locales (id, name)
values (2, 'TURKISH');
select setval('locales_seq', 2);

insert into about_pages (id, image_name, title, text, locale_id)
values (1, 'image1.jpg', 'title', 'text', 1);
insert into about_pages (id, image_name, title, text, locale_id)
values (2, 'image1.jpg', 'baslik', 'metin', 2);
select setval('about_pages_seq', 2);