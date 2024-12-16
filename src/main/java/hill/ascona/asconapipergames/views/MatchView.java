package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.entities.TestMatch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
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

        //----Add Matches-----
        Boolean team = false;
        Boolean upcoming = true;
        String pOrT = "Team";

        AnchorPane paneAdd = new AnchorPane();

        String date = "";



        paneAdd.setPrefSize(550, 600);
        Button buttonShow = new Button("Show matches");
//        buttonShow.setOnAction(e -> {
//
//        })


        Button addSingel = new Button("Add a player match");
        Button addTeams = new Button("Add a team match");
        Button cancel = new Button("Cancel");
        Button saveMatch = new Button("Save match");
        saveMatch.setOnAction(event -> {
           saveButtonClicked();
        });


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
        datePicker.setPromptText("Date is required");
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
        cancel.setLayoutX(300);
        cancel.setLayoutY(420);
        saveMatch.setLayoutX(380);
        saveMatch.setLayoutY(420);




        paneAdd.getChildren().addAll(buttonShow, addSingel, addTeams, labelScore,
                labelDate, labelTime, labelUpcoming, labelWinner, labelMvp,
                labelPOrTOne,labelPOrTwo,labelPOrTOneName,labelPOrTTwoName, labelGame,
                comboBoxGame,comboBoxPOrT1,comboBoxPOrT2,comboBoxWinner,comboBoxMvp,
                datePicker,cancel, saveMatch);

        //----Show Matches----

        AnchorPane paneShow = new AnchorPane();
        paneShow.setPrefSize(550, 600);

                //----TableView----


        TableView<TestMatch> table = new TableView<>();
        ObservableList<TestMatch> data = FXCollections.observableArrayList(
                new TestMatch("2024-11-12", "tetris", "Team", "röda", true, "blåa"),
                new TestMatch("igår", "WoW", "Single", "röda", true, "röda"),
                 new TestMatch("imorgon", "CoD", "blåa", "gröna", false, null),
                new TestMatch("idag", "halo", "röda", "lila", true, "lila")
        );


        table.setEditable(false);
        table.setPrefWidth(530);

        TableColumn<TestMatch,String> dateCol = new TableColumn<>("Date");
        dateCol.setPrefWidth(70);
        dateCol.setCellValueFactory(
                new PropertyValueFactory<>("date")
        );
        TableColumn<TestMatch,String> gameCol = new TableColumn<>("Game");
        gameCol.setPrefWidth(90);
        gameCol.setCellValueFactory(
                new PropertyValueFactory<>("game")
        );
        TableColumn<TestMatch,String> pOrTCol = new TableColumn<>("Team/\n Singel");
        pOrTCol.setPrefWidth(50);
        pOrTCol.setCellValueFactory(
                new PropertyValueFactory<>("team1")
        );
        TableColumn<TestMatch,String> pOrTOneCol = new TableColumn<>("Participant 1");
        pOrTOneCol.setPrefWidth(80);
        pOrTOneCol.setCellValueFactory(
                new PropertyValueFactory<>("team1")
        );
        TableColumn<TestMatch,String> pOrTTwoCol = new TableColumn<>("Participant 2");
        pOrTTwoCol.setPrefWidth(80);
        pOrTTwoCol.setCellValueFactory(
                new PropertyValueFactory<>("team2")
        );
        TableColumn<TestMatch,String> decidedCol = new TableColumn<>("Decided");
        decidedCol.setPrefWidth(80);
        decidedCol.setCellValueFactory(
                new PropertyValueFactory<>("played")
        );
        TableColumn<TestMatch,String> winnerCol = new TableColumn<>("Winner");
        winnerCol.setPrefWidth(80);
        winnerCol.setCellValueFactory(
                new PropertyValueFactory<>("winner")
        );

        table.setItems(data);
        table.getColumns().addAll(dateCol, gameCol, pOrTCol, pOrTOneCol, pOrTTwoCol, decidedCol, winnerCol);




        //----End TableView----



        HBox hBox = new HBox();
        VBox vbox = new VBox();
        Button buttonNew = new Button("Add a new match");
        buttonNew.setOnAction(event -> {
            deleteButtonClicked();
            table.getSelectionModel().clearSelection();
            table.getSelectionModel().getSelectedItems();
            table.getSelectionModel().getSelectedCells().clear();

        });

        Button buttonDelete = new Button("Delete match");
        Button buttonEdit = new Button("Edit/see details of match");
        buttonEdit.setDisable(true);
        //buttonDelete.setDisable(true);




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


        return paneAdd;

    }


    public void saveButtonClicked(){
        TestMatch testMatch = new TestMatch();
        testMatch.setDate("new Date");
        testMatch.setGame("Single");
        testMatch.setTeam1("ettan");
        testMatch.setTeam2("tvåan");
        testMatch.setPlayed(true);
        testMatch.setWinner("ettan");
        //datepicker.clear();

    }

    public void deleteButtonClicked(){


    }

}
