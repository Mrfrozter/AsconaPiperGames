package hill.ascona.asconapipergames.managers;

import hill.ascona.asconapipergames.views.Login;
import hill.ascona.asconapipergames.views.MainView;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewManager {
    private static Stage primaryStage;
    private static String loggedInUser;
    public static void start(Stage stage){
        primaryStage = stage;
        primaryStage.setTitle("Piper Games");
    }

    public static void loginView (){
        primaryStage.setScene(new Scene(new Login().start()));
        primaryStage.show();
    }

    public static void mainView(){
        primaryStage.setScene(new Scene(new MainView().start()));
        primaryStage.show();
    }

    public static String getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(String user){
        loggedInUser = user;
    }
}
