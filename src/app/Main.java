package app;

import app.controllers.DashController;
import app.utils.Connextion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    double x, y = 0;

    @Override
    public void start(Stage stage) throws Exception {
        Connextion connextion = new Connextion();
        Parent root;
        if (connextion.isLogedIn()) {
            root = FXMLLoader.load(getClass().getResource("views/dash.fxml"));
        }else{
            root = FXMLLoader.load(getClass().getResource("views/Home.fxml"));
        }

        stage.setTitle("PCF");
        stage.getIcons().add(new Image("imgs/logo1.png"));
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.resizableProperty().setValue(false);

        //grab your root here
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        //move around here
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });



        stage.setScene(new Scene(root));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
