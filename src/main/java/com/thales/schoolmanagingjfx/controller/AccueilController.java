package com.thales.schoolmanagingjfx.controller;
import com.gluonhq.connect.GluonObservableList;
import com.thales.schoolmanagingjfx.SchoolManagingApplication;
import com.thales.schoolmanagingjfx.model.School;
import com.thales.schoolmanagingjfx.model.User;
import com.thales.schoolmanagingjfx.utils.HttpRequests;
import com.thales.schoolmanagingjfx.utils.NewSchoolSingleton;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

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
        //formate texte de la combobox
            Callback<ListView<School>, ListCell<School>> schoolCellFactory =
                    new Callback<ListView<School>, ListCell<School>>() {
            @Override
            public ListCell<School> call(ListView<School> l) {
                return new ListCell<School>() {

                    @Override
                    protected void updateItem(School item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                } ;
            }
        };

        this.cbSchool.setButtonCell(schoolCellFactory.call(null));
        this.cbSchool.setCellFactory(schoolCellFactory);

        NewSchoolSingleton.getInstance().addSchoolProperty().addListener(observable -> {
            GluonObservableList<School> mylistSchool = HttpRequests.getAllSchool();

            mylistSchool.setOnSucceeded(connectStateEvent -> {
                this.cbSchool.setItems(mylistSchool);

                System.out.println(mylistSchool);
            });
        });

        GluonObservableList<School> mylistSchool = HttpRequests.getAllSchool();
        mylistSchool.setOnSucceeded(connectStateEvent -> {
            this.cbSchool.setItems(mylistSchool);

        });

        cbSchool.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newStr) -> {
            if ((School) cbSchool.valueProperty().getValue() != null) {
                mySchool = (School) cbSchool.valueProperty().getValue();
                System.out.println(mySchool);
                SchoolManagingApplication.setMySchool(mySchool);
                SchoolManagingApplication.setMyAdresse(mySchool.getAddress());
                hboxbBtn.setVisible(true);
                lbNameSchool.setText("** " + mySchool.getName() + " **");
                lbNameSchool.setVisible(true);
            }
        });
    }

    private void initializeTxt() {
       lbNameSchool.isVisible();
       this.lbNameSchool.setText("****");
       SchoolManagingApplication.connectedUserProperty().addListener((observableValue, user, t1) -> {
           this.lbLogin.setText(SchoolManagingApplication.getConnectedUser().getLogin());
       });
        this.lbLogin.setText(SchoolManagingApplication.getConnectedUser().getLogin());
    }

    private void initializeButtons() {

        this.btnCourse.setOnMouseClicked(mouseEvent -> {
            SchoolManagingApplication.setScreen("gestionMatiere");

        });
        this.btnRoom.setOnMouseClicked(mouseEvent -> {
            SchoolManagingApplication.setScreen("gestionRoom");

        });
        this.btnTeacher.setOnMouseClicked(mouseEvent -> {
            SchoolManagingApplication.setScreen("gestionProf");

        });
        this.btnGrade.setOnMouseClicked(mouseEvent -> {
            SchoolManagingApplication.setScreen("gestionGrade");

        });
        this.btnEtablissement.setOnMouseClicked(mouseEvent -> {
            SchoolManagingApplication.setScreen("gestionEtablissement");

        });
        this. btnDeco.setOnMouseClicked(mouseEvent -> {
            SchoolManagingApplication.setScreen("userConnect");

        });
        this.btnUser.setOnMouseClicked(mouseEvent -> {
            SchoolManagingApplication.setScreen("gestionUser");

        });
    }

    public static School getSchool() {
        //todo Récupérer l'établissement selectionné
        School mySchool= new School();
        return mySchool;
    }
}
