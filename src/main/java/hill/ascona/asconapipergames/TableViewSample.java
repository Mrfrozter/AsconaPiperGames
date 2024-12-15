package hill.ascona.asconapipergames;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import hill.ascona.asconapipergames.entities.TestMatch;

public class TableViewSample extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(500);

        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        TableView<TestMatch> table = new TableView<TestMatch>();
        table.setEditable(false);
        ObservableList<TestMatch> data =
                FXCollections.observableArrayList(
                        new TestMatch("idag", "tetris", "blåa", "röda", true, "blåa"),
                        new TestMatch("igår", "WoW", "gröna", "röda", true, "röda"),
                        new TestMatch("imorgon", "CoD", "blåa", "gröna", false, null),
                        new TestMatch("idag", "halo", "röda", "lila", true, "lila")
                );
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<TestMatch, String>("date"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<TestMatch, String>("game"));

        TableColumn emailCol = new TableColumn("t");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<TestMatch, String>("team1"));

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }


}