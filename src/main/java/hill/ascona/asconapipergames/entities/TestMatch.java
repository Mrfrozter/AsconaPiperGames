package hill.ascona.asconapipergames.entities;

public class TestMatch {
    private String date;
    private String game;
    private String team1;
    private String team2;
    private boolean played;
    private String winner;

    public TestMatch(String date, String game, String team1, String team2, boolean played, String winner) {
        this.date = date;
        this.game = game;
        this.team1 = team1;
        this.team2 = team2;
        this.played = played;
        this.winner = winner;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
