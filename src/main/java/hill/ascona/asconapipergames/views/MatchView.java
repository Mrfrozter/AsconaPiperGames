package hill.ascona.asconapipergames.views;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class MatchView {

    public AnchorPane start() {
        AnchorPane pane = new AnchorPane();
        Label label = new Label("Match");
        Button buttonAll = new Button("See all matches");
        Button buttonUpcoming = new Button("See all upcoming matches");
        Button buttonOld = new Button("See played matches");
        Button buttonNew = new Button("Add a new match");

//        tournaments = FXCollections.observableList(tDao.getAllTournaments());
      pane.getChildren().addAll(label, buttonNew, buttonAll, buttonUpcoming, buttonOld, buttonNew);
/*      buttonNew.setOnAction(e -> {

      })
        btn.setOnMouseClicked((e) -> {
            content.getChildren().add(newTour());
        });*/
        return pane;
    }

}
