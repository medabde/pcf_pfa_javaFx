package app.controllers;

import app.models.Client;
import app.models.Produit;
import app.utils.Connextion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import jdk.nashorn.api.tree.Tree;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    double x, y = 0;

    @FXML
    TextField username;
    @FXML
    PasswordField password;

    @FXML
    Pane homePane,loginPane,produitsPane;

    @FXML
    Text err;


    @FXML
    JFXButton login,produits,login1,cancel,cancelP;

    @FXML
    TableView<Produit> tableProduits;
    @FXML
    TableColumn<Produit,Integer> idCol;
    @FXML
    TableColumn<Produit,String> nomCol;
    @FXML
    TableColumn<Produit,Double> prixCol;
    @FXML
    TableColumn<Produit,Integer> quantiteCol;


    public void homePage(){
        loginPane.opacityProperty().set(0);
        produitsPane.opacityProperty().set(0);
        homePane.opacityProperty().set(1);

        homePane.toFront();
        login.setOnAction(event -> {

            loginPane.opacityProperty().set(1);
            homePane.opacityProperty().set(0);
            produitsPane.opacityProperty().set(0);
            loginPane.toFront();

            System.out.println("hello");
        });
        produits.setOnAction(event -> {

            loginPane.opacityProperty().set(0);
            homePane.opacityProperty().set(0);
            produitsPane.opacityProperty().set(1);
            produitsPane.toFront();

            System.out.println("hello");
        });
    }


    public void loginPage() throws SQLException {

        Connextion connextion = new Connextion();


        err.opacityProperty().set(0);

        cancel.setOnAction(event -> {

            username.setText("");
            password.setText("");
            err.opacityProperty().set(0);


            loginPane.opacityProperty().set(0);
            homePane.opacityProperty().set(1);
            homePane.toFront();
        });
        login1.setOnAction(event -> {

            if (connextion.checkUsernamePass(username.getText(),password.getText())){

                connextion.setIsLogedIn(true);

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../views/dash.fxml"));
                    Stage stage = new Stage();



                    stage.setTitle("PCF");
                    stage.getIcons().add(new Image("imgs/logo1.png"));

                    //stage.initStyle(StageStyle.UNDECORATED);
                    stage.resizableProperty().setValue(false);

                    //grab your root here
                    root.setOnMousePressed(event1 -> {
                        x = event1.getSceneX();
                        y = event1.getSceneY();
                    });

                    //move around here
                    root.setOnMouseDragged(event2 -> {
                        stage.setX(event2.getScreenX() - x);
                        stage.setY(event2.getScreenY() - y);
                    });

                    stage.setScene(new Scene(root));
                    stage.show();



                    ((Node)(event.getSource())).getScene().getWindow().hide();



                } catch (IOException e) {
                    e.printStackTrace();
                }



            }else{
                username.setText("");
                password.setText("");
                err.opacityProperty().set(1);
            }
        });
    }

    public void produitsPage(){
        cancelP.setOnAction(event -> {
            loginPane.opacityProperty().set(0);
            homePane.opacityProperty().set(1);
            homePane.toFront();
        });
    }


    public ObservableList<Produit> getProduits() throws SQLException {
        ObservableList<Produit> produits = FXCollections.observableArrayList();

        for (int i = 0; i <new Connextion().getProduits().size() ; i++) {
            produits.add(new Connextion().getProduits().get(i));
        }


        return produits;

    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantiteCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        try {
            tableProduits.setItems(getProduits());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableProduits.setRowFactory( tv -> {
            TableRow<Produit> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Produit rowData = row.getItem();
                    System.out.println(rowData.getId());
                }
            });
            return row ;
        });


        homePage();
        try {
            loginPage();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        produitsPage();




    }
}
