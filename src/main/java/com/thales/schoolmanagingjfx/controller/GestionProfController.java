package com.thales.schoolmanagingjfx.controller;

import com.gluonhq.connect.GluonObservableList;
import com.thales.schoolmanagingjfx.SchoolManagingApplication;
import com.thales.schoolmanagingjfx.model.Teacher;
import com.thales.schoolmanagingjfx.utils.HttpRequests;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GestionProfController implements Initializable {
    @FXML
    public HBox enTete;
    @FXML
    public TableView tbView;
    @FXML
    public Label lbIdCourse;
    @FXML
    public TextField tbFName;
    @FXML
    public TextField tbLName;
    @FXML
    public DatePicker dpBirthDate;
    @FXML
    public ComboBox cbCourse1;
    @FXML
    public ComboBox cbCourse2;
    @FXML
    public ComboBox cbCourse3;
    @FXML
    public ComboBox cbMainCourse;
    @FXML
    public Button btnClear;
    @FXML
    public Button btnAdd;
    @FXML
    public Button btnSup;

    private ObjectProperty<Teacher> selectedTeacher = new SimpleObjectProperty<Teacher>();

    private ObservableList<Teacher> teachers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEntete();
        initializeTableView();
        initializeButtons();
    }

    private void initializeButtons() {
        btnSup.setOnMouseClicked(mouseEvent -> {
            //String id = lbId.getText();
            //HttpRequests.deleteCourse(id);
            SchoolManagingApplication.setScreen("accueil");
        });
    }

    private void initializeTableView() {

        TableView<Teacher> tbView = new TableView<>();

        TableColumn<Teacher, String> idProfCol = new TableColumn<>("Identifiant");
        TableColumn<Teacher, String> fnameProfCol = new TableColumn<>("Nom");
        TableColumn<Teacher, String> lnameProfCol = new TableColumn<>("Prénom");
        TableColumn<Teacher, String> dateProfCol = new TableColumn<>("Section");
        TableColumn<Teacher, String> profMatCol = new TableColumn<>("Matières");


        tbView.getColumns().addAll(idProfCol,fnameProfCol,lnameProfCol,dateProfCol,profMatCol);

        idProfCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fnameProfCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lnameProfCol.setCellValueFactory(new PropertyValueFactory<>("section"));
        dateProfCol.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        //TODO ne fonctionnera pas
        profMatCol.setCellValueFactory(new PropertyValueFactory<>("matiere"));


        GluonObservableList<Teacher> gotList = HttpRequests.getAllTeacher();
        gotList.setOnSucceeded(connectStateEvent -> {
            this.teachers = FXCollections.observableArrayList(gotList);
            tbView.setItems(this.teachers);
        });

        tbView.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            selectedTeacher.setValue((Teacher) t1);
        });
    }

    private void setEntete(){
        Label title = (Label) enTete.getChildren().get(1);
        title.setText("  Gestion des professeurs  ");
    }
}
