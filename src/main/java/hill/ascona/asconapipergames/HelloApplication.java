package hill.ascona.asconapipergames;

import hill.ascona.asconapipergames.DAO.PersonDAO;
import hill.ascona.asconapipergames.entities.Person;
import hill.ascona.asconapipergames.views.PersonView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    public void start(Stage primaryStage) throws IOException {
        AnchorPane loginAnchorPane=new AnchorPane();
        loginAnchorPane.setPrefSize(700,600);

        Scene loginScene=new Scene(loginAnchorPane);
        primaryStage.setScene(loginScene);

        Label label = new Label("Welcome to Piper Game page!");
        label.setPrefSize(450,450);
        label.setFont(new Font("Cambria Bold",20));
        label.setTextFill(Color.GRAY);
        label.setLayoutX(190);
        label.setLayoutY(10);

        Button loginButton=new Button("Logging in");
        loginButton.setDisable(true);
        loginButton.setOnAction(e ->{
            primaryStage.setScene(showMainScene());});
        loginButton.setPrefSize(115,28);
        loginButton.setLayoutX(275);
        loginButton.setLayoutY(310);

        ComboBox<String> loginComboBox =new ComboBox<>();
        loginComboBox.setPromptText("Choose your name:");
        loginComboBox.setLayoutX(260);
        loginComboBox.setLayoutY(280);

        PersonDAO personDAO = new PersonDAO();
        List<Person> allUsers= personDAO.getAllUsersInfo();
        for (Person i: allUsers){
            loginComboBox.getItems().add(i.getName()+' '+i.getLastname());
        }
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
        tabPane.setPrefSize(700,600);

        Tab tab1 = new Tab("Users or Players", new PersonView().start());
        Tab tab2 = new Tab("Teams", new Label("Show teams"));
        Tab tab3 = new Tab("Games", new Label("Show games"));
        Tab tab4 = new Tab("Matches", new Label("Show matches"));
        Tab tab5 = new Tab("Tournament", new Label("Show tournament"));

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(tab4);
        tabPane.getTabs().add(tab5);

        AnchorPane mainAnchorPane = new AnchorPane(tabPane);
        return new Scene(mainAnchorPane, 700, 600);
    }

    public static void main(String[] args) {
        launch();
    }
}