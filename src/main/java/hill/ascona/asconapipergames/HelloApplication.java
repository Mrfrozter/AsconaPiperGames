package hill.ascona.asconapipergames;

import hill.ascona.asconapipergames.managers.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
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
