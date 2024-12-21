package hill.ascona.asconapipergames;

import hill.ascona.asconapipergames.DAO.MatchDAO;
import hill.ascona.asconapipergames.DAO.PersonDAO;
import hill.ascona.asconapipergames.entities.Match;
import hill.ascona.asconapipergames.entities.Person;
import hill.ascona.asconapipergames.managers.ViewManager;
import hill.ascona.asconapipergames.views.GameView;
import hill.ascona.asconapipergames.views.MatchView;
import hill.ascona.asconapipergames.views.PersonView;
import hill.ascona.asconapipergames.views.TournamentView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloApplication extends Application {
    public void start(Stage primaryStage) throws IOException {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        ViewManager.start(primaryStage);
        ViewManager.loginView();
    }

    public static void main(String[] args) {
        launch();
    }
}
