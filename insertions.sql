Create Table coaster(  nom VARCHAR(35) Primary KEY,
   vitesse INT, hauteur INT);
   
Create Table Emplacement( parc VARCHAR(30)  NOT NULL, manege VARCHAR(35) NOT NULL,
   index(manege), FOREIGN KEY (manege) REFERENCES coaster (nom) ON DELETE CASCADE
   ON UPDATE CASCADE, PRIMARY KEY (parc, manege) );

Insert Into coaster values('Batman the ride', 50, 109);
Insert Into coaster values('Boomerang', 50, 125);
Insert Into coaster values('Condor', 30, 112);
Insert Into coaster values('Deja vu', 65, 178);
Insert Into coaster values('Dragon Mountain', 50, 187);
Insert Into coaster values('Le Monstre', 60, 131);
Insert Into coaster values('Raptor', 57, 137);
Insert Into coaster values('Mantis', 60, 145);
Insert Into coaster values('Mind Eraser', 64, 115);
Insert Into coaster values('SpongeBob SquarePants 3-D', 0, 3);
Insert Into coaster values('Superman Krypton Coaster', 70, 168);
Insert Into coaster values('Superman Ride of Steel', 73, 208);
Insert Into coaster values('Superman ultimate flight', 60, 115);
Insert Into coaster values('Test Track', 65, 65);
Insert Into coaster values('El Toro', 70, 181);
Insert Into coaster values('Kingda Ka', 128, 456);
Insert Into coaster values('Nitro', 80, 230);
Insert Into coaster values('Viper', 50, 121);
Insert Into coaster values('Predator', 50, 95);

Insert into Emplacement values ('Magic mountain', 'Deja vu');
Insert into Emplacement values ('Great America', 'Deja vu');
Insert into Emplacement values ('Great America', 'Superman ultimate flight');
Insert into Emplacement values ('Magic mountain', 'Batman the ride');
Insert into Emplacement values ('Great America', 'Batman the ride');
Insert into Emplacement values ('Six Flags over Georgia', 'Batman the ride');
Insert into Emplacement values ('Six Flags over Georgia', 'Superman ultimate flight');
Insert into Emplacement values ('La Ronde', 'Boomerang');
Insert into Emplacement values ('Darien Lake', 'Boomerang');
Insert into Emplacement values ('The Great Escape', 'Boomerang');
Insert into Emplacement values ('La Ronde', 'Condor');
Insert into Emplacement values ('The Great Escape', 'Condor');
Insert into Emplacement values ('Great America', 'Condor');
Insert into Emplacement values ('Six Flags New Orleans', 'Batman the ride');
Insert into Emplacement values ('Elitch Gardens', 'Boomerang');
Insert into Emplacement values ('Darien Lake', 'Superman Ride of Steel');
Insert into Emplacement values ('Six Flags over Texas', 'Batman the ride');
Insert into Emplacement values ('Six Flags New England', 'Superman Ride of Steel');
Insert into Emplacement values ('Darien Lake', 'Mind Eraser');
Insert into Emplacement values ('Six Flags America', 'Superman Ride of Steel');
Insert into Emplacement values ('Great Adventure', 'SpongeBob SquarePants 3-D');
Insert into Emplacement values ('Six Flags New England', 'Mind Eraser');
Insert into Emplacement values ('Great Adventure', 'Batman the ride');
Insert into Emplacement values ('Great Adventure', 'Superman ultimate flight');
Insert into Emplacement values ('Six Flags America', 'Mind Eraser');
Insert into Emplacement values ('La Ronde', 'SpongeBob SquarePants 3-D');
Insert into Emplacement values ('Six Flags New Orleans', 'SpongeBob SquarePants 3-D');
Insert into Emplacement values ('Elitch Gardens', 'Mind Eraser');
Insert into Emplacement values ('Fiesta Texas', 'Boomerang');
Insert into Emplacement values ('Fiesta Texas', 'Superman Krypton Coaster');
Insert into Emplacement values ('Marineland', 'Dragon Mountain');
Insert into Emplacement values ('Wild Adventures', 'Boomerang');
Insert into Emplacement values ('Epcot', 'Test Track');
Insert into Emplacement values ('La Ronde', 'Le Monstre');
Insert into Emplacement values ('Cedar Point', 'Mantis');
Insert into Emplacement values ('Cedar Point', 'Raptor');
Insert into Emplacement values ('Great Adventure', 'El Toro');
Insert into Emplacement values ('Great Adventure', 'Kingda Ka');
Insert into Emplacement values ('Great Adventure', 'Nitro');
Insert into Emplacement values ('Darien Lake', 'Viper');
Insert into Emplacement values ('Darien Lake', 'Predator');