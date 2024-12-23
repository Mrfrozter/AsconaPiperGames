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

--//----Elham Farhang--(table persons)----
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id, game_id) VALUES ('Richard','Hendricks','Rich','Silicon Valley',123,'Silicon Valley','Usa','Hendricks@mail','User',null, null);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id, game_id) VALUES ('Bertram','Gilfoyle','BG','Silicon Valley',567,'Silicon Valley','Usa','Gilfoyle@mail','User',null,null);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id, game_id) VALUES ('Dinesh','Chugtai','Din23','Silicon Valley',348,'Silicon Valley','Usa','Chugtai@mail','User',null,null);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id, game_id) VALUES ('Johan','lundgren','Kung','Stockholm',32466,'Stockholm','Sverige','Lundgren@mail','Player',1,1);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id, game_id) VALUES ('Sara','Andersson','Queen','Mamlmö',7865,'Mamlmö','Sverige','Andersson@mail','Player',5,5);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id, game_id) VALUES ('Erik','Eriksson','ErEr','Umeå',2145,'Umeå','Sverige','Eriksson@mail','Player',3,3);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id, game_id) VALUES ('Sara','Emill','Sara','Mamlmö',7896,'Mamlmö','Sverige','Emill@mail','Player',8,3);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id, game_id) VALUES ('Anna','Hendricksson','Queen12','Skåne',3135,'Skåne','Sverige','Hendricksson@mail','Player',2,2);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id, game_id) VALUES ('Simon','Norberg','Sim30','Jönköping',675,'Jönköping','Sverige','Norberg@mail','Player',3,3);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id, game_id) VALUES ('Emil','White','Emil','Gävle',23425,'Gävle','Sverige','White@mail','Player',4,4);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id, game_id) VALUES ('Jakob','Hansson','Prins','linköping',654457,'linköping','Sverige','Hansson@mail','Player',7,2);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id, game_id) VALUES ('Johan','Hägglund','Jo36','Stockholm',145623,'Stockholm','Sverige','Hägglund@mail','Player',5,5);



INSERT INTO matches(game_id,match_singel_team, match_Played,final_score,match_winner, match_date, part_one_name,part_two_name ) VALUES(1,'Player',true, '1-0','Sim30','2024-10-10 15:12','Sim30', 'Queen12');
INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(1,'Team',false,'2024-11-11 12:15','Norrköping', 'Elfsborg');
INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(3,'Team',false,'2024-12-11 12:13','Göteborg', 'AIK');
INSERT INTO matches(game_id,match_singel_team, match_Played,final_score,match_winner, match_date, part_one_name,part_two_name ) VALUES(1,'Player',true,'0-0','Draw', '2024-10-10 12:14','Sara', 'ErEr');
INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(1,'Team',false,'2024-11-11 12:11','Hammarby', 'Malmö');
INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(3,'Player',false,'2024-12-11 18:12','Queen', 'ElFa');
INSERT INTO matches(game_id,match_singel_team, match_Played,final_score,match_winner, match_date, part_one_name,part_two_name ) VALUES(1,'Player',true,'1-0','Sara', '2024-10-10 18:12','Sara', 'Din23');
INSERT INTO matches(game_id,match_singel_team, match_Played,final_score,match_winner, match_date, part_one_name,part_two_name ) VALUES(1,'Team',true,'0-1','Djurgården','2024-11-11 17:12','GAIS', 'Djurgården');
INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(3,'Team',false,'2024-12-11 16:12','Hammarby', 'Elfsborg');
INSERT INTO matches(game_id,match_singel_team, match_Played,final_score,match_winner, match_date, part_one_name,part_two_name ) VALUES(1,'Player',true,'1-0','Queen12', '2024-10-10 22:12','Queen12', 'Sim30');
INSERT INTO matches(game_id,match_singel_team, match_Played,final_score,match_winner, match_date, part_one_name,part_two_name ) VALUES(1,'Team',true,'0-1','Malmö','2024-11-11 11:12','Norrköping', 'Malmö');
INSERT INTO matches(game_id,match_singel_team, match_Played, match_date, part_one_name,part_two_name ) VALUES(3,'Player',false,'2024-12-11 22:12','Sim30', 'ErEr');


INSERT INTO tournaments(game_id, tmnt_date) VALUES (1, '2024-12-18');
INSERT INTO tournaments(game_id, tmnt_date) VALUES (2, '2024-12-19');
INSERT INTO tournaments(game_id, tmnt_date) VALUES (3, '2024-12-20');
INSERT INTO tournaments(game_id, tmnt_date) VALUES (4, '2024-12-21');
INSERT INTO tournaments(game_id, tmnt_date) VALUES (5, '2024-12-24');
