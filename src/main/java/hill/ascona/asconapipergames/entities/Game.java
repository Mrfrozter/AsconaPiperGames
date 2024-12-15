package hill.ascona.asconapipergames.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "game_title", length = 100, nullable = false)
    private String title;

    @Column(name = "game_genre", length = 50, nullable = false)
    private String genre;

    @Column(name = "game_numberOfTeams", nullable = false)
    private int numberOfTeams;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        if (numberOfTeams <= 0) {
            throw new IllegalArgumentException("Number of teams must be greater than 0.");
        }
        this.numberOfTeams = numberOfTeams;
    }

    public Game(int id) {
        this.id = id;
    }
}

