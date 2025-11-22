CREATE TABLE "user" (
	id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	age INTEGER
)

INSERT INTO "user" (name, age) VALUES
('Alice', 25),
('Bob', 30),
('Charlie', 22),
('Diana', 28),
('Edward', 35);


SELECT * FROM "user";