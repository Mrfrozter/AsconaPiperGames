package hill.ascona.asconapipergames.entities;

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
    @JoinColumn(name = "game_id", nullable = true)
    private Game game;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Match> matches = new ArrayList<>();

    public Tournament() {
    }

    public Tournament(Game game, String date) {
        this.game = game;
        this.date = date;
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

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public void addMatch(Match match) {
        this.matches.add(match);
        match.setGame(this.game); // Sätter spelets referens i matchen
    }

    public void removeMatch(Match match) {
        match.setGame(null); // Tar bort referensen till spelet
        this.matches.remove(match);
    }

    public void removeAllMatches() {
        for (Match match : new ArrayList<>(matches)) {
            match.setGame(null); // Bryt kopplingen till spelet
            matches.remove(match);
        }
    }

    public void clearGameReference() {
        this.game = null; // Bryter kopplingen till spelet
    }

    public void prepareForDeletion() {
        removeAllMatches();
        clearGameReference();
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", game=" + (game != null ? game.getTitle() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tournament that = (Tournament) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
