insert into locales (id, name)
values (1, 'ENGLISH');
insert into locales (id, name)
values (2, 'TURKISH');
select setval('locales_seq', 2);

insert into contact_pages (id, title, text, email, location, working_days, working_hours, google_maps_coordination,
                           locale_id)
values (1, 'title', 'text', 'email', 'location', 'mon-fri', '08:30-17:30', 'gmaps', 1);
insert into contact_pages (id, title, text, email, location, working_days, working_hours, google_maps_coordination,
                           locale_id)
values (2, 'baslik', 'metin', 'eposta', 'lokasyon', 'mon-fri', '08:30-17:30', 'gmaps', 2);
select setval('contact_pages_seq', 2);

insert into contact_phone_components (id, contact_id, tag, number, display_order)
values (1, 1, 'home', '+904442223311', 1);
insert into contact_phone_components (id, contact_id, tag, number, display_order)
values (2, 1, 'office', '+904443332211', 2);
insert into contact_phone_components (id, contact_id, tag, number, display_order)
values (3, 2, 'ev', '+904442223311', 1);
insert into contact_phone_components (id, contact_id, tag, number, display_order)
values (4, 2, 'is', '+904443332211', 2);
select setval('contact_phone_components_seq', 4)