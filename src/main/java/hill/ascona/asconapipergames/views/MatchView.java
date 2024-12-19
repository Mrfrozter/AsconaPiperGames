package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.DAO.GameDAO;
import hill.ascona.asconapipergames.DAO.MatchDAO;
import hill.ascona.asconapipergames.DAO.PersonDAO;
import hill.ascona.asconapipergames.DAO.TeamDAO;
import hill.ascona.asconapipergames.entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
    private int player1Id;
    private int player2Id;
    private int team1Id;
    private int team2Id;
    private int winnerId;
    private String nameOne;
    private String nameTwo;
    private Random random = new Random();
    private Person player1;
    private Person player2;
    private Team team1;
    private Team team2;
    private ObservableList<Match> matches = FXCollections.observableList(new ArrayList<>());
    private List<Match> matchSimple = new ArrayList<>();


    public void addMatch(){
        AnchorPane paneAdd = new AnchorPane();
        paneAdd.setPrefSize(480, 450);
        Scene scene2 = new Scene(paneAdd);
        Stage stage2 = new Stage();
        stage2.setScene(scene2);
        stage2.show();
        PersonDAO pDao = new PersonDAO();
        List<Person> persons = pDao.getAllPlayersInfo();
        TeamDAO teamDao = new TeamDAO();
        List<Team> teams = teamDao.getAllTeams();


        //----------------------------------------------------------------------Labels---------------

        Label nowShowing = new Label("ADD A PLAYER MATCH");
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


        //----------------------------------------------------------------------Checkbox---------------
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(true);
        checkBox.setOnAction(event -> {
            allreadyPlayed=!allreadyPlayed;
            //Grey out / make fillable                                  // TODO ----------------------
        });

        //----------------------------------------------------------------------ComboBoxes---------------

        //comboBoxGame.setValue("Delay rounds 3 second");



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
        });

        ComboBox<String> comboBoxPOrT1 =new ComboBox<>();
        comboBoxPOrT1.setPromptText("Choose first participant");
        ComboBox<String> comboBoxPOrT2 =new ComboBox<>();
        comboBoxPOrT2.setPromptText("Choose second participant");
        if (singelNotTeam) {
            for (Person person : persons) {
                comboBoxPOrT1.getItems().add(person.getNickname());
                comboBoxPOrT2.getItems().add(person.getNickname());
            }
        }else{
            for (Team team : teams) {
                comboBoxPOrT1.getItems().add(team.getTeam_name());               // TODO ----get teams-------------
                comboBoxPOrT2.getItems().add(team.getTeam_name());
            }
        }

        comboBoxPOrT1.setOnAction(e ->{
            nameOne = comboBoxPOrT1.getValue();
            if (singelNotTeam) {
                player1Id = comboBoxPOrT1.getSelectionModel().getSelectedIndex();
                player1 = persons.get(player1Id);                   // TODO -----borde vara getByName???????------------
            }else {
                team1Id = comboBoxPOrT1.getSelectionModel().getSelectedIndex();
                team1 = teams.get(team1Id);
            }

        });

                     // TODO ----------------------
      comboBoxPOrT2.setOnAction(e ->{
            nameTwo = comboBoxPOrT2.getValue();
            if (singelNotTeam) {                                                // TODO ----------------------
                player2Id = comboBoxPOrT1.getSelectionModel().getSelectedIndex();
                player2 = persons.get(player2Id);
            }else {                                                                  // TODO ----------------------
                team2Id = comboBoxPOrT1.getSelectionModel().getSelectedIndex();
                team2 = teams.get(team1Id);
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
        //----------------------------------------------------------------------Buttons---------------

        Button pOrTButton = new Button("Add a team match");
        pOrTButton.setOnAction(event -> {
                    singelNotTeam=!singelNotTeam;
                    comboBoxPOrT1.getItems().clear();
                    comboBoxPOrT2.getItems().clear();
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

            /// ////////test av matchDAO.getAllMatches()
/*            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            matchSimple.addAll(matchDAO.getAllMatches());
            matches.addAll(matchDAO.getAllMatches());
            for (Match match : matches) {
                System.out.println("gega");
                System.out.println(match.getNameOne());
            }*/
            /// //testrader slut-------------------------------------------------------------
                   stage2.close();
                }
        );

        Button saveTheMatch = new Button("Save match");
        saveTheMatch.setOnAction(event -> {
            /// ////////// olika matchtemp beronde på avgjord eller inte????? winnerId,
            Match matchTemp = new Match(date, allreadyPlayed, singelNotTeam, gamePlay, nameOne, nameTwo);

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
        saveTheMatch.setLayoutX(340);
        saveTheMatch.setLayoutY(255);


        paneAdd.getChildren().addAll(checkBox, labelGame, labelPOrTOne,labelDate, labelWinner,pOrTButton, nowShowing, labelScore,
                labelTime, labelUpcoming,  labelMvp,
                labelPOrTwo,labelPOrTOneName,labelPOrTTwoName,
                comboBoxGame,comboBoxPOrT1,comboBoxPOrT2,comboBoxWinner,comboBoxMvp,
                datePicker,cancel, saveTheMatch);
    }


    public AnchorPane start() { //----Show Matches----

        AnchorPane paneShow = new AnchorPane();
        paneShow.setPrefSize(550, 600);

        //----TableView-------------------------------------------------------------------------------


      // matches = FXCollections.observableList(matchDAO.getAllMatches());
        // matches.add(matchDAO.getMatchById(3));
        //matches.add(matchDAO.getMatchById(2));
        matches.addAll(matchDAO.getAllMatches());

        TableView<Match> table = new TableView<>();
        table.setEditable(true);    //???????
        table.setPrefWidth(530);

        TableColumn<Match,String> dateCol = new TableColumn<>("Date");
        dateCol.setPrefWidth(70);
        dateCol.setCellValueFactory(
                new PropertyValueFactory<>("date")
        );
        TableColumn<Match,String> gameCol = new TableColumn<>("Game");
        gameCol.setPrefWidth(90);
        gameCol.setCellValueFactory(
                new PropertyValueFactory<>("game")
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
/*        TableColumn<Match,String> winnerCol = new TableColumn<>("Winner");
        winnerCol.setPrefWidth(80);
        winnerCol.setCellValueFactory(
                new PropertyValueFactory<>("winnerId")

                , winnerCol
        );*/

        table.setItems(matches);
        table.getColumns().addAll(dateCol, gameCol, pOrTCol, pOrTOneCol, pOrTTwoCol, decidedCol);

       // matches.addListener(new ListChangeListener<>();


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
}