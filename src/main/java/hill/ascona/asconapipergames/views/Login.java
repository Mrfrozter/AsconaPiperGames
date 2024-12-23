package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.DAO.PersonDAO;
import hill.ascona.asconapipergames.entities.Person;
import hill.ascona.asconapipergames.managers.ViewManager;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

//----Elham Farhang--(class Login)----
public class Login {
    public VBox start(){
        AnchorPane loginAnchorPane=new AnchorPane();
        loginAnchorPane.setPrefSize(700,600);

        Label label = new Label("Welcome to Piper Game page!");
        label.setPrefSize(450,450);
        label.setFont(new Font("Cambria Bold",20));
        label.setTextFill(Color.GRAY);
        label.setLayoutX(190);
        label.setLayoutY(10);

        ComboBox<String> loginComboBox =new ComboBox<>();
        loginComboBox.setPromptText("Choose your name:");
        loginComboBox.setLayoutX(260);
        loginComboBox.setLayoutY(275);

        Button loginButton=new Button("Logging in");
        loginButton.setDisable(true);
        loginButton.setOnAction(e -> {
            ViewManager.setLoggedInUser(loginComboBox.getValue());
            ViewManager.mainView();
        });

        loginButton.setPrefSize(115,28);
        loginButton.setLayoutX(275);
        loginButton.setLayoutY(310);

        PersonDAO personDAO = new PersonDAO();
        List<Person> allUsers= personDAO.getAllPlayersOrUsers("User");
        for (Person i: allUsers){
            loginComboBox.getItems().add(i.getName()+' '+i.getLastname());
        }
        loginComboBox.setOnAction(e ->{
            loginButton.setDisable(false);
        });

        loginAnchorPane.getChildren().addAll(label,loginComboBox,loginButton);
        return new VBox(loginAnchorPane);
    }
}
