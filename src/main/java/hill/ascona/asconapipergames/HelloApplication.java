package hill.ascona.asconapipergames;

import hill.ascona.asconapipergames.views.TournamentView;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import hill.ascona.asconapipergames.DAO.PersonDAO;
import hill.ascona.asconapipergames.entities.Person;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        PersonDAO personDAO = new PersonDAO();
        Person user = new Person("Elham","Farhang"," ","Vallentuna",123,"Stockholm","Sverige","elham@mail","spelare",1);
        if (personDAO.addPerson(user)){
            System.out.println("User saved!");
        }else {
            System.out.println("Not saved!");
        }

        Person personFromDatabase = personDAO.getPersonInfoById(1);
        System.out.println("Hämtad från db med namn: " + personFromDatabase.getName());

        System.out.println("Storlek på listan med personer är "+ personDAO.getAllPersonsInfo().size());

        AnchorPane loginAnchorPane=new AnchorPane();
        loginAnchorPane.setPrefSize(600,600);

        Scene loginScene=new Scene(loginAnchorPane);
        primaryStage.setScene(loginScene);

        Label label = new Label("Välkommen till Piper Game sida!");
        label.setPrefSize(400,400);
        label.setFont(new Font("Cambria Bold",20));
        label.setTextFill(Color.GRAY);
        label.setLayoutX(130);
        label.setLayoutY(10);

        Button loginButton=new Button("Logga in");
        loginButton.setDisable(true);
        loginButton.setOnAction(e ->{
            primaryStage.setScene(showMainScene());});
        loginButton.setPrefSize(115,28);
        loginButton.setLayoutX(220);
        loginButton.setLayoutY(330);

        ComboBox<String> loginComboBox =new ComboBox<>();
        loginComboBox.setPromptText("välj dit namn:");
        loginComboBox.setLayoutX(220);
        loginComboBox.setLayoutY(280);
        loginComboBox.getItems().addAll("Richard","Gilfoyle","Dinesh");
        loginComboBox.setOnAction(e ->{
            loginButton.setDisable(false);
        });

        loginAnchorPane.getChildren().addAll(label,loginComboBox,loginButton);

        primaryStage.setTitle("Piper Game");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public Scene showMainScene(){

        TabPane tabPane = new TabPane();
        tabPane.setPrefSize(600,600);

        AnchorPane anchorPane1= new AnchorPane();
//        anchorPane1.setPrefSize(550,600);
        TextArea textArea= new TextArea();
        textArea.setEditable(false);
        textArea.setVisible(false);
        textArea.setPrefSize(300,500);
        textArea.setLayoutX(250);
        textArea.setLayoutY(40);

        ComboBox<String> comboBoxShowTable =new ComboBox<>();
        comboBoxShowTable.setPromptText("välj:");
        comboBoxShowTable.setLayoutX(15);
        comboBoxShowTable.setLayoutY(40);
        comboBoxShowTable.getItems().addAll("Spelare","Användare");
        comboBoxShowTable.setOnAction(e ->{
            if(comboBoxShowTable.getValue().equals("Spelare")){

            } else if (comboBoxShowTable.getValue().equals("Användare")) {

            }
        });

        Button showButton=new Button("Visa");
        showButton.setLayoutX(130);
        showButton.setLayoutY(40);
        showButton.setOnAction(e ->{
            textArea.setVisible(true);
        });

        Separator separator1= new Separator(Orientation.HORIZONTAL);
        separator1.setLayoutY(100);
        separator1.setLayoutX(10);

        anchorPane1.getChildren().addAll(comboBoxShowTable,separator1,showButton,textArea);

        Tab tab1 = new Tab("Spelare", anchorPane1);
        Tab tab2 = new Tab("Lag", new Label("Show teams"));
        Tab tab3 = new Tab("Spel", new Label("Show games"));
        Tab tab4 = new Tab("Matcher", new Label("Show matches"));
        Tab tab5 = new Tab("Turnering", new TournamentView().start());

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(tab4);
        tabPane.getTabs().add(tab5);

        AnchorPane mainAnchorPane = new AnchorPane(tabPane);
        return new Scene(mainAnchorPane);
    }

    public static void main(String[] args) {
        launch();
    }
}