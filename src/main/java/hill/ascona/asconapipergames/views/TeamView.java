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
        Tab tab2 = new Tab("View Members/Players", membersView());

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);

        loadGames();

        return new AnchorPane(tabPane);
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
                setText(empty || team == null ? null : team.getTeam_name());
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

        //REMOVE OCH ADD KNAPPAR SAMT COMBOBOX MED SPELARE UTAN LAG
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

        ComboBox<Person> playersComboBox = new ComboBox<>();
        playersComboBox.setPromptText("Select a player");
        playersComboBox.setLayoutX(460);
        playersComboBox.setLayoutY(390);

        // Tar alla spelare från databasen
        ObservableList<Person> allPlayers = FXCollections.observableArrayList(personDAO.getAllPlayersOrUsers("player"));
        playersComboBox.setItems(allPlayers);

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
                        teamDAO.deleteTeam(selectedTeam);
                        teams.remove(selectedTeam);
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

}
