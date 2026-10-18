create database bibliotheque; 
Use bibliotheque; 

create table LIVRES( 
isbn varchar(20) primary key, 
titre varchar(20), 
auteur varchar(20), 
categorie varchar(20), 
disponible boolean default true); 

create table ADHERENTS( 
numtel int primary key,
nom varchar(20),
prenom varchar(20),
datenaiss date,
type ENUM('Standard','Premium'));

create table EMPRUNT(
id int primary key, 
isbn varchar(20),
numtel int,
dateemp date, 
dateretour date, 
foreign key(isbn) references LIVRES(isbn), 
foreign key(numtel) references ADHERENTS(numtel)); 

create table UTILISATEUR( 
ident int auto_increment primary key, 
nomutilisateur varchar(255) unique, 
motdepasse varchar(200));