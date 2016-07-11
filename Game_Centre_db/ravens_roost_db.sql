--SQL Fake Book Song Data


--CREATE DATABASE TABLES
--=======================
create table if not exists students(
	student_number int primary key not null,
	number_of_appearances integer default 0 not null,
	fName text not null,
	lName text not null
);

create table if not exists consoles(
	consoleID integer primary key not null,
	console_name text not null
);

create table if not exists games(
	date_added text not null,
	consoleID integer not null,
	gameName text,
	primary key (consoleID,gameName),
	foreign key (consoleID) references consoles(consoleID)
);

create table if not exists outcomes(
	student_number integer not null,
	gameName text not null,
	consoleID integer not null,
	day text not null,
	game_result text,
	updated text,
	primary key (student_number, gameName, consoleID, day),
	foreign key (student_number) references student(student_number),
	foreign key (gameName) references game(gameName),
	foreign key (consoleID) references consoles(consoleID)
);
--INSERT DATA
--=======================

begin transaction;

--Insert consoles
insert into consoles(console_name) values('PS4');
insert into consoles(console_name) values('PS3');
insert into consoles(console_name) values('XBOX 360');
insert into consoles(console_name) values('XBOX ONE');
insert into consoles(console_name) values('Wii');
insert into consoles(console_name) values('Nintendo Gamecube');
insert into consoles(console_name) values('Wii U');

--Insert games
insert into games(date_added, consoleID, gameName) values('June 2010', (select consoleID from consoles where console_name = 'XBOX 360'), 'Rock Band Hero 3');
insert into games(date_added, consoleID, gameName) values('June 2010', (select consoleID from consoles where console_name = 'XBOX 360'), 'Rock Band Hero 2');
insert into games(date_added, consoleID, gameName) values('June 2010', (select consoleID from consoles where console_name = 'XBOX 360'), 'Guitar Hero');
insert into games(date_added, consoleID, gameName) values('August 2011', (select consoleID from consoles where console_name = 'XBOX 360'), 'Forza Horizon');
insert into games(date_added, consoleID, gameName) values('August 2013', (select consoleID from consoles where console_name = 'XBOX 360'), 'FIFA 13');
insert into games(date_added, consoleID, gameName) values('August 2013', (select consoleID from consoles where console_name = 'XBOX 360'), 'NFL 13');
insert into games(date_added, consoleID, gameName) values('August 2013', (select consoleID from consoles where console_name = 'XBOX 360'), 'NBA 2K 13');
insert into games(date_added, consoleID, gameName) values('January 2015', (select consoleID from consoles where console_name = 'XBOX ONE'), 'NHL 15');
insert into games(date_added, consoleID, gameName) values('January 2015', (select consoleID from consoles where console_name = 'XBOX ONE'), 'FIFA 16');
insert into games(date_added, consoleID, gameName) values('January 2015', (select consoleID from consoles where console_name = 'XBOX ONE'), 'UFC');
insert into games(date_added, consoleID, gameName) values('January 2015', (select consoleID from consoles where console_name = 'XBOX ONE'), 'NBA 2K 14');
insert into games(date_added, consoleID, gameName) values('January 2015', (select consoleID from consoles where console_name = 'XBOX ONE'), 'JUST DANCE 2015');
insert into games(date_added, consoleID, gameName) values('August 2011', (select consoleID from consoles where console_name = 'Wii'), 'Mario Kart');
insert into games(date_added, consoleID, gameName) values('August 2011', (select consoleID from consoles where console_name = 'Wii'), 'Wii Sports Resort');
insert into games(date_added, consoleID, gameName) values('August 2011', (select consoleID from consoles where console_name = 'Wii'), 'Mario and Sonic Olympics');
insert into games(date_added, consoleID, gameName) values('August 2011', (select consoleID from consoles where console_name = 'Wii U'), 'Just Dance 4');
insert into games(date_added, consoleID, gameName) values('August 2014', (select consoleID from consoles where console_name = 'Wii U'), 'Mario Tennis: Ultra Smash');
insert into games(date_added, consoleID, gameName) values('August 2009', (select consoleID from consoles where console_name = 'Nintendo Gamecube'), 'Mario Kart: Double Dash');
insert into games(date_added, consoleID, gameName) values('August 2009', (select consoleID from consoles where console_name = 'Nintendo Gamecube'), 'Super Smash Brothers');
insert into games(date_added, consoleID, gameName) values('January 2015', (select consoleID from consoles where console_name = 'PS4'), 'FIFA 15');
insert into games(date_added, consoleID, gameName) values('January 2015', (select consoleID from consoles where console_name = 'PS4'), 'NBA 2K 15');
insert into games(date_added, consoleID, gameName) values('January 2015', (select consoleID from consoles where console_name = 'PS4'), 'Need for Speed Rivals');
insert into games(date_added, consoleID, gameName) values('January 2012', (select consoleID from consoles where console_name = 'PS3'), 'FIFA 14');
insert into games(date_added, consoleID, gameName) values('January 2012', (select consoleID from consoles where console_name = 'PS3'), 'NBA Live 14');

---Insert Students
insert into students(student_number, number_of_appearances,fName,lName) values (100999232, 2, 'Bruce', 'Wayne');
insert into students(student_number, number_of_appearances,fName,lName) values (100234984, 1, 'Jilian', 'Clarke');
insert into students(student_number, number_of_appearances,fName,lName) values (101908235, 4, 'Nancy', 'Hardy');
insert into students(student_number, number_of_appearances,fName,lName) values (100990123, 1, 'Jeremy', 'Granger');


--Insert outcomes of student games
insert into outcomes(student_number,gameName, consoleID, day, game_result, updated) values(101908235, 'FIFA 15', (select consoleID from consoles where console_name = 'PS4'), '26 11 2015', 'WIN', 'UPDATED');
insert into outcomes(student_number,gameName, consoleID, day, game_result, updated) values(100990123, 'NBA 2K 15', (select consoleID from consoles where console_name = 'PS4'), '26 11 2015', 'WIN', 'UPDATED');
insert into outcomes(student_number,gameName, consoleID, day, game_result, updated) values(100234984, 'Just Dance 4', (select consoleID from consoles where console_name = 'Wii U'), '27 11 2015', 'LOSE', 'UPDATED');
insert into outcomes(student_number,gameName, consoleID, day, game_result, updated) values(100999232, 'Rock Band Hero 2', (select consoleID from consoles where console_name = 'XBOX 360'), '28 11 2015', 'WIN', 'UPDATED');
insert into outcomes(student_number,gameName, consoleID, day, game_result, updated) values(100999232, 'Rock Band Hero 2', (select consoleID from consoles where console_name = 'XBOX 360'), '29 11 2015', 'LOSE', 'UPDATED');
insert into outcomes(student_number,gameName, consoleID, day, game_result, updated) values(101908235, 'FIFA 15', (select consoleID from consoles where console_name = 'PS4'), '27 11 2015', 'WIN', 'UPDATED');
insert into outcomes(student_number,gameName, consoleID, day, game_result, updated) values(101908235, 'FIFA 15', (select consoleID from consoles where console_name = 'PS4'), '28 11 2015', 'WIN', 'UPDATED');
insert into outcomes(student_number,gameName, consoleID, day, game_result, updated) values(101908235, 'FIFA 15', (select consoleID from consoles where console_name = 'PS4'), '29 11 2015', 'WIN', 'UPDATED');

end transaction;
