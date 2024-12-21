package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.DAO.GameDAO;
import hill.ascona.asconapipergames.entities.Game;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class GameView {
    private ObservableList<Game> games = FXCollections.observableList(new ArrayList<>());
    GameDAO gDao = new GameDAO();

    public VBox start() {
        VBox content = new VBox();

        Label btn = new Label("New game");

        games = FXCollections.observableList(gDao.getAllGames());
        content.getChildren().addAll(logView(200), btn);

        btn.setOnMouseClicked((e) -> {
            content.getChildren().add(newGame());
        });
        return content;
    }

    public VBox newGame() {
        VBox content = new VBox();

        Label title = new Label("Add New Game");

        TextField nameField = new TextField();
        nameField.setPromptText("Game title");

        TextField genreField = new TextField();
        genreField.setPromptText("Game genre");

        TextField teamsField = new TextField();
        teamsField.setPromptText("Number of teams");

        Label add = new Label("Add game");

        add.setOnMouseClicked((e) -> {
            String gameTitle = nameField.getText();
            String gameGenre = genreField.getText();
            String teamsInput = teamsField.getText();

            if (!gameTitle.isEmpty() && !gameGenre.isEmpty() && !teamsInput.isEmpty()) {
                try {
                    int numberOfTeams = Integer.parseInt(teamsInput);
                    Game newGame = new Game(gameTitle, gameGenre, numberOfTeams);
                    gDao.saveGame(newGame);
                    games.add(newGame);
                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Number of teams must be a valid integer.");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("All fields must be filled out.");
                alert.show();
            }
        });

        content.getChildren().addAll(title, nameField, genreField, teamsField, add);
        return content;
    }

    public VBox logView(int height) {
        VBox content = new VBox();

        ScrollPane scrollPane = new ScrollPane();

        VBox logBox = new VBox();
        logBox.setPrefHeight(height);
        logBox.setPrefWidth(300);
        content.setPadding(new Insets(15));

        for (Game game : games) {
            logBox.getChildren().addAll(rowBox(game));
        }

        games.addListener(new ListChangeListener<Game>() {
            @Override
            public void onChanged(Change<? extends Game> change) {
                logBox.getChildren().clear();
                for (Game game : change.getList()) {
                    logBox.getChildren().addAll(rowBox(game));
                }
            }
        });

        scrollPane.setContent(logBox);
        scrollPane.setFitToWidth(true);
        content.getChildren().add(scrollPane);
        return content;
    }

    private HBox rowBox(Game game) {
        Label idLab = new Label("ID: " + game.getId());

        Label titleLab = new Label("Title: " + game.getTitle());

        Label genreLab = new Label("Genre: " + game.getGenre());

        Label teamsLab = new Label("Teams: " + game.getNumberOfTeams());

        Label delete = new Label("<(^^)>");

        HBox mBox = new HBox();
        mBox.setSpacing(10);
        mBox.getChildren().addAll(idLab, titleLab, genreLab, teamsLab, delete);

        mBox.setOnMouseClicked((e) -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                    "Do you want to delete the game: " + game.getTitle() + "?",
                    ButtonType.YES, ButtonType.NO);
            confirmation.showAndWait();

            if (confirmation.getResult() == ButtonType.YES) {
                gDao.deleteGame(game); // Radera från databasen
                games.remove(game); // Ta bort från ObservableList
            }
        });

        return mBox;
    }
}
