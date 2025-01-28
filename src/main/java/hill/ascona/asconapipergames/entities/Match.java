package hill.ascona.asconapipergames.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private int id;

    @Column(name = "match_date", length = 20, nullable = true)
    private String date;

    @Column(name = "match_played", columnDefinition = "boolean default true", nullable = true)
    private boolean allreadyPlayed = false;

    @Column(name = "match_singel_team", nullable = true)
    private String playerTeam;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id", nullable = true)
    private Game game;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Person> players = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Team> teams = new ArrayList<>();

    @Column(name = "match_winner", nullable = true)
    private Integer winnerId;

    @Column(name = "part_one_name", length = 50, nullable = true)
    private String nameOne;

    @Column(name = "part_two_name", length = 50, nullable = true)
    private String nameTwo;

    public Match() {
    }

    public Match(String date, boolean allreadyPlayed, String playerTeam, Game game, String nameOne, String nameTwo) {
        this.date = date;
        this.allreadyPlayed = allreadyPlayed;
        this.playerTeam = playerTeam;
        this.game = game;
        this.nameOne = nameOne;
        this.nameTwo = nameTwo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isAllreadyPlayed() {
        return allreadyPlayed;
    }

    public void setAllreadyPlayed(boolean allreadyPlayed) {
        this.allreadyPlayed = allreadyPlayed;
    }

    public String getPlayerTeam() {
        return playerTeam;
    }

    public void setPlayerTeam(String playerTeam) {
        this.playerTeam = playerTeam;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Person> getPlayers() {
        return players;
    }

    public void setPlayers(List<Person> players) {
        this.players = players;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public Integer getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Integer winnerId) {
        this.winnerId = winnerId;
    }

    public String getNameOne() {
        return nameOne;
    }

    public void setNameOne(String nameOne) {
        this.nameOne = nameOne;
    }

    public String getNameTwo() {
        return nameTwo;
    }

    public void setNameTwo(String nameTwo) {
        this.nameTwo = nameTwo;
    }

    public void clearGameReference() {
        this.game = null; // Bryter kopplingen till spelet
    }
}