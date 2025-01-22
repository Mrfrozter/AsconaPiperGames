package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.DAO.GameDAO;
import hill.ascona.asconapipergames.DAO.PersonDAO;
import hill.ascona.asconapipergames.DAO.TeamDAO;
import hill.ascona.asconapipergames.entities.Game;
import hill.ascona.asconapipergames.entities.Person;
import hill.ascona.asconapipergames.entities.Team;
import javafx.application.Platform;
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
    //tillagd:
    private ObservableList<Person> players = FXCollections.observableList(new ArrayList<>());

    private TeamDAO teamDAO = new TeamDAO();
    private PersonDAO personDAO = new PersonDAO();
    private GameDAO gameDAO = new GameDAO();

    public AnchorPane start() {
        TabPane tabPane = new TabPane();
        tabPane.setPrefSize(700,600);

        Tab tab1 = new Tab("Add/Remove Team", teamsView());
        Tab tab2 = new Tab("View Members/Players", membersView());
        Tab tab3 = new Tab("Edit/Update teams", editTeamsView());

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);

        loadPlayers();

        tab2.setOnSelectionChanged(event -> {
            if (tab2.isSelected()) {
                loadPlayers();
            }
        });

        loadGames();

        return new AnchorPane(tabPane);
    }

    private AnchorPane editTeamsView(){
        AnchorPane anchorPane = new AnchorPane();

        loadTeams();

        TableView<Team> table = new TableView<>();

        table.setPrefSize(690, 225);
        table.setLayoutX(5);
        table.setLayoutY(10);

        TableColumn<Team, Integer> teamIdColumn = new TableColumn<>("Team ID");
        teamIdColumn.setCellValueFactory(new PropertyValueFactory<>("teamId"));

        TableColumn<Team, String> teamNameColumn = new TableColumn<>("Team Name");
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));

        TableColumn<Team, String> gameNameColumn = new TableColumn<>("Game");
        gameNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getGame().getTitle())
        );

        table.getColumns().addAll(teamIdColumn, teamNameColumn, gameNameColumn);

        table.setItems(teams);


        TextField teamNameField = new TextField();
        teamNameField.setPromptText("Enter new team name");
        teamNameField.setLayoutX(15);
        teamNameField.setLayoutY(260);

        ComboBox<Game> gameComboBox = new ComboBox<>(games);
        gameComboBox.setPromptText("Select new game");
        gameComboBox.setLayoutX(15);
        gameComboBox.setLayoutY(300);

        Button addButton = new Button("Update team");
        addButton.setLayoutX(15);
        addButton.setLayoutY(340);
        gameComboBox.setOnShowing(event -> loadGames());

        addButton.setOnAction(e -> {

            Team selectedTeam = table.getSelectionModel().getSelectedItem();
            if(selectedTeam == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select a team");
                alert.show();
                return;
            }

            String newTeamName = teamNameField.getText().trim();
            Game selectedGame = gameComboBox.getValue();

            if (!newTeamName.isEmpty()) {
                selectedTeam.setTeamName(newTeamName);
            }

            if (selectedGame!= null){
                selectedTeam.setGame(selectedGame);
            }

            try {
                teamDAO.updateTeam(selectedTeam);
                loadTeams();
            } catch(Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("An error has occured while updating the team");
                alert.show();
            }

        });

        anchorPane.getChildren().add(table);
        anchorPane.getChildren().add(teamNameField);
        anchorPane.getChildren().add(gameComboBox);
        anchorPane.getChildren().add(addButton);




        return anchorPane;
    }

    private AnchorPane membersView(){

        AnchorPane anchorPane = new AnchorPane();

        loadTeams();

        ListView<Team> listView = new ListView(teams);

        // "Sätter cell factory" för att displaya namnet på teamet i ListView (Förkortar)
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Team team, boolean empty) {
                super.updateItem(team, empty);
                setText(empty || team == null ? null : team.getTeamName());
            }
        });

        listView.setPrefSize(135,300);
        listView.setLayoutX(5);
        listView.setLayoutY(100);

        TableView<Person> playersTable = new TableView<>();


        TableColumn<Person, Integer> playerIdColumn = new TableColumn<>("Player ID");
        playerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Person, String> playerNameColumn = new TableColumn<>("First Name");
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Person, String> playerLastNameColumn = new TableColumn<>("Last name");
        playerLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));

        TableColumn<Person, String> playerNicknameColumn = new TableColumn<>("Nickname");
        playerNicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));


        playersTable.getColumns().addAll(playerIdColumn, playerNameColumn, playerLastNameColumn, playerNicknameColumn);


        playersTable.setPrefSize(375, 225);
        playersTable.setLayoutX(250);
        playersTable.setLayoutY(100);

        ObservableList<Person> members = FXCollections.observableArrayList();

        //Sätter items för playersTable
        playersTable.setItems(members);

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                members.clear();
                members.addAll(personDAO.getMembersByTeam(newValue));
            }
        });

        //REMOVE OCH ADD KNAPPAR SAMT COMBOBOX MED SPELARE
        //Remove
        Button removeButton = new Button("Remove member from team");
        removeButton.setLayoutX(260);
        removeButton.setLayoutY(350);

        removeButton.setOnAction(e -> {
            Person selectedPlayer = playersTable.getSelectionModel().getSelectedItem();

                    selectedPlayer.setTeam(null);
                    personDAO.updatePlayersInfo(selectedPlayer);

                    members.remove(selectedPlayer);

        });

        //Add, här finns en ComboBox spelare. Om man väljer en spelare som har ett lag byter spelaren lag
        Button addButton = new Button("Transfer player to team");
        addButton.setLayoutX(460);
        addButton.setLayoutY(350);


        ComboBox<Person> playersComboBox = new ComboBox<>(players);
        playersComboBox.setPromptText("Select a player");
        playersComboBox.setLayoutX(460);
        playersComboBox.setLayoutY(390);
        playersComboBox.setOnShowing(event -> loadPlayers());

        addButton.setOnAction(e -> {
            Person selectedPlayer = playersComboBox.getSelectionModel().getSelectedItem();
            Team selectedTeam = listView.getSelectionModel().getSelectedItem(); // Get selected team from the ListView

            if (selectedPlayer != null && selectedTeam != null) {
                // Kollar om spelaren redan tillhör ett lag
                if (selectedPlayer.getTeam() != null) {
                    // Om spelaren har ett lag uppdaterar vi laget
                    selectedPlayer.setTeam(selectedTeam);
                    personDAO.updatePlayersInfo(selectedPlayer);
                } else {
                    // Om spelaren inte har ett lag så får han ett nytt
                    selectedPlayer.setTeam(selectedTeam);
                    personDAO.updatePlayersInfo(selectedPlayer);
                }
            }
        });

        anchorPane.getChildren().add(listView);
        anchorPane.getChildren().add(playersTable);
        anchorPane.getChildren().add(addButton);
        anchorPane.getChildren().add(removeButton);
        anchorPane.getChildren().add(playersComboBox);

        return anchorPane;
    }

    private AnchorPane teamsView(){
        AnchorPane anchorPane = new AnchorPane();

        TableView<Team> table = new TableView<>();

        table.setPrefSize(690, 225);
        table.setLayoutX(5);
        table.setLayoutY(10);

        TableColumn<Team, Integer> teamIdColumn = new TableColumn<>("Team ID");
        teamIdColumn.setCellValueFactory(new PropertyValueFactory<>("teamId"));

        TableColumn<Team, String> teamNameColumn = new TableColumn<>("Team Name");
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));

        TableColumn<Team, String> gameNameColumn = new TableColumn<>("Game");
        gameNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getGame().getTitle())
        );

        table.getColumns().addAll(teamIdColumn, teamNameColumn, gameNameColumn);

        table.setItems(teams);

        //OG FRÅN LAURI

        // Click for deletion - Modifierad från Lauri
        table.setRowFactory(tv -> {
            TableRow<Team> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Team selectedTeam = row.getItem();
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                            "Do you want to delete the team: " + selectedTeam.getTeamName() + "?",
                            ButtonType.YES, ButtonType.NO);
                    confirmation.showAndWait();

                    if (confirmation.getResult() == ButtonType.YES) {
                        teamDAO.deleteTeam(selectedTeam);
                        teams.remove(selectedTeam);
                    }
                }
            });
            return row;
        });


        // Click for deletion - Modifierad från Lauri (GAMLA)
        //Fanns bug att det inte togs bort från listan, därmed modifierad loadTeams och runLater
        /*table.setRowFactory(tv -> {
            TableRow<Team> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Team selectedTeam = row.getItem();
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                            "Do you want to delete the team: " + selectedTeam.getTeamName() + "?",
                            ButtonType.YES, ButtonType.NO);
                    confirmation.showAndWait();

                    if (confirmation.getResult() == ButtonType.YES) {
                        try {
                            teamDAO.deleteTeam(selectedTeam);
                            Platform.runLater(() -> loadTeams()); // Use Platform.runLater
                        } catch (Exception e) {
                            Alert error = new Alert(Alert.AlertType.ERROR);
                            error.setContentText("Could not delete team: " + e.getMessage());
                            error.show();
                        }
                    }
                }
            });
            return row;
        }); */

        //NYA MODIFERAD FRÅN LAURI
        /*table.setRowFactory(tv -> {
            TableRow<Team> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Team selectedTeam = row.getItem();
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                            "Do you want to delete the team: " + selectedTeam.getTeamName() + "?",
                            ButtonType.YES, ButtonType.NO);
                    confirmation.showAndWait();

                    if (confirmation.getResult() == ButtonType.YES) {
                        // Spara gamla listan
                        ObservableList<Team> oldTeams = FXCollections.observableArrayList(teams);

                        try {
                            teamDAO.deleteTeam(selectedTeam);
                            // Ladda om teams från databasen för att bekräfta att raderingen lyckades
                            loadTeams();
                        } catch (Exception e) {
                            // Om något går fel, återställ listan och visa felmeddelande
                            teams.clear();
                            teams.addAll(oldTeams);
                            Alert error = new Alert(Alert.AlertType.ERROR);
                            error.setContentText("Could not delete team. Make sure all player connections are removed first.");
                            error.show();
                        }
                    }
                }
            });
            return row;
        }); */


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
        gameComboBox.setOnShowing(event -> loadGames());

        addButton.setOnAction(e -> {
            String teamName = teamNameField.getText();
            Game chosenGame = gameComboBox.getValue();

            if (teamName == null || teamName.trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Team name must not be empty.");
                alert.show();
                return;
            }

            if (chosenGame == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select a game.");
                alert.show();
                return;
            }

            try {
                Team newTeam = new Team(teamName, null, chosenGame);
                teamDAO.saveTeam(newTeam);
                teams.add(newTeam);

                teamNameField.clear();
                gameComboBox.getSelectionModel().clearSelection();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("An error occurred while creating the team: " + ex.getMessage());
                alert.show();
            }
        });

        anchorPane.getChildren().add(table);
        anchorPane.getChildren().add(teamNameField);
        anchorPane.getChildren().add(gameComboBox);
        anchorPane.getChildren().add(addButton);

        return anchorPane;
    }

    private void loadGames() {
        games.clear();
        games.addAll(gameDAO.getAllGames());
    }

    private void loadTeams() {
        teams.clear();
        teams.addAll(teamDAO.getAllTeams());
    }

    private void loadPlayers() {
        players.clear();
        players.addAll(personDAO.getAllPlayersOrUsers("player"));
    }

}
