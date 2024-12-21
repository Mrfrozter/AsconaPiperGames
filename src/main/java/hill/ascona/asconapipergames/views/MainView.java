package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.managers.ViewManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainView {
    public VBox start(){
        VBox box = new VBox();
        TabPane tabPane = new TabPane();
        box.setMaxSize(700,600);

        Label usrLabel = new Label("Logged in as "+ViewManager.getLoggedInUser());
        usrLabel.setMaxHeight(20);

        HBox usrBox = new HBox();
        Button logBtn = new Button("Logout");
        logBtn.setOnAction((e)->{
            ViewManager.loginView();
        });
        usrBox.setPadding(new Insets(2));
        usrBox.setSpacing(5);
        usrBox.getChildren().addAll(usrLabel,logBtn);
        usrBox.setAlignment(Pos.BASELINE_RIGHT);

        Tab tab1 = new Tab("Users or Players", new PersonView().start());
        Tab tab2 = new Tab("Teams", new Label("Show teams"));
        Tab tab3 = new Tab("Games", new GameView().start());
        Tab tab4 = new Tab("Matches", new MatchView().start());
        Tab tab5 = new Tab("Tournament", new TournamentView().start());

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(tab4);
        tabPane.getTabs().add(tab5);

        box.getChildren().addAll(tabPane, usrBox);

        return box;
    }
}
