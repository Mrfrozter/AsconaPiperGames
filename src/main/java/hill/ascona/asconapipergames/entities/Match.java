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

    @Column(name = "turneringar_id")
    private int turneringar_id;

    @Column(name = "match_date", nullable = true)
    private LocalDate date;

    @Column(name = "match_played", nullable = false)
    private boolean allreadyPlayed;

    @Column(name = "match_singel_team", nullable = false)
    private boolean singelNotTeam;

    @Column(name = "match_game_id", nullable = true)
    private int game_id;

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

    @Column(name = "part_one_name", length = 50, nullable = false)
    private String p_name_one;

    @Column(name = "part_two_name", length = 50, nullable = false)
    private String p_name_two;

    public boolean isSingelNotTeam() {
        return singelNotTeam;
    }

    public void setSingelNotTeam(boolean singelNotTeam) {
        this.singelNotTeam = singelNotTeam;
    }

    public Match() {
    }


    public Match(boolean allreadyPlayed, boolean singelNotTeam,  int game_id, int winner_id, String p_name_one, String p_name_two) {
        this.allreadyPlayed = allreadyPlayed;
        this.singelNotTeam = singelNotTeam;
        this.game_id = game_id;
        this.winner_id = winner_id;
        this.p_name_one = p_name_one;
        this.p_name_two = p_name_two;
    }

    public Match(int id, int turneringar_id, LocalDate date, boolean allreadyPlayed, boolean singelNotTeam, int game_id, int player1_id, int player2_id, int team1_id, int team2_id, int winner_id, String p_name_one, String p_name_two) {
        this.id = id;
        this.turneringar_id = turneringar_id;
        this.date = date;
        this.allreadyPlayed = allreadyPlayed;
        this.singelNotTeam = singelNotTeam;
        this.game_id = game_id;
        this.player1_id = player1_id;
        this.player2_id = player2_id;
        this.team1_id = team1_id;
        this.team2_id = team2_id;
        this.winner_id = winner_id;
        this.p_name_one = p_name_one;
        this.p_name_two = p_name_two;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getP_name_one() {
        return p_name_one;
    }

    public void setP_name_one(String p_name_one) {
        this.p_name_one = p_name_one;
    }

    public String getP_name_two() {
        return p_name_two;
    }

    public void setP_name_two(String p_name_two) {
        this.p_name_two = p_name_two;
    }
}


