package app.controllers;


import app.models.Client;
import app.models.Commande;
import app.models.CommandeUpdate;
import app.models.Produit;
import app.utils.Connextion;
import com.jfoenix.controls.*;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashController implements Initializable {

    double x, y = 0;

    @FXML
    private JFXButton Acceuil,Clients,Produits,Commandes,logOut,addCmd,addProduit,searchCmd,searchClient;
    @FXML
    private JFXButton print;
    @FXML
    private Pane addCmdRightPane,addProduitRightPane,searchCmdRightPane,searchClientRightPane;

    @FXML
    private Pane paneAcceuil,paneClient,paneCommandes,paneProduits;

    @FXML
    private TableView<Produit> tableProduit;
    @FXML
    private TableView<Client> tableClient;
    @FXML
    private TableView<Commande> tableCommandes;

    @FXML
    private Pane paneClientRightPane,paneCommandeRightPane,paneProduitRightPane;

    @FXML
    TableColumn<Produit,Integer> idColProduit;
    @FXML
    TableColumn<Produit,String> nomColProduit;
    @FXML
    TableColumn<Produit,Double> prixColProduit;
    @FXML
    TableColumn<Produit,Integer> quantiteColProduit;


    @FXML
    TableColumn<Client,Integer> idColClient;
    @FXML
    TableColumn<Client,String> nomColClient;
    @FXML
    TableColumn<Client,String> sexeColClient;
    @FXML
    TableColumn<Client,String> dateNaissanceColClient;
    @FXML
    TableColumn<Client,String> telephoneColClient;



    @FXML
    private TableColumn<Commande,Integer> idColCommande;
    @FXML
    private TableColumn<Commande,String> clientColCommande;
    @FXML
    private TableColumn<Commande,String> dateColCommande;
    @FXML
    private TableColumn<Commande,String> statusColCommande;
    @FXML
    private TableColumn<Commande,String> produitColCommande;


    @FXML
    private JFXTextField modifCLientNom,modifCLientTelephone,modifCLientDateNaissance;
    @FXML
    private JFXButton modifCLientEnregistrer;
    @FXML
    private Label modifClientLabel;
    @FXML
    private JFXRadioButton modifClientH,modifClientF;


    @FXML
    private JFXTextField modifProduitNom,modifProduitPrix,modifProduitQuantite;
    @FXML
    private JFXButton modifProduitEnregistrer;
    @FXML
    private Label modifProduitLabel;


    @FXML
    private JFXTextField addProduitRightPaneName,addProduitRightPanePrice,addProduitRightPaneQuantity;
    @FXML
    private JFXButton addProduitRightPaneEnregistrer;


    @FXML
    private JFXTextField searchClientRightPaneName;
    @FXML
    private JFXRadioButton searchClientRightPaneGenderH,searchClientRightPaneGenderF;
    @FXML
    private JFXButton searchClientRightPaneEnregistrer;


    @FXML
    private JFXTextField searchCmdRightPaneName;
    @FXML
    private JFXButton searchCmdRightPaneEnregistrer;



    @FXML
    private JFXTextField addCmdRightPaneQuantite1,addCmdRightPaneQuantite2,addCmdRightPaneQuantite3;
    @FXML
    private JFXComboBox<String> addCmdRightPaneNomProduit1,addCmdRightPaneNomProduit2,addCmdRightPaneNomProduit3,addCmdRightPaneStatus;
    @FXML
    private JFXTextField addCmdRightPaneTelephone,addCmdRightPaneNom,addCmdRightPaneDateDeNaissance;
    @FXML
    private JFXRadioButton addCmdRightPaneGenderH,addCmdRightPaneGenderF;
    @FXML
    private JFXButton addCmdRightPaneEnregistrer;



    @FXML
    private JFXTextField paneCommandeRightPaneQuantite1,paneCommandeRightPaneQuantite2,paneCommandeRightPaneQuantite3;
    @FXML
    private JFXComboBox<String> paneCommandeRightPaneNomProduit1,paneCommandeRightPaneNomProduit2,paneCommandeRightPaneNomProduit3,paneCommandeRightPaneStatus;
    @FXML
    private JFXTextField paneCommandeRightPaneTelephone,paneCommandeRightPaneNom,paneCommandeRightPaneDateDeNaissance;
    @FXML
    private JFXRadioButton paneCommandeRightPaneGenderH,paneCommandeRightPaneGenderF;
    @FXML
    private JFXButton paneCommandeRightPaneEnregistrer;









    private void navigationHandler(){
        hideNavPanesExcept(paneAcceuil);
        paneAcceuil.toFront();

        EventHandler<ActionEvent> navHandler = new EventHandler<ActionEvent>(){

            @Override
            public void handle(final ActionEvent event) {

                if (event.getSource() == Acceuil){

                    addCmdRightPane.opacityProperty().set(0);
                    addProduitRightPane.opacityProperty().set(0);
                    searchCmdRightPane.opacityProperty().set(0);
                    searchClientRightPane.opacityProperty().set(0);

                    searchClientRightPaneName.setText("");
                    searchClientRightPaneGenderH.setSelected(false);
                    searchClientRightPaneGenderF.setSelected(false);

                    addProduitRightPaneName.setText("");
                    addProduitRightPanePrice.setText("");
                    addProduitRightPaneQuantity.setText("");



                    hideNavPanesExcept(paneAcceuil);
                    paneAcceuil.toFront();
                    System.out.println("0");
                }
                else if(event.getSource() == Clients) {
                    hideNavPanesExcept(paneClient);
                    paneClientRightPane.setOpacity(0);
                    paneClient.toFront();
                    try {
                        setTableClient(getClients());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.out.println("1");
                }
                else if(event.getSource() == Produits) {
                    hideNavPanesExcept(paneProduits);
                    paneProduits.toFront();

                    try {
                        setTableProduit(getProduits());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    paneProduitRightPane.setOpacity(0);
                    System.out.println("2");
                }
                else if(event.getSource() == Commandes) {
                    hideNavPanesExcept(paneCommandes);
                    paneCommandes.toFront();
                    paneCommandeRightPane.setOpacity(0);
                    try {
                        setTableCommandes(getCommandes());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.out.println("3");
                }
                else if(event.getSource() == print){


                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("imgs/logo1.png"));

                    alert.setHeaderText("Imprimation de la liste des commandes en cours");
                    alert.setContentText("Etes vous sur?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){

                        PrinterJob printerJob = PrinterJob.createPrinterJob();
                        if(printerJob.showPrintDialog(stage.getOwner()) && printerJob.printPage(tableCommandes))
                            printerJob.endJob();

                    }



                    System.out.println("printing...");
                }
                else if(event.getSource() == logOut) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("");
                    Stage stage1 = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage1.getIcons().add(new Image("imgs/logo1.png"));

                    alert.setHeaderText("Deconnexion");
                    alert.setContentText("Etes vous sur?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        try {
                            new Connextion().setIsLogedIn(false);

                            Parent root = FXMLLoader.load(getClass().getResource("../views/Home.fxml"));
                            Stage stage = new Stage();

                            stage.setTitle("PCF");
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



                        } catch (IOException | SQLException e) {
                            e.printStackTrace();
                        }

                    }



                    System.out.println("4");
                }

            }

        };

        Acceuil.addEventHandler(ActionEvent.ACTION,navHandler);
        Clients.addEventHandler(ActionEvent.ACTION,navHandler);
        Produits.addEventHandler(ActionEvent.ACTION,navHandler);
        Commandes.addEventHandler(ActionEvent.ACTION,navHandler);
        logOut.addEventHandler(ActionEvent.ACTION,navHandler);
        print.addEventHandler(ActionEvent.ACTION,navHandler);

    }
    private void acceuilPageNavigationHandler() throws SQLException {

        addCmdRightPane.opacityProperty().set(0);
        addProduitRightPane.opacityProperty().set(0);
        searchCmdRightPane.opacityProperty().set(0);
        searchClientRightPane.opacityProperty().set(0);




        ArrayList<String> t = new ArrayList<>();
        for (int i = 0; i <getProduits().size(); i++) {
            t.add(getProduits().get(i).getId()+" "+getProduits().get(i).getName());
        }

        addCmdRightPaneNomProduit1.setItems(FXCollections.observableArrayList(t));
        addCmdRightPaneNomProduit2.setItems(FXCollections.observableArrayList(t));
        addCmdRightPaneNomProduit3.setItems(FXCollections.observableArrayList(t));

        ArrayList<String> s = new ArrayList();
        s.add("livree");
        s.add("en cours");
        s.add("annuler");
        addCmdRightPaneStatus.setItems(FXCollections.observableArrayList(s));


        final int[] clientId = new int[1];
        addCmdRightPaneTelephone.setOnKeyReleased(event2 -> {
            try {

                Client c = new Connextion().getClient(addCmdRightPaneTelephone.getText());
                if (c!=null){
                    clientId[0] =c.getId();
                    addCmdRightPaneNom.setText(c.getName());
                    addCmdRightPaneDateDeNaissance.setText(c.getDateNaissance());
                    addCmdRightPaneGenderF.setSelected(c.getSexe().equals("F"));
                    addCmdRightPaneGenderH.setSelected(c.getSexe().equals("H"));

                    addCmdRightPaneNom.setDisable(true);
                    addCmdRightPaneDateDeNaissance.setDisable(true);
                    addCmdRightPaneGenderF.setDisable(true);
                    addCmdRightPaneGenderH.setDisable(true);
                    addCmdRightPaneTelephone.setDisable(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });





        EventHandler<ActionEvent> buttonsListHandler = event -> {
             if (event.getSource()==addCmd){
                 hideAcceuilRightPanesExcept(addCmdRightPane);
                 addCmdRightPane.toFront();


                 addCmdRightPaneTelephone.setText("");
                 addCmdRightPaneNom.setText("");
                 addCmdRightPaneDateDeNaissance.setText("");
                 addCmdRightPaneGenderF.setSelected(false);
                 addCmdRightPaneGenderH.setSelected(false);

                 addCmdRightPaneNomProduit1.setValue("");
                 addCmdRightPaneNomProduit2.setValue("");
                 addCmdRightPaneNomProduit3.setValue("");


                 addCmdRightPaneQuantite1.setText("");
                 addCmdRightPaneQuantite2.setText("");
                 addCmdRightPaneQuantite3.setText("");

                 addCmdRightPaneStatus.setValue("");


                 addCmdRightPaneNom.setDisable(false);
                 addCmdRightPaneDateDeNaissance.setDisable(false);
                 addCmdRightPaneGenderF.setDisable(false);
                 addCmdRightPaneGenderH.setDisable(false);
                 addCmdRightPaneTelephone.setDisable(false);








                 addCmdRightPaneEnregistrer.setOnAction(event14 -> {

                     try {
                         Connextion connextion = new Connextion();

                         int[] p = new int[3];

                         p[0]=(addCmdRightPaneNomProduit1.getValue()==null ||addCmdRightPaneNomProduit1.getValue().equals(""))?0:Integer.parseInt(addCmdRightPaneNomProduit1.getValue().charAt(0)+"");
                         p[1]=(addCmdRightPaneNomProduit2.getValue()==null ||addCmdRightPaneNomProduit2.getValue().equals(""))?0:Integer.parseInt(addCmdRightPaneNomProduit2.getValue().charAt(0)+"");
                         p[2]=(addCmdRightPaneNomProduit3.getValue()==null ||addCmdRightPaneNomProduit3.getValue().equals(""))?0:Integer.parseInt(addCmdRightPaneNomProduit3.getValue().charAt(0)+"");

                         int[] q = new int[3];
                         q[0]=(addCmdRightPaneNomProduit1.getValue()==null||addCmdRightPaneNomProduit1.getValue().equals(""))?0:Integer.parseInt(addCmdRightPaneQuantite1.getText());
                         q[1]=(addCmdRightPaneNomProduit2.getValue()==null||addCmdRightPaneNomProduit2.getValue().equals(""))?0:Integer.parseInt(addCmdRightPaneQuantite2.getText());
                         q[2]=(addCmdRightPaneNomProduit3.getValue()==null||addCmdRightPaneNomProduit3.getValue().equals(""))?0:Integer.parseInt(addCmdRightPaneQuantite3.getText());

                         for (int i = 0; i <p.length ; i++) {
                             if (p[i]>0) connextion.reduceFromQuantity(p[i],q[i]);
                         }



                         if (clientId[0]>0)
                            connextion.addCommande(clientId[0],p,q,addCmdRightPaneStatus.getValue());
                         else
                             connextion.addCommande(addCmdRightPaneNom.getText(),addCmdRightPaneTelephone.getText(),(addCmdRightPaneGenderH.isSelected())?"H":"F",addCmdRightPaneDateDeNaissance.getText(),p,q,addCmdRightPaneStatus.getValue());

                         addCmdRightPaneTelephone.setText("");
                         addCmdRightPaneNom.setText("");
                         addCmdRightPaneDateDeNaissance.setText("");
                         addCmdRightPaneGenderF.setSelected(false);
                         addCmdRightPaneGenderH.setSelected(false);

                         addCmdRightPaneNom.setDisable(false);
                         addCmdRightPaneDateDeNaissance.setDisable(false);
                         addCmdRightPaneGenderF.setDisable(false);
                         addCmdRightPaneGenderH.setDisable(false);
                         addCmdRightPaneTelephone.setDisable(false);



                         setTableCommandes(getCommandes());
                         hideNavPanesExcept(paneCommandes);
                         paneCommandes.toFront();
                         paneCommandeRightPane.setOpacity(0);

                     } catch (SQLException e) {
                         e.printStackTrace();
                     }


                 });










                 System.out.println("adding cmd");
             }else if(event.getSource()==addProduit){

                 hideAcceuilRightPanesExcept(addProduitRightPane);
                 addProduitRightPane.toFront();

                 addProduitRightPaneName.setText("");
                 addProduitRightPanePrice.setText("");
                 addProduitRightPaneQuantity.setText("");











                 addProduitRightPaneEnregistrer.setOnAction(event1 -> {
                     try {
                         new Connextion().addProduit(addProduitRightPaneName.getText(),addProduitRightPanePrice.getText(),addProduitRightPaneQuantity.getText());

                         setTableProduit(getProduits());
                         hideNavPanesExcept(paneProduits);
                         paneProduits.toFront();
                         paneProduitRightPane.setOpacity(0);



                     } catch (SQLException e) {
                         e.printStackTrace();
                     }
                 });



                 System.out.println("add produit clicked");
             }else if(event.getSource()==searchCmd){
                 hideAcceuilRightPanesExcept(searchCmdRightPane);
                 searchCmdRightPane.toFront();

                 searchCmdRightPaneName.setText("");


                 searchCmdRightPaneEnregistrer.setOnAction(event13 -> {
                     String name = searchCmdRightPaneName.getText();

                     try {
                         ObservableList<Produit> produits =FXCollections.observableArrayList();
                         for (int i = 0; i <new Connextion().searchProduit(name).size() ; i++) {
                             produits.add(new Connextion().searchProduit(name).get(i));
                         }


                         setTableProduit(produits);

                         hideNavPanesExcept(paneProduits);
                         paneProduits.toFront();
                         paneProduitRightPane.setOpacity(0);



                     } catch (SQLException e) {
                         e.printStackTrace();
                     }




                 });


                 System.out.println("search cmd clicked");
             }else if(event.getSource()==searchClient){
                 hideAcceuilRightPanesExcept(searchClientRightPane);
                 searchClientRightPane.toFront();

                 searchClientRightPaneName.setText("");
                 searchClientRightPaneGenderH.setSelected(false);
                 searchClientRightPaneGenderF.setSelected(false);
                 

                 searchClientRightPaneEnregistrer.setOnAction(event12 -> {
                     String name = searchClientRightPaneName.getText();
                     String gender = (searchClientRightPaneGenderH.isSelected())?"H":"F";

                     try {
                         ObservableList<Client> clients =FXCollections.observableArrayList();
                         for (int i = 0; i <new Connextion().searchClient(name,gender).size() ; i++) {
                             clients.add(new Connextion().searchClient(name,gender).get(i));
                         }


                         setTableClient(clients);

                         hideNavPanesExcept(paneClient);
                         paneClient.toFront();
                         paneClientRightPane.setOpacity(0);



                     } catch (SQLException e) {
                         e.printStackTrace();
                     }


                 });








                 System.out.println("search client clicked");
             }
         };





        addCmd.addEventHandler(ActionEvent.ACTION,buttonsListHandler);
        addProduit.addEventHandler(ActionEvent.ACTION,buttonsListHandler);
        searchCmd.addEventHandler(ActionEvent.ACTION,buttonsListHandler);
        searchClient.addEventHandler(ActionEvent.ACTION,buttonsListHandler);


    }
    public void hideAcceuilRightPanesExcept(Pane pane){
        if (pane.getId()==addCmdRightPane.getId()) {
            addCmdRightPane.opacityProperty().set(1);
            addProduitRightPane.opacityProperty().set(0);
            searchCmdRightPane.opacityProperty().set(0);
            searchClientRightPane.opacityProperty().set(0);
        }else if (pane.getId()==addProduitRightPane.getId()) {
            addCmdRightPane.opacityProperty().set(0);
            addProduitRightPane.opacityProperty().set(1);
            searchCmdRightPane.opacityProperty().set(0);
            searchClientRightPane.opacityProperty().set(0);
        }else if (pane.getId()==searchCmdRightPane.getId()) {
            addCmdRightPane.opacityProperty().set(0);
            addProduitRightPane.opacityProperty().set(0);
            searchCmdRightPane.opacityProperty().set(1);
            searchClientRightPane.opacityProperty().set(0);
        }else if (pane.getId()==searchClientRightPane.getId()) {
            addCmdRightPane.opacityProperty().set(0);
            addProduitRightPane.opacityProperty().set(0);
            searchCmdRightPane.opacityProperty().set(0);
            searchClientRightPane.opacityProperty().set(1);
        }


    }
    public void hideNavPanesExcept(Pane pane){
        if (pane.getId()==paneAcceuil.getId()) {
            paneAcceuil.opacityProperty().set(1);
            paneClient.opacityProperty().set(0);
            paneCommandes.opacityProperty().set(0);
            paneProduits.opacityProperty().set(0);
        }else if (pane.getId()==paneClient.getId()){
            paneAcceuil.opacityProperty().set(0);
            paneClient.opacityProperty().set(1);
            paneCommandes.opacityProperty().set(0);
            paneProduits.opacityProperty().set(0);
        }else if (pane.getId()==paneCommandes.getId()){
            paneAcceuil.opacityProperty().set(0);
            paneClient.opacityProperty().set(0);
            paneCommandes.opacityProperty().set(1);
            paneProduits.opacityProperty().set(0);
        }else if (pane.getId()==paneProduits.getId()){
            paneAcceuil.opacityProperty().set(0);
            paneClient.opacityProperty().set(0);
            paneCommandes.opacityProperty().set(0);
            paneProduits.opacityProperty().set(1);
        }
    }


    public ObservableList<Produit> getProduits() throws SQLException {
        ObservableList<Produit> produits = FXCollections.observableArrayList();


        for (int i = 0; i <new Connextion().getProduits().size() ; i++) {
            produits.add(new Connextion().getProduits().get(i));
        }


        return produits;

    }
    public ObservableList<Client> getClients() throws SQLException {
        ObservableList<Client> clients = FXCollections.observableArrayList();

        for (int i = 0; i <new Connextion().getClients().size() ; i++) {
            clients.add(new Connextion().getClients().get(i));
        }



        return clients;

    }
    public ObservableList<Commande> getCommandes()throws SQLException{
        ObservableList<Commande> commandes = FXCollections.observableArrayList();

        for (int i = 0; i <new Connextion().getCommandes().size(); i++) {
            commandes.add(new Connextion().getCommandes().get(i));
        }


        return commandes;

    }



    public void setTableProduit(ObservableList<Produit> produits) throws SQLException {
        idColProduit.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColProduit.setCellValueFactory(new PropertyValueFactory<>("name"));
        prixColProduit.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantiteColProduit.setCellValueFactory(new PropertyValueFactory<>("quantity"));


        tableProduit.setItems(produits);
        paneProduitRightPane.setOpacity(0);

        tableProduit.setRowFactory( tv -> {
            TableRow<Produit> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    Produit rowData = row.getItem();
                    paneProduitRightPane.setOpacity(1);

                    modifProduitLabel.setText("Produit #"+rowData.getId());
                    modifProduitNom.setText(rowData.getName());
                    modifProduitPrix.setText(rowData.getPrice()+"");
                    modifProduitQuantite.setText(rowData.getQuantity()+"");

                    modifProduitEnregistrer.setOnAction(event1 -> {

                        try {
                            new Connextion().modifyProduit(rowData.getId(),modifProduitNom.getText(),modifProduitPrix.getText(),modifProduitQuantite.getText());
                            setTableProduit(getProduits());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });







                }else if (event.getClickCount()==2 && (! row.isEmpty())){
                    Produit rowData = row.getItem();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("imgs/logo1.png"));

                    alert.setHeaderText("Le Produit numero "+rowData.getId()+" va etre supprimer");
                    alert.setContentText("Etes vous sur?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){




                        try {
                            new Connextion().deleteProduit(rowData.getId());
                            setTableProduit(getProduits());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }


                    }


                }
            });
            return row ;
        });

    }
    public void setTableClient(ObservableList<Client> clients) throws SQLException {
        idColClient.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColClient.setCellValueFactory(new PropertyValueFactory<>("name"));
        sexeColClient.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        telephoneColClient.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        dateNaissanceColClient.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));

        tableClient.setItems(clients);
        paneClientRightPane.setOpacity(0);


        tableClient.setRowFactory( tv -> {
            TableRow<Client> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    Client rowData = row.getItem();
                    paneClientRightPane.setOpacity(1);

                    modifClientLabel.setText("Client #"+rowData.getId());
                    modifCLientNom.setText(rowData.getName());
                    modifCLientDateNaissance.setText(rowData.getDateNaissance());
                    modifCLientTelephone.setText(rowData.getTelephone());
                    if (rowData.getSexe().equals("H")) modifClientH.setSelected(true);
                    else modifClientF.setSelected(true);

                    modifCLientEnregistrer.setOnAction(event1 -> {

                        try {
                            new Connextion().modifyClient(rowData.getId(),modifCLientNom.getText(),(modifClientH.isSelected())?"H":"F",modifCLientTelephone.getText(),modifCLientDateNaissance.getText());
                            setTableClient(getClients());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });


                }else if (event.getClickCount()==2 && (! row.isEmpty())){
                    Client rowData = row.getItem();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("imgs/logo1.png"));

                    alert.setHeaderText("Le Client numero "+rowData.getId()+" va etre supprimer");
                    alert.setContentText("Etes vous sur?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){


                        try {
                            new Connextion().deleteClient(rowData.getId());
                            setTableClient(getClients());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }


                    }



                }
            });
            return row ;
        });



    }
    public void setTableCommandes(ObservableList<Commande> commandes) throws SQLException {
        setNumbers();
        idColCommande.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientColCommande.setCellValueFactory(new PropertyValueFactory<>("client"));
        statusColCommande.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateColCommande.setCellValueFactory(new PropertyValueFactory<>("date"));
        produitColCommande.setCellValueFactory(new PropertyValueFactory<>("produits"));

        tableCommandes.setItems(getCommandes());
        paneCommandeRightPane.setOpacity(0);




        tableCommandes.setRowFactory( tv -> {
            TableRow<Commande> row = new TableRow<>();
            row.setOnMouseClicked(event -> {

                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    Commande rowData = row.getItem();
                    paneCommandeRightPane.setOpacity(1);

                    paneCommandeRightPaneNomProduit1.setValue("");
                    paneCommandeRightPaneNomProduit2.setValue("");
                    paneCommandeRightPaneNomProduit3.setValue("");

                    paneCommandeRightPaneQuantite1.setText("");
                    paneCommandeRightPaneQuantite2.setText("");
                    paneCommandeRightPaneQuantite3.setText("");



                    try {
                        Connextion connextion = new Connextion();


                        Client client = new Client();
                        ArrayList<Client> clients = connextion.getClients();
                        for (int i = 0; i <clients.size() ; i++) {
                            if (clients.get(i).getName().equals(rowData.getClient())){
                                client = clients.get(i);
                                break;
                            }
                        }


                        ArrayList<String> t = new ArrayList<>();
                        for (int i = 0; i <getProduits().size(); i++) {
                            t.add(getProduits().get(i).getId()+" "+getProduits().get(i).getName());
                        }

                        paneCommandeRightPaneNomProduit1.setItems(FXCollections.observableArrayList(t));
                        paneCommandeRightPaneNomProduit2.setItems(FXCollections.observableArrayList(t));
                        paneCommandeRightPaneNomProduit3.setItems(FXCollections.observableArrayList(t));

                        ArrayList<String> s = new ArrayList();
                        s.add("livree");
                        s.add("en cours");
                        s.add("annuler");
                        paneCommandeRightPaneStatus.setItems(FXCollections.observableArrayList(s));


                        paneCommandeRightPaneEnregistrer.setOnAction(event1 -> {

                            System.out.println("you wanna save :p");

                            int[] p = new int[3];

                            p[0] = (paneCommandeRightPaneNomProduit1.getValue() == null || paneCommandeRightPaneNomProduit1.getValue().equals("")) ? 0 : Integer.parseInt(paneCommandeRightPaneNomProduit1.getValue().charAt(0)+ "");
                            p[1] = (paneCommandeRightPaneNomProduit2.getValue() == null || paneCommandeRightPaneNomProduit2.getValue().equals(""))  ? 0 : Integer.parseInt(paneCommandeRightPaneNomProduit2.getValue().charAt(0)+ "");
                            p[2] = (paneCommandeRightPaneNomProduit3.getValue() == null || paneCommandeRightPaneNomProduit3.getValue().equals(""))  ? 0 : Integer.parseInt(paneCommandeRightPaneNomProduit3.getValue().charAt(0)+"");

                            int[] q = new int[3];
                            q[0] = (paneCommandeRightPaneNomProduit1.getValue() == null || paneCommandeRightPaneNomProduit1.getValue().equals("")) ? 0 : Integer.parseInt(paneCommandeRightPaneQuantite1.getText());
                            q[1] = (paneCommandeRightPaneNomProduit2.getValue() == null || paneCommandeRightPaneNomProduit2.getValue().equals("")) ? 0 : Integer.parseInt(paneCommandeRightPaneQuantite2.getText());
                            q[2] = (paneCommandeRightPaneNomProduit3.getValue() == null || paneCommandeRightPaneNomProduit3.getValue().equals("")) ? 0 : Integer.parseInt(paneCommandeRightPaneQuantite3.getText());

                            for (int i = 0; i <p.length ; i++) {
                                if (p[i]>0) {
                                    if (connextion.productIsInCmd(rowData.getId(),p[i])) {
                                        int nb = q[i] - connextion.getOldQuantity(rowData.getId(), p[i]);

                                        if (nb > 0) connextion.reduceFromQuantity(p[i], nb);
                                        else connextion.addToQuantity(p[i], nb * (-1));
                                    }else {
                                        connextion.reduceFromQuantity(p[i],q[i]);
                                    }

                                }
                            }

                            connextion.modifyCommande(rowData.getId(),p,q,paneCommandeRightPaneStatus.getValue());

                            paneCommandeRightPaneNomProduit1.setValue("");
                            paneCommandeRightPaneNomProduit2.setValue("");
                            paneCommandeRightPaneNomProduit3.setValue("");

                            paneCommandeRightPaneQuantite1.setText("");
                            paneCommandeRightPaneQuantite2.setText("");
                            paneCommandeRightPaneQuantite3.setText("");

                            paneCommandeRightPaneStatus.setValue("");

                            try {
                                setTableCommandes(getCommandes());
                                hideNavPanesExcept(paneCommandes);
                                paneCommandes.toFront();
                                paneCommandeRightPane.setOpacity(0);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }



                        });





                        paneCommandeRightPaneNom.setText(client.getName());
                        paneCommandeRightPaneTelephone.setText(client.getTelephone());
                        paneCommandeRightPaneDateDeNaissance.setText(client.getDateNaissance());
                        paneCommandeRightPaneGenderF.setSelected((client.getSexe().equals("F")));
                        paneCommandeRightPaneGenderH.setSelected((client.getSexe().equals("H")));


                        paneCommandeRightPaneNom.setDisable(true);
                        paneCommandeRightPaneTelephone.setDisable(true);
                        paneCommandeRightPaneDateDeNaissance.setDisable(true);
                        paneCommandeRightPaneGenderF.setDisable(true);
                        paneCommandeRightPaneGenderH.setDisable(true);




                        ArrayList<CommandeUpdate> cmds = connextion.productsInCmd(rowData.getId());

                        switch (cmds.size()){
                            case 0:
                                break;
                            case 1:
                                paneCommandeRightPaneNomProduit1.setValue(cmds.get(0).getProductName());
                                paneCommandeRightPaneQuantite1.setText(cmds.get(0).getProductQuantity()+"");
                                break;
                            case 2:
                                paneCommandeRightPaneNomProduit1.setValue(cmds.get(0).getProductName());
                                paneCommandeRightPaneQuantite1.setText(cmds.get(0).getProductQuantity()+"");

                                paneCommandeRightPaneNomProduit2.setValue(cmds.get(1).getProductName());
                                paneCommandeRightPaneQuantite2.setText(cmds.get(1).getProductQuantity()+"");
                                break;
                            case 3:

                                paneCommandeRightPaneNomProduit1.setValue(cmds.get(0).getProductName());
                                paneCommandeRightPaneQuantite1.setText(cmds.get(0).getProductQuantity()+"");

                                paneCommandeRightPaneNomProduit2.setValue(cmds.get(1).getProductName());
                                paneCommandeRightPaneQuantite2.setText(cmds.get(1).getProductQuantity()+"");

                                paneCommandeRightPaneNomProduit3.setValue(cmds.get(2).getProductName());
                                paneCommandeRightPaneQuantite3.setText(cmds.get(2).getProductQuantity()+"");
                                break;
                        }
                        paneCommandeRightPaneStatus.setValue(rowData.getStatus());









                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }else if (event.getClickCount()==2 && (! row.isEmpty())){
                    Commande rowData = row.getItem();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("imgs/logo1.png"));

                    alert.setHeaderText("La commande numero "+rowData.getId()+" va etre supprimer");
                    alert.setContentText("Etes vous sur?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){

                        try {
                            new Connextion().deleteCommande(rowData.getId());
                            setTableCommandes(getCommandes());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }




                }
            });
            return row ;
        });



    }

    @FXML
    Label nbLivree,nbEnCours,nbAnnuler;

    public void setNumbers() throws SQLException {
        ArrayList<Commande> cmds = new Connextion().getCommandes();
        int nbLivree1=0,nbEnCours1=0,nbAnnuler1=0;
        for (int i = 0; i <cmds.size() ; i++) {
            if (cmds.get(i).getStatus().equals("en cours")) nbEnCours1++;
            if (cmds.get(i).getStatus().equals("livree")) nbLivree1++;
            if (cmds.get(i).getStatus().equals("annuler")) nbAnnuler1++;
        }

        nbLivree.setText(nbLivree1+" livree");
        nbEnCours.setText(nbEnCours1+" en cours");
        nbAnnuler.setText(nbAnnuler1+" annuler");

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navigationHandler();

        try {
            setNumbers();
            acceuilPageNavigationHandler();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            setTableProduit(getProduits());
            setTableClient(getClients());
            setTableCommandes(getCommandes());
        } catch (SQLException e) {
            e.printStackTrace();
        }




    }


}

