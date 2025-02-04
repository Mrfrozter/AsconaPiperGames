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
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TournamentView {
    private ObservableList<Tournament> tournaments = FXCollections.observableList(new ArrayList<>());
    TournamentDAO tDao = new TournamentDAO();
    GameDAO gDao = new GameDAO();
    private List<Game> games = gDao.getAllGames();
    VBox baseContent;
    boolean darkMode = true;
    int eRow = -1;
    int expandedId = -1;

    public VBox start() {
        baseContent = new VBox();
        baseContent.setSpacing(5);
        baseContent.getStylesheets().add("tmnt.css");
        baseContent.getStylesheets().add(darkMode ? "tmnt-dark.css" : "tmnt-light.css");
        Label btn = new Label("New tournament");
        btn.getStyleClass().add("btn");
        btn.setId("addBtn");
        tournaments = FXCollections.observableList(tDao.getAllTournaments());
        HBox hBox = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        hBox.getChildren().addAll(btn, searchBox(), spacer, switchBtn());
        hBox.setSpacing(5);
        baseContent.getChildren().addAll(hBox, tabView());

        btn.setOnMouseClicked((e) -> {
            Tournament tmnt = new Tournament();
            tDao.saveTM(tmnt);
            eRow = tournaments.size();
            refresh(tDao.getAllTournaments());
        });
        baseContent.setId("basePane");
        return baseContent;
    }

    private HBox searchBox() {
        HBox box = new HBox();
        box.setSpacing(2);
        TextField searchField = new TextField();
        ChoiceBox gBox = gameBox();
        gBox.getItems().add(0, "All games");
        gBox.setValue(gBox.getItems().get(0));
        AtomicInteger dblCheck = new AtomicInteger();
        gBox.setOnHidden(e -> {
            if (dblCheck.getAndIncrement() % 2 == 0) {
                if (!gBox.getValue().equals("All games")) {
                    refresh(tDao.filterByGame(gDao.getGameIdByTitle(gBox.getValue().toString())));
                } else
                    refresh(tDao.getAllTournaments());
            }
        });
        ChoiceBox searchBy = new ChoiceBox();
        searchBy.getItems().addAll("Title", "Date");
        searchBy.setValue(searchBy.getItems().get(0));
        searchField.setPromptText("[Enter to search]");
        searchField.setMinHeight(30);
        searchField.setOnKeyReleased(e -> {
            refresh(tDao.filterByString(searchBy.getValue().toString().toLowerCase(), searchField.getText()));
            if (searchField.getText().isEmpty())
                refresh(tDao.getAllTournaments());
        });
        box.getChildren().addAll(gBox, searchBy, searchField);
        return box;
    }

    private TableView tabView() {
        TableView table = new TableView();

        //  Ugly hack
        table.setPrefHeight(1200);
        baseContent.setPrefHeight(600);

        TableColumn<Tournament, Integer> idCol = new TableColumn<>("id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(20);
        table.getColumns().addAll(idCol, titleCol(), dateCol(), gameCol(), matchCol());
        table.setItems(tournaments);
        table.setRowFactory(r -> {
            TableRow<Tournament> row = new TableRow<>();
            row.setOnMouseEntered((m) -> {
                if (!row.isEmpty()) {
                    row.setContextMenu(contextMenu(row));
                }
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

    private TableColumn<Tournament, String> titleCol() {
        TableColumn<Tournament, String> column = new TableColumn<>("title");
        column.setCellValueFactory(new PropertyValueFactory<>("title"));
        int length = 10;
        for (Tournament tmnt : tournaments) {
            int l = tmnt.getTitle().length();
            if (l > length)
                length = l;
        }
        column.setPrefWidth(length * 10);
        column.setCellFactory(c -> new TableCell<Tournament, String>() {
            //            @Override
            public void updateItem(String title, boolean empty) {
                super.updateItem(title, empty);
                if (!empty) {
                    if (getTableRow() != null) {
                        int idx = getTableRow().getIndex();
                        Tournament tmnt = tournaments.get(idx);
                        if (idx == eRow || tmnt.getTitle() == null) {
                            String txt = tmnt.getTitle();
                            TextField field = new TextField(txt);
                            Button btn = new Button("+");
                            HBox box = new HBox();
                            box.getChildren().addAll(field, btn);
                            btn.setOnAction(e -> {
                                tmnt.setTitle(field.getText());
                                tDao.updateTmnt(tmnt);
                                refresh(tDao.getAllTournaments());

                                eRow = -1;
                            });
                            setGraphic(box);
                        } else
                            setText(title);
                    } else
                        setText(null);
                } else {
                    setGraphic(null);
                    setText(null);
                }
            }
        });
        return column;
    }

    private TableColumn<Tournament, String> dateCol() {
        TableColumn<Tournament, String> column = new TableColumn<>("date");
        column.setCellValueFactory(new PropertyValueFactory<>("date"));
        int length = 10;
        for (Tournament tmnt : tournaments) {
            int l = tmnt.getDate().length();
            if (l > length)
                length = l;
        }
//        System.out.println("Length = " +length);
        column.setPrefWidth(length * 10);
        column.setCellFactory(c -> new TableCell<Tournament, String>() {
            public void updateItem(String date, boolean empty) {
                super.updateItem(date, empty);
                if (!empty && getTableRow() != null) {
                    int idx = getTableRow().getIndex();
                    Tournament tmnt = tournaments.get(idx);
                    if (idx == eRow || tmnt.getDate() == null) {
                        HBox box = new HBox();
                        DatePicker datePicker = new DatePicker();
                        Button btn = new Button("+");
                        box.getChildren().addAll(datePicker, btn);
                        btn.setOnAction(e -> {
                            tmnt.setDate(datePicker.getValue().toString());
                        });
                        setGraphic(box);
                    } else
                        setText(date);
                } else {
                    setGraphic(null);
                    setText(null);
                }
            }
        });
        return column;
    }

    private TableColumn<Tournament, Game> gameCol() {
        TableColumn<Tournament, Game> column = new TableColumn<>("game");
        column.setCellValueFactory(new PropertyValueFactory<>("game"));
        int length = 10;
        for (Game g : games) {
            int l = g.getTitle().length();
            if (l > length)
                length = l;
        }
        column.setPrefWidth(length * 10);
        column.setCellFactory(c -> new TableCell<Tournament, Game>() {
            public void updateItem(Game game, boolean empty) {
                super.updateItem(game, empty);
                if (!empty && getTableRow() != null) {
                    int idx = getTableRow().getIndex();
                    if (idx == eRow || tournaments.get(idx).getGame() == null) {
                        Tournament tmnt = tournaments.get(idx);

                        Button btn = new Button("+");
                        HBox box = new HBox();
                        ChoiceBox gBox = gameBox();
                        gBox.setValue(tmnt.getGame() != null ? tmnt.getGame().getTitle() : gBox.getItems().get(0));
                        box.getChildren().addAll(gBox, btn);
                        btn.setOnAction(e -> {
                            tmnt.setGame(gDao.getGameIdByTitle(gBox.getValue().toString()));
                            tDao.updateTmnt(tmnt);
                            refresh(tDao.getAllTournaments());

                            eRow = -1;
                        });
                        setGraphic(box);
                    } else
                        setText(game.getTitle());
                } else {
                    setGraphic(null);
                    setText(null);
                }
            }
        });
        return column;
    }

    private TableColumn<Tournament, List<Match>> matchCol() {
        TableColumn<Tournament, List<Match>> column = new TableColumn<>("matches");
        column.setCellValueFactory(new PropertyValueFactory<>("matches"));
        column.setPrefWidth(482);
        column.setCellFactory(c -> new TableCell<Tournament, List<Match>>() {
            @Override
            public void updateItem(List<Match> matches, boolean empty) {
                super.updateItem(matches, empty);
                if (!empty && getTableRow() != null) {
                    setGraphic(expander(getTableRow().getItem(), getTableRow().getIndex()));
                } else
                    setGraphic(null);
            }
        });
        return column;
    }

    private VBox expander(Tournament tmnt, int row) {
        VBox box = new VBox();
        Button btn = new Button("V");
        BracketView bracketView = new BracketView(tmnt, tDao);
        final boolean[] isExpanded = {false};
        box.getChildren().addAll(btn);
        box.setPrefSize(360, 20);
        if (row == expandedId) {
            box.getChildren().add(bracketView.show(tournaments));
            box.setPrefSize(360, 170);
            isExpanded[0] = true;
        }
        btn.setOnAction(e -> {
            if (!isExpanded[0]) {
                expandedId = row;
                box.getChildren().add(bracketView.show(tournaments));
                box.setPrefSize(360, 170);
            } else {
                expandedId = -1;
                box.getChildren().remove(1);
                box.setPrefHeight(20);
            }
            isExpanded[0] = !isExpanded[0];
        });
        return box;
    }

    private ContextMenu contextMenu(TableRow<Tournament> row) {
        int rowIdx = row.getIndex();
        ContextMenu context = new ContextMenu();
        MenuItem update = new MenuItem("Update");
        update.setOnAction((e) -> {
            eRow = rowIdx;
            refresh(tDao.getAllTournaments());

        });

        MenuItem delete = new MenuItem("Delete");
        Tournament tmnt = row.getItem();
        delete.setOnAction((e) -> {
            tDao.deleteTmnt(tmnt);
            refresh(tDao.getAllTournaments());

        });

        MenuItem details = new MenuItem("View brackets");

        context.getItems().addAll(details, update, delete);
        return context;
    }

    private ChoiceBox gameBox() {
        ChoiceBox box = new ChoiceBox();
        for (Game game : games) {
            box.getItems().add(game.getTitle());
        }
        box.setValue(games.get(0).getTitle());
        return box;
    }

    private void refresh(List<Tournament> tmnts) {
        tournaments.clear();
        tournaments.addAll(tmnts);
    }

    private HBox switchBtn() {
        HBox box = new HBox();
        ImageView view = new ImageView();
        Image dark = new Image("moon-solid.png");
        Image light = new Image("sun-solid.png");
        Label label = new Label();
        
        label.getStyleClass().add("txt");
        view.setImage(darkMode ? light : dark);
        label.setText("Switch to " + (darkMode ? "light" : "dark"));
        
        box.setOnMouseClicked((e) -> {
            darkMode = !darkMode;
            String theme = darkMode ? "dark" : "light";
            baseContent.getStylesheets().remove(1);
            baseContent.getStylesheets().add("tmnt-" + theme + ".css");
            view.setImage(darkMode ? light : dark);
            label.setText("Switch to " + (darkMode ? "light" : "dark"));
        });
        box.getStyleClass().add("btn");
        box.setSpacing(5);
        HBox.setHgrow(view, Priority.ALWAYS);
        box.getChildren().addAll(label, view);
        return box;
    }
}
