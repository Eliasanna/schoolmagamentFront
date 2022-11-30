package com.thales.schoolmanagingjfx.controller;
import com.gluonhq.connect.GluonObservableList;
import com.thales.schoolmanagingjfx.SchoolManagingApplication;
import com.thales.schoolmanagingjfx.model.School;
import com.thales.schoolmanagingjfx.utils.HttpRequests;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccueilController implements Initializable  {
    @FXML
    public ComboBox cbSchool;
    @FXML
    public Label lbNameSchool;
    @FXML
    public Button btnCourse;
    @FXML
    public Button btnRoom;
    @FXML
    public Button btnTeacher;
    @FXML
    public Button btnGrade;
    @FXML
    public Button btnDeco;
    @FXML
    public Button btnUser;
    @FXML
    public Button btnEtablissement;
    @FXML
    public Label lbLogin;

    private School mySchool;


    public School getMySchool() {
        return mySchool;
    }

    public void setMySchool(School mySchool) {
        this.mySchool = mySchool;
    }

    public HBox hboxbBtn;
    ArrayList<School> listShcoolAll = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialiseComboBox();
        initializeButtons();
        initializeTxt();
    }

    private void initialiseComboBox() {

        cbSchool.setDisable(false);

        GluonObservableList<School> mylistSchool = HttpRequests.getAllSchool();
        mylistSchool.setOnSucceeded(connectStateEvent -> {

            this.cbSchool.setItems(mylistSchool);


            //cbSchool.getSelectionModel().selectFirst();
            //this.listShcoolAll = (ArrayList<School>) FXCollections.observableArrayList(mylistSchool);
        });

        cbSchool.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newStr) -> {
            mySchool = (School) cbSchool.valueProperty().getValue();
           // System.out.println(mySchool);
            SchoolManagingApplication.setMySchool(mySchool);
            hboxbBtn.setVisible(true);
            lbNameSchool.setText("** "+mySchool.getName()+" **");
            lbNameSchool.setVisible(true);
        });
    }

    private void initializeTxt() {
       lbNameSchool.isVisible();
       this.lbNameSchool.setText("****");
       this.lbLogin.setText(SchoolManagingApplication.getUser().getLogin());
    }

    private void initializeButtons() {

        this.btnCourse.setOnMouseClicked(mouseEvent -> {
            System.out.println("gestionMatiere");
            SchoolManagingApplication.setScreen("gestionMatiere");

        });
        this.btnRoom.setOnMouseClicked(mouseEvent -> {
            System.out.println("gestionRoom");
            SchoolManagingApplication.setScreen("gestionRoom");

        });
        this.btnTeacher.setOnMouseClicked(mouseEvent -> {
            System.out.println("gestionProf");
            SchoolManagingApplication.setScreen("gestionProf");

        });
        this.btnGrade.setOnMouseClicked(mouseEvent -> {
            System.out.println("gestionClasse");
            SchoolManagingApplication.setScreen("gestionGrade");

        });
        this.btnEtablissement.setOnMouseClicked(mouseEvent -> {
            SchoolManagingApplication.setScreen("gestionEtablissement");
            System.out.println("gestionEtablissement");
        });
        this. btnDeco.setOnMouseClicked(mouseEvent -> {
            SchoolManagingApplication.setScreen("userConnect");
            System.out.println("userConnect");
        });
        this.btnUser.setOnMouseClicked(mouseEvent -> {
            SchoolManagingApplication.setScreen("");
        });
    }

    public static School getSchool() {
        //todo Récupérer l'établissement selectionné
        School mySchool= new School();
        return mySchool;
    }
}
