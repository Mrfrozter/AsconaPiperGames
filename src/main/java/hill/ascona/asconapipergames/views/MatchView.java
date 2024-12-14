package hill.ascona.asconapipergames.views;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
//        tournaments = FXCollections.observableList(tDao.getAllTournaments());

    /*      buttonNew.setOnAction(e -> {

          })
            btn.setOnMouseClicked((e) -> {
                content.getChildren().add(newTour());
            });*/

    public AnchorPane start() {
        Boolean team = false;
        Boolean upcoming = true;
        String pOrT = "Team";

        //----Add Matches-----
        String date = "";


        AnchorPane paneAdd = new AnchorPane();
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


        HBox hbox = new HBox();
        Button buttonAll = new Button("See all matches");
        Button buttonUpcoming = new Button("See all upcoming matches");
        Button buttonOld = new Button("See played matches");
        Button buttonNew = new Button("Add a new match");

        hbox.getChildren().addAll(buttonAll, buttonUpcoming, buttonOld, buttonNew);
        paneShow.getChildren().add(hbox);


        return paneAdd;
    }

}
