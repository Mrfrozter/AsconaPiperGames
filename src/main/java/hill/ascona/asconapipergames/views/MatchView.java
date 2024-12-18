package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.DAO.GameDAO;
import hill.ascona.asconapipergames.DAO.MatchDAO;
import hill.ascona.asconapipergames.DAO.PersonDAO;
import hill.ascona.asconapipergames.entities.Game;
import hill.ascona.asconapipergames.entities.Match;
import hill.ascona.asconapipergames.entities.Person;
import hill.ascona.asconapipergames.entities.Tournament;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
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
    MatchDAO matchDAO = new MatchDAO();
    private String gameChosen = "";
    Boolean team = false;
    Boolean upcoming = true;
    String pOrTString = "Player";
    int turneringarId;
    String date = "";
    boolean allreadyPlayed = false;
    boolean singelNotTeam = false;
    String gameName;
    Game game;
    int player1Id;
    int player2Id;
    int team1Id;
    int team2Id;
    int winnerId;
    String nameOne;
    String nameTwo;
    Random random = new Random();



    public void addMatch(){
        AnchorPane paneAdd = new AnchorPane();
        paneAdd.setPrefSize(480, 450);
        Scene scene2 = new Scene(paneAdd);
        Stage stage2 = new Stage();
        stage2.setScene(scene2);
        stage2.show();

        //----------------------------------------------------------------------Labels---------------

        Label nowShowing = new Label("ADD A TEAM MATCH");
        nowShowing.setFont(new Font("Arial bold", 12));

        Label labelGame = new Label("Game: ");
        Label labelPOrTOne = new Label(pOrTString + " one: ");
        Label labelPOrTwo = new Label(pOrTString + " two: ");
        Label labelDate = new Label("Date: ");
        Label labelTime = new Label("Time: ");
        Label labelUpcoming = new Label("Upcoming: ");
        Label labelWinner = new Label("Winner: ");
        Label labelMvp = new Label("MVP");
        Label labelScore = new Label("Score: ");
        Label labelPOrTOneName = new Label("Show p1 name: ");
        Label labelPOrTTwoName = new Label("Show p2 name: ");

        //----------------------------------------------------------------------Buttons---------------

        Button pOrTButton = new Button("Add a team match");
        pOrTButton.setOnAction(event -> {
            singelNotTeam=!singelNotTeam;
            if (singelNotTeam){
                pOrTButton.setText("Add a player match");
                pOrTString = "Team";
                nowShowing.setText("ADD A TEAM MATCH");
            }else{
                pOrTButton.setText("Add a team match");
                pOrTString = "Player";
                nowShowing.setText("ADD A PLAYER MATCH");
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

        Button saveMatch = new Button("Save match");
        saveMatch.setOnAction(event -> {
            if (singelNotTeam) {
                matchDAO.saveMatch(new Match(date, allreadyPlayed, singelNotTeam, game, player1Id,
                        player2Id, winnerId, nameOne, nameTwo));


            }else {
                matchDAO.saveMatch(new Match(singelNotTeam, date, allreadyPlayed, game,  team1Id,
                        team2Id, winnerId, nameOne, nameTwo));
            }

            //int turneringarId;
            stage2.close(); ///// nollställ istället?
        });

        //----------------------------------------------------------------------Checkbox---------------
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(true);
        checkBox.setOnAction(event -> {
            allreadyPlayed=!allreadyPlayed;
            //Grey out / make fillable                                  // TODO ----------------------
        });

        //----------------------------------------------------------------------ComboBoxes---------------

        //comboBoxGame.setValue("Delay rounds 3 second");


  /*      ObservableList<Game> games = FXCollections.observableList(new ArrayList<>());
        GameDAO gDao = new GameDAO();
        games = FXCollections.observableList(gDao.getAllGames());

*/
        ComboBox<String> comboBoxGame =new ComboBox<>();
        comboBoxGame.setPromptText("Choose game");
        GameDAO gDao = new GameDAO();
        List<Game> games = gDao.getAllGames();
        for (Game game : games) {
            comboBoxGame.getItems().add(game.getTitle());
        }
        comboBoxGame.setOnAction(e ->{
            gameChosen = (String) comboBoxGame.getValue();
            game = gDao.getByName(gameChosen);
        });

        PersonDAO pDao = new PersonDAO();
        List<Person> persons = pDao.getAllPlayersInfo();
        ComboBox<String> comboBoxPOrT1 =new ComboBox<>();
        comboBoxPOrT1.setPromptText("Choose first participant");
        if (singelNotTeam) {
            for (Person person : persons) {
                comboBoxPOrT1.getItems().add(person.getName());
            }
        }else{                                  // TODO ----get teams-------------

        }
        comboBoxPOrT1.setOnAction(e ->{
            //player1Id = comboBoxPOrT1.getSelectionModel().getSelectedIndex();
            nameOne = comboBoxPOrT1.getValue();
            if (singelNotTeam) {                                                // TODO ----------------------
                player1Id=random.nextInt(10);
            }else {
                team1Id=random.nextInt(10);
            }
        });

        ComboBox<String> comboBoxPOrT2 =new ComboBox<>();
        comboBoxPOrT2.setPromptText("Choose second participant");                   // TODO ----------------------
        comboBoxPOrT2.getItems().addAll("ADD Players/teams2 HERE",  "Kalle", "Sara", "The team", "the lost ones", "Fredrik"); // TODO ----------------------
        comboBoxPOrT2.setOnAction(e ->{
            nameTwo = comboBoxPOrT2.getValue();
            if (singelNotTeam) {                                                // TODO ----------------------
                player2Id=random.nextInt(10);
            }else {                                                                  // TODO ----------------------
                team2Id=random.nextInt(10);
            }
        });

        ComboBox<String> comboBoxWinner =new ComboBox<>();
        comboBoxWinner.setPromptText("Enter winner");                              // TODO ----------------------
        comboBoxWinner.getItems().addAll("ADD Players/teams HERE", "Medium"); // TODO ----------------------
        comboBoxWinner.setOnAction(e ->{
            winnerId=99;                                                     // TODO ----------------------
        });

        ComboBox<String> comboBoxMvp =new ComboBox<>();
        comboBoxMvp.setPromptText("Choose MVP");                                      // TODO ----------------------
        comboBoxMvp.getItems().addAll("ADD Players HERE", "Medium");             // TODO ----------------------
        comboBoxMvp.setOnAction(e ->{
                                                                                    // TODO ----------------------
        });

        DatePicker datePicker = new DatePicker();                                   // ??? ----------------------
        datePicker.setPromptText("Date is required");
        datePicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                LocalDate dateLocal = datePicker.getValue();
                date = dateLocal.toString();
            }
        });

        //----------------------------------------------------------------------Layout---------------
        nowShowing.setLayoutX(20);
        nowShowing.setLayoutY(20);

        pOrTButton.setLayoutX(380);
        pOrTButton.setLayoutY(15);

        labelGame.setLayoutX(20);
        labelGame.setLayoutY(45);
        labelPOrTOne.setLayoutX(20);
        labelPOrTOne.setLayoutY(85);
        labelDate.setLayoutX(20);
        labelDate.setLayoutY(125);
        labelWinner.setLayoutX(20);
        labelWinner.setLayoutY(185);
        labelScore.setLayoutX(20);
        labelScore.setLayoutY(225);

        comboBoxGame.setLayoutX(80);
        comboBoxGame.setLayoutY(45);
        comboBoxPOrT1.setLayoutX(80);
        comboBoxPOrT1.setLayoutY(85);
        datePicker.setLayoutX(80);
        datePicker.setLayoutY(125);
        comboBoxWinner.setLayoutX(80);
        comboBoxWinner.setLayoutY(185);
        labelPOrTOneName.setLayoutX(80);
        labelPOrTOneName.setLayoutY(225);

        labelUpcoming.setLayoutX(270);
        labelUpcoming.setLayoutY(45);
        labelPOrTwo.setLayoutX(270);
        labelPOrTwo.setLayoutY(85);
        labelTime.setLayoutX(270);
        labelTime.setLayoutY(125);
        labelMvp.setLayoutX(270);
        labelMvp.setLayoutY(185);
        labelPOrTTwoName.setLayoutX(270);
        labelPOrTTwoName.setLayoutY(225);
        cancel.setLayoutX(270);
        cancel.setLayoutY(255);

        checkBox.setLayoutX(340);
        checkBox.setLayoutY(45);
        comboBoxPOrT2.setLayoutX(330);
        comboBoxPOrT2.setLayoutY(85);
        comboBoxMvp.setLayoutX(330);
        comboBoxMvp.setLayoutY(185);
        saveMatch.setLayoutX(340);
        saveMatch.setLayoutY(255);


        paneAdd.getChildren().addAll(checkBox, labelGame, labelPOrTOne,labelDate, labelWinner,pOrTButton, nowShowing, labelScore,
                labelTime, labelUpcoming,  labelMvp,
                labelPOrTwo,labelPOrTOneName,labelPOrTTwoName,
                comboBoxGame,comboBoxPOrT1,comboBoxPOrT2,comboBoxWinner,comboBoxMvp,
                datePicker,cancel, saveMatch);
    }


    public AnchorPane start() {


 //----Show Matches----

        AnchorPane paneShow = new AnchorPane();
        paneShow.setPrefSize(550, 600);

        //----TableView----


   /*     TableView<TestMatch> tableTest = new TableView<>();
        ObservableList<TestMatch> data2 = FXCollections.observableArrayList(
                new TestMatch("2024-11-12", "tetris", "Team", "röda", true, "blåa"),
                new TestMatch("igår", "WoW", "Single", "röda", true, "röda"),
                 new TestMatch("imorgon", "CoD", "blåa", "gröna", false, null),
                new TestMatch("idag", "halo", "röda", "lila", true, "lila")
        );*/

        TableView<Match> table = new TableView<>();
      ObservableList<Match> data = FXCollections.observableArrayList(
/*                new Match(true,false, 5, 4, "test", "green"),
                new Match(false,true, 7, 3, "yeyn", "red"),
                ne w Match(false,true,9, 4, "tb twest", "lila"),
                new Match(false,true,2, 3, "tesssett", "blue")*/
      );
/*

        List<Match> test = matchDAO.getAllMatches();
        // data = FXCollections.observableList(test);
*/

        //ObservableList<Match> data = FXCollections.observableList(matchDAO.getAllMatches());

                table.setEditable(false);
        table.setPrefWidth(530);

        TableColumn<Match,String> dateCol = new TableColumn<>("Date");
        dateCol.setPrefWidth(70);
        dateCol.setCellValueFactory(
                new PropertyValueFactory<>("date")
        );
        TableColumn<Match,String> gameCol = new TableColumn<>("Game");
        gameCol.setPrefWidth(90);
        gameCol.setCellValueFactory(
                new PropertyValueFactory<>("gameId")
        );
        TableColumn<Match,String> pOrTCol = new TableColumn<>("Team/\n Singel");
        pOrTCol.setPrefWidth(50);
        pOrTCol.setCellValueFactory(
                new PropertyValueFactory<>("singelNotTeam")
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
        TableColumn<Match,String> decidedCol = new TableColumn<>("Played?");
        decidedCol.setPrefWidth(80);
        decidedCol.setCellValueFactory(
                new PropertyValueFactory<>("allreadyPlayed")
        );
        TableColumn<Match,String> winnerCol = new TableColumn<>("Winner");
        winnerCol.setPrefWidth(80);
        winnerCol.setCellValueFactory(
                new PropertyValueFactory<>("winnerId")
        );

        table.setItems(data);
        table.getColumns().addAll(dateCol, gameCol, pOrTCol, pOrTOneCol, pOrTTwoCol, decidedCol, winnerCol);


        //----End TableView----



        HBox hBox = new HBox();
        VBox vbox = new VBox();
        Button buttonNew = new Button("Add a new match");
        buttonNew.setOnAction(event -> {
            addMatch();
    /*        table.getSelectionModel().clearSelection();
            table.getSelectionModel().getSelectedItems();
            table.getSelectionModel().getSelectedCells().clear();*/
        });

        Button buttonDelete = new Button("Delete match");
        //buttonDelete.setDisable(true);
        buttonDelete.setOnAction(event -> {
     //       data.remove(table.getSelectionModel().getSelectedItem());
        });

        Button buttonEdit = new Button("Edit/see details of match");
        buttonEdit.setDisable(true);
        buttonEdit.setOnAction(event -> {

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
        buttons.getChildren().addAll(buttonNew, buttonEdit, buttonDelete);
        hBox.setSpacing(20);
        vbox.setSpacing(10);
        hBox.getChildren().addAll(r,buttons);
        vbox.getChildren().addAll(table, hBox);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        //table.getSelectionModel().getSelectedCells().addListener((v, oldValue, newValue)->buttonDelete.setDisable(false); );

    /*    togglePorT.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n)
            {

                RadioButton rb = (RadioButton)togglePorT.getSelectedToggle();

                if (rb != null) {
                    String s = rb.getText();

                    // change the label
                    //l2.setText(s + " selected");
                }
            }
        });*/

        paneShow.getChildren().add(vbox);


        return paneShow;

    }


    public void saveButtonClicked(){
/*        TestMatch testMatch = new TestMatch();
        testMatch.setDate("new Date");
        testMatch.setGame("Single");
        testMatch.setTeam1("ettan");
        testMatch.setTeam2("tvåan");
        testMatch.setPlayed(true);
        testMatch.setWinner("ettan");*/
        //datepicker.clear();

    }

    public void deleteButtonClicked(){


    }



}