package hill.ascona.asconapipergames.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private int id;

    @Column(name = "game_title", length = 50, nullable = false)
    private String title;

    @Column(name = "game_numberOfTeams", length = 50, nullable = false)
    private String title;

    @Column(name = "game_genre", length = 10, nullable = false)
    private String genre;

    public Game() {
        this.genre =genre;
        this.title = title;
    }
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
    public String getGenre{
        return.genre;
    }
}

