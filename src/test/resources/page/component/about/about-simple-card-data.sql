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

insert into about_simple_card_components (id, about_id, image_name, text, display_order, title)
values (1, 1, 'image1.jpg', 'text', 1, 'title');
insert into about_simple_card_components (id, about_id, image_name, text, display_order, title)
values (2, 1, 'image2.jpg', 'text', 2, 'title');
insert into about_simple_card_components (id, about_id, image_name, text, display_order, title)
values (3, 1, 'image3.jpg', 'text', 3, 'title');
insert into about_simple_card_components (id, about_id, image_name, text, display_order, title)
values (4, 2, 'image1.jpg', 'metin', 1, 'baslik');
insert into about_simple_card_components (id, about_id, image_name, text, display_order, title)
values (5, 2, 'image2.jpg', 'metin', 2, 'baslik');
insert into about_simple_card_components (id, about_id, image_name, text, display_order, title)
values (6, 2, 'image3.jpg', 'metin', 3, 'baslik');
select setval('about_simple_card_components_seq', 6)
