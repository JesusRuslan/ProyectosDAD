DROP TABLE Placas;
DROP TABLE Actuadores;
DROP TABLE Sensores;

CREATE TABLE Sensores(
valueId INTEGER PRIMARY KEY AUTO_INCREMENT,
placaId Integer,
groupId Integer,
sensorId INTEGER,
temperatura DOUBLE,
humedad DOUBLE,
tiempo BIGINT);

CREATE TABLE Actuadores(
valueId INTEGER PRIMARY KEY AUTO_INCREMENT,
placaId INTEGER,
groupId Integer,
actuadorId INTEGER,
velocidad  DOUBLE,
sentido BOOLEAN,
tiempo BIGINT);

CREATE TABLE Placas(
valueId INTEGER PRIMARY KEY AUTO_INCREMENT,
placaId INTEGER);


INSERT INTO Sensores(placaId,groupId,sensorId, temperatura, humedad, tiempo) VALUES (1,1,1, 20.2, 35.0, 9372036807);
INSERT INTO Sensores(placaId,groupId,sensorId, temperatura, humedad, tiempo) VALUES (1,1,2, 10.8, 19.0, 9372036810);
INSERT INTO Sensores(placaId,groupId,sensorId, temperatura, humedad, tiempo) VALUES (1,1,3, 3.5, 9.0, 9372036811);
INSERT INTO Sensores(placaId,groupId,sensorId, temperatura, humedad, tiempo) VALUES (1,1,4, 0.8, 2.0, 9372036812);

INSERT INTO Actuadores(placaId,groupId,actuadorId, velocidad, sentido, tiempo) VALUES (1,1,1, 15, true, 937203807);
INSERT INTO Actuadores(placaId,groupId,actuadorId, velocidad, sentido, tiempo) VALUES (1,1,2, 10, true, 937203810);


INSERT INTO Placas(placaId) VALUES (1);


SELECT * FROM Sensores;
SELECT * FROM Actuadores;
SELECT * FROM Placas;
SELECT * FROM Sensores ORDER BY valueId ASC LIMIT 3;
SELECT * FROM proyectodad.sensores WHERE sensorid=2;
SELECT * FROM proyectodad.actuadores WHERE actuadorId = 1;