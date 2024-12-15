package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.entities.Person;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
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
        Label labelShowTable= new Label("Person's information:");
        labelShowTable.setLayoutX(10);
        labelShowTable.setLayoutY(5);
        ComboBox<String> comboBoxShowTable =new ComboBox<>();
        comboBoxShowTable.setPromptText("Role:");
        comboBoxShowTable.setLayoutX(10);
        comboBoxShowTable.setLayoutY(25);
        comboBoxShowTable.getItems().addAll("Player","User");
        comboBoxShowTable.setOnAction(e ->{
            if(comboBoxShowTable.getValue().equals("Player")){

            } else if (comboBoxShowTable.getValue().equals("User")) {

            }
        });

        TextArea textArea= new TextArea();
        textArea.setEditable(false);
        textArea.setPrefSize(670,70);
        textArea.setLayoutX(10);
        textArea.setLayoutY(60);

        Button showButton=new Button("Show information");
        showButton.setLayoutX(130);
        showButton.setLayoutY(25);
        showButton.setOnAction(e ->{
           List<Person> allPlayers= personDAO.getAllPlayersInfo();
//            Person personFromDatabase = personDAO.getPersonInfoById(1);
//            System.out.println("Hämtad från db med namn: " + personFromDatabase.getName());
            for (Person i: allPlayers){
                textArea.appendText(personDAO.getAllPlayersInfo().toString());
            }
        });

        Pane pane1=new Pane(labelShowTable,comboBoxShowTable,showButton,textArea);
        pane1.setPrefSize(700,135);
        pane1.setLayoutX(5);
        pane1.setLayoutY(5);

        Separator separator1= new Separator(Orientation.HORIZONTAL);
        VBox vBox1=new VBox(separator1);
        vBox1.setPrefSize(690,10);
        vBox1.setLayoutX(5);
        vBox1.setLayoutY(145);

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
        textFieldCity.setPromptText("Zone");
        TextField textFieldCountry= new TextField();
        textFieldCountry.setPromptText("Country");
        TextField textFieldEmail= new TextField();
        textFieldEmail.setPromptText("e-mail");
        TextField textFieldTeamId= new TextField();
        textFieldTeamId.setPromptText("Team-Id");

        Label labelControl = new Label("First-name, Last-name, Nickname");
        labelControl.setTextFill(Color.RED);
        labelControl.setVisible(false);

        Button buttonSave=new Button("Save");
        buttonSave.setPrefSize(145,23);
        buttonSave.setOnAction(e->{
            labelControl.setVisible(true);
            Person user = new Person(textFieldName.getText(),textFieldLastName.getText(),textFieldNickName.getText(),textFieldAddress.getText()
                    ,textFieldPostNo.getText(),textFieldCity.getText(),textFieldCountry.getText(),textFieldEmail.getText(),comboBoxRole.getValue(), textFieldTeamId.getText());
            if (personDAO.addPerson(user)){
                System.out.println("User saved!");
            }else {
                System.out.println("Not saved!");
            }
        });

        TilePane tilePane1= new TilePane((Node) comboBoxRole,textFieldName,textFieldLastName,textFieldNickName,textFieldAddress,textFieldPostNo,textFieldCity,textFieldCountry,textFieldEmail,textFieldTeamId, labelControl,buttonSave);
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
        vBox2.setLayoutY(310);

        //Ändra/ta bort spelare
        anchorPane1.getChildren().addAll(pane1,vBox1,labelRegister,tilePane1,vBox2);
        return anchorPane1;
    }
}
