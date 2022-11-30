package com.thales.schoolmanagingjfx.controller;

import com.gluonhq.connect.GluonObservableObject;
import com.thales.schoolmanagingjfx.model.Address;
import com.thales.schoolmanagingjfx.model.Course;
import com.thales.schoolmanagingjfx.model.School;
import com.thales.schoolmanagingjfx.utils.HttpRequests;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GestionEtablissementController implements Initializable  {
    @FXML
    public Label lbName;
    @FXML
    public TextField txtCity;
    @FXML
    public TextField txtCountry;
    @FXML
    public Button btnClear;
    @FXML
    public Button btnAdd;
    @FXML
    public Label lbId;
    @FXML
    public Label lbTel;
    @FXML
    public Label lbStreat;
    @FXML
    public Label lbCity;
    @FXML
    public Label lbCountry;
    @FXML
    public ImageView imgLogo;
    @FXML
    public Label lbIdShcool;
    @FXML
    public ComboBox cbType;
    @FXML
    public TextField txtTel;
    @FXML
    public TextField txtUrlIm;
    @FXML
    public TextField txtnbStreat;
    @FXML
    public TextField txtStreat;
    @FXML
    public HBox enTete;
    @FXML
    public Button btnSup;
    @FXML
    public TextField txtName;
    @FXML
    public Button btnUpdate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEntete();
        initializeButtons();
        initializeCb();
    }

    private void initializeCb() {
        List<String> listeType = new ArrayList<>();
        listeType.add("Maternelle");
        listeType.add("Elémentaire");
        listeType.add("Lycée");
        listeType.add("Collège");
        listeType.add("Faculté");
        ObservableList<String> oType = FXCollections.observableArrayList(listeType);
        this.cbType.setItems(oType);
    }

    private void initializeButtons() {
        btnClear.setOnMouseClicked(mouseEvent -> {
            lbId.setVisible(false);

        });

        btnAdd.setOnMouseClicked(mouseEvent -> {
            Address adresse = new Address();
            adresse.setCity(txtCity.getText());
            adresse.setCountry(txtCountry.getText());
            adresse.setStreet(txtStreat.getText());
            adresse.setStreetNumber(Integer.parseInt(txtnbStreat.getText()));

            GluonObservableObject<Address> potentialConnected = HttpRequests.addAddress(adresse);

            School mySchool = new School();
            mySchool.setName(txtName.getText());
            mySchool.setType((String) cbType.valueProperty().getValue());
            mySchool.setPhoneNumber(txtTel.getText());
            mySchool.setLogo(txtUrlIm.getText());
            mySchool.setAddress(adresse);
            potentialConnected.setOnSucceeded(connectStateEvent -> {
                GluonObservableObject<School> potentialConnected2 = HttpRequests.addSchool(mySchool);});
        });

        btnUpdate.setOnMouseClicked(mouseEvent -> {

        });
    }

    private void setEntete(){
        Label title = (Label) enTete.getChildren().get(1);
        title.setText("  Gestion de l'établissement  ");
    }

}
