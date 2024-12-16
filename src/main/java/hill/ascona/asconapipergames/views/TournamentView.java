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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class TournamentView {
    private ObservableList<Tournament> tournaments = FXCollections.observableList(new ArrayList<>());
    TournamentDAO tDao = new TournamentDAO();
    VBox baseContent;

    public VBox start() {
        baseContent = new VBox();
        baseContent.setSpacing(5);
        baseContent.getStylesheets().add("tmnt.css");
        Label btn = new Label("New tournament");
        btn.getStyleClass().add("btn");
        btn.setId("addBtn");
//        tDao.saveTM(new Tournament(new GameDAO().getByName("Halo 3"), "2009-02-15"));
        tournaments = FXCollections.observableList(tDao.getAllTournaments());
        baseContent.getChildren().addAll(btn, tabView());
//        baseContent.getChildren().addAll(btn, logView());

        btn.setOnMouseClicked((e) -> {
            VBox nT = newTour();
            baseContent.getChildren().add(1, nT);
        });
        baseContent.setId("basePane");
        return baseContent;
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
            choiceBox.getItems().add(game.getName());
        }
        add.setOnMouseClicked((e) -> {
            Game game = dao.getByName(choiceBox.getValue().toString());
            String date = dp.getValue().toString();
            tDao.saveTM(new Tournament(game, date));
            tournaments.clear();
            tournaments.addAll(tDao.getAllTournaments());
            baseContent.getChildren().remove(1);
        });

        choiceBox.setValue(games.get(0).getName());
        content.getChildren().addAll(title, dp, choiceBox, add);

        return content;
    }

    private TableView tabView() {
        TableView table = new TableView();

        TableColumn<Tournament, Integer> id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Tournament, Game> game = new TableColumn<>("game");
        game.setCellValueFactory(new PropertyValueFactory<>("game"));
        TableColumn<Tournament, String> date = new TableColumn<>("date");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.getColumns().addAll(id, date, game);

        table.setItems(tournaments);
//        table.setId("tableView");
        table.setRowFactory(e -> {
            TableRow<Tournament> row = new TableRow<>();
            row.setOnMouseClicked((m) -> {
                if(!row.isEmpty())
                    tournaments.remove(row.getItem());
//                    System.out.println(row.getItem().getGame().getName());
            });
            return row;
        });
        tournaments.addListener(new ListChangeListener<Tournament>() {
            @Override
            public void onChanged(Change<? extends Tournament> change) {
                table.setItems(change.getList());
            }
        });
        return table;
    }

    public VBox logView() {

        VBox content = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        VBox logBox = new VBox();
//        logBox.setPrefHeight(height);
//        logBox.setPrefWidth(300);
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

    private HBox rowBox(Tournament tmnt) {
        Label idLab = new Label(tmnt.getId() + "");
        Label gmeLab = new Label(tmnt.getGame() != null ? tmnt.getGame().getName() : "null");
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
