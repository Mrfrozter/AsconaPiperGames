package hill.ascona.asconapipergames;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.asconapipergames.DAO.MatchDAO;
import org.example.asconapipergames.entities.Match;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {


        MatchDAO matchDAO = new MatchDAO();
        Match testMatch = new Match("t7u9p");

        if(matchDAO.saveMatch(testMatch)){
            System.out.println("Match Saved");
        } else {
            System.out.println("Match Not Saved");
        }




        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}