package hill.ascona.asconapipergames.entities;

import jakarta.persistence.*;

@Entity
@Table(name ="matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private int id;

    @Column(name = "turneringar_id")
    private int turneringar_id;

    @Column(name = "match_date", nullable = false)
    private String date;

    @Column(name = "match_played", nullable = false)
    private boolean allreadyPlayed;

    @Column(name = "match_player1", nullable = true)
    private int player1_id;

    @Column(name = "match_player2", nullable = true)
    private int player2_id;

    @Column(name = "match_team1", nullable = true)
    private int team1_id;

    @Column(name = "match_team2", nullable = true)
    private int team2_id;

    @Column(name = "match_winner", nullable = true)
    private int winner_id;

    public Match() {
    }

    public Match(String date) {
        this.date = date;
    }

    public Match(int turneringar_id, int winner_id, String date, boolean allreadyPlayed, int player1_id, int player2_id, int team1_id, int team2_id) {
        this.turneringar_id = turneringar_id;
        this.winner_id = winner_id;
        this.date = date;
        this.allreadyPlayed = allreadyPlayed;
        this.player1_id = player1_id;
        this.player2_id = player2_id;
        this.team1_id = team1_id;
        this.team2_id = team2_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWinner_id() {
        return winner_id;
    }

    public void setWinner_id(int winner_id) {
        this.winner_id = winner_id;
    }

    public int getTurneringar_id() {
        return turneringar_id;
    }

    public void setTurneringar_id(int turneringar_id) {
        this.turneringar_id = turneringar_id;
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

    public int getPlayer1_id() {
        return player1_id;
    }

    public void setPlayer1_id(int player1_id) {
        this.player1_id = player1_id;
    }

    public int getPlayer2_id() {
        return player2_id;
    }

    public void setPlayer2_id(int player2_id) {
        this.player2_id = player2_id;
    }

    public int getTeam1_id() {
        return team1_id;
    }

    public void setTeam1_id(int team1_id) {
        this.team1_id = team1_id;
    }

    public int getTeam2_id() {
        return team2_id;
    }

    public void setTeam2_id(int team2_id) {
        this.team2_id = team2_id;
    }
}


