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

INSERT INTO Teams(game_id, team_name) VALUES (1, 'Hammarby');
INSERT INTO Teams(game_id, team_name) VALUES (2, 'Malmö');
INSERT INTO Teams(game_id, team_name) VALUES (3, 'Göteborg');
INSERT INTO Teams(game_id, team_name) VALUES (4, 'AIK');
INSERT INTO Teams(game_id, team_name) VALUES (5, 'Djurgården');
INSERT INTO Teams(game_id, team_name) VALUES (1, 'Norrköping');
INSERT INTO Teams(game_id, team_name) VALUES (2, 'Elfsborg');
INSERT INTO Teams(game_id, team_name) VALUES (3, 'GAIS');

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
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id, game_id) VALUES ('Gavin','Belson','GavinB','CA, Palo Alto',94420,'Palo Alto','USA','gavinb@hooli.com','Player',null,1);
Insert into persons(p_name, p_lastname, p_nickname, p_address, p_postNumber, p_city, p_country, p_email, p_role, team_id, game_id) VALUES ('Jack','Barker','ActionJB62','CA, Palo Alto',94360,'Palo Alto','USA','actionjack@hooli.com','Player',null,1);



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


INSERT INTO tournaments(game_id, tmnt_date, tmnt_title) VALUES (1, '2024-12-18', 'Ultimate Chess')
INSERT INTO tournaments(game_id, tmnt_date, tmnt_title) VALUES (2, '2024-12-19', 'H3 MLG')

INSERT INTO matches(match_date, part_one_name, part_two_name, tmnt_id, game_id) VALUES ('13:00', 'GavinB','ActionJB62', 1, 1)
INSERT INTO matches(match_date, part_one_name, part_two_name, tmnt_id, game_id) VALUES ('14:00', 'Jo36','Sim30', 1, 1)
INSERT INTO matches(match_date, part_one_name, part_two_name, tmnt_id, game_id) VALUES ('13:00', 'ErEr','Queen12', 1, 1)
INSERT INTO matches(match_date, part_one_name, part_two_name, tmnt_id, game_id) VALUES ('14:00', 'Kung','Prins', 1, 1)

INSERT INTO matches(match_date, part_one_name, part_two_name, tmnt_id, game_id) VALUES ('15:00', 'GavinB','Queen12', 1, 1)
INSERT INTO matches(match_date, part_one_name, part_two_name, tmnt_id, game_id) VALUES ('16:00', 'Sim30','Kung', 1, 1)
INSERT INTO matches(match_date, part_one_name, part_two_name, tmnt_id, game_id) VALUES ('17:00', 'Queen12','Kung', 1, 1)


INSERT INTO matches(match_date, part_one_name, part_two_name, tmnt_id, game_id) VALUES ('13:00', 'Faze','OpTic', 2, 2)
INSERT INTO matches(match_date, tmnt_id, part_one_name, part_two_name, game_id) VALUES ('13:45', 2, 'Pipers', 'HooliGang', 2)
INSERT INTO matches(match_date, tmnt_id, part_one_name, part_two_name, game_id) VALUES ('14:45', 2, 'Ninjas in Pyjamas', 'Heroic', 2)
INSERT INTO matches(match_date, tmnt_id, part_one_name, part_two_name, game_id) VALUES ('14:00', 2, 'Fnatic', 'Cloud9', 2)
