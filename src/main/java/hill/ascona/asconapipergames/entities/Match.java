package hill.ascona.asconapipergames.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name ="matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private int id;

    @Column(name = "turneringar_id", nullable = true)
    private int turneringarId;

    @Column(name = "match_date", length =20, nullable = true)
    private String date;

    @Column(name = "match_played", columnDefinition = "boolean default true", nullable = true)
    private boolean allreadyPlayed = false;

    @Column(name = "match_singel_team", columnDefinition = "boolean default true", nullable = true)
    private boolean singelNotTeam = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "match_game_id")
    private Game game;

    @Column(name = "match_player1", nullable = true)
    private int player1Id;

    @Column(name = "match_player2", nullable = true)
    private int player2Id;

    @Column(name = "match_team1", nullable = true)
    private int team1Id;

    @Column(name = "match_team2", nullable = true)
    private int team2Id;

    @Column(name = "match_winner", nullable = true)
    private int winnerId;

    @Column(name = "part_one_name", length = 50, nullable = true)
    private String nameOne;

    @Column(name = "part_two_name", length = 50, nullable = true)
    private String nameTwo;

    public Match() {
    }

    //singel's match
    public Match(String date, boolean allreadyPlayed, boolean singelNotTeam, Game game, int player1Id, int player2Id, int winnerId, String nameOne, String nameTwo) {
        this.date = date;
        this.allreadyPlayed = allreadyPlayed;
        this.singelNotTeam = singelNotTeam;
        this.game = game;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.winnerId = winnerId;
        this.nameOne = nameOne;
        this.nameTwo = nameTwo;
    }


    // team match
    public Match(boolean singelNotTeam,String date, boolean allreadyPlayed,  Game game,  int team1Id, int team2Id, int winnerId, String nameOne, String nameTwo) {
        this.singelNotTeam = singelNotTeam;
        this.date = date;
        this.allreadyPlayed = allreadyPlayed;
        this.game = game;
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.winnerId = winnerId;
        this.nameOne = nameOne;
        this.nameTwo = nameTwo;
    }

    public Match(int turneringarId, String date, boolean allreadyPlayed, boolean singelNotTeam, Game game, int player1Id, int player2Id, int team1Id, int team2Id, int winnerId, String nameOne, String nameTwo) {
        this.turneringarId = turneringarId;
        this.date = date;
        this.allreadyPlayed = allreadyPlayed;
        this.singelNotTeam = singelNotTeam;
        this.game = game;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.winnerId = winnerId;
        this.nameOne = nameOne;
        this.nameTwo = nameTwo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTurneringarId() {
        return turneringarId;
    }

    public void setTurneringarId(int turneringarId) {
        this.turneringarId = turneringarId;
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

    public boolean isSingelNotTeam() {
        return singelNotTeam;
    }

    public void setSingelNotTeam(boolean singelNotTeam) {
        this.singelNotTeam = singelNotTeam;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(int player1Id) {
        this.player1Id = player1Id;
    }

    public int getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(int player2Id) {
        this.player2Id = player2Id;
    }

    public int getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(int team1Id) {
        this.team1Id = team1Id;
    }

    public int getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(int team2Id) {
        this.team2Id = team2Id;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
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
}