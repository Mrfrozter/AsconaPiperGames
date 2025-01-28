package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.DAO.GameDAO;
import hill.ascona.asconapipergames.DAO.MatchDAO;
import hill.ascona.asconapipergames.DAO.PersonDAO;
import hill.ascona.asconapipergames.DAO.TeamDAO;
import hill.ascona.asconapipergames.entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MatchView {
    private MatchDAO matchDAO = new MatchDAO();
    private String gameChosen = "";
    private Boolean singelNotTeam = true;
    private Game gamePlay;
    private Person player1;
    private Person player2;
    private Team team1;
    private Team team2;
    private ObservableList<Match> matches = FXCollections.observableList(new ArrayList<>());

    public void addMatch() {
        AnchorPane paneAdd = new AnchorPane();
        paneAdd.setPrefSize(540, 300);
        Scene scene2 = new Scene(paneAdd);
        Stage stage2 = new Stage();
        stage2.setScene(scene2);
        stage2.show();

        PersonDAO pDao = new PersonDAO();
        List<Person> persons = pDao.getAllPlayersOrUsers("Player");
        TeamDAO teamDao = new TeamDAO();
        List<Team> teams = teamDao.getAllTeams();

        Label nowShowing = new Label("ADD A PLAYER MATCH");
        nowShowing.setFont(new Font("Arial bold", 12));

        ComboBox<String> comboBoxGame = new ComboBox<>();
        comboBoxGame.setPromptText("Choose game");
        GameDAO gDao = new GameDAO();
        List<Game> games = gDao.getAllGames();
        for (Game game : games) {
            comboBoxGame.getItems().add(game.getTitle());
        }

        ComboBox<String> comboBoxPOrT1 = new ComboBox<>();
        ComboBox<String> comboBoxPOrT2 = new ComboBox<>();
        comboBoxPOrT1.setDisable(true);
        comboBoxPOrT2.setDisable(true);

        comboBoxGame.setOnAction(e -> {
            gameChosen = comboBoxGame.getValue();
            gamePlay = gDao.getByName(gameChosen);
            comboBoxPOrT1.setDisable(false);
            comboBoxPOrT2.setDisable(false);
            comboBoxPOrT1.getItems().clear();
            comboBoxPOrT2.getItems().clear();

            if (singelNotTeam) {
                for (Person person : persons) {
                    comboBoxPOrT1.getItems().add(person.getNickname());
                    comboBoxPOrT2.getItems().add(person.getNickname());
                }
            } else {
                for (Team team : teams) {
                    if (team.getGame() != null && team.getGame().getId() == gamePlay.getId()) {
                        comboBoxPOrT1.getItems().add(team.getTeam_name());
                        comboBoxPOrT2.getItems().add(team.getTeam_name());
                    }
                }
            }
        });

        comboBoxPOrT1.setOnAction(e -> {
            if (singelNotTeam) {
                player1 = pDao.getByNickname(comboBoxPOrT1.getValue());
            } else {
                team1 = teamDao.getTeamByName(comboBoxPOrT1.getValue());
            }
        });

        comboBoxPOrT2.setOnAction(e -> {
            if (singelNotTeam) {
                player2 = pDao.getByNickname(comboBoxPOrT2.getValue());
            } else {
                team2 = teamDao.getTeamByName(comboBoxPOrT2.getValue());
            }
        });

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select date");

        Button saveMatch = new Button("Save match");
        saveMatch.setOnAction(event -> {
            Match match = new Match(datePicker.getValue().toString(), false, singelNotTeam ? "Player" : "Team", gamePlay, comboBoxPOrT1.getValue(), comboBoxPOrT2.getValue());

            if (singelNotTeam) {
                match.getPlayers().add(player1);
                match.getPlayers().add(player2);
            } else {
                match.getTeams().add(team1);
                match.getTeams().add(team2);
            }

            matchDAO.saveMatch(match);
            matches.add(match);
            stage2.close();
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> stage2.close());

        nowShowing.setLayoutX(20);
        nowShowing.setLayoutY(20);

        comboBoxGame.setLayoutX(20);
        comboBoxGame.setLayoutY(50);

        comboBoxPOrT1.setLayoutX(20);
        comboBoxPOrT1.setLayoutY(100);

        comboBoxPOrT2.setLayoutX(20);
        comboBoxPOrT2.setLayoutY(150);

        datePicker.setLayoutX(20);
        datePicker.setLayoutY(200);

        saveMatch.setLayoutX(20);
        saveMatch.setLayoutY(250);

        cancel.setLayoutX(100);
        cancel.setLayoutY(250);

        paneAdd.getChildren().addAll(nowShowing, comboBoxGame, comboBoxPOrT1, comboBoxPOrT2, datePicker, saveMatch, cancel);
    }

    public VBox start() {
        matches = FXCollections.observableList(matchDAO.getAllMatches());

        TableView<Match> table = new TableView<>();
        table.setItems(matches);

        TableColumn<Match, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Match, String> gameCol = new TableColumn<>("Game");
        gameCol.setCellValueFactory(new PropertyValueFactory<>("game"));

        TableColumn<Match, String> pOrTCol = new TableColumn<>("Type");
        pOrTCol.setCellValueFactory(new PropertyValueFactory<>("playerTeam"));

        TableColumn<Match, String> pOrTOneCol = new TableColumn<>("Participant 1");
        pOrTOneCol.setCellValueFactory(new PropertyValueFactory<>("nameOne"));

        TableColumn<Match, String> pOrTTwoCol = new TableColumn<>("Participant 2");
        pOrTTwoCol.setCellValueFactory(new PropertyValueFactory<>("nameTwo"));

        table.getColumns().addAll(dateCol, gameCol, pOrTCol, pOrTOneCol, pOrTTwoCol);

        Button addButton = new Button("Add Match");
        addButton.setOnAction(e -> addMatch());

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(table, addButton);

        return vbox;
    }
}
