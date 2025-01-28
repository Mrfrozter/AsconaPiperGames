package hill.ascona.asconapipergames.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "game_title", length = 100)
    private String title;

    @Column(name = "game_genre", length = 50)
    private String genre;

    @Column(name = "game_numberOfTeams")
    private Integer numberOfTeams;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true
    )
    @JoinColumn(name = "game_id")
    private List<Team> teams = new ArrayList<>();

    @OneToMany(
            mappedBy = "game",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Match> matches = new ArrayList<>();

    @OneToMany(
            mappedBy = "game",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Tournament> tournaments = new ArrayList<>();

    @OneToMany(
            mappedBy = "game",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Person> persons = new ArrayList<>();

    public Game() {}

    public Game(String title, String genre, Integer numberOfTeams) {
        this.title = title;
        this.genre = genre;
        this.numberOfTeams = numberOfTeams;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(Integer numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public void addTeam(Team team) {
        this.teams.add(team);
        team.setGame(this);
    }

    public void removeTeam(Team team) {
        team.setGame(null);
        this.teams.remove(team);
    }

    public void removeAllTeams() {
        for (Team team : new ArrayList<>(teams)) {
            team.setGame(null);
            teams.remove(team);
        }
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public void addMatch(Match match) {
        this.matches.add(match);
        match.setGame(this);
    }

    public void removeMatch(Match match) {
        match.setGame(null);
        this.matches.remove(match);
    }

    public void removeAllMatches() {
        for (Match match : new ArrayList<>(matches)) {
            match.setGame(null);
            matches.remove(match);
        }
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public void addTournament(Tournament tournament) {
        this.tournaments.add(tournament);
        tournament.setGame(this);
    }

    public void removeTournament(Tournament tournament) {
        tournament.setGame(null);
        this.tournaments.remove(tournament);
    }

    public void removeAllTournaments() {
        for (Tournament tournament : new ArrayList<>(tournaments)) {
            tournament.setGame(null);
            tournaments.remove(tournament);
        }
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public void addPerson(Person person) {
        this.persons.add(person);
        person.setGame(this);
    }

    public void removePerson(Person person) {
        person.setGame(null);
        this.persons.remove(person);
    }

    public void removeAllPersons() {
        for (Person person : new ArrayList<>(persons)) {
            person.setGame(null);
            persons.remove(person);
        }
    }

    public void prepareForDeletion() {
        removeAllMatches();
        removeAllTournaments();
        removeAllTeams();
        removeAllPersons();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, genre, numberOfTeams, teams, matches, tournaments, persons);
    }

    @Override
    public String toString() {
        return title;
    }
}