package hill.ascona.asconapipergames.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private int id;

    @Column(name = "match_date", length =20, nullable = true)
    private String date;

    @Column(name = "match_played", columnDefinition = "boolean default false", nullable = true)
    private boolean allreadyPlayed;

    @Column(name = "match_singel_team", nullable = true)
    private String playerTeam;

    @ManyToOne(fetch = FetchType.EAGER    )
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER    )
    @JoinColumn(name = "winner_if_player")
    private Person winnerIfPlayer;

    @ManyToOne(fetch = FetchType.EAGER    )
    @JoinColumn(name = "winner_if_team")
    private Team winnerIfTeam;


    @ManyToMany(fetch = FetchType.EAGER)
    private List<Person> players = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Team> teams = new ArrayList<>();

    @Column(name = "match_winner", nullable = true)
    private String winnerName;

    @Column(name = "part_one_name", length = 50, nullable = true)
    private String nameOne;

    @Column(name = "part_two_name", length = 50, nullable = true)
    private String nameTwo;

    @Column(name = "final_score", length = 50, nullable = true)
    private String finalScore;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tmnt_id")
    private Tournament tournament;

    public Match() {
    }

    // everything except id
    public Match(String finalScore, String nameTwo, String nameOne, String winnerName, List<Team> teams, List<Person> players, Team winnerIfTeam, Person winnerIfPlayer, Game game, String playerTeam, boolean allreadyPlayed, String date) {
        this.finalScore = finalScore;
        this.nameTwo = nameTwo;
        this.nameOne = nameOne;
        this.winnerName = winnerName;
        this.teams = teams;
        this.players = players;
        this.winnerIfTeam = winnerIfTeam;
        this.winnerIfPlayer = winnerIfPlayer;
        this.game = game;
        this.playerTeam = playerTeam;
        this.allreadyPlayed = allreadyPlayed;
        this.date = date;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
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

    public Person getWinnerIfPlayer() {
        return winnerIfPlayer;
    }

    public void setWinnerIfPlayer(Person winnerIfPlayer) {
        this.winnerIfPlayer = winnerIfPlayer;
    }

    public Team getWinnerIfTeam() {
        return winnerIfTeam;
    }

    public void setWinnerIfTeam(Team winnerIfTeam) {
        this.winnerIfTeam = winnerIfTeam;
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

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
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

    public String getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(String finalScore) {
        this.finalScore = finalScore;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", allreadyPlayed=" + allreadyPlayed +
                ", playerTeam='" + playerTeam + '\'' +
                ", game=" + game +
                ", winnerIfPlayer=" + winnerIfPlayer +
                ", winnerIfTeam=" + winnerIfTeam +
                ", players=" + players +
                ", teams=" + teams +
                ", winnerName='" + winnerName + '\'' +
                ", nameOne='" + nameOne + '\'' +
                ", nameTwo='" + nameTwo + '\'' +
                ", finalScore='" + finalScore + '\'' +
                '}';
    }
}