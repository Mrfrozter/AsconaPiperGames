package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.DAO.GameDAO;
import hill.ascona.asconapipergames.DAO.TournamentDAO;
import hill.ascona.asconapipergames.entities.Game;
import hill.ascona.asconapipergames.entities.Tournament;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class TournamentView {
    private ObservableList<Tournament> tournaments = FXCollections.observableList(new ArrayList<>());
        TournamentDAO tDao = new TournamentDAO();

    public VBox start() {
        VBox content = new VBox();
        Label btn = new Label("New tournament");
        tournaments = FXCollections.observableList(tDao.getAllTournaments());
        content.getChildren().addAll(logView(200), btn);
//        content.getChildren().add(btn);
        btn.setOnMouseClicked((e) -> {
            content.getChildren().add(newTour());
        });
        return content;
    }

    public VBox newTour() {
        VBox content = new VBox();
        Label title = new Label("Tournaments");
        DatePicker dp = new DatePicker();
        ChoiceBox choiceBox = new ChoiceBox();
        Label add = new Label("Add tournament");

        GameDAO dao = new GameDAO();
        List<Game> games = dao.getAllGames();
        for (Game game : games) {
            choiceBox.getItems().add(game.getTitle());
        }
/*        add.setOnMouseClicked((e) -> {
            Game game = dao.getGameByI(choiceBox.getValue().toString());
            String date = dp.getValue().toString();
            new TournamentDAO().saveTM(new Tournament(game, date));
            tournaments.add(tDao.getTmById(tDao.getAllTournaments().size()));
        });*/

        choiceBox.setValue(games.get(0).getTitle());
        content.getChildren().addAll(title, dp, choiceBox, add);

        return content;
    }

    public VBox logView(int height) {

        VBox content = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        VBox logBox = new VBox();
        logBox.setPrefHeight(height);
        logBox.setPrefWidth(300);
        content.setId("listBox");
        content.setPadding(new Insets(15));
        int i = 0;
        for (Tournament tmnt : tournaments) {
            logBox.getChildren().addAll(rowBox(tmnt));
        }

        tournaments.addListener(new ListChangeListener<Tournament>() {
            @Override
            public void onChanged(Change<? extends Tournament> change) {
                logBox.getChildren().clear();
                for (Tournament tmnt : change.getList()) {
                    logBox.getChildren().addAll(rowBox(tmnt));
                }
            }
        });
        scrollPane.setContent(logBox);
        scrollPane.setFitToWidth(true);
        content.getChildren().add(scrollPane);
        return content;
    }

    private HBox rowBox(Tournament tmnt){
        Label idLab = new Label(tmnt.getId() + "");
        Label gmeLab = new Label(tmnt.getGame() != null ? tmnt.getGame().getTitle() : "null");
        Label datLab = new Label(tmnt.getDate());
        Label delete = new Label("x");
        idLab.getStyleClass().add("txt");
        idLab.setId("log");
        gmeLab.getStyleClass().add("txt");
        gmeLab.setId("log");
        datLab.getStyleClass().add("txt");
        datLab.setId("log");
        delete.getStyleClass().add("txt");
        delete.setId("log");

        delete.setOnMouseClicked((e) -> {
//            tDao.deleteTournament(tmnt);
        });

        HBox mBox = new HBox();
        mBox.setSpacing(2);
        mBox.getChildren().addAll(idLab, gmeLab, datLab, delete);
        return mBox;
    }
}
