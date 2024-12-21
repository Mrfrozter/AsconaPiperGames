package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.managers.ViewManager;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class MainView {
    public VBox start(){
        VBox box = new VBox();
        TabPane tabPane = new TabPane();
        tabPane.setPrefSize(700,600);

        Label logBtn = new Label("Logout");
        logBtn.setText(ViewManager.getLoggedInUser());
        logBtn.setMaxHeight(20);
        logBtn.setOnMouseClicked((e)->{
            ViewManager.loginView();
        });
        logBtn.setAlignment(Pos.CENTER_RIGHT);

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

        box.getChildren().addAll(tabPane, logBtn);

        return box;
    }
}
