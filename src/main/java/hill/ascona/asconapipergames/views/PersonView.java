package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.DAO.PersonDAO;
import hill.ascona.asconapipergames.entities.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.util.List;

public class PersonView {

    private final PersonDAO personDAO = new PersonDAO();
    private ObservableList<Person> pInfo = FXCollections.observableArrayList();

    public AnchorPane start() {
        TabPane personTabPane = new TabPane();
        personTabPane.setPrefSize(700, 600);

        Tab tab1 = new Tab("Show All", showAllPersons());
        Tab tab2 = new Tab("Search", searchPlayer());
        Tab tab3 = new Tab("Update", updatePlayersInfo());

        personTabPane.getTabs().addAll(tab1, tab2, tab3);

        return new AnchorPane(personTabPane);
    }

    private AnchorPane showAllPersons() {
        AnchorPane anchorPane1 = new AnchorPane();

        Label labelShowTable = new Label("Person's information:");
        labelShowTable.setFont(new Font("Cambria Bold", 12));
        labelShowTable.setLayoutX(15);
        labelShowTable.setLayoutY(25);

        ComboBox<String> comboBoxShowTable = new ComboBox<>();
        comboBoxShowTable.setPrefSize(145, 23);
        comboBoxShowTable.setPromptText("Role:");
        comboBoxShowTable.setLayoutX(25);
        comboBoxShowTable.setLayoutY(55);
        comboBoxShowTable.getItems().addAll("Player", "User");

        TableView<Person> tableView = new TableView<>();
        tableView.setEditable(false);
        tableView.setPrefSize(650, 200);
        tableView.setLayoutX(25);
        tableView.setLayoutY(90);

        TableColumn<Person, Integer> column1 = new TableColumn<>("Id");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Person, String> column2 = new TableColumn<>("First Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Person, String> column3 = new TableColumn<>("Last Name");
        column3.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        TableColumn<Person, String> column4 = new TableColumn<>("Nickname");
        column4.setCellValueFactory(new PropertyValueFactory<>("nickname"));

        tableView.getColumns().addAll(column1, column2, column3, column4);

        comboBoxShowTable.setOnAction(e -> {
            String selectedRole = comboBoxShowTable.getValue();
            if ("Player".equals(selectedRole)) {
                pInfo = FXCollections.observableList(getAllPlayersOrUsers("Player"));
            } else if ("User".equals(selectedRole)) {
                pInfo = FXCollections.observableList(getAllPlayersOrUsers("User"));
            }
            tableView.setItems(pInfo);
        });

        // Add double-click delete functionality with confirmation
        tableView.setRowFactory(tv -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Person selectedPerson = row.getItem();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Deletion");
                    alert.setHeaderText("Are you sure you want to delete this person?");
                    alert.setContentText("Person: " + selectedPerson.getName() + " " + selectedPerson.getLastname());

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            boolean deleted = personDAO.deletePerson(selectedPerson);
                            if (deleted) {
                                pInfo.remove(selectedPerson);
                                tableView.refresh();
                                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                                successAlert.setTitle("Deletion Successful");
                                successAlert.setHeaderText(null);
                                successAlert.setContentText("Person successfully deleted!");
                                successAlert.showAndWait();
                            } else {
                                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                errorAlert.setTitle("Deletion Failed");
                                errorAlert.setHeaderText(null);
                                errorAlert.setContentText("Failed to delete the person. Please try again.");
                                errorAlert.showAndWait();
                            }
                        }
                    });
                }
            });
            return row;
        });

        anchorPane1.getChildren().addAll(labelShowTable, comboBoxShowTable, tableView);
        return anchorPane1;
    }

    private List<Person> getAllPlayersOrUsers(String role) {
        return personDAO.getAllPlayersOrUsers(role);
    }

    private AnchorPane searchPlayer() {
        return new AnchorPane();
    }

    private AnchorPane updatePlayersInfo() {
        return new AnchorPane();
    }
}