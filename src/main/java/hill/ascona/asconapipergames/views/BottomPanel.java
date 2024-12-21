package hill.ascona.asconapipergames.views;

import hill.ascona.asconapipergames.managers.ViewManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class BottomPanel {
    public static HBox show(){
        HBox usrBox = new HBox();
        ImageView view = new ImageView();
        Image img = new Image("apg-logo.png");
        view.setImage(img);

        Label usrLabel = new Label("Logged in as "+ ViewManager.getLoggedInUser());
        Button logBtn = new Button("Logout");
        logBtn.setOnAction((e)->{
            ViewManager.loginView();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        usrBox.setPadding(new Insets(2,5,2,5));
        usrBox.setSpacing(5);
        usrBox.getChildren().addAll(view, spacer, usrLabel,logBtn);
        usrBox.setAlignment(Pos.BASELINE_RIGHT);
        usrBox.setAlignment(Pos.CENTER);
        return usrBox;
    }
}
