INSERT INTO game(game_title, game_genre, game_numberOfTeams) VALUES ('Halo 3', 'FPS', 8);
INSERT INTO game(game_title, game_genre, game_numberOfTeams) VALUES ('Counter Strike 2', 'FPS', 5);
INSERT INTO game(game_title, game_genre, game_numberOfTeams) VALUES ('Fortnite', 'BR', 20);
INSERT INTO game(game_title, game_genre, game_numberOfTeams) VALUES ('Dota 2', 'MOBA', 5);
INSERT INTO game(game_title, game_genre, game_numberOfTeams) VALUES ('LoL', 'MOBA', 5);
INSERT INTO game(game_title, game_genre, game_numberOfTeams) VALUES ('Valorant', 'FPS', 5);

Insert into teams(game_id,team_name) VALUES ('1','team1');
Insert into teams(game_id,team_name) VALUES ('2','team2');
Insert into teams(game_id,team_name) VALUES ('3','team3');
Insert into teams(game_id,team_name) VALUES ('4','team4');
Insert into teams(game_id,team_name) VALUES ('5','team5');
Insert into teams(game_id,team_name) VALUES ('6','team6');


Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Richard','Hendricks','Rich','NY',123,'NY','Usa','Hendricks@mail','User',null);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Bertram','Gilfoyle','BG','NY',567,'NY','Usa','Gilfoyle@mail','User',null);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Dinesh','Chugtai','Din23','NY',348,'NY','Usa','Chugtai@mail','User',null);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Johan','lundgren','ElFa','Stockholm',123,'Stockholm','Sverige','Lundgren@mail','Player',1);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Sara','Andersson','Queen','Mamlmö',7865,'Mamlmö','Sverige','Andersson@mail','Player',5);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Erik','Eriksson','ErEr','Umeå',2145,'Umeå','Sverige','Eriksson@mail','Player',3);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Sara','Emill','Sara','Mamlmö',123,'Mamlmö','Sverige','Emill@mail','Player',2);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Anna','Hendricksson','Queen12','Skåne',3135,'Skåne','Sverige','Hendricksson@mail','Player',2);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Simon','Andersson','Sim30','Gävle',123,'Mamlmö','Sverige','Andersson30@mail','Player',3);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Emil','White','Sim30','Gävle',123,'Mamlmö','Sverige','White@mail','Player',4);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Sam','Petersson','Sim30','Gävle',123,'Mamlmö','Sverige','Petersson0@mail','Player',6);


INSERT INTO tournaments(game_id, tmnt_date) VALUES (1, '2024-12-18');
INSERT INTO tournaments(game_id, tmnt_date) VALUES (2, '2024-12-19');
INSERT INTO tournaments(game_id, tmnt_date) VALUES (3, '2024-12-20');
INSERT INTO tournaments(game_id, tmnt_date) VALUES (4, '2024-12-21');
INSERT INTO tournaments(game_id, tmnt_date) VALUES (5, '2024-12-24');
