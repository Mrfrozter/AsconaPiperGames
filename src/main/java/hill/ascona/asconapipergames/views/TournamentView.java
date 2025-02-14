package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.DAO.GameDAO;
import hill.ascona.asconapipergames.DAO.TournamentDAO;
import hill.ascona.asconapipergames.entities.Game;
import hill.ascona.asconapipergames.entities.Match;
import hill.ascona.asconapipergames.entities.Tournament;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class TournamentView {
    private ObservableList<Tournament> tournaments = FXCollections.observableList(new ArrayList<>());
    TournamentDAO tDao = new TournamentDAO();
    VBox baseContent;
    boolean darkMode = false;

    public VBox start() {
        baseContent = new VBox();
        baseContent.setSpacing(5);
        baseContent.getStylesheets().add("tmnt.css");
        if (!darkMode)
            baseContent.getStylesheets().add("tmnt-light.css");
        Label btn = new Label("New tournament");
        btn.getStyleClass().add("btn");
        btn.setId("addBtn");
//        tDao.saveTM(new Tournament(new GameDAO().getByName("Halo 3"), "2009-02-15"));
        tournaments = FXCollections.observableList(tDao.getAllTournaments());
        HBox hBox = new HBox();

        HBox sBtn = switchBtn();
//        sBtn.setAlignment(Pos.CENTER_RIGHT);
        hBox.getChildren().addAll(btn, sBtn);
        HBox.setHgrow(sBtn, Priority.ALWAYS);
        baseContent.getChildren().addAll(hBox, tabView());
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
            choiceBox.getItems().add(game.getTitle());
        }
        add.setOnMouseClicked((e) -> {
            Game game = dao.getByName(choiceBox.getValue().toString());
            String date = dp.getValue().toString();
            tDao.saveTM(new Tournament(game, date));
            tournaments.clear();
            tournaments.addAll(tDao.getAllTournaments());
            baseContent.getChildren().remove(1);
        });

        choiceBox.setValue(games.get(0).getTitle());
        content.getChildren().addAll(title, dp, choiceBox, add);

        return content;
    }

    private TableView tabView() {
        TableView table = new TableView();

        TableColumn<Tournament, Integer> id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Tournament, Game> gameCol = new TableColumn<>("game");
        gameCol.setCellValueFactory(new PropertyValueFactory<>("game"));
        TableColumn<Tournament, String> dateCol = new TableColumn<>("date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        table.getColumns().addAll(id, dateCol, gameCol);

        if(!tournaments.isEmpty() && !tournaments.get(0).getMatches().isEmpty()) {
            TableColumn<Tournament, List<Match>> matchCol = new TableColumn<>("matches");
            matchCol.setCellValueFactory(new PropertyValueFactory<>("matches"));
            matchCol.setCellFactory(c -> new TableCell<Tournament, List<Match>>() {
                @Override
                public void updateItem(List<Match> matches, boolean empty) {
                    super.updateItem(matches, empty);
                    if (!empty) {
                        ChoiceBox box = new ChoiceBox();
                        for (Match m : matches) {
                            box.getItems().add(m.getNameOne() + " vs" + m.getNameTwo());
                        }
                        box.setValue(box.getItems().get(0));
                        setGraphic(box);
                    } else
                        setText(null);
                }
            });
            table.getColumns().add(matchCol);
        }


        table.setItems(tournaments);
        gameCol.setCellFactory(c -> new TableCell<Tournament, Game>() {
            @Override
            public void updateItem(Game gme, boolean empty) {
                super.updateItem(gme, empty);
                if (!empty)
                    setText(gme.getTitle());
                else
                    setText(null);
            }
        });
        table.setRowFactory(e -> {
            TableRow<Tournament> row = new TableRow<>();
            row.setOnMouseClicked((m) -> {
                if (!row.isEmpty())
                    tournaments.remove(row.getItem());
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

    private HBox switchBtn() {
        HBox box = new HBox();
        ImageView view = new ImageView();
        Image dark = new Image("moon-solid.png");
        Image light = new Image("sun-solid.png");
        Label label = new Label();
        label.getStyleClass().add("txt");
//        baseContent.getStylesheets().add("tmnt-light.css");
        view.setImage(dark);
        label.setText("Switch to dark");
        box.setOnMouseClicked((e) -> {
            darkMode = !darkMode;
            baseContent.getStylesheets().remove(1);
            System.out.println(baseContent.getStylesheets().size());
            if (darkMode) {
                baseContent.getStylesheets().add("tmnt-dark.css");
                view.setImage(light);
                label.setText("Switch to light");
            } else {
                baseContent.getStylesheets().add("tmnt-light.css");
                view.setImage(dark);
                label.setText("Switch to dark");
            }
        });
        box.getStyleClass().add("btn");
        box.setSpacing(5);
        HBox.setHgrow(view, Priority.ALWAYS);
        box.getChildren().addAll(label, view);
        return box;
    }

//    public VBox logView() {
//
//        VBox content = new VBox();
//        ScrollPane scrollPane = new ScrollPane();
//        VBox logBox = new VBox();
////        logBox.setPrefHeight(height);
////        logBox.setPrefWidth(300);
//        content.setId("listBox");
//        content.setPadding(new Insets(15));
//        int i = 0;
//        for (Tournament tmnt : tournaments) {
//            logBox.getChildren().addAll(rowBox(tmnt));
//        }
//
//        tournaments.addListener(new ListChangeListener<Tournament>() {
//            @Override
//            public void onChanged(Change<? extends Tournament> change) {
//                logBox.getChildren().clear();
//                for (Tournament tmnt : change.getList()) {
//                    logBox.getChildren().addAll(rowBox(tmnt));
//                }
//            }
//        });
//        scrollPane.setContent(logBox);
//        scrollPane.setFitToWidth(true);
//        content.getChildren().add(scrollPane);
//        return content;
//    }
//
//    private HBox rowBox(Tournament tmnt) {
//        Label idLab = new Label(tmnt.getId() + "");
//        Label gmeLab = new Label(tmnt.getGame() != null ? tmnt.getGame().getName() : "null");
//        Label datLab = new Label(tmnt.getDate());
//        Label delete = new Label("x");
//        idLab.getStyleClass().add("txt");
//        idLab.setId("log");
//        gmeLab.getStyleClass().add("txt");
//        gmeLab.setId("log");
//        datLab.getStyleClass().add("txt");
//        datLab.setId("log");
//        delete.getStyleClass().add("txt");
//        delete.setId("log");
//
//        delete.setOnMouseClicked((e) -> {
////            tDao.deleteTournament(tmnt);
//        });
//
//        HBox mBox = new HBox();
//        mBox.setSpacing(2);
//        mBox.getChildren().addAll(idLab, gmeLab, datLab, delete);
//        return mBox;
//    }
}
