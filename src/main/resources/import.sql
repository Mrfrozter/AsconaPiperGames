INSERT INTO game(game_title, game_genre, game_numberOfTeams) VALUES ('Chess', 'Strategy', 2);
INSERT INTO game(game_title, game_genre, game_numberOfTeams) VALUES ('Halo 3', 'FPS', 8);
INSERT INTO game(game_title, game_genre, game_numberOfTeams) VALUES ('Counter Strike 2', 'FPS', 5);
INSERT INTO game(game_title, game_genre, game_numberOfTeams) VALUES ('Fortnite', 'BR', 20);
INSERT INTO game(game_title, game_genre, game_numberOfTeams) VALUES ('Dragonball Sparking Zero', 'Fighting', 2);
INSERT INTO game(game_title, game_genre, game_numberOfTeams) VALUES ('FallGuys', 'Party', 4);
INSERT INTO game(game_title, game_genre, game_numberOfTeams) VALUES ('MobileLegends', 'MOBA', 5);
INSERT INTO game(game_title, game_genre, game_numberOfTeams) VALUES ('DragonsDogma 2', 'RPG', 1);
INSERT INTO game(game_title, game_genre, game_numberOfTeams) VALUES ('Dota 2', 'MOBA', 5);
INSERT INTO game(game_title, game_genre, game_numberOfTeams) VALUES ('LoL', 'MOBA', 5);
INSERT INTO game(game_title, game_genre, game_numberOfTeams) VALUES ('Valorant', 'FPS', 5);

INSERT INTO teams(game_id, team_name) VALUES (1, 'Hammarby');
INSERT INTO teams(game_id, team_name) VALUES (2, 'Malmö');
INSERT INTO teams(game_id, team_name) VALUES (3, 'Göteborg');
INSERT INTO teams(game_id, team_name) VALUES (4, 'AIK');
INSERT INTO teams(game_id, team_name) VALUES (5, 'Djurgården');
INSERT INTO teams(game_id, team_name) VALUES (1, 'Norrköping');
INSERT INTO teams(game_id, team_name) VALUES (2, 'Elfsborg');
INSERT INTO teams(game_id, team_name) VALUES (3, 'GAIS');

Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Richard','Hendricks','Rich','NY',123,'NY','Usa','Hendricks@mail','User',null);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Bertram','Gilfoyle','BG','NY',567,'NY','Usa','Gilfoyle@mail','User',null);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Dinesh','Chugtai','Din23','NY',348,'NY','Usa','Chugtai@mail','User',null);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Erik','lundgren','ElFa','Stockholm',123,'Stockholm','Sverige','Farhang@mail','Player',1);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Anna','Andersson','Queen','Mamlmö',7865,'Mamlmö','Sverige','Andersson@mail','Player',1);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Erik','Eriksson','ErEr','Umeå',2145,'Umeå','Sverige','Eriksson@mail','Player',3);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Sara','Emill','Sara','Mamlmö',123,'Mamlmö','Sverige','Emill@mail','Player',2);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Anna','Hendricksson','Queen12','Skåne',3135,'Skåne','Sverige','Hendricksson@mail','Player',2);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id) VALUES ('Simon','Andersson','Sim30','Gävle',123,'Mamlmö','Sverige','Andersson30@mail','Player',3);


INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(1,'Player',true, '2024-10-10','Sim30', 'Queen12');
INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(1,'Team',false,'2024-11-11','Norrköping', 'Elfsborg');
INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(3,'Team',false,'2024-12-11','Göteborg', 'AIK');
INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(1,'Player',true, '2024-10-10','Sara', 'ErEr');
INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(1,'Team',false,'2024-11-11','Hammarby', 'Malmö');
INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(3,'Player',false,'2024-12-11','Queen', 'ElFa');
INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(1,'Player',true, '2024-10-10','Sara', 'Din23');
INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(1,'Team',true,'2024-11-11','GAIS', 'Djurgården');
INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(3,'Team',false,'2024-12-11','Hammarby', 'Elfsborg');
INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(1,'Player',true, '2024-10-10','Queen12', 'Sim30');
INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(1,'Team',true,'2024-11-11','Norrköping', 'Malmö');
INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(3,'Player',false,'2024-12-11','Sim30', 'ErEr');


INSERT INTO tournaments(game_id, tmnt_date) VALUES (1, '2024-12-18');
INSERT INTO tournaments(game_id, tmnt_date) VALUES (2, '2024-12-19');
INSERT INTO tournaments(game_id, tmnt_date) VALUES (3, '2024-12-20');
INSERT INTO tournaments(game_id, tmnt_date) VALUES (4, '2024-12-21');
INSERT INTO tournaments(game_id, tmnt_date) VALUES (5, '2024-12-24');
