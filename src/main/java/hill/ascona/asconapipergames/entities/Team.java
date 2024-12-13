package hill.ascona.asconapipergames.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private int team_id;

    @Column(name = "medlemmar", length = 100)
    private String members;

    @Column(name = "game_id", length = 50, nullable = false)
    private String game_id;


    //Tom konstruktor

    public Team() {
    }

    //Konstruktor med allt förutom ID, eftersom att vi inte ska välja ID själva

    public Team(String game_id, String members) {
        this.game_id = game_id;
        this.members = members;
    }

    //Getters och setters


    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }
}

