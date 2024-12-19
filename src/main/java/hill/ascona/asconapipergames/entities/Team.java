package hill.ascona.asconapipergames.entities;

import jakarta.persistence.*;
import hill.ascona.asconapipergames.entities.Person;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private int team_id;

    @Column(name = "team_name")
    private String team_name;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Person> members = new ArrayList<>();

    @Column(name = "game_id", length = 50, nullable = false)
    private String game_id;


    //Tom konstruktor

    public Team() {
    }

    //Konstruktor med allt förutom ID, eftersom att vi inte ska välja ID själva

    public Team(String team_name, List<Person> members, String game_id) {
        this.team_name = team_name;
        this.members = members;
        this.game_id = game_id;
    }

    //Getters och setters


    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public List<Person> getMembers() {
        return members;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }
}