package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.DAO.GameDAO;
import hill.ascona.asconapipergames.DAO.MatchDAO;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class TournamentView {
    private ObservableList<Tournament> tournaments = FXCollections.observableList(new ArrayList<>());
    TournamentDAO tDao = new TournamentDAO();
    VBox baseContent;
    boolean darkMode = false;
    Tournament edit;

    public VBox start() {
        baseContent = new VBox();
        baseContent.setSpacing(5);
        baseContent.getStylesheets().add("tmnt.css");
//        baseContent.setPrefSize(700,1600);
        if (!darkMode)
            baseContent.getStylesheets().add("tmnt-light.css");
        Label btn = new Label("New tournament");
        btn.getStyleClass().add("btn");
        btn.setId("addBtn");
//        tDao.saveTM(new Tournament(new GameDAO().getByName("Halo 3"), "2009-02-15"));
        tournaments = FXCollections.observableList(tDao.getAllTournaments());
        HBox hBox = new HBox();

//        HBox sBtn = switchBtn();
//        sBtn.setAlignment(Pos.CENTER_RIGHT);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hBox.getChildren().addAll(btn, spacer, switchBtn());
        baseContent.getChildren().addAll(hBox, tabView());
//        baseContent.getChildren().addAll(btn, logView());

        btn.setOnMouseClicked((e) -> {
            VBox nT = newTour(null);
            baseContent.getChildren().add(1, nT);
        });
        baseContent.setId("basePane");
        return baseContent;
    }

    public VBox newTour(Tournament tmnt) {
        VBox content = new VBox();
        Label title = new Label("Tournaments");
        DatePicker dp = new DatePicker();
        ChoiceBox choiceBox = new ChoiceBox();
        ChoiceBox matchBox = new ChoiceBox();
        TextField txtBox = new TextField();
        txtBox.setPromptText("Fill title");
        Label add = new Label("Add tournament");

        GameDAO dao = new GameDAO();
        List<Game> games = dao.getAllGames();
        for (Game game : games) {
            choiceBox.getItems().add(game.getTitle());
        }
        choiceBox.setValue(games.get(0).getTitle());
        for (Match match : new MatchDAO().getAllMatches()) {
            matchBox.getItems().add(String.format("(%s) %s vs %s", match.getId(), match.getNameOne(), match.getNameTwo()));
        }
        if (tmnt != null) {
            if (!tmnt.getMatches().isEmpty())
                matchBox.setValue(String.format("(%s) %s vs %s", tmnt.getMatches().get(0).getId(), tmnt.getMatches().get(0).getNameOne(), tmnt.getMatches().get(0).getNameTwo()));
            choiceBox.setValue(tmnt.getGame().getTitle());
            txtBox.setText(tmnt.getTitle() != null && !tmnt.getTitle().isEmpty() ? tmnt.getTitle() : "");
        }
        add.setOnMouseClicked((e) -> {
            if (!txtBox.getText().isEmpty()) {
                Game game = dao.getByName(choiceBox.getValue().toString());
                String date = dp.getValue().toString();
                String m = matchBox.getValue().toString();
                Match match = new MatchDAO().getMatchById(Integer.parseInt(m.substring(1, m.indexOf(')'))));
                tDao.saveTM(new Tournament(game, date, txtBox.getText(), match));
                tournaments.clear();
                tournaments.addAll(tDao.getAllTournaments());
                baseContent.getChildren().remove(1);
            } else
                System.out.println("ERROR");
        });

        content.getChildren().addAll(title, dp, choiceBox, matchBox, txtBox, add);

        return content;
    }

    private TableView tabView() {
        TableView table = new TableView();

        TableColumn<Tournament, Integer> idCol = new TableColumn<>("id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//        idCol.setCellFactory(c -> new TableCell<Tournament, Integer>(){
//            @Override
//            public void updateItem(int id, boolean empty){
//                super.updateItem(id, empty);
//                if(!empty){
//
//                } else
//                    setText(null);
//            }
//        });
        TableColumn<Tournament, String> titleCol = new TableColumn<>("title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setCellFactory(c -> new TableCell<Tournament, String>() {
            @Override
            public void updateItem(String title, boolean empty) {
                super.updateItem(title, empty);
                if (!empty) {
                    if (edit != null && edit.getTitle().equals(title)) {
                        setGraphic(new TextField(edit.getTitle() != null ? edit.getTitle() : ""));
                    } else
                        setText(title);
                } else
                    setText(null);
            }
        });

        TableColumn<Tournament, Game> gameCol = new TableColumn<>("game");
        gameCol.setCellValueFactory(new PropertyValueFactory<>("game"));
        TableColumn<Tournament, String> dateCol = new TableColumn<>("date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

//        TableColumn<Tournament, List<Match>> matchCol = new TableColumn<>("matches");
//        matchCol.setCellValueFactory(new PropertyValueFactory<>("matches"));
//        matchCol.setCellFactory(c -> new TableCell<Tournament, List<Match>>() {
//            @Override
//            public void updateItem(List<Match> matches, boolean empty) {
//                super.updateItem(matches, empty);
//                if (!empty) {
//                    ChoiceBox box = new ChoiceBox();
//                    for (Match m : matches) {
//                        System.out.println(m.getDate());
//                        System.out.println(m.getTeams().size());
//                        if(!m.getTeams().isEmpty()) {
//                            box.getItems().add(m.getTeams().get(0).getTeam_name() + " vs " + m.getTeams().get(1).getTeam_name());
//                        }
//                    }
////                    if (!box.getItems().isEmpty())
////                        box.setValue(box.getItems().get(0));
//                    System.out.println(box.getItems());
//                    box.setValue("hallo");
//                    setGraphic(box);
//                } else
//                    setText(null);
//            }
//        });
        table.getColumns().addAll(idCol, titleCol, dateCol, gameCol);
//        table.getColumns().add(matchCol);


        table.setItems(tournaments);
        gameCol.setCellFactory(c -> new TableCell<Tournament, Game>() {
            @Override
            public void updateItem(Game game, boolean empty) {
                super.updateItem(game, empty);
                if (!empty) {
                    if (edit != null) {
                        ChoiceBox choiceBox = new ChoiceBox();
                        List<Game> games = new GameDAO().getAllGames();
                        for (Game g : games) {
                            choiceBox.getItems().add(game.getTitle());
                        }
                        choiceBox.setValue(games.get(0).getTitle());
                        setGraphic(choiceBox);
                    } else
                        setText(game.getTitle());
                } else
                    setText(null);
            }
        });
        table.setRowFactory(r -> {
            TableRow<Tournament> row = new TableRow<>();
            row.setOnMouseClicked((m) -> {
                if (!row.isEmpty() && m.getClickCount() == 2) {
                    edit = row.getItem();
                    tournaments.clear();
                    tournaments.addAll(tDao.getAllTournaments());
                } else if (!row.isEmpty()) {
                    ContextMenu context = new ContextMenu();
                    MenuItem update = new MenuItem("Update");
                    update.setOnAction((e) -> {
                        newTour(row.getItem());
                    });

                    MenuItem delete = new MenuItem("Delete");
                    Tournament tmnt = row.getItem();
                    delete.setOnAction((e) -> {
                        if (tDao.deleteTmnt(tmnt))
                            System.out.println("Deleted " + (tmnt.getTitle() != null ? tmnt.getTitle() : tmnt.getDate()));
                        else
                            System.out.println("Failed to delete " + tmnt);
                        tournaments.clear();
                        tournaments.addAll(tDao.getAllTournaments());
                    });
                    context.getItems().addAll(update, delete);
                    row.setContextMenu(context);
//                    Tournament tmnt = row.getItem();
//                    if(tDao.deleteTmnt(tmnt))
//                        System.out.println("Deleted " + (tmnt.getTitle() != null ? tmnt.getTitle() : tmnt.getDate()));
//                    else
//                        System.out.println("Failed to delete " + tmnt);
////                    Tournament tmnt = row.getItem();
////                    tmnt.setTitle("test");
////                    tDao.updateTmnt(tmnt);
                }
//                    tournaments.remove(row.getItem());
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
}
