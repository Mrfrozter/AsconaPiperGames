package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.entities.Person;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import hill.ascona.asconapipergames.DAO.PersonDAO;
import hill.ascona.asconapipergames.entities.Person;

import java.util.List;

public  class PersonView {
    public AnchorPane start() {

        PersonDAO personDAO = new PersonDAO();
        AnchorPane anchorPane1= new AnchorPane();
        anchorPane1.setPrefSize(690,690);

        Button showButton=new Button("Show information");
        showButton.setDisable(true);
        Label labelShowTable= new Label("Person's information:");
        labelShowTable.setLayoutX(10);
        labelShowTable.setLayoutY(5);
        ComboBox<String> comboBoxShowTable =new ComboBox<>();
        comboBoxShowTable.setPromptText("Role:");
        comboBoxShowTable.setLayoutX(10);
        comboBoxShowTable.setLayoutY(25);
        comboBoxShowTable.getItems().addAll("Player","User");
        comboBoxShowTable.setOnAction(e-> showButton.setDisable(false));

        TextArea textArea= new TextArea();
        textArea.setEditable(false);
        textArea.setPrefSize(670,70);
        textArea.setLayoutX(10);
        textArea.setLayoutY(60);

        showButton.setLayoutX(130);
        showButton.setLayoutY(25);
        showButton.setOnAction(e ->{
            if(comboBoxShowTable.getValue().equals("Player")){
                List<Person> allPlayers= personDAO.getPersonInfo("Player");
                textArea.clear();
                for (Person i: allPlayers){
                    System.out.println(i);
                    textArea.appendText(i.toString()+'\n');
                }
            } else if (comboBoxShowTable.getValue().equals("User")) {
                List<Person> allUsers = personDAO.getPersonInfo("User");
                textArea.clear();
                for (Person i: allUsers){
                    System.out.println(i);
                    textArea.appendText(i.toString()+'\n');
                }
            }
        });

        Pane pane1=new Pane(labelShowTable,comboBoxShowTable,showButton,textArea);
        pane1.setPrefSize(690,135);
        pane1.setLayoutX(5);
        pane1.setLayoutY(5);

        Separator separator1= new Separator(Orientation.HORIZONTAL);
        VBox vBox1=new VBox(separator1);
        vBox1.setPrefSize(690,10);
        vBox1.setLayoutX(5);
        vBox1.setLayoutY(150);

        Label labelRegister= new Label("Add new person:");
        labelRegister.setLayoutX(10);
        labelRegister.setLayoutY(155);

        ComboBox<String> comboBoxRole =new ComboBox<>();
        comboBoxRole.setPromptText("Role:");
        comboBoxRole.getItems().addAll("Player","User");
        comboBoxRole.setPrefSize(165,23);
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
        TextField textFieldTeamId= new TextField();
        textFieldTeamId.setPromptText("Team-Id");

        Label labelControl = new Label("Enter the person's first name, last name and nickname");
        labelControl.setLayoutX(10);
        labelControl.setLayoutY(270);
        labelControl.setTextFill(Color.RED);
        labelControl.setVisible(false);

        Button buttonSave=new Button("Save");
        buttonSave.setPrefSize(145,23);
        buttonSave.setOnAction(e->{
            if (textFieldName.getText().isEmpty()|| textFieldLastName.getText().isEmpty()||textFieldNickName.getText().isEmpty()){

                labelControl.setVisible(true);
            } else {
                Person user = new Person(textFieldName.getText(), textFieldLastName.getText(), textFieldNickName.getText(), textFieldAddress.getText()
                        , textFieldPostNo.getText(), textFieldCity.getText(), textFieldCountry.getText(), textFieldEmail.getText(), comboBoxRole.getValue(), textFieldTeamId.getText());
                if (personDAO.addPerson(user)) {
                    labelControl.setText("User saved!");
                    System.out.println("User saved!");
                } else {
                    labelControl.setText("Not saved!");
                    System.out.println("Not saved!");
                }
            }
        });

        TilePane tilePane1= new TilePane((Node) comboBoxRole,textFieldName,textFieldLastName,textFieldNickName,textFieldAddress,textFieldPostNo,textFieldCity,textFieldCountry,textFieldEmail,textFieldTeamId, buttonSave);
        tilePane1.setHgap(5);
        tilePane1.setVgap(5);
        tilePane1.setTileAlignment(Pos.CENTER);
        tilePane1.setPrefSize(690,140);
        tilePane1.setLayoutX(10);
        tilePane1.setLayoutY(180);

        Separator separator2= new Separator(Orientation.HORIZONTAL);
        VBox vBox2=new VBox(separator2);
        vBox2.setPrefSize(690,10);
        vBox2.setLayoutX(5);
        vBox2.setLayoutY(300);

        //Ändra/ta bort spelare


        TableView<Person> tableView = new TableView<>();

        tableView.setPrefSize(690,80);
        tableView.setLayoutX(5);
        tableView.setLayoutY(330);

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
        TableColumn<Person, String> column11 = new TableColumn<>("Team-id");
        column11.setCellValueFactory(new PropertyValueFactory<>("teamID"));

        tableView.getColumns().addAll(column1,column2,column3,column4,column5,column6,column7,column8,column9,column10,column11);


        anchorPane1.getChildren().addAll(pane1,vBox1,labelRegister,tilePane1,labelControl,vBox2,tableView);
        return anchorPane1;
    }
}
