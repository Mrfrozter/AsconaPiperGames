package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.DAO.GameDAO;
import hill.ascona.asconapipergames.entities.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class GameView {
    private ObservableList<Game> games = FXCollections.observableList(new ArrayList<>());
    GameDAO gDao = new GameDAO();

    public AnchorPane start() {
        TabPane gameTabPane = new TabPane();
        gameTabPane.setPrefSize(700, 600);

        Tab tab1 = new Tab("View Games", logView());
        Tab tab2 = new Tab("Add Game", newGameView());

        gameTabPane.getTabs().add(tab1);
        gameTabPane.getTabs().add(tab2);

        return new AnchorPane(gameTabPane);
    }

    private AnchorPane logView() {
        AnchorPane anchorPane = new AnchorPane();

        Label title = new Label("Games List");
        title.setFont(new Font("Cambria Bold", 16));
        title.setLayoutX(15);
        title.setLayoutY(15);

        TableView<Game> tableView = new TableView<>();
        tableView.setEditable(false);
        tableView.setPrefSize(650, 400);
        tableView.setLayoutX(15);
        tableView.setLayoutY(50);

        TableColumn<Game, Integer> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Game, String> column2 = new TableColumn<>("Title");
        column2.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Game, String> column3 = new TableColumn<>("Genre");
        column3.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<Game, Integer> column4 = new TableColumn<>("Teams");
        column4.setCellValueFactory(new PropertyValueFactory<>("numberOfTeams"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);

        tableView.setItems(games);

        // Click for deletion
        tableView.setRowFactory(tv -> {
            TableRow<Game> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Game selectedGame = row.getItem();
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                            "Do you want to delete the game: " + selectedGame.getTitle() + "?",
                            ButtonType.YES, ButtonType.NO);
                    confirmation.showAndWait();

                    if (confirmation.getResult() == ButtonType.YES) {
                        gDao.deleteGame(selectedGame); // Delete from database
                        games.remove(selectedGame); // Remove from list
                    }
                }
            });
            return row;
        });

        Button refreshButton = new Button("Refresh");
        refreshButton.setLayoutX(15);
        refreshButton.setLayoutY(470);
        refreshButton.setOnAction(e -> {
            games.clear();
            games.addAll(gDao.getAllGames());
        });

        anchorPane.getChildren().add(title);
        anchorPane.getChildren().add(tableView);
        anchorPane.getChildren().add(refreshButton);
        return anchorPane;
    }

    private AnchorPane newGameView() {
        AnchorPane anchorPane = new AnchorPane();

        Label title = new Label("Add New Game");
        title.setFont(new Font("Cambria Bold", 16));
        title.setLayoutX(15);
        title.setLayoutY(15);

        TextField nameField = new TextField();
        nameField.setPromptText("Game title");
        nameField.setLayoutX(15);
        nameField.setLayoutY(50);
        nameField.setPrefWidth(200);

        TextField genreField = new TextField();
        genreField.setPromptText("Game genre");
        genreField.setLayoutX(15);
        genreField.setLayoutY(90);
        genreField.setPrefWidth(200);

        TextField teamsField = new TextField();
        teamsField.setPromptText("Number of teams");
        teamsField.setLayoutX(15);
        teamsField.setLayoutY(130);
        teamsField.setPrefWidth(200);

        Button addButton = new Button("Add Game");
        addButton.setLayoutX(15);
        addButton.setLayoutY(170);
        addButton.setOnAction(e -> {
            String gameTitle = nameField.getText();
            String gameGenre = genreField.getText();
            String teamsInput = teamsField.getText();

            if (!gameTitle.isEmpty() && !gameGenre.isEmpty() && !teamsInput.isEmpty()) {
                try {
                    int numberOfTeams = Integer.parseInt(teamsInput);
                    Game newGame = new Game(gameTitle, gameGenre, numberOfTeams);
                    gDao.saveGame(newGame);
                    games.add(newGame);
                    nameField.clear();
                    genreField.clear();
                    teamsField.clear();
                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Antal team måste ha valid integer.");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Alla fält måste vara inskrivna.");
                alert.show();
            }
        });

        anchorPane.getChildren().add(title);
        anchorPane.getChildren().add(nameField);
        anchorPane.getChildren().add(genreField);
        anchorPane.getChildren().add(teamsField);
        anchorPane.getChildren().add(addButton);
        return anchorPane;
    }
}


