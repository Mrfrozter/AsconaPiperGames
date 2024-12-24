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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class TeamView {
    private ObservableList<Team> teams = FXCollections.observableList(new ArrayList<>());
    private ObservableList<Game> games = FXCollections.observableList(new ArrayList<>());

    TeamDAO teamDAO = new TeamDAO();
    PersonDAO personDAO = new PersonDAO();
    GameDAO gameDAO = new GameDAO();

    public AnchorPane start() {
        TabPane tabPane = new TabPane();
        tabPane.setPrefSize(700,600);

        Tab tab1 = new Tab("Add/Remove Team", logView());
        Tab tab2 = new Tab("Add/Remove Members", membersView());

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);

        loadGames();

        return new AnchorPane(tabPane);
    }

    private AnchorPane membersView(){

        AnchorPane anchorPane = new AnchorPane();

        loadTeams();

        ListView<Team> listView = new ListView(teams);

        // Set the cell factory to display the team name in the ListView
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Team team, boolean empty) {
                super.updateItem(team, empty);
                setText(empty || team == null ? null : team.getTeam_name());
            }
        });

        listView.setPrefSize(135,300);
        listView.setLayoutX(5);
        listView.setLayoutY(100);

        TableView<Person> playersTable = new TableView<>();


        TableColumn<Person, Integer> playerIdColumn = new TableColumn<>("Player ID");
        playerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Person, String> playerNameColumn = new TableColumn<>("Player Name");
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Person, String> playerNicknameColumn = new TableColumn<>("Nickname");
        playerNicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));


        playersTable.getColumns().addAll(playerIdColumn, playerNameColumn, playerNicknameColumn);


        playersTable.setPrefSize(375, 225);
        playersTable.setLayoutX(250);
        playersTable.setLayoutY(100);

        ObservableList<Person> members = FXCollections.observableArrayList();

        // Set items for players table
        playersTable.setItems(members);

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                members.clear();
                members.addAll(personDAO.getMembersByTeam(newValue));
            }
        });

        anchorPane.getChildren().add(listView);
        anchorPane.getChildren().add(playersTable);

        return anchorPane;
    }

    private AnchorPane logView(){
        AnchorPane anchorPane = new AnchorPane();

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

        //Knapp för att refresha listan
        Button refreshButton = new Button("Refresh");
        refreshButton.setLayoutX(15);
        refreshButton.setLayoutY(470);
        refreshButton.setOnAction(e -> {
            teams.clear();
            teams.addAll(teamDAO.getAllTeams());
        });


        // Click for deletion - Modifierad från Lauri
        table.setRowFactory(tv -> {
            TableRow<Team> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Team selectedTeam = row.getItem();
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                            "Do you want to delete the team: " + selectedTeam.getTeam_name() + "?",
                            ButtonType.YES, ButtonType.NO);
                    confirmation.showAndWait();

                    if (confirmation.getResult() == ButtonType.YES) {
                        teamDAO.deleteTeam(selectedTeam); // Delete from database
                        teams.remove(selectedTeam); // Remove from list
                    }
                }
            });
            return row;
        });


        TextField teamNameField = new TextField();
        teamNameField.setPromptText("Enter team name");
        teamNameField.setLayoutX(15);
        teamNameField.setLayoutY(260);

        ComboBox<Game> gameComboBox = new ComboBox<>(games);
        gameComboBox.setPromptText("Select a game");
        gameComboBox.setLayoutX(15);
        gameComboBox.setLayoutY(300);

        Button addButton = new Button("Add New Team");
        addButton.setLayoutX(15);
        addButton.setLayoutY(340);

        //"Gammal" testmetod
        /*addButton.setOnAction(e -> {
            Team addedTeam = new Team(teamNameField.getText(), gameComboBox.getValue());
            teamDAO.saveTeam(addedTeam);
            teams.add(addedTeam);
            teams.clear();
            teams.addAll(teamDAO.getAllTeams());
        }); */

        addButton.setOnAction(e -> {
            String teamName = teamNameField.getText();
            Game chosenGame = gameComboBox.getValue();

            if (!teamName.isEmpty() && !chosenGame.getTeams().isEmpty()){
                try {
                    //int numberOfTeams = Integer.parseInt(teamsInput);
                    Team newTeam = new Team(teamName, null, chosenGame);
                    teamDAO.saveTeam(newTeam);
                    teams.add(newTeam);
                    teamNameField.clear();
                    //genreField.clear();
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

        anchorPane.getChildren().add(table);
        anchorPane.getChildren().add(refreshButton);
        anchorPane.getChildren().add(teamNameField);
        anchorPane.getChildren().add(gameComboBox);
        anchorPane.getChildren().add(addButton);

        return anchorPane;
    }

    /*public AnchorPane start() {
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
    } */

    /*private TableView<Team> createTableView() {
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
    } */

    /*private void loadTeams() {
        teams.clear();
        teams.addAll(teamDAO.getAllTeams());
        tableView.refresh();
    } */

    private void loadGames() {
        games.clear();
        games.addAll(gameDAO.getAllGames());
    }

    private void loadTeams() {
        teams.clear();
        teams.addAll(teamDAO.getAllTeams());
    }



    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
