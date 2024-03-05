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