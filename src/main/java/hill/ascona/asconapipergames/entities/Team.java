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
    private int team_id;

    @Column(name = "team_name")
    private String team_name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Person> members = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "game_id", nullable = true)
    private Game game;

    public Team() {}

    public Team(String team_name, List<Person> members, Game game) {
        this.team_name = team_name;
        this.members = members;
        this.game = game;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Person> getMembers() {
        return members;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return team_name;
    }
}


