DROP TABLE IF EXISTS employees;

CREATE TABLE employees (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(250),
department VARCHAR(250),
office_location VARCHAR(250),
start_date DATE,
salary NUMBER (8,2)
);

INSERT INTO employees (name, department, office_location, start_date, salary) VALUES
	('James Kirk','Command','Bridge', TO_DATE('01 Jan 1975'),700000),
	('Spock','Science','Bridge',TO_DATE('01 Jan 1970'),400000),
	('Leonard McCoy','Medical','Sickbay',TO_DATE('01 Mar 1975'),200000),
	('Montgomery Scott','Engineering','Main Engineering',TO_DATE('01 Apr 1976'),200000),
	('Hikaru Sulu','Command','Bridge',TO_DATE('01 Jan 1980'),100000);

CREATE TABLE winners (
employee_id NUMBER(19) NOT NULL,
date_of_win DATE NOT NULL, 
FOREIGN KEY(employee_id) REFERENCES employees(id),
PRIMARY KEY(employee_id, date_of_win)
);