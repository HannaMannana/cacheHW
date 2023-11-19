DROP TABLE IF EXISTS users;


CREATE TABLE IF NOT EXISTS users (
	id BIGSERIAL PRIMARY KEY,
	name_user VARCHAR (120) NOT NULL,
	last_name VARCHAR (120) NOT NULL,
	email VARCHAR (160) NOT NULL,
	password VARCHAR (250) NOT NULL
);

INSERT into users
	(id, name_user, last_name, email, password)
	values
	(46, 'Harry', 'Potter', 'Potter@gmail.com', '141486QWE1'),
    (47, 'Ron', 'Wiesley', 'Wiesley@gmail.com', 'Q14786QWE1'),
    (48, 'Joe', 'Mitchel', 'Mitchel@gmail.com', '12478odTE1'),
    (49, 'John', 'Dolby', 'Dolby@gmail.com', '12400aPWE1'),
    (50, 'Vik', 'MikkiY', 'MikkiY@gmail.com', '12400aP01K');