package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.DAO.GameDAO;
import hill.ascona.asconapipergames.DAO.MatchDAO;
import hill.ascona.asconapipergames.DAO.PersonDAO;
import hill.ascona.asconapipergames.DAO.TeamDAO;
import hill.ascona.asconapipergames.entities.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
När personalen listar matcher ska det synas vilka spelare/lag som tävlar mot
varandra samt om matchen är kommande eller avgjord, om matchen är avgjord ska
det gå att se vem som blev vinnare. Här implementeras med fördel funktionalitet för
att lista samtliga matcher, endast avgjorda och endast kommande matcher.
• Matcher ska alltid ha ett datum.
• Matchen avgörs (poängen sätts) genom att Pied Pipers personal loggar in och
registrerar resultatet.
• */

public class MatchView {
    private MatchDAO matchDAO = new MatchDAO();
    private String gameChosen = "";
    private Boolean team = false;
    private Boolean upcoming = true;
    private String pOrTString = "Player";
    private int turneringarId;
    private String date = "";
    private boolean allreadyPlayed = false;
    private boolean singelNotTeam = true;
    private  String gameName;
    private Game gamePlay;
    private String player1Nickname;
    private String player2Nickname;
    private String team1Name;
    private String team2Name;
    private int winnerIdSend;
    private String nameOne;
    private String nameTwo;
    private Person player1;
    private Person player2;
    private Team team1;
    private Team team2;
    private ObservableList<Match> matches = FXCollections.observableList(new ArrayList<>());
    private List<Match> matchSimple = new ArrayList<>();


    public void addMatch(){
        AnchorPane paneAdd = new AnchorPane();
        paneAdd.setPrefSize(540, 250);
        Scene scene2 = new Scene(paneAdd);
        Stage stage2 = new Stage();
        stage2.setScene(scene2);
        stage2.show();
        PersonDAO pDao = new PersonDAO();
        List<Person> persons = pDao.getAllPlayersOrUsers("Player");
        TeamDAO teamDao = new TeamDAO();
        List<Team> teams = teamDao.getAllTeams();


        //----------------------------------------------------------------------Labels---------------

        Label nowShowing = new Label("ADD A PLAYER MATCH");
        nowShowing.setFont(new Font("Arial bold", 12));

        Label labelGame = new Label("Game: ");
        Label labelPOrTOne = new Label(pOrTString + " one: ");
        Label labelPOrTwo = new Label(pOrTString + " two: ");
        Label labelDate = new Label("Date: ");
        Label labelUpcoming = new Label("Upcoming: ");
        Label labelWinner = new Label("Winner: ");


        //----------------------------------------------------------------------ComboBoxes---------------

        ComboBox<String> comboBoxPOrT1 =new ComboBox<>();
        comboBoxPOrT1.setPromptText("Picka a game first");
        comboBoxPOrT1.setDisable(true);
        ComboBox<String> comboBoxPOrT2 =new ComboBox<>();
        comboBoxPOrT2.setPromptText("Picka a game first");
        comboBoxPOrT2.setDisable(true);


        ComboBox<String> comboBoxGame =new ComboBox<>();
        comboBoxGame.setPromptText("Choose game");
        GameDAO gDao = new GameDAO();
        List<Game> games = gDao.getAllGames();
        for (Game game : games) {
            comboBoxGame.getItems().add(game.getTitle());
        }
        comboBoxGame.setOnAction(e ->{
            gameChosen = (String) comboBoxGame.getValue();
            gamePlay = gDao.getByName(gameChosen);
            comboBoxPOrT1.setDisable(false);
            comboBoxPOrT2.setDisable(false);
            comboBoxPOrT1.setPromptText("Choose first participant");
            comboBoxPOrT2.setPromptText("Choose second participant");
            comboBoxPOrT1.getItems().removeAll(comboBoxPOrT1.getItems());
            comboBoxPOrT2.getItems().removeAll(comboBoxPOrT2.getItems());
            if (singelNotTeam) {
                for (Person person : persons) {
                    comboBoxPOrT1.getItems().add(person.getNickname());
                    comboBoxPOrT2.getItems().add(person.getNickname());
                }
            }else{
                int teamsCount= 0;
                for (Team team : teams) {
                    if (team.getGame_id()==gamePlay.getId()){
                        teamsCount++;
                        comboBoxPOrT1.getItems().add(team.getTeam_name());               // TODO ----get teams-------------
                        comboBoxPOrT2.getItems().add(team.getTeam_name());
                    }
                    if (teamsCount==0){
                        comboBoxPOrT1.setPromptText("No teams for that game");
                        comboBoxPOrT2.setPromptText("No teams for that game");
                    }
                }
            }
        });


        comboBoxPOrT1.setDisable(true);
        comboBoxPOrT1.setOnAction(e ->{
            nameOne = comboBoxPOrT1.getValue();
            if (singelNotTeam) {
                player1Nickname = nameOne;
                player1 = pDao.getByNickname(player1Nickname);                   // TODO -----borde vara getByName???????------------
            }else {
                team1Name = nameOne;
                team1 = teamDao.getTeamByName(team1Name);
            }
        });

                     // TODO ----------------------
      comboBoxPOrT2.setOnAction(e ->{
            nameTwo = comboBoxPOrT2.getValue();
            if (singelNotTeam) {                                                // TODO ----------------------
                player2Nickname = nameOne;
                player2 = pDao.getByNickname(player2Nickname);
            }else {                                                                  // TODO ----------------------
                team2Name = nameOne;
                team2 = teamDao.getTeamByName(team2Name);
            }
        });

        ComboBox<String> comboBoxWinner =new ComboBox<>();
        comboBoxWinner.setPromptText("Enter winner");
        comboBoxWinner.setDisable(true);                                                                 // TODO ----------------------
        comboBoxWinner.getItems().addAll("ADD Players/teams HERE", "Medium"); // TODO ----------------------
        comboBoxWinner.setOnAction(e ->{
            winnerIdSend=1;                                                     // TODO ----------------------
        });


        DatePicker datePicker = new DatePicker();                                   // ??? ----------------------
        datePicker.setPromptText("Date is required");
        datePicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                LocalDate dateLocal = datePicker.getValue();
                date = dateLocal.toString();
            }
        });


        //----------------------------------------------------------------------Checkbox---------------
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(true);
        checkBox.setOnAction(event -> {
            allreadyPlayed=!allreadyPlayed;
            comboBoxWinner.setDisable(!allreadyPlayed);
            if (allreadyPlayed) {
                comboBoxWinner.getItems().addAll(nameOne,nameTwo);                 // TODO -------fyll i addAll---------------
            }else{
                comboBoxWinner.getItems().removeAll(nameOne,nameTwo);
            }
        });
        //----------------------------------------------------------------------Buttons---------------

        Button pOrTButton = new Button("Add a team match");
        pOrTButton.setOnAction(event -> {
                    singelNotTeam=!singelNotTeam;
                    comboBoxPOrT1.setPromptText("Choose first participant");     // TODO ----------------
                    comboBoxPOrT2.setPromptText("Choose second participant");
                    comboBoxPOrT1.getItems().removeAll(comboBoxPOrT1.getItems());
                    comboBoxPOrT2.getItems().removeAll(comboBoxPOrT2.getItems());
                    if(singelNotTeam){
                        pOrTButton.setText("Add a team match");
                        pOrTString = "Player";
                        nowShowing.setText("ADD A PLAYER MATCH");
                        for (Team team : teams) {
                            comboBoxPOrT1.getItems().add(team.getTeam_name());               // TODO ----get teams-------------
                            comboBoxPOrT2.getItems().add(team.getTeam_name());
                        }

                    }else{
                        pOrTButton.setText("Add a player match");
                        pOrTString = "Team";
                        nowShowing.setText("ADD A TEAM MATCH");
                        for (Person person : persons) {
                            comboBoxPOrT1.getItems().add(person.getNickname());
                            comboBoxPOrT2.getItems().add(person.getNickname());
                        }
                    }
                    labelPOrTOne.setText(pOrTString + " one: ");
                    labelPOrTwo.setText(pOrTString + " two: ");
                    //update participant comboboxes                                // TODO ----------------------
                }
        );

        Button cancel = new Button("Cancel");
        cancel.setOnAction( event ->{
            stage2.close();
                }
        );

        Button saveTheMatch = new Button("Save match");
        saveTheMatch.setOnAction(event -> {
            Match matchTemp = new Match(date, allreadyPlayed, pOrTString, gamePlay, nameOne, nameTwo);
            if (singelNotTeam) {
                matchTemp.getPlayers().add(player1);
                matchTemp.getPlayers().add(player2);
            }else {
                matchTemp.getTeams().add(team1);
                matchTemp.getTeams().add(team2);
            }
            matchDAO.saveMatch(matchTemp);
            matches.add(matchTemp);
//            matches.clear();
//            matches.addAll(matchDAO.getAllMatches());


         //   stage2.close(); ///// nollställ istället?
        });

        //----------------------------------------------------------------------Layout---------------
        nowShowing.setLayoutX(20);
        nowShowing.setLayoutY(20);



        labelGame.setLayoutX(20);
        labelGame.setLayoutY(55);
        labelPOrTOne.setLayoutX(20);
        labelPOrTOne.setLayoutY(90);
        labelUpcoming.setLayoutX(20);
        labelUpcoming.setLayoutY(150);

        comboBoxGame.setLayoutX(85);
        comboBoxGame.setLayoutY(55);
        comboBoxPOrT1.setLayoutX(85);
        comboBoxPOrT1.setLayoutY(90);
        checkBox.setLayoutX(90);
        checkBox.setLayoutY(150);
        comboBoxWinner.setLayoutX(340);
        comboBoxWinner.setLayoutY(150);


        labelDate.setLayoutX(270);
        labelDate.setLayoutY(55);
        labelPOrTwo.setLayoutX(270);
        labelPOrTwo.setLayoutY(90);
        labelWinner.setLayoutX(270);
        labelWinner.setLayoutY(150);

        pOrTButton.setLayoutX(20);
        pOrTButton.setLayoutY(210);

        cancel.setLayoutX(270);
        cancel.setLayoutY(210);

        datePicker.setLayoutX(340);
        datePicker.setLayoutY(55);
        comboBoxPOrT2.setLayoutX(340);
        comboBoxPOrT2.setLayoutY(90);
        saveTheMatch.setLayoutX(340);
        saveTheMatch.setLayoutY(210);


        paneAdd.getChildren().addAll(checkBox, labelGame, labelPOrTOne,labelDate,
                labelWinner,pOrTButton, nowShowing, labelUpcoming, labelPOrTwo,
                comboBoxGame,comboBoxPOrT1,comboBoxPOrT2,comboBoxWinner,
                datePicker,cancel, saveTheMatch);
    }


    public AnchorPane start() { //----Show Matches----

        AnchorPane paneShow = new AnchorPane();
        paneShow.setPrefSize(550, 600);

        //----TableView-------------------------------------------------------------------------------


      matches = FXCollections.observableList(matchDAO.getAllMatches());
        //   matches.addAll(matchDAO.getAllMatches());

        TableView<Match> table = new TableView<>();
        table.setEditable(true);    //???????
        table.setPrefWidth(530);

        TableColumn<Match,String> dateCol = new TableColumn<>("Date");
        dateCol.setPrefWidth(70);
        dateCol.setCellValueFactory(
                new PropertyValueFactory<>("date")
        );
        TableColumn<Match,Game> gameCol = new TableColumn<>("Game");
        gameCol.setPrefWidth(90);
        gameCol.setCellValueFactory(
                new PropertyValueFactory<>("game")
        );
        TableColumn<Match,String> pOrTCol = new TableColumn<>("Team/\nSingel\nplayer");
        pOrTCol.setPrefWidth(50);
        pOrTCol.setCellValueFactory(
                new PropertyValueFactory<>("playerTeam")
        );
        TableColumn<Match,String> pOrTOneCol = new TableColumn<>("Participant 1");
        pOrTOneCol.setPrefWidth(80);
        pOrTOneCol.setCellValueFactory(
                new PropertyValueFactory<>("nameOne")
        );
        TableColumn<Match,String> pOrTTwoCol = new TableColumn<>("Participant 2");
        pOrTTwoCol.setPrefWidth(80);
        pOrTTwoCol.setCellValueFactory(
                new PropertyValueFactory<>("nameTwo")
        );
        TableColumn<Match, Boolean> decidedCol = new TableColumn<>("Played?");
        decidedCol.setPrefWidth(80);
        decidedCol.setCellValueFactory(
                new PropertyValueFactory<>("allreadyPlayed")
        );
        TableColumn<Match,String> winnerCol = new TableColumn<>("Winner");
        winnerCol.setPrefWidth(80);
        winnerCol.setCellValueFactory(
                new PropertyValueFactory<>("winnerId")


        );

        table.setItems(matches);
        gameCol.setCellFactory(c -> new TableCell<Match, Game>() {
            @Override
            public void updateItem(Game game, boolean empty) {
                super.updateItem(game, empty);
                if (!empty)
                    setText(game.getTitle());
                else
                    setText(null);
            }
        });

/*                decidedCol.setCellFactory(column -> new CheckBoxTableCell<>());
                decidedCol.setCellValueFactory(cellData -> {
                table cellValue = cellData.getValue();
                allreadyPlayed property = cellValue.choosedProperty();

                // Add listener to handler change
                property.addListener((observable, oldValue, newValue) -> cellValue.setChoosed(newValue));

                return property;
            });*/

        table.getColumns().addAll(dateCol, gameCol, pOrTCol, winnerCol, pOrTOneCol, pOrTTwoCol, decidedCol);
        table.setPlaceholder(
                new Label("No matches like that in the database. Try adding one!"));



       // matches.addListener(new ListChangeListener<>();


        //----End TableView----


        HBox hBox = new HBox();
        VBox vbox = new VBox();

        Button buttonDelete = new Button("Delete match");
        buttonDelete.setDisable(true);
        buttonDelete.setOnAction(event -> {
            buttonDelete.setDisable(true);
            matchDAO.deleteMatch(table.getSelectionModel().getSelectedItem());
            matches.remove(table.getSelectionModel().getSelectedItem());
        });

        Button buttonNew = new Button("Add a new match");
        buttonNew.setOnAction(event -> {
            buttonDelete.setDisable(true);
            addMatch();
        });

/*        Button buttonEdit = new Button("Edit/see details of match");
        buttonEdit.setDisable(true);
        buttonEdit.setOnAction(event -> {

        });*/


        table.setOnMouseClicked((e) -> {
            buttonDelete.setDisable(false);
        });


        ToggleGroup togglePorT = new ToggleGroup();

        RadioButton r1 = new RadioButton("Show both upcoming and finished matches");
        RadioButton r2 = new RadioButton("Show only upcoming matches");
        RadioButton r3 = new RadioButton("Show only finished matches");

        r1.setToggleGroup(togglePorT);
        r2.setToggleGroup(togglePorT);
        r3.setToggleGroup(togglePorT);
        togglePorT.selectToggle(r1);

        VBox r = new VBox();
        r.setSpacing(10);
        r.getChildren().add(r1);
        r.getChildren().add(r2);
        r.getChildren().add(r3);

        VBox buttons = new VBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(buttonNew,  buttonDelete); //buttonEdit,
        hBox.setSpacing(20);
        vbox.setSpacing(10);
        hBox.getChildren().addAll(r,buttons);
        vbox.getChildren().addAll(table, hBox);
        vbox.setPadding(new Insets(10, 10, 10, 10));


        togglePorT.selectedToggleProperty().addListener(new ChangeListener<>() {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n) {

                RadioButton rb = (RadioButton) togglePorT.getSelectedToggle();
                buttonDelete.setDisable(true);
                if (rb == r1) {
                    matches.clear();
                    matches.addAll(matchDAO.getAllMatches());
                } else if (rb == r2) {
                    matches.clear();
                    matches.addAll(matchDAO.getAllreadyPlayed(false));
                }else if (rb == r3) {
                    matches.clear();
                    matches.addAll(matchDAO.getAllreadyPlayed(true));
                }

                if (rb != null) {
                    String s = rb.getText();

                    // change the label
                    //l2.setText(s + " selected");
                }
            }
        });

        paneShow.getChildren().add(vbox);


        return paneShow;

    }
}