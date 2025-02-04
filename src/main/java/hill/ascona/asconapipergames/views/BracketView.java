package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.DAO.MatchDAO;
import hill.ascona.asconapipergames.DAO.TournamentDAO;
import hill.ascona.asconapipergames.entities.Match;
import hill.ascona.asconapipergames.entities.Tournament;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.*;

public class BracketView {
    private Tournament tmnt;
    private TournamentDAO tDao;
    private ObservableList<Tournament> tournaments;

    public BracketView(Tournament t, TournamentDAO tDao) {
        tmnt = t;
        this.tDao = tDao;
    }

    public VBox show(ObservableList<Tournament> tournaments) {
        this.tournaments = tournaments;
        VBox content = new VBox();
        content.setPrefSize(360, 170);
        VBox titleVBox = new VBox();
        HBox titleHBox = new HBox();
        HBox title = txtHbox(new Label(tmnt.getTitle()), "title");
        HBox date = txtHbox(new Label(tmnt.getDate()), "txt");
        titleVBox.getChildren().add(title);
        titleVBox.getChildren().add(date);
        titleHBox.getChildren().add(titleVBox);
        titleHBox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox();
        VBox quarter = new VBox();
        quarter.setSpacing(20);

        Queue<Match> matchQueue = new LinkedList<>(tmnt.getMatches());

        for (int i = 0; i < 4; i++) {
            quarter.getChildren().add(matchBox(80, matchQueue.isEmpty() ? new Match() : matchQueue.poll()));
        }
        VBox semi = new VBox();
        semi.setSpacing(90);
        semi.setAlignment(Pos.CENTER);
        if (tmnt.getMatches().size() >= 4) {
            for (int i = 0; i < 2; i++) {
                semi.getChildren().add(matchBox(135, matchQueue.isEmpty() ? new Match() : matchQueue.poll()));
            }
        }

        VBox finalsBox = new VBox();
        VBox winner = new VBox();
        if (tmnt.getMatches().size() >= 6) {
            finalsBox.setAlignment(Pos.CENTER);
            finalsBox.getChildren().add(matchBox(270, matchQueue.isEmpty() ? new Match() : matchQueue.poll()));

        }
        if(tmnt.getMatches().size() >= 7) {
            TextField field = teamText(118, 12);
            winner.setAlignment(Pos.CENTER);
            winner.getChildren().add(field);
            field.getStyleClass().add("line");
        }

        hBox.getChildren().addAll(quarter, semi, finalsBox, winner);
        content.getChildren().addAll(titleHBox, hBox);
        return content;
    }

    private VBox matchBox(int height, Match match) {
        boolean isNew = false;
        if (match.getNameOne() == null)
            isNew = true;
        VBox content = new VBox();
        VBox box = new VBox();
        box.setMinSize(120, height);
        box.setMaxSize(120, height);
        box.getStyleClass().add("box");

        HBox centerBox = new HBox();
        VBox labelBox = new VBox();
        Label updateLabel = new Label("Click to update");
        TextField timeLabel = teamText(50, 18);

        TextField team1 = teamText(118, 12);
        TextField team2 = teamText(118, 12);

        box.setPrefSize(120, height);

        labelBox.getChildren().addAll(timeLabel, txtHbox(updateLabel, "txt"));
        centerBox.getChildren().add(labelBox);
        box.getChildren().add(centerBox);
        box.setAlignment(Pos.CENTER);
        centerBox.setAlignment(Pos.CENTER);
        labelBox.setAlignment(Pos.CENTER);

        if (match != null) {
            team1.setText(match.getNameOne());
            team2.setText(match.getNameTwo());
            timeLabel.setText(match.getDate());
        }
        boolean finalIsNew = isNew;
        box.setOnMouseClicked(e -> {
            match.setNameOne(team1.getText() != null ? team1.getText() : "");
            match.setNameTwo(team2.getText() != null ? team2.getText() : "");
            match.setDate(timeLabel.getText() != null ? timeLabel.getText() : "");
            match.setTournament(tmnt);
            if (finalIsNew)
                new MatchDAO().saveMatch(match);
            else
                new MatchDAO().updateMatch(match);
            tDao.updateTmnt(tmnt);
            
            // Ugly but works
            tournaments.clear();
            tournaments.addAll(tDao.getAllTournaments());
        });

        content.getChildren().addAll(team1, box, team2);
        return content;
    }

    private TextField teamText(int size, int font) {
        TextField field = new TextField();
        field.setMinWidth(size);
        field.setMaxWidth(size);
        field.getStyleClass().add("teamText");
        field.setStyle("-fx-background-color:transparent;");
        field.setPromptText("[Empty]");
        field.setFont(new Font(font));
        return field;
    }

    private HBox txtHbox(Node node, String css) {
        HBox box = new HBox();
        node.getStyleClass().add(css);
        box.getChildren().add(node);
        box.setAlignment(Pos.CENTER);
        return box;
    }
}
