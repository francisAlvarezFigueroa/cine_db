DROP TABLE IF EXISTS Cartelera;
SELECT USER(), VERSION();
CREATE DATABASE IF NOT EXISTS CineDB;
USE CineDB;

CREATE TABLE IF NOT EXISTS Cartelera(
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    director VARCHAR(50) NOT NULL,
    anio INT NOT NULL,
    duracion INT NOT NULL,
    genero VARCHAR(50) NOT NULL,
    CONSTRAINT chk_genero CHECK(genero IN('Comedia', 'Drama', 'CienciaFiccion', 'Terror', 'Romance', 'Infantil')),
    CONSTRAINT chk_anio CHECK(anio BETWEEN 1888 AND 2025),
    CONSTRAINT chk_duracion CHECK(duracion BETWEEN 1 AND 480)
);

-- Datos de ejemplo
INSERT INTO Cartelera(titulo, director, anio, duracion, genero)VALUES
('Interestellar', 'Christopher Nolan', 2014, 169, 'CienciaFiccion'),
('Forrest Gump', 'Robert Zemeckis', 1994, 142, 'Drama'),
('Los fantasmas de mis ex', 'Mark Waters', 2009, 100, 'Comedia'),
('La casa en el lago', 'Alejandro Agresti', 2006, 99, 'Romance'),
('Crepusculo', 'Catherine Hardwicke', 2008, 130, 'Drama'),
('El planeta del tesoro', 'John Musker', 2002, 95, 'Infantil');

SELECT * FROM Cartelera;