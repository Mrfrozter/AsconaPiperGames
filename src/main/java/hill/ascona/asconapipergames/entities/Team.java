package hill.ascona.asconapipergames.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private int teamId;

    @Column(name = "team_name")
    private String teamName;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Person> members = new ArrayList<>();

    /*@Column(name = "game_id", length = 50, nullable = false)
    private int game_id;  // ÄNDRAT FRÅN STRING TILL INT */

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;


    //Tom konstruktor

    public Team() {
    }

    //Konstruktor med allt förutom ID, eftersom att vi inte ska välja ID själva

    public Team(String teamName, List<Person> members, Game game) {
        this.teamName = teamName;
        this.members = members;
        this.game = game;
    }

    //skapad konstruktor automatiskt från errorn i TeamView på rad 75 - test / tillfällig
    public Team(String teamName, Game selectedGame) {
    }

    //Getters och setters

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int team_id) {
        this.teamId = team_id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String team_name) {
        this.teamName = team_name;
    }

    public List<Person> getMembers() {
        return members;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return teamName;
    }
}

