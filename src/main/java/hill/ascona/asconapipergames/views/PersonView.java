package hill.ascona.asconapipergames.views;


//----Elham Farhang----

import hill.ascona.asconapipergames.DAO.GameDAO;
import hill.ascona.asconapipergames.DAO.PersonDAO;
import hill.ascona.asconapipergames.DAO.TeamDAO;
import hill.ascona.asconapipergames.entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public  class PersonView {

    private PersonDAO personDAO = new PersonDAO();
    List<Person> personList= new ArrayList<>();
    private Person dataToUpdate= new Person();
    private GameDAO gameDAO = new GameDAO();
    List<Game> gameList= new ArrayList<>();
    private Game game =new Game();
    private TeamDAO teamDAO = new TeamDAO();
    private Team team = new Team();
    List<Team> teamList= new ArrayList<>();
    private ObservableList<Person> pInfo= FXCollections.observableArrayList(new ArrayList<>());

    public AnchorPane start() {

        TabPane personTabPane = new TabPane();
        personTabPane.setPrefSize(700,600);

        Tab tab1 = new Tab("Add", showAllPersons());
        Tab tab2 = new Tab("Search", searchPlayer());
        Tab tab3 = new Tab("Update", updatePlayersInfo());

        personTabPane.getTabs().add(tab1);
        personTabPane.getTabs().add(tab2);
        personTabPane.getTabs().add(tab3);

        AnchorPane personAnchorPane= new AnchorPane(personTabPane);
        return personAnchorPane;
    }

    private AnchorPane showAllPersons() {
        AnchorPane anchorPane1= new AnchorPane();

//--------------SHOW PERSON'S INFO
        Separator separator1= new Separator(Orientation.HORIZONTAL);
        VBox vBox1=new VBox(separator1);
        vBox1.setPrefSize(550,10);
        vBox1.setLayoutX(140);
        vBox1.setLayoutY(35);

        Button showButton=new Button("Show information");
        showButton.setDisable(true);
        showButton.setPrefSize(145,23);
        showButton.setLayoutX(175);
        showButton.setLayoutY(55);

        Label labelShowTable= new Label("Person's information:");
        labelShowTable.setFont(new Font("Cambria Bold",12));
        labelShowTable.setLayoutX(15);
        labelShowTable.setLayoutY(25);

        ComboBox<String> comboBoxShowTable =new ComboBox<>();
        comboBoxShowTable.setPrefSize(145,23);
        comboBoxShowTable.setPromptText("Role:");
        comboBoxShowTable.setLayoutX(25);
        comboBoxShowTable.setLayoutY(55);
        comboBoxShowTable.getItems().addAll("Player","User");
        comboBoxShowTable.setOnAction(e-> showButton.setDisable(false));

        TableView<Person> tableView = new TableView();
        tableView.setEditable(false);
        tableView.setPrefSize(650,200);
        tableView.setLayoutX(25);
        tableView.setLayoutY(90);

        TableColumn<Person, Integer> column1 = new TableColumn<>("Id");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Person, String> column2 = new TableColumn<>("First Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Person, String> column3 = new TableColumn<>("Last Name");
        column3.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        TableColumn<Person, String> column4 = new TableColumn<>("Nickname");
        column4.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        TableColumn<Person, String> column5 = new TableColumn<>("Address");
        column5.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<Person, String> column6 = new TableColumn<>("Post No.");
        column6.setCellValueFactory(new PropertyValueFactory<>("postNumber"));
        TableColumn<Person, String> column7 = new TableColumn<>("City");
        column7.setCellValueFactory(new PropertyValueFactory<>("city"));
        TableColumn<Person, String> column8 = new TableColumn<>("Country");
        column8.setCellValueFactory(new PropertyValueFactory<>("country"));
        TableColumn<Person, String> column9 = new TableColumn<>("E-mail");
        column9.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Person, String> column10 = new TableColumn<>("Role");
        column10.setCellValueFactory(new PropertyValueFactory<>("role"));
        TableColumn<Person, Team> column11 = new TableColumn<>("Team");
        column11.setCellValueFactory(new PropertyValueFactory<>("team"));
        TableColumn<Person, Game> column12 = new TableColumn<>("Game");
        column12.setCellValueFactory(new PropertyValueFactory<>("game"));

        tableView.getColumns().addAll(column1,column2,column3,column4,column5,column6,column7,column8,column9,column10,column11,column12);

        showButton.setOnAction(e ->{
            if(comboBoxShowTable.getValue().equals("Player")){
                pInfo = FXCollections.observableList(personDAO.getAllPlayersOrUsers("Player"));
                tableView.setItems(pInfo);
            } else if (comboBoxShowTable.getValue().equals("User")) {
                pInfo = FXCollections.observableList(personDAO.getAllPlayersOrUsers("User"));
                tableView.setItems(pInfo);
            }
        });

//--------------ADD NEW PERSON
        Separator separator2= new Separator(Orientation.HORIZONTAL);
        VBox vBox2=new VBox(separator2);
        vBox2.setPrefSize(575,10);
        vBox2.setLayoutX(112);
        vBox2.setLayoutY(318);

        Label labelRegister= new Label("Add new person:");
        labelRegister.setFont(new Font("Cambria Bold",12));
        labelRegister.setLayoutX(15);
        labelRegister.setLayoutY(310);

        ComboBox<Game> comboBoxGame =new ComboBox<>();
        comboBoxGame.setPromptText("Game:");
        comboBoxGame.setPrefSize(165,23);
        ComboBox<Team> comboBoxTeam =new ComboBox<>();
        comboBoxTeam.setPromptText("Team:");
        comboBoxTeam.getItems().addAll(teamDAO.getAllTeams());
        comboBoxTeam.setPrefSize(165,23);
        comboBoxTeam.setOnAction(e->{
            comboBoxGame.getItems().clear();
            comboBoxGame.setValue(null);
            team = comboBoxTeam.getValue();
            game = team.getGame();
            System.out.println(game);
            comboBoxGame.getItems().add(game);
        });
        ComboBox<String> comboBoxRole =new ComboBox<>();
        comboBoxRole.setPromptText("Role:");
        comboBoxRole.getItems().addAll("Player","User");
        comboBoxRole.setPrefSize(165,23);
        comboBoxRole.setOnAction(e->{
            if(comboBoxRole.getValue().equals("User")){
                comboBoxTeam.setDisable(true);
                comboBoxGame.setDisable(true);}
            else if (comboBoxRole.getValue().equals("Player")){
                comboBoxTeam.setDisable(false);
            comboBoxGame.setDisable(false);}
        });
        TextField textFieldName= new TextField();
        textFieldName.setPromptText("First name");
        TextField textFieldLastName= new TextField();
        textFieldLastName.setPromptText("Last name");
        TextField textFieldNickName= new TextField();
        textFieldNickName.setPromptText("Nickname");
        TextField textFieldAddress= new TextField();
        textFieldAddress.setPromptText("Address");
        TextField textFieldPostNo= new TextField();
        textFieldPostNo.setPromptText("Post number");
        TextField textFieldCity= new TextField();
        textFieldCity.setPromptText("City");
        TextField textFieldCountry= new TextField();
        textFieldCountry.setPromptText("Country");
        TextField textFieldEmail= new TextField();
        textFieldEmail.setPromptText("e-mail");

        Label labelControl = new Label("Enter the person's first name, last name and nickname");
        labelControl.setLayoutX(10);
        labelControl.setLayoutY(437);
        labelControl.setTextFill(Color.RED);
        labelControl.setVisible(false);

        Button buttonSave=new Button("Save");
        buttonSave.setPrefSize(145,23);
        buttonSave.setOnAction(e->{

            if (textFieldName.getText().isEmpty()|| textFieldLastName.getText().isEmpty()||textFieldNickName.getText().isEmpty())
            {
                labelControl.setVisible(true);
            } else {
                Person user = new Person(textFieldName.getText(), textFieldLastName.getText(), textFieldNickName.getText(), textFieldAddress.getText()
                        , textFieldPostNo.getText(), textFieldCity.getText(), textFieldCountry.getText(), textFieldEmail.getText(), comboBoxRole.getValue(), comboBoxTeam.getValue(),comboBoxGame.getValue());
                if (personDAO.addPerson(user)) {
                    labelControl.setText("User saved!");
                    labelControl.setVisible(true);
                    System.out.println("User saved!");
                } else {
                    labelControl.setText("Not saved!");
                    labelControl.setVisible(true);
                    System.out.println("Not saved!");
                }
            }
        });

        TilePane tilePane1= new TilePane((Node) comboBoxRole,textFieldName,textFieldLastName,textFieldNickName,textFieldAddress,textFieldPostNo,textFieldCity,textFieldCountry,textFieldEmail,comboBoxTeam,comboBoxGame, buttonSave);
        tilePane1.setHgap(5);
        tilePane1.setVgap(5);
        tilePane1.setTileAlignment(Pos.CENTER);
        tilePane1.setPrefSize(690,140);
        tilePane1.setLayoutX(10);
        tilePane1.setLayoutY(345);

        anchorPane1.getChildren().addAll(vBox1,labelShowTable,comboBoxShowTable,showButton,tableView,vBox2,labelRegister,tilePane1,labelControl);
        return anchorPane1;
    }

//--------------SEARCH PLAYERS
    private AnchorPane searchPlayer() {
        AnchorPane anchorPane2= new AnchorPane();

        Separator separator3= new Separator(Orientation.HORIZONTAL);
        VBox vBox3=new VBox(separator3);
        vBox3.setPrefSize(480,10);
        vBox3.setLayoutX(180);
        vBox3.setLayoutY(35);

        Label labelSearch=new Label("Search player by game name:");
        labelSearch.setFont(new Font("Cambria Bold",12));
        labelSearch.setLayoutX(15);
        labelSearch.setLayoutY(25);

        TextField textFieldSearch=new TextField();
        textFieldSearch.setPrefSize(195,23);
        textFieldSearch.setLayoutX(65);
        textFieldSearch.setLayoutY(55);
        textFieldSearch.setPromptText("Game name");

        Button buttonSearch= new Button("Search");
        buttonSearch.setPrefSize(145,23);
        buttonSearch.setLayoutX(275);
        buttonSearch.setLayoutY(55);

        Button buttonShowAll= new Button("Show all games");
        buttonShowAll.setPrefSize(145,23);
        buttonShowAll.setLayoutX(440);
        buttonShowAll.setLayoutY(55);

        TableView<Person> tableView = new TableView();
        tableView.setEditable(false);
        tableView.setPrefSize(600,300);
        tableView.setLayoutX(50);
        tableView.setLayoutY(95);

        TableColumn<Person, String> column2 = new TableColumn<>("First Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Person, String> column3 = new TableColumn<>("Last Name");
        column3.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        TableColumn<Person, String> column4 = new TableColumn<>("Nickname");
        column4.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        TableColumn<Person, Team> column11 = new TableColumn<>("Team");
        column11.setCellValueFactory(new PropertyValueFactory<>("team"));
        column11.setCellFactory(c -> new TableCell<Person, Team>() {
                    @Override
                    public void updateItem(Team team, boolean empty) {
                        super.updateItem(team, empty);
                        if (!empty)
                            setText(team.getTeam_name());
                        else
                            setText(null);
                    }
        });
        TableColumn<Person, Game> column12 = new TableColumn<>("Game");
        column12.setCellValueFactory(new PropertyValueFactory<>("game"));
        column12.setCellFactory(c -> new TableCell<Person, Game>() {
            @Override
            public void updateItem(Game game, boolean empty) {
                super.updateItem(game, empty);
                if (!empty)
                    setText(game.getTitle());
                else
                    setText(null);
            }
        });


        tableView.getColumns().addAll(column2,column3,column4,column11,column12);

        Label labelControl= new Label("Not found!");
        labelControl.setLayoutX(60);
        labelControl.setLayoutY(430);
        labelControl.setTextFill(Color.RED);
        labelControl.setVisible(false);

        buttonSearch.setOnAction(e->{
            labelControl.setVisible(false);
            tableView.getItems().clear();
            if(textFieldSearch.getText().isEmpty()){
                labelControl.setVisible(true);
            }else {
            String text = textFieldSearch.getText();
            String regex = ",";
            String[] gameTitle = text.split(regex);
            for(String i: gameTitle)
             {
                 System.out.println(i);
                 game = gameDAO.getGameIdByTitle(i);
                 teamList = teamDAO.getTeamIdByGameId(game);
                 personList.addAll(personDAO.getPlayersInfoByTeamId(teamList));
            }
            pInfo = FXCollections.observableList(personList);
            if (pInfo.isEmpty() || textFieldSearch.getText().isEmpty()){
            }
            tableView.setItems(pInfo);}
        });

        buttonShowAll.setOnAction(e->{
            tableView.getItems().clear();
            personList.clear();
            gameList.addAll(gameDAO.getAllGames());
            for(Game i: gameList) {
                teamList = teamDAO.getTeamIdByGameId(i);
                personList.addAll(personDAO.getPlayersInfoByTeamId(teamList));
            }
            pInfo = FXCollections.observableList(personList);
            tableView.setItems(pInfo);
        });

        anchorPane2.getChildren().addAll(vBox3,labelSearch,textFieldSearch,buttonSearch,buttonShowAll,tableView,labelControl);
        return anchorPane2;
    }

//--------------EDIT OR DELETE
    private AnchorPane updatePlayersInfo() {
        AnchorPane anchorPane3= new AnchorPane();

        Separator separator4= new Separator(Orientation.HORIZONTAL);
        VBox vBox4=new VBox(separator4);
        vBox4.setPrefSize(515,10);
        vBox4.setLayoutX(180);
        vBox4.setLayoutY(35);

        Label labelSearch=new Label("Update player's information:");
        labelSearch.setFont(new Font("Cambria Bold",12));
        labelSearch.setLayoutX(15);
        labelSearch.setLayoutY(25);

        Label labelEdit= new Label("Search player by name:");
        labelEdit.setLayoutX(25);
        labelEdit.setLayoutY(55);

        TextField textFieldSearch=new TextField();
        textFieldSearch.setPrefSize(145,23);
        textFieldSearch.setLayoutX(155);
        textFieldSearch.setLayoutY(52);
        textFieldSearch.setPromptText("Player-name");

        Button buttonSearch= new Button("Search");
        buttonSearch.setPrefSize(145,23);
        buttonSearch.setLayoutX(310);
        buttonSearch.setLayoutY(52);

        TableView<Person> tableView = new TableView();
        tableView.setEditable(false);
        tableView.setPrefSize(650,170);
        tableView.setLayoutX(25);
        tableView.setLayoutY(95);

        TableColumn<Person, Integer> column1 = new TableColumn<>("Id");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Person, String> column2 = new TableColumn<>("First Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Person, String> column3 = new TableColumn<>("Last Name");
        column3.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        TableColumn<Person, String> column4 = new TableColumn<>("Nickname");
        column4.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        TableColumn<Person, String> column10 = new TableColumn<>("Role");
        column10.setCellValueFactory(new PropertyValueFactory<>("role"));
        TableColumn<Person, Team> column11 = new TableColumn<>("Team");
        column11.setCellValueFactory(new PropertyValueFactory<>("team"));

        tableView.getColumns().addAll(column1,column2,column3,column4,column10,column11);

        buttonSearch.setOnAction(e->{
            pInfo = FXCollections.observableList(personDAO.getPersonInfo(textFieldSearch.getText()));
            tableView.setItems(pInfo);
        });

        ComboBox<Game> comboBoxGame =new ComboBox<>();
        comboBoxGame.setPromptText("Game:");
        comboBoxGame.setPrefSize(165,23);
        ComboBox<Team> comboBoxTeam =new ComboBox<>();
        comboBoxTeam.setPromptText("Team:");
        comboBoxTeam.getItems().addAll(teamDAO.getAllTeams());
        comboBoxTeam.setPrefSize(165,23);
        comboBoxTeam.setOnAction(e->{
            comboBoxGame.getItems().clear();
            comboBoxGame.setValue(null);
            team = comboBoxTeam.getValue();
            game = team.getGame();
            comboBoxGame.getItems().add(game);
        });
        ComboBox<String> comboBoxRole =new ComboBox<>();
        comboBoxRole.setPromptText("Role:");
        comboBoxRole.getItems().addAll("Player","User");
        comboBoxRole.setPrefSize(165,23);
        comboBoxRole.setOnAction(e->{
            if(comboBoxRole.getValue().equals("User"))
                comboBoxTeam.setDisable(true);
            else if (comboBoxRole.getValue().equals("Player"))
                comboBoxTeam.setDisable(false);
        });
        TextField textFieldName= new TextField();
        textFieldName.setPromptText("First name");
        TextField textFieldLastName= new TextField();
        textFieldLastName.setPromptText("Last name");
        TextField textFieldNickName= new TextField();
        textFieldNickName.setPromptText("Nickname");
        TextField textFieldAddress= new TextField();
        textFieldAddress.setPromptText("Address");
        TextField textFieldPostNo= new TextField();
        textFieldPostNo.setPromptText("Post number");
        TextField textFieldCity= new TextField();
        textFieldCity.setPromptText("City");
        TextField textFieldCountry= new TextField();
        textFieldCountry.setPromptText("Country");
        TextField textFieldEmail= new TextField();
        textFieldEmail.setPromptText("e-mail");

        Label labelInfo= new Label();
        labelInfo.setLayoutX(75);
        labelInfo.setLayoutY(460);
        labelInfo.setTextFill(Color.RED);
        labelInfo.setVisible(false);

        Button buttonUpdate=new Button("Update");
        buttonUpdate.setDisable(true);
        buttonUpdate.setPrefSize(145,23);
        buttonUpdate.setOnAction(e->{
            dataToUpdate.setRole(comboBoxRole.getValue());
            dataToUpdate.setName(textFieldName.getText());
            dataToUpdate.setLastname(textFieldLastName.getText());
            dataToUpdate.setNickname(textFieldNickName.getText());
            dataToUpdate.setAddress(textFieldAddress.getText());
            dataToUpdate.setPostNumber(textFieldPostNo.getText());
            dataToUpdate.setCity(textFieldCity.getText());
            dataToUpdate.setCountry(textFieldCountry.getText());
            dataToUpdate.setEmail(textFieldEmail.getText());
            dataToUpdate.setTeam(comboBoxTeam.getValue());
            dataToUpdate.setGame(comboBoxGame.getValue());
            personDAO.updatePlayersInfo(dataToUpdate);
            labelInfo.setText("Updated!");
            labelInfo.setVisible(true);
            buttonUpdate.setDisable(true);
        });

        Button buttonDelete = new Button("Delete");
        buttonDelete.setDisable(true);
        buttonDelete.setPrefSize(145, 23);
        buttonDelete.setOnAction(e -> {
            personDAO.remove(dataToUpdate);
            labelInfo.setText("Deleted!");
            labelInfo.setVisible(true);
            buttonDelete.setDisable(true);
            tableView.getItems().clear();
            comboBoxRole.getItems().clear();
            textFieldName.setText(null);
            textFieldLastName.setText(null);
            textFieldNickName.setText(null);
            textFieldAddress.setText(null);
            textFieldPostNo.setText(null);
            textFieldCity.setText(null);
            textFieldCountry.setText(null);
            textFieldEmail.setText(null);
            comboBoxTeam.getItems().clear();
            comboBoxGame.getItems().clear();
            buttonUpdate.setDisable(true);
        });

        TilePane tilePane1= new TilePane((Node) comboBoxRole,textFieldName,textFieldLastName,textFieldNickName,textFieldAddress,textFieldPostNo,textFieldCity,textFieldCountry,textFieldEmail,comboBoxTeam,comboBoxGame,labelInfo,buttonUpdate,buttonDelete);
        tilePane1.setHgap(5);
        tilePane1.setVgap(5);
        tilePane1.setTileAlignment(Pos.CENTER);
        tilePane1.setPrefSize(570,200);
        tilePane1.setLayoutX(65);
        tilePane1.setLayoutY(300);

        tableView.setRowFactory(e -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked((m) -> {
                dataToUpdate=(row.getItem());
                comboBoxRole.setValue("Player");
                textFieldName.setText(dataToUpdate.getName());
                textFieldLastName.setText(dataToUpdate.getLastname());
                textFieldNickName.setText(dataToUpdate.getNickname());
                textFieldAddress.setText(dataToUpdate.getAddress());
                textFieldPostNo.setText(dataToUpdate.getPostNumber());
                textFieldCity.setText(dataToUpdate.getCity());
                textFieldCountry.setText(dataToUpdate.getCountry());
                textFieldEmail.setText(dataToUpdate.getEmail());
                comboBoxTeam.setValue(dataToUpdate.getTeam());
                buttonUpdate.setDisable(false);
                buttonDelete.setDisable(false);
            });
            return row;
        });

        anchorPane3.getChildren().addAll(vBox4,labelSearch,labelEdit,textFieldSearch,buttonSearch,tableView,tilePane1);
        return anchorPane3;
    }
}

