package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.DAO.GameDAO;
import hill.ascona.asconapipergames.DAO.PersonDAO;
import hill.ascona.asconapipergames.DAO.TeamDAO;
import hill.ascona.asconapipergames.entities.Game;
import hill.ascona.asconapipergames.entities.Person;
import hill.ascona.asconapipergames.entities.Team;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class TeamView {
    private final ObservableList<Team> teams = FXCollections.observableArrayList();
    private final ObservableList<Game> games = FXCollections.observableArrayList();
    private final TeamDAO teamDAO;
    private final GameDAO gameDAO;
    private final PersonDAO personDAO;
    private TableView<Team> tableView;

    public TeamView() {
        this.teamDAO = new TeamDAO();
        this.gameDAO = new GameDAO();
        this.personDAO = new PersonDAO();
    }

    public AnchorPane start() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(700, 700);

        // Create TableView
        tableView = createTableView();

        // Create input controls
        VBox inputBox = createInputBox();

        // Load initial data
        loadTeams();
        loadGames();

        // Add components to anchor pane
        anchorPane.getChildren().addAll(tableView, inputBox);

        return anchorPane;
    }

    private TableView<Team> createTableView() {
        TableView<Team> table = new TableView<>();
        table.setPrefSize(690, 225);
        table.setLayoutX(5);
        table.setLayoutY(10);

        TableColumn<Team, Integer> teamIdColumn = new TableColumn<>("Team ID");
        teamIdColumn.setCellValueFactory(new PropertyValueFactory<>("team_id"));

        TableColumn<Team, String> teamNameColumn = new TableColumn<>("Team Name");
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("team_name"));

        TableColumn<Team, String> gameNameColumn = new TableColumn<>("Game");
        gameNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getGame().getTitle())
        );

        table.getColumns().addAll(teamIdColumn, teamNameColumn, gameNameColumn);
        table.setItems(teams);

        return table;
    }



    private VBox createInputBox() {
        VBox inputBox = new VBox(10);
        inputBox.setPadding(new Insets(10));
        inputBox.setLayoutX(5);
        inputBox.setLayoutY(240);

        TextField teamNameField = new TextField();
        teamNameField.setPromptText("Enter team name");

        ComboBox<Game> gameComboBox = new ComboBox<>(games);
        gameComboBox.setPromptText("Select a game");

        Button addButton = new Button("Add New Team");
        addButton.setOnAction(event -> handleAddTeam(teamNameField, gameComboBox));

        inputBox.getChildren().addAll(teamNameField, gameComboBox, addButton);
        return inputBox;
    }

    private void handleAddTeam(TextField teamNameField, ComboBox<Game> gameComboBox) {
        String teamName = teamNameField.getText();
        Game selectedGame = gameComboBox.getValue();

        if (teamName != null && !teamName.trim().isEmpty() && selectedGame != null) {
            try {
                // Create new team with proper constructor
                Team newTeam = new Team(teamName, selectedGame);
                newTeam.setTeam_name(teamName);
                newTeam.setGame(selectedGame);

                // Save to database
                boolean saved = teamDAO.saveTeam(newTeam);

                if (saved) {
                    // Reload all teams to ensure we have the latest data
                    loadTeams();

                    // Clear input fields
                    teamNameField.clear();
                    gameComboBox.setValue(null);

                    // Show success message
                    showAlert(Alert.AlertType.INFORMATION, "Success",
                            "Team added successfully", "The new team has been added to the database.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error",
                            "Failed to add team", "There was an error saving the team to the database.");
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Error adding team", "An error occurred: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid Input",
                    "Please provide valid input", "Make sure both team name and game are selected.");
        }
    }

    private void loadTeams() {
        teams.clear();
        teams.addAll(teamDAO.getAllTeams());
        tableView.refresh();
    }

    private void loadGames() {
        games.clear();
        games.addAll(gameDAO.getAllGames());
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
