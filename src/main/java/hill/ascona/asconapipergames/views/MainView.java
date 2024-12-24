package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.managers.ViewManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


//----Elham Farhang--(class MainView)----

public class MainView {
    public VBox start(){
        VBox box = new VBox();
        TabPane tabPane = new TabPane();
        VBox.setVgrow(tabPane, Priority.ALWAYS);

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

        box.getChildren().addAll(tabPane, BottomPanel.show());

        return box;
    }
}
