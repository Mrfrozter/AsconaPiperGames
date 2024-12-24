package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.DAO.GameDAO;
import hill.ascona.asconapipergames.DAO.MatchDAO;
import hill.ascona.asconapipergames.DAO.TeamDAO;
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
    GameDAO gDao = new GameDAO();
    MatchDAO mDao = new MatchDAO();
    private List<Game> games = gDao.getAllGames();
    VBox baseContent;
    boolean darkMode = false;
    int eRow = -1;

    public VBox start() {
        baseContent = new VBox();
        baseContent.setSpacing(5);
        baseContent.getStylesheets().add("tmnt.css");
        if (!darkMode)
            baseContent.getStylesheets().add("tmnt-light.css");
        Label btn = new Label("New tournament");
        btn.getStyleClass().add("btn");
        btn.setId("addBtn");
        tournaments = FXCollections.observableList(tDao.getAllTournaments());
        HBox hBox = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hBox.getChildren().addAll(btn, spacer, switchBtn());
        baseContent.getChildren().addAll(hBox, tabView());

        btn.setOnMouseClicked((e) -> {
            if (tDao.saveTM(new Tournament()))
                refresh();
        });
        baseContent.setId("basePane");
        return baseContent;
    }

    private TableView tabView() {
        TableView table = new TableView();

        TableColumn<Tournament, Integer> idCol = new TableColumn<>("id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(20);
        table.getColumns().addAll(idCol, titleCol(), dateCol(), gameCol(), matchCol());
        table.setItems(tournaments);
        table.setRowFactory(r -> {
            TableRow<Tournament> row = new TableRow<>();
            row.setOnMouseClicked((m) -> {
                if (!row.isEmpty()) {
                    eRow = -1;
                    refresh();
                }
            });
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
            @Override
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
                                refresh();

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
                        ChoiceBox hourPicker = numBox(24);
                        ChoiceBox minutePicker = numBox(60);
                        Button btn = new Button("+");
                        box.getChildren().addAll(datePicker, hourPicker, minutePicker, btn);
                        btn.setOnAction(e -> {
                            tmnt.setDate(String.format("%s %s:%s", datePicker.getValue(), hourPicker.getValue(), minutePicker.getValue()));
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
                            refresh();

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
        column.setCellFactory(c -> new TableCell<Tournament, List<Match>>() {
            @Override
            public void updateItem(List<Match> matches, boolean empty) {
                super.updateItem(matches, empty);
                if (!empty) {
                    setGraphic(expander());
                } else
                    setGraphic(null);
            }
        });
        return column;
    }

    private VBox expander() {
        VBox box = new VBox();
        Button btn = new Button("V");
        final boolean[] isExpanded = {false};
        box.getChildren().addAll(btn);
        box.setPrefHeight(20);
        btn.setOnAction(e -> {
            if (!isExpanded[0])
                box.setPrefHeight(200);
            else
                box.setPrefHeight(20);
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
            refresh();

        });

        MenuItem delete = new MenuItem("Delete");
        Tournament tmnt = row.getItem();
        delete.setOnAction((e) -> {
            if (tDao.deleteTmnt(tmnt))
                System.out.println("Deleted " + (tmnt.getTitle() != null ? tmnt.getTitle() : tmnt.getDate()));
            else
                System.out.println("Failed to delete " + tmnt);
            refresh();

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
    
    private ChoiceBox matchBox(){
        ChoiceBox box = new ChoiceBox();
        
        return box;
    }

    private ChoiceBox numBox(int num) {
        ChoiceBox box = new ChoiceBox();
        for (int i = 0; i <= num; i++) {
            box.getItems().add(i + "");
        }
        return box;
    }

    private void refresh() {
        tournaments.clear();
        tournaments.addAll(tDao.getAllTournaments());
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
