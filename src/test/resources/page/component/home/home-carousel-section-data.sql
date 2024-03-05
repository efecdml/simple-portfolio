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

insert into home_carousel_section_components (id, home_id, image_name, text, display_order)
values (1, 1, 'image1.jpg', 'text', 1);
insert into home_carousel_section_components (id, home_id, image_name, text, display_order)
values (2, 1, 'image2.jpg', 'text', 2);
insert into home_carousel_section_components (id, home_id, image_name, text, display_order)
values (3, 1, 'image3.jpg', 'text', 3);
insert into home_carousel_section_components (id, home_id, image_name, text, display_order)
values (4, 2, 'image1.jpg', 'metin', 1);
insert into home_carousel_section_components (id, home_id, image_name, text, display_order)
values (5, 2, 'image2.jpg', 'metin', 2);
insert into home_carousel_section_components (id, home_id, image_name, text, display_order)
values (6, 2, 'image3.jpg', 'metin', 3);
select setval('home_carousel_section_components_seq', 6)