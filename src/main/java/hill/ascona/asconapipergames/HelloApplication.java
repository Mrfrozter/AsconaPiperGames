package hill.ascona.asconapipergames;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import hill.ascona.asconapipergames.DAO.PersonDAO;
import hill.ascona.asconapipergames.entities.Person;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

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


        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 320, 240);
        stage.setTitle("Piper Games");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}