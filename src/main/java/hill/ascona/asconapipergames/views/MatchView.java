package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.DAO.GameDAO;
import hill.ascona.asconapipergames.DAO.MatchDAO;
import hill.ascona.asconapipergames.DAO.PersonDAO;
import hill.ascona.asconapipergames.DAO.TeamDAO;
import hill.ascona.asconapipergames.entities.*;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class MatchView {
    private MatchDAO matchDAO = new MatchDAO();
    private String gameChosen = "";
  //  private Boolean team = false;
  //  private Boolean upcoming = true;
    private String pOrTString = "Player";
  //  private int turneringarId;
    private String date = "";
    private boolean allreadyPlayed;
    private boolean singelNotTeam;
    private Game gamePlay;
   // private String player1Nickname;
   // private String player2Nickname;
    private String team1Name;
    private String team2Name;
   // private int winnerIdSend;
   // private String nameOne;
  //  private String nameTwo;
    private Person player1;
    private Person player2;
    private Team team1;
    private Team team2;
    private ObservableList<Match> matches = FXCollections.observableList(new ArrayList<>());
    private int checkPlayed=1;
    private int checkPorT=1;
    private String hourSelected;
    private String minSelected;
    private Match matchTemp;
    private String finalScore;
    private int scoreP1;
    private int scoreP2;
    private boolean scoreChanged;

    public void addMatch(int newOrUpdating) {
        AnchorPane paneAdd = new AnchorPane();
        paneAdd.setPrefSize(540, 290);
        Scene scene2 = new Scene(paneAdd);
        Stage stage2 = new Stage();
        stage2.setScene(scene2);
        stage2.show();
        PersonDAO pDao = new PersonDAO();
        List<Person> persons = pDao.getAllPlayersOrUsers("Player");
        TeamDAO teamDao = new TeamDAO();
        List<Team> teams = teamDao.getAllTeams();
        singelNotTeam = true;
        boolean updating;


        //----------------------------------------------------------------------Labels---------------

        Label nowShowing = new Label("ADD A PLAYER MATCH");
        if(newOrUpdating==-1){
            updating=false;
            allreadyPlayed = false;
            matchTemp = new Match();
        }else{
            updating=true;
            matchTemp = matchDAO.getMatchById(newOrUpdating);
            nowShowing.setText("UPDATING MATCH");
            pOrTString = matchTemp.getPlayerTeam();
            allreadyPlayed =matchTemp.isAllreadyPlayed();
        }

        nowShowing.setFont(new Font("Arial bold", 12));

        Label labelGame = new Label("Game: ");
        Label labelPOrTOne = new Label(pOrTString + " one: ");
        Label labelPOrTwo = new Label(pOrTString + " two: ");
        Label labelDate = new Label("Date: ");
        Label labelAllreadyPlayed = new Label("Already played: ");
        Label labelTime = new Label("Time: ");
        Label labelScore1 = new Label("Enter score:    " + pOrTString + " 1: ");
        Label labelScore2 = new Label(pOrTString + " 2: ");

        labelScore1.setDisable(!allreadyPlayed);
        labelScore2.setDisable(!allreadyPlayed);

        //----------------------------------------------------------------------ComboBoxes---------------

        ComboBox<String> comboBoxPOrT1 =new ComboBox<>();
        ComboBox<String> comboBoxPOrT2 =new ComboBox<>();
        comboBoxPOrT1.setDisable(true);
        comboBoxPOrT2.setDisable(true);
        if(!updating){
            comboBoxPOrT1.setPromptText("Pick a game first");
            comboBoxPOrT2.setPromptText("Pick a game first");
        }else{
            comboBoxPOrT1.setPromptText(matchTemp.getNameOne());
            comboBoxPOrT2.setPromptText(matchTemp.getNameTwo());
        }


        ComboBox<String> comboBoxGame =new ComboBox<>();

        if (updating){
            comboBoxGame.setPromptText(matchTemp.getGame().getTitle());
        }else {
            comboBoxGame.setPromptText("Choose game");
        }
        GameDAO gDao = new GameDAO();
        List<Game> games = gDao.getAllGames();
        for (Game game : games) {
            comboBoxGame.getItems().add(game.getTitle());
        }

        comboBoxGame.setOnAction(e ->{
            gameChosen = (String) comboBoxGame.getValue();
            matchTemp.setGame(gDao.getByName(gameChosen));
            comboBoxPOrT1.setDisable(false);
            comboBoxPOrT2.setDisable(false);
            if (!updating){
                comboBoxPOrT1.setPromptText("Choose first participant");
                comboBoxPOrT2.setPromptText("Choose second participant");
            }
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
                    if (team.getGame().getId()==gamePlay.getId()){
                        teamsCount++;
                        comboBoxPOrT1.getItems().add(team.getTeam_name());
                        comboBoxPOrT2.getItems().add(team.getTeam_name());
                    }
                }
                if (teamsCount==0){
                    comboBoxPOrT1.setPromptText("No teams for that game");
                    comboBoxPOrT2.setPromptText("No teams for that game");
                }
            }
        });


        comboBoxPOrT1.setDisable(true);
        comboBoxPOrT1.setOnAction(e ->{
            matchTemp.setNameOne(comboBoxPOrT1.getValue());
            if (singelNotTeam) {
                player1 = pDao.getByNickname(comboBoxPOrT1.getValue());
            }else {
                team1 = teamDao.getTeamByName(team1Name);
            }
        });

        comboBoxPOrT2.setOnAction(e ->{
            matchTemp.setNameTwo(comboBoxPOrT2.getValue());
            if (singelNotTeam) {
                player2 = pDao.getByNickname(comboBoxPOrT2.getValue());
            }else {
                team2 = teamDao.getTeamByName(team2Name);
            }
        });

        ComboBox<String> timeHour = new ComboBox();
        if (updating){
            hourSelected = matchTemp.getDate().substring(11, 13);
            timeHour.setPromptText(hourSelected);
        }else {
            timeHour.setPromptText("Hour");
        }
        timeHour.getItems().addAll("00","01","02","03","04","05","06","07","08","09",
                "10","11","12","13","14","15","16","17","18","19","20","21",
                "22","23","24");

        ComboBox<String> timeMin = new ComboBox();
        if (updating){
            minSelected = matchTemp.getDate().substring(14, 16);
            timeMin.setPromptText(minSelected);
        }else {
            timeMin.setPromptText("min");
        }
        timeMin.getItems().addAll("00","01","02","03","04","05","06","07","08","09",
                "10","11","12","13","14","15","16","17","18","19","20","21",
                "22","23","24","25","26","27","28","29","30","31","32","33",
                "34","35","36","37","38","39","40","41","42","43","44","45","46","47",
                "48","49","50","51","52","53","54","55","56","57","58","59");

        timeHour.setOnAction(e ->{
           hourSelected = timeHour.getValue();
        });
        timeMin.setOnAction(e ->{
           minSelected = timeMin.getValue();
        });


        DatePicker datePicker = new DatePicker();
        if (updating){
            date =matchTemp.getDate().substring(0, 10);
            datePicker.setPromptText(date);
        }else {
            datePicker.setPromptText("Date is required");
        }
        datePicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                LocalDate dateLocal = datePicker.getValue();
                date = dateLocal.toString();
            }
        });

        TextField score1TF = new TextField();
        TextField score2TF = new TextField();
        score1TF.setDisable(!allreadyPlayed);
        score2TF.setDisable(!allreadyPlayed);
        if (updating){
            score1TF.setPromptText("Enter score");                          // TODO ----------------------
            score2TF.setPromptText("Enter score");                          // TODO ----------------------
        }

        score1TF.setTextFormatter(new TextFormatter<Integer>(change -> {
                String newText = change.getControlNewText();
                if (newText.matches("\\d*")) {
                    return change;
                }
                return null;
            }));

        score2TF.setTextFormatter(new TextFormatter<Integer>(change -> {
                String newText = change.getControlNewText();
                if (newText.matches("\\d*")) {
                    return change;
                }
                return null;
            }));




        //----------------------------------------------------------------------Checkbox---------------
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(false);
        checkBox.setOnAction(event -> {
            allreadyPlayed=!allreadyPlayed;
            score1TF.setDisable(!allreadyPlayed);
            score2TF.setDisable(!allreadyPlayed);
            labelScore1.setDisable(!allreadyPlayed);
            labelScore2.setDisable(!allreadyPlayed);
        });
        //----------------------------------------------------------------------Buttons---------------

        Button pOrTButton = new Button("Add a team match");
        pOrTButton.setOnAction(event -> {
                    singelNotTeam=!singelNotTeam;
                    comboBoxPOrT2.getItems().removeAll(comboBoxPOrT2.getItems());
                    comboBoxPOrT1.setValue("");

                    comboBoxPOrT1.setPromptText("Choose first participant");
                    comboBoxPOrT2.setPromptText("Choose second participant");
                    comboBoxPOrT1.getItems().removeAll(comboBoxPOrT1.getItems()); // TODO ----------------

                    if(singelNotTeam){
                        pOrTButton.setText("Add a team match");
                        pOrTString = "Player";
                        nowShowing.setText("ADD A PLAYER MATCH");
                        for (Team team : teams) {
                            comboBoxPOrT1.getItems().add(team.getTeam_name());
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
                    stage2.show();
                }
        );



        Button cancel = new Button("Cancel");
        cancel.setOnAction( event ->{
            stage2.close();
                }
        );

        Button saveTheMatch = new Button("Save match");
        saveTheMatch.setOnAction(event -> {
            if(score1TF.getText()=="")
                System.out.println("score1TF is null");
            if(comboBoxPOrT1.getValue()=="")
                System.out.println("comboBoxPOrT1 is null");
            try{
                scoreP1=Integer.parseInt(score1TF.getText());
                scoreP2=Integer.parseInt(score2TF.getText());
                setScore();
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println(date);
            }

            date=date + " " + hourSelected + ":" + minSelected;
            matchTemp.setDate(date);
            matchTemp.setAllreadyPlayed(allreadyPlayed);
            matchTemp.setPlayerTeam(pOrTString);
            if (singelNotTeam) {
                matchTemp.getPlayers().add(player1);
                matchTemp.getPlayers().add(player2);
            }else {
                matchTemp.getTeams().add(team1);
                matchTemp.getTeams().add(team2);
            }
            matchDAO.saveMatch(matchTemp);
            matches.add(matchTemp);

            //stage2.close();                                              ///// nollställ istället?
        });

        Button updateTheMatch = new Button("Update match");
        updateTheMatch.setOnAction(event -> {

            setScore();

            date=date + " " + hourSelected + ":" + minSelected;
            matchTemp.setDate(date);
            matchTemp.setAllreadyPlayed(allreadyPlayed);
            matchTemp.setPlayerTeam(pOrTString);
            if (singelNotTeam) {
                matchTemp.getPlayers().add(player1);
                matchTemp.getPlayers().add(player2);
            }else {
                matchTemp.getTeams().add(team1);
                matchTemp.getTeams().add(team2);
            }
            matchDAO.updateMatch(matchTemp);
            matches.clear();
            matches.addAll(matchDAO.getAllMatches());

            stage2.close();
        });

        //----------------------------------------------------------------------Layout---------------
        nowShowing.setLayoutX(20);
        nowShowing.setLayoutY(20);

        labelGame.setLayoutX(20);
        labelGame.setLayoutY(55);
        labelPOrTOne.setLayoutX(20);
        labelPOrTOne.setLayoutY(90);
        labelDate.setLayoutX(20);
        labelDate.setLayoutY(125);

        comboBoxGame.setLayoutX(85);
        comboBoxGame.setLayoutY(55);
        comboBoxPOrT1.setLayoutX(85);
        comboBoxPOrT1.setLayoutY(90);

        datePicker.setLayoutX(85);
        datePicker.setLayoutY(125);
        timeHour.setLayoutX(340);
        timeHour.setLayoutY(125);
        timeMin.setLayoutX(420);
        timeMin.setLayoutY(125);

        labelAllreadyPlayed.setLayoutX(340);
        labelAllreadyPlayed.setLayoutY(55);
        labelPOrTwo.setLayoutX(270);
        labelPOrTwo.setLayoutY(90);
        labelTime.setLayoutX(270);
        labelTime.setLayoutY(125);

        pOrTButton.setLayoutX(20);
        pOrTButton.setLayoutY(250);

        cancel.setLayoutX(270);
        cancel.setLayoutY(250);

        labelScore1.setLayoutX(20);
        labelScore1.setLayoutY(195);
        labelScore2.setLayoutX(310);
        labelScore2.setLayoutY(195);
        score1TF.setLayoutX(145);
        score1TF.setLayoutY(192);
        score2TF.setLayoutX(360);
        score2TF.setLayoutY(192);
        comboBoxPOrT2.setLayoutX(340);
        comboBoxPOrT2.setLayoutY(90);
        saveTheMatch.setLayoutX(340);
        saveTheMatch.setLayoutY(250);
        updateTheMatch.setLayoutX(340);
        updateTheMatch.setLayoutY(250);
        checkBox.setLayoutX(440);
        checkBox.setLayoutY(55);

        paneAdd.getChildren().addAll(checkBox, labelGame, labelPOrTOne,labelDate,
                labelTime,nowShowing, labelAllreadyPlayed, labelPOrTwo,
                comboBoxGame,comboBoxPOrT1,comboBoxPOrT2, labelScore1,labelScore2,
                datePicker,cancel, timeHour,timeMin,score1TF,score2TF);
        if(updating){
            paneAdd.getChildren().add(updateTheMatch);
        }else{
            paneAdd.getChildren().addAll(saveTheMatch,pOrTButton);
        }
    }

    public void setScore() {
        finalScore = scoreP1 + " - " + scoreP2;
        matchTemp.setFinalScore(finalScore);
        if (scoreP1==scoreP2){
            matchTemp.setWinnerName("Draw");
        } else if (scoreP1>=scoreP2) {
            if (singelNotTeam){
                matchTemp.setWinnerName(player1.getNickname());
                matchTemp.setWinnerIfPlayer(player1);
            }else{
                matchTemp.setWinnerName(team1Name);
                matchTemp.setWinnerIfTeam(team1);
            }
        }else {
            if (singelNotTeam){
                matchTemp.setWinnerName(player2.getNickname());
                matchTemp.setWinnerIfPlayer(player2);
            }else{
                matchTemp.setWinnerName(team2Name);
                matchTemp.setWinnerIfTeam(team2);
            }
        }
    }

    //----start shows matches----
    public VBox start() {

        //----TableView-------------------------------------------------------------------------------

        matches = FXCollections.observableList(matchDAO.getAllMatches());

        TableView<Match> table = new TableView<>();
        table.setPrefWidth(530);

        TableColumn<Match,String> dateCol = new TableColumn<>("Date");
        dateCol.setPrefWidth(105);
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
        decidedCol.setEditable(false);
        decidedCol.setCellFactory(c->new TableCell<Match,Boolean>(){
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if(!empty) {
                    CheckBox checkBoxTable = new CheckBox();
                    checkBoxTable.setDisable(true);
                    checkBoxTable.setOpacity(1);
                        if (item) {
                            checkBoxTable.setSelected(true);
                        } else {
                         checkBoxTable.setSelected(false);
                        }
                    setGraphic(checkBoxTable);
                }
                else{setGraphic(null);}
            }
        });


        TableColumn<Match,String> winnerCol = new TableColumn<>("Winner");
        winnerCol.setPrefWidth(80);
        winnerCol.setCellValueFactory(
                new PropertyValueFactory<>("winnerName")
        );

        TableColumn<Match,String> scoreCol = new TableColumn<>("Score");
        scoreCol.setPrefWidth(80);
        scoreCol.setCellValueFactory(
                new PropertyValueFactory<>("finalScore")
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


        table.getColumns().addAll(dateCol, gameCol, pOrTCol,  pOrTOneCol, pOrTTwoCol,
                decidedCol,  scoreCol,winnerCol);

        table.setPlaceholder(new Label("No matches like that in the database. Try adding one!"));

        //----------------------------------------------End TableView--------------------------------


        //----------------------------------------------Buttons--------------------------------

        Button buttonDelete = new Button("Delete selected match");
        buttonDelete.setOnAction(event -> {
            matchDAO.deleteMatch(table.getSelectionModel().getSelectedItem());
            matches.remove(table.getSelectionModel().getSelectedItem());
        });

        Button buttonNew = new Button("Add a new match");
        buttonNew.setOnAction(event -> {
            addMatch(-1);
        });

        Button buttonEdit = new Button("Edit results of selected match");
        buttonEdit.setOnAction(event -> {
            addMatch((table.getSelectionModel().getSelectedItem().getId()));
        });

        buttonDelete.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));
        buttonEdit.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));

        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(buttonNew,buttonEdit, buttonDelete);


        VBox vBoxAll = new VBox();
        vBoxAll.setPrefSize(630, 565);//bredd, höjd

        vBoxAll.getChildren().addAll(table, toggle(),buttons);
        vBoxAll.setPadding(new Insets(10, 10, 10, 10));
        vBoxAll.setSpacing(10);

        return vBoxAll;
    }

    public HBox toggle(){
        //----------------------------------------------Toggle buttons--------------------------------

        ToggleGroup togglePlayed = new ToggleGroup();
        ToggleGroup togglePorT = new ToggleGroup();

        RadioButton rbPlayed1 = new RadioButton("Show both upcoming and finished matches");
        RadioButton rbPlayed2 = new RadioButton("Show only upcoming matches");
        RadioButton rbPlayed3 = new RadioButton("Show only finished matches");

        RadioButton rbPorT1 = new RadioButton("Show both \"Team\" and \"Player\" matches");
        RadioButton rbPort2 = new RadioButton("Show only \"Team\" matches");
        RadioButton rbPorT3 = new RadioButton("Show only \"Player\" matches");

        rbPlayed1.setToggleGroup(togglePlayed);
        rbPlayed2.setToggleGroup(togglePlayed);
        rbPlayed3.setToggleGroup(togglePlayed);
        togglePlayed.selectToggle(rbPlayed1);

        rbPorT1.setToggleGroup(togglePorT);
        rbPort2.setToggleGroup(togglePorT);
        rbPorT3.setToggleGroup(togglePorT);
        togglePorT.selectToggle(rbPorT1);


        togglePlayed.selectedToggleProperty().addListener(new ChangeListener<>() {
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {
                RadioButton rb = (RadioButton) togglePlayed.getSelectedToggle();
                if (rb==rbPlayed1) {
                    checkPlayed=1;
                    if (checkPorT==1) {
                        matches.clear();
                        matches.addAll(matchDAO.getAllMatches());
                    } else if (checkPorT==2) {
                        matches.clear();
                        matches.addAll(matchDAO.getPlayerTeam("Team"));
                    } else if (checkPorT==3) {
                        matches.clear();
                        matches.addAll(matchDAO.getPlayerTeam("Player"));
                    }
                } else if (rb==rbPlayed2) {
                    checkPlayed=2;
                    if (checkPorT==1) {
                        matches.clear();
                        matches.addAll(matchDAO.getAllreadyPlayed(false));
                    } else if (checkPorT==2) {
                        matches.clear();
                        matches.addAll(matchDAO.getAllreadyPlayedAndPorT(false, "Team"));
                    } else if (checkPorT==3) {
                        matches.clear();
                        matches.addAll(matchDAO.getAllreadyPlayedAndPorT(false,"Player"));
                    }
                } else if (rb==rbPlayed3) {
                    checkPlayed=3;
                    if (checkPorT==1) {
                        matches.clear();
                        matches.addAll(matchDAO.getAllreadyPlayed(true));
                    } else if (checkPorT==2) {
                        matches.clear();
                        matches.addAll(matchDAO.getAllreadyPlayedAndPorT(true, "Team"));
                    } else if (checkPorT==3) {
                        matches.clear();
                        matches.addAll(matchDAO.getAllreadyPlayedAndPorT(true, "Player"));
                    }
                }
            }
        });

        togglePorT.selectedToggleProperty().addListener(new ChangeListener<>() {
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {
                RadioButton rb2 = (RadioButton) togglePorT.getSelectedToggle();
                if (rb2==rbPorT1) {
                    checkPorT=1;
                    if (checkPlayed==1) {
                        matches.clear();
                        matches.addAll(matchDAO.getAllMatches());
                    } else if (checkPlayed==2) {
                        matches.clear();
                        matches.addAll(matchDAO.getAllreadyPlayed(false));
                    } else if (checkPlayed==3) {
                        matches.clear();
                        matches.addAll(matchDAO.getAllreadyPlayed(true));
                    }
                } else if (rb2==rbPort2) {
                    checkPorT=2;
                    if (checkPlayed==1) {
                        matches.clear();
                        matches.addAll(matchDAO.getPlayerTeam("Team"));
                    } else if (checkPlayed==2) {
                        matches.clear();
                        matches.addAll(matchDAO.getAllreadyPlayedAndPorT(false, "Team"));
                    } else if (checkPlayed==3) {
                        matches.clear();
                        matches.addAll(matchDAO.getAllreadyPlayedAndPorT(true,"Team"));
                    }
                } else if (rb2==rbPorT3) {
                    checkPorT=3;
                    if (checkPlayed==1) {
                        matches.clear();
                        matches.addAll(matchDAO.getPlayerTeam("Player"));
                    } else if (checkPlayed==2) {
                        matches.clear();
                        matches.addAll(matchDAO.getAllreadyPlayedAndPorT(false, "Player"));
                    } else if (checkPlayed==3) {
                        matches.clear();
                        matches.addAll(matchDAO.getAllreadyPlayedAndPorT(true, "Player"));
                    }
                }
            }
        });

        //---------------------------------------End Toggle buttons------------------------

        String cssLayout = "-fx-border-color: lightgrey;\n" +
                "-fx-border-radius: 10px;" +
                "-fx-border-width: 1;";

        VBox vBoxPlayed = new VBox();
        VBox vBoxPorT = new VBox();

        vBoxPlayed.setSpacing(10);
        vBoxPorT.setSpacing(10);
        vBoxPlayed.setPadding(new Insets(10, 10, 10, 10));
        vBoxPorT.setPadding(new Insets(10, 10, 10, 10));

        vBoxPlayed.getChildren().add(rbPlayed1);
        vBoxPlayed.getChildren().add(rbPlayed2);
        vBoxPlayed.getChildren().add(rbPlayed3);

        vBoxPorT.getChildren().add(rbPorT1);
        vBoxPorT.getChildren().add(rbPort2);
        vBoxPorT.getChildren().add(rbPorT3);

        vBoxPorT.setStyle(cssLayout);
        vBoxPlayed.setStyle(cssLayout);

        HBox hBoxUnderTable = new HBox();
        hBoxUnderTable.setSpacing(20);
        hBoxUnderTable.getChildren().addAll(vBoxPlayed,vBoxPorT);

        return hBoxUnderTable;
    }

}