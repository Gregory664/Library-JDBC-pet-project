INSERT INTO genre (title)
VALUES
('Классическая литература'),
('Зарубежная литература'),
('Русская литература'),
('Фантастика'),
('Повести');

INSERT INTO author (name)
VALUES
('Федор Достоевский'),
('Михаил Булгаков'),
('Лев Толстой'),
('Уильям Шекспир'),
('Чарльз Диккенс'),
('Гомер'),
('Олдос Хаксли'),
('Джон Толкиен'),
('Данте Алигьери');

INSERT INTO publisher (title)
VALUES
('Эксмо'),
('АСТ'),
('Просвещение'),
('Стандартинформ'),
('Росмэн'),
('Эгмонт'),
('Дрофа');

INSERT INTO book (title, author_id, publisher_id, genre_id, length)
VALUES
('book 1', 1, 2, 1, 532),
('book 2', 2, 1, 1, 124),
('book 3', 1, 5, 2, 1432),
('book 4', 2, 4, 2, 1542),
('book 5', 3, 6, 2, 765);

INSERT INTO bookCopy (book_id)
VALUES 
(1),(1),
(2),(2),
(3),(3),
(4),(4),
(5),(5);

INSERT INTO shelf (invent_num)
VALUES ('Z10'),('Z20'),('Z30'),('Z40'),('Z50');

INSERT INTO bookshelf(shelf_id, bookCopy_id)
VALUES 
(1,1), (1,2), (1,3), 
(2,4),
(3,5), (3,6), 
(4,7), (4,8), 
(5,9), (5,10);

INSERT INTO reader (fio, age, phone, passport, address)
VALUES
('Романов Александр Владимирович',     15,'88001112233', '2013566665', 'г. Воронеж, ул. Мира'),
('Романов Алексей Владимирович', 	   42,'88009992211', '2013111111', 'г. Воронеж, ул. Мира'),
('Ефимов, Александр Александрович',    45,'88003332233', '2013666666', 'г. Воронеж, ул. Мира'),
('Семёнов, Александр Александрович',   16,'88005552233', '2013444444', 'г. Воронеж, ул. Мира'),
('Щербаков, Александр Александрович ', 8, '88004322233', '2013222222', 'г. Воронеж, ул. Мира');

INSERT INTO book_rent (reader_id, bookCopy_id, start_date, end_date) 
VALUES
(1, 1, now(), now() + INTERVAL 10 DAY),
(2, 3, now(), now() + INTERVAL 20 DAY),
(3, 5, now(), now() + INTERVAL 2 DAY),
(4, 7, now(), now() + INTERVAL 30 DAY),
(5, 9, now() - INTERVAL 10 DAY, now() + INTERVAL 60 DAY);