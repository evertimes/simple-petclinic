INSERT INTO public.owner (id, address, name) VALUES (1, 'г. Рязань', 'Андрей');
INSERT INTO public.owner (id, address, name) VALUES (2, 'г. Рязань', 'Костя');

INSERT INTO public.pet_type (type) VALUES ('Собака');
INSERT INTO public.pet_type (type) VALUES ('Кошка');
INSERT INTO public.pet_type (type) VALUES ('Хомяк');
INSERT INTO public.pet_type (type) VALUES ('Кролик');

INSERT INTO public.pet (date_of_birth, id, owner_id, name, pet_type_type) VALUES ('2023-10-13 23:20:09.257205', 2, 1, 'Вениамин', 'Хомяк');
INSERT INTO public.pet (date_of_birth, id, owner_id, name, pet_type_type) VALUES ('2023-10-13 23:20:09.257205', 1, 1, 'Барсик', 'Кошка');
INSERT INTO public.pet (date_of_birth, id, owner_id, name, pet_type_type) VALUES ('2023-10-13 23:20:09.257205', 4, 2, 'Виктор', 'Кролик');
INSERT INTO public.pet (date_of_birth, id, owner_id, name, pet_type_type) VALUES ('2023-10-13 23:20:09.257205', 3, 2, 'Шарик', 'Собака');

INSERT INTO public.visit (date_of_visit, id, pet, description) VALUES ('2023-10-13 23:20:09.089495', 2, 1, 'Несварение');
INSERT INTO public.visit (date_of_visit, id, pet, description) VALUES ('2023-10-13 23:20:09.202850', 12, 2, 'Анализы');
INSERT INTO public.visit (date_of_visit, id, pet, description) VALUES ('2023-10-13 23:20:09.114090', 4, 3, 'Груминг');
INSERT INTO public.visit (date_of_visit, id, pet, description) VALUES ('2023-10-13 23:20:09.192815', 11, 4, 'Пропал аппетит');
INSERT INTO public.visit (date_of_visit, id, pet, description) VALUES ('2023-10-13 23:20:09.171170', 9, 2, 'Плановый осмотр хомяка');
INSERT INTO public.visit (date_of_visit, id, pet, description) VALUES ('2023-10-13 23:20:09.138069', 6, 1, 'Кастрация');
INSERT INTO public.visit (date_of_visit, id, pet, description) VALUES ('2023-10-13 23:20:09.125201', 5, 1, 'Взятие крови на анализы');
INSERT INTO public.visit (date_of_visit, id, pet, description) VALUES ('2023-10-13 23:20:09.102736', 3, 1, 'Стрижка когтей');
INSERT INTO public.visit (date_of_visit, id, pet, description) VALUES ('2023-10-13 23:20:09.148419', 7, 1, 'Плановый осмотр кошки');
INSERT INTO public.visit (date_of_visit, id, pet, description) VALUES ('2023-10-13 23:20:09.182443', 10, 4, 'Плановый осмотр кролика');
INSERT INTO public.visit (date_of_visit, id, pet, description) VALUES ('2023-10-13 23:20:09.159808', 8, 3, 'Плановый осмотр собаки');
INSERT INTO public.visit (date_of_visit, id, pet, description) VALUES ('2023-10-13 23:20:08.868762', 1, 3, 'Боль в лапе');
