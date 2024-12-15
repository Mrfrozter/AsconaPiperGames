package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.entities.TestMatch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;

public class MatchView {

/*
När personalen listar matcher ska det synas vilka spelare/lag som tävlar mot
varandra samt om matchen är kommande eller avgjord, om matchen är avgjord ska
det gå att se vem som blev vinnare. Här implementeras med fördel funktionalitet för
att lista samtliga matcher, endast avgjorda och endast kommande matcher.
• Matcher ska alltid ha ett datum.
• Matchen avgörs (poängen sätts) genom att Pied Pipers personal loggar in och
registrerar resultatet.
• */

    public AnchorPane start() {
        AnchorPane paneShow = new AnchorPane(); /* Boolean team = false;
        Boolean upcoming = true;
        String pOrT = "Team";

        //----Add Matches-----
        String date = "";



        paneAdd.setPrefSize(550, 600);
        Button buttonShow = new Button("Show matches");
//        buttonShow.setOnAction(e -> {
//
//        })


        Button addSingel = new Button("Add a player match");
        Button addTeams = new Button("Add a team match");

        Label labelGame = new Label("Game: ");
        Label labelPOrTOne = new Label(pOrT + " one: ");
        Label labelPOrTwo = new Label(pOrT + " two: ");
        Label labelDate = new Label("Date: ");
        Label labelTime = new Label("Time: ");
        Label labelUpcoming = new Label("Upcoming: ");
        Label labelWinner = new Label("Winner: ");
        Label labelMvp = new Label("MVP");
        Label labelScore = new Label("Score: ");
        Label labelPOrTOneName = new Label("Show p1 name: ");
        Label labelPOrTTwoName = new Label("Show p2 name: ");

        ComboBox<String> comboBoxGame =new ComboBox<>();
        comboBoxGame.setPromptText("Choose game");
        comboBoxGame.getItems().addAll("ADD GAMESLIST HERE", "Medium", "Hard"); // TODO ----------------------
        comboBoxGame.setOnAction(e ->{
                                                                                     // TODO ----------------------
        });

        ComboBox<String> comboBoxPOrT1 =new ComboBox<>();
        comboBoxPOrT1.setPromptText("Choose first player/team");                       // TODO ----------------------
        comboBoxPOrT1.getItems().addAll("ADD Players/teams HERE", "Medium");    // TODO ----------------------
        comboBoxPOrT1.setOnAction(e ->{
                                                                                    // TODO ----------------------
        });

        ComboBox<String> comboBoxPOrT2 =new ComboBox<>();
        comboBoxPOrT2.setPromptText("Choose second player/team");                   // TODO ----------------------
        comboBoxPOrT2.getItems().addAll("ADD Players/teams HERE", "Medium"); // TODO ----------------------
        comboBoxPOrT2.setOnAction(e ->{
                                                                                    // TODO ----------------------
        });

        ComboBox<String> comboBoxWinner =new ComboBox<>();
        comboBoxWinner.setPromptText("Enter winner");                              // TODO ----------------------
        comboBoxWinner.getItems().addAll("ADD Players/teams HERE", "Medium"); // TODO ----------------------
        comboBoxWinner.setOnAction(e ->{
                                                                                     // TODO ----------------------
        });

        ComboBox<String> comboBoxMvp =new ComboBox<>();
        comboBoxMvp.setPromptText("Choose MVP");                                      // TODO ----------------------
        comboBoxMvp.getItems().addAll("ADD Players HERE", "Medium");             // TODO ----------------------
        comboBoxMvp.setOnAction(e ->{
                                                                                   // TODO ----------------------
        });

        DatePicker datePicker = new DatePicker();                                   // ??? ----------------------
        datePicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                LocalDate date = datePicker.getValue();
                System.err.println("Selected date: " + date);
            }
        });



        //---- Layout

        buttonShow.setLayoutX(10);
        buttonShow.setLayoutY(10);

        addTeams.setLayoutX(40);
        addTeams.setLayoutY(50);

        addSingel.setLayoutX(300);
        addSingel.setLayoutY(50);

        labelGame.setLayoutX(40);
        labelGame.setLayoutY(100);
        labelScore.setLayoutX(300);
        labelScore.setLayoutY(50);
        labelDate.setLayoutX(40);
        labelDate.setLayoutY(200);
        labelTime.setLayoutX(300);
        labelTime.setLayoutY(200);
        labelUpcoming.setLayoutX(40);
        labelUpcoming.setLayoutY(250);
        labelWinner.setLayoutX(40);
        labelWinner.setLayoutY(300);
        labelMvp.setLayoutX(300);
        labelMvp.setLayoutY(300);
        labelScore.setLayoutX(40);
        labelScore.setLayoutY(350);
        labelPOrTOne.setLayoutX(40);
        labelPOrTOne.setLayoutY(150);
        labelPOrTwo.setLayoutX(300);
        labelPOrTwo.setLayoutY(150);
        labelPOrTOneName.setLayoutX(40);
        labelPOrTOneName.setLayoutY(380);
        labelPOrTTwoName.setLayoutX(300);
        labelPOrTTwoName.setLayoutY(380);

        datePicker.setLayoutX(140);
        datePicker.setLayoutY(200);

        comboBoxGame.setLayoutX(140);
        comboBoxGame.setLayoutY(100);
        comboBoxPOrT1.setLayoutX(140);
        comboBoxPOrT1.setLayoutY(150);
        comboBoxPOrT2.setLayoutX(400);
        comboBoxPOrT2.setLayoutY(150);
        comboBoxWinner.setLayoutX(140);
        comboBoxWinner.setLayoutY(300);
        comboBoxMvp.setLayoutX(400);
        comboBoxMvp.setLayoutY(300);




        paneAdd.getChildren().addAll(buttonShow, addSingel, addTeams, labelScore,
                labelDate, labelTime, labelUpcoming, labelWinner, labelMvp,
                labelPOrTOne,labelPOrTwo,labelPOrTOneName,labelPOrTTwoName, labelGame,
                comboBoxGame,comboBoxPOrT1,comboBoxPOrT2,comboBoxWinner,comboBoxMvp,
                datePicker);

        //----Show Matches----
        AnchorPane paneShow = new AnchorPane();
        paneShow.setPrefSize(550, 600);

        //----TableView----


        TableView<TestMatch> table = new TableView<TestMatch>();
        ObservableList<TestMatch> data = FXCollections.observableArrayList(
                new TestMatch("idag", "tetris", "blåa", "röda", true, "blåa"),
                new TestMatch("igår", "WoW", "gröna", "röda", true, "röda"),
                new TestMatch("imorgon", "CoD", "blåa", "gröna", false, null),
                new TestMatch("idag", "halo", "röda", "lila", true, "lila")
        );


        table.setEditable(false);

        TableColumn dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(
                new PropertyValueFactory<TestMatch,String>("date")
        );
        TableColumn gameCol = new TableColumn("Game");
        gameCol.setCellValueFactory(
                new PropertyValueFactory<TestMatch,String>("game")
        );
*//*   TableColumn pOrTCol = new TableColumn("Team/\n Singel");
        pOrTCol.setCellValueFactory(
                new PropertyValueFactory<TestMatch,String>("team1")
        );*//*

        TableColumn pOrTOneCol = new TableColumn("Participant 1");
        pOrTOneCol.setCellValueFactory(
                new PropertyValueFactory<TestMatch,String>("team1")
        );
        TableColumn pOrTTwoCol = new TableColumn("Participant 2");
        pOrTTwoCol.setCellValueFactory(
                new PropertyValueFactory<TestMatch,String>("team2")
        );
        TableColumn decidedCol = new TableColumn("Decided");
        decidedCol.setCellValueFactory(
                new PropertyValueFactory<TestMatch,String>("played")
        );
        TableColumn winnerCol = new TableColumn("Winner");
        winnerCol.setCellValueFactory(
                new PropertyValueFactory<TestMatch,String>("winner")
        );

        table.setItems(data);
        table.getColumns().addAll(dateCol, gameCol, pOrTOneCol, pOrTTwoCol, decidedCol, winnerCol);




        //----End TableView----



        HBox hBox = new HBox();
        VBox vbox = new VBox();
        Button buttonAll = new Button("See all matches");
        Button buttonUpcoming = new Button("See all upcoming matches");
        Button buttonOld = new Button("See played matches");
        Button buttonNew = new Button("Add a new match");

        CheckBox checkUpp = new CheckBox("Show upcoming matches");
        CheckBox checkDone = new CheckBox("Show finished matches");
        CheckBox checkPlayers = new CheckBox("Show PvP matches");
        CheckBox checkTeams = new CheckBox("Show team matches");

        vbox.getChildren().addAll(buttonNew,checkUpp, checkDone, checkPlayers, checkTeams);
        hBox.getChildren().addAll(table, vbox);*/
        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        TableView<TestMatch> table = new TableView<TestMatch>();
        table.setEditable(false);
        ObservableList<TestMatch> data =
                FXCollections.observableArrayList(
                        new TestMatch("idag", "tetris", "blåa", "röda", true, "blåa"),
                        new TestMatch("igår", "WoW", "gröna", "röda", true, "röda"),
                        new TestMatch("imorgon", "CoD", "blåa", "gröna", false, null),
                        new TestMatch("idag", "halo", "röda", "lila", true, "lila")
                );
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<TestMatch, String>("date"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<TestMatch, String>("game"));

        TableColumn emailCol = new TableColumn("t");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<TestMatch, String>("team1"));

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);



        paneShow.getChildren().add(vbox);


        return paneShow;
    }

}
