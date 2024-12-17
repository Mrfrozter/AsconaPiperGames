package hill.ascona.asconapipergames.entities;

import jakarta.persistence.*;
import java.util.Objects;

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

    // Constructors
    public Game() {
        // Default constructor required by JPA
    }

    public Game(String title, String genre, int numberOfTeams) {
        this.title = title;
        this.genre = genre;
        setNumberOfTeams(numberOfTeams); // Using setter to validate
    }

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
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Genre cannot be null or empty.");
        }
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

    // Override equals and hashCode for proper entity comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id &&
                numberOfTeams == game.numberOfTeams &&
                Objects.equals(title, game.title) &&
                Objects.equals(genre, game.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, genre, numberOfTeams);
    }

    // toString for easy debugging
    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", numberOfTeams=" + numberOfTeams +
                '}';
    }
}