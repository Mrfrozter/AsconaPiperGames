package hill.ascona.asconapipergames.entities;

import hill.ascona.asconapipergames.DAO.MatchDAO;
import hill.ascona.asconapipergames.DAO.TeamDAO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tournaments")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tmnt_id")
    private int id;

    @Column(name = "tmnt_title")
    private String title;

    @Column(name = "tmnt_date")
    private String date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

//    @JoinColumn(name = "match_id")
    @OneToMany(fetch = FetchType.EAGER)
    private List<Match> matches = new ArrayList<>();

    public Tournament() {
    }

    public Tournament(Game game, String date, String title, Match match) {
        this.game = game;
        this.date = date;
        this.title = title;
        MatchDAO matchDAO = new MatchDAO();
        Match m1 = new Match();
//        Match m2 = new Match();
        List<Team> lt1 = new ArrayList<>();
//        m1 = matchDAO.getMatchById(1);
        match.getTeams().add(new TeamDAO().getTeamByName(match.getNameOne()));
        match.getTeams().add(new TeamDAO().getTeamByName(match.getNameTwo()));
//        match.setTeams(lt1);
//        match.getTeams().add(lt1);
        matches.add(match);
        System.out.println("teams: "+match.getTeams().size());
//        m2 = matchDAO.getMatchById(2);

//        matches.add(new MatchDAO().getAllMatches().get(0));
//        matches.add(new MatchDAO().getAllMatches().get(1));
//        System.out.println(matches.get(0).getPlayers().size());
//        System.out.println(matches.get(1).getPlayers().size());
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Match> getMatches(){
        return matches;
    }
}
