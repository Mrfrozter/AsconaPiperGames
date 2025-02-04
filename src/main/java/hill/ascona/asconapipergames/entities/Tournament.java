package hill.ascona.asconapipergames.entities;

import hill.ascona.asconapipergames.DAO.MatchDAO;
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

    @OneToMany(mappedBy = "tournament", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Match> matches = new ArrayList<>();

    @Column(name = "tmnt_exp", nullable = false, columnDefinition = "boolean default false")
    private boolean expanded = false;

    public Tournament() {
    }

    public Tournament(Game game, String date, String title, Match match) {
        this.game = game;
        this.date = date;
        this.title = title;
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
        if(title == null)
            title = "";
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Match> getMatches(){
        return matches;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
