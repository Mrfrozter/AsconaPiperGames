package hill.ascona.asconapipergames.views;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MatchView {

/*
När personalen listar matcher ska det synas vilka spelare/lag som tävlar mot
varandra samt om matchen är kommande eller avgjord, om matchen är avgjord ska
det gå att se vem som blev vinnare. Här implementeras med fördel funktionalitet för
att lista samtliga matcher, endast avgjorda och endast kommande matcher.
• Matcher ska alltid ha ett datum.
• Matchen avgörs (poängen sätts) genom att Pied Pipers personal loggar in och
registrerar resultatet.
• */

    public AnchorPane start() {
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(550, 600);
        HBox hbox = new HBox();
        Button buttonAll = new Button("See all matches");
        Button buttonUpcoming = new Button("See all upcoming matches");
        Button buttonOld = new Button("See played matches");
        Button buttonNew = new Button("Add a new match");
        pane.getChildren().add(hbox);

//        tournaments = FXCollections.observableList(tDao.getAllTournaments());
      hbox.getChildren().addAll(buttonAll, buttonUpcoming, buttonOld, buttonNew);
/*      buttonNew.setOnAction(e -> {

      })
        btn.setOnMouseClicked((e) -> {
            content.getChildren().add(newTour());
        });*/
        return pane;
    }

}
