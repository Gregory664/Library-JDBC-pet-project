CREATE DATABASE IF NOT EXISTS library;
use library;

DROP TABLE IF EXISTS author;
CREATE TABLE IF NOT EXISTS author(
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS publisher;
CREATE TABLE IF NOT EXISTS publisher (
	id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS genre;
CREATE TABLE IF NOT EXISTS genre (
	id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS book;
CREATE TABLE IF NOT EXISTS book (
	id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    author_id INT NULL,
    publisher_id INT NULL,
    genre_id INT NULL,
    length INT NOT NULL,
    CONSTRAINT book_author_fk FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE SET NULL,
    CONSTRAINT book_publisher_fk FOREIGN KEY (publisher_id) REFERENCES publisher(id) ON DELETE SET NULL,
    CONSTRAINT book_genre_fk FOREIGN KEY (genre_id) REFERENCES genre(id) ON DELETE SET NULL
);

CREATE TABLE bookCopy (
	id INTEGER PRIMARY KEY auto_increment,
    book_id INTEGER,
    CONSTRAINT bookCopy_book_fk FOREIGN KEY (book_id) REFERENCES book (id)
);

DROP TABLE IF EXISTS shelf;
CREATE TABLE IF NOT EXISTS shelf (
	id INT AUTO_INCREMENT PRIMARY KEY,
    invent_num VARCHAR(20) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS bookshelf;
CREATE TABLE IF NOT EXISTS bookshelf (
	id INT AUTO_INCREMENT PRIMARY KEY,
    shelf_id INT NOT NULL,
    bookCopy_id INT NOT NULL UNIQUE,
    CONSTRAINT bookshelf_shelf_fk FOREIGN KEY (shelf_id) REFERENCES shelf(id) ON DELETE CASCADE,
    CONSTRAINT bookshelf_book_fk FOREIGN KEY (bookCopy_id) REFERENCES bookCopy(id) ON DELETE CASCADE
); 

DROP TABLE IF EXISTS reader;
CREATE TABLE IF NOT EXISTS reader (
	id INT AUTO_INCREMENT PRIMARY KEY,
    fio VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    address VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL, 
    passport VARCHAR(10) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS book_rent;
CREATE TABLE IF NOT EXISTS book_rent (
	id INT AUTO_INCREMENT PRIMARY KEY, 
    reader_id INT NOT NULL,
    bookCopy_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date date NOT NULL,
    CONSTRAINT book_rent_reader_fk FOREIGN KEY (reader_id) REFERENCES reader(id) ON DELETE CASCADE,
    CONSTRAINT book_rent_book_fk FOREIGN KEY (bookCopy_id) REFERENCES bookCopy(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS book_rent_log;
CREATE TABLE IF NOT EXISTS book_rent_log (
	id INT AUTO_INCREMENT PRIMARY KEY, 
    reader_id INT NOT NULL,
    bookCopy_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date date NOT NULL,
    CONSTRAINT book_rent_log_reader_fk FOREIGN KEY (reader_id) REFERENCES reader(id) ON DELETE CASCADE,
    CONSTRAINT book_rent_log_book_fk FOREIGN KEY (bookCopy_id) REFERENCES bookCopy(id) ON DELETE CASCADE
);

delimiter $$ 

CREATE TRIGGER bookprent_log
	AFTER INSERT
    ON book_rent FOR EACH ROW
BEGIN
	INSERT INTO book_rent_log(reader_id, bookCopy_id, start_date, end_date)
    VALUES(NEW.reader_id, NEW.bookCopy_id, NEW.start_date, NEW.end_date);
END$$