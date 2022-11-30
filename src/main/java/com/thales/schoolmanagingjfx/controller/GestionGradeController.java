package com.thales.schoolmanagingjfx.controller;

import com.gluonhq.connect.GluonObservableList;
import com.gluonhq.connect.GluonObservableObject;
import com.thales.schoolmanagingjfx.SchoolManagingApplication;
import com.thales.schoolmanagingjfx.model.Grade;
import com.thales.schoolmanagingjfx.model.School;
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

public class GestionGradeController implements Initializable  {
    @FXML
    public HBox enTete;
    @FXML
    public TableView tbView;
    @FXML
    public Label lbId;
    @FXML
    public TextField tbName;
    @FXML
    public TextField tbSection;
    @FXML
    public Button btnClear;
    @FXML
    public Button btnAdd;
    @FXML
    public Button btnSup;
    public ComboBox cbListProf;

    private ObjectProperty<Grade> selectedGrade = new SimpleObjectProperty<Grade>();

    private ObservableList<Grade> grades;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEntete();
        initializeButtons();
        initializeTableView();
        initializeSelection();
    }

    private void initializeTableView() {

        TableColumn<Grade, String> idGradeCol = new TableColumn<>("Identifiant");
        TableColumn<Grade, String> nameGradeCol = new TableColumn<>("Nom");
        TableColumn<Grade, String> sectionGradeCol = new TableColumn<>("Section");
        TableColumn<Grade, String> profGradeCol = new TableColumn<>("Prof principal");

        tbView.getColumns().addAll(idGradeCol,nameGradeCol,sectionGradeCol,profGradeCol);
        idGradeCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameGradeCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        sectionGradeCol.setCellValueFactory(new PropertyValueFactory<>("section"));
        profGradeCol.setCellValueFactory(new PropertyValueFactory<>("teacher"));

        GluonObservableList<Grade> gotList = HttpRequests.getAllGrade(SchoolManagingApplication.getMySchool().getId());
        gotList.setOnSucceeded(connectStateEvent -> {
            this.grades = FXCollections.observableArrayList(gotList);
            tbView.setItems(this.grades);
        });

        tbView.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            selectedGrade.setValue((Grade) t1);
        });
    }

    private void initializeButtons() {
        btnClear.setOnMouseClicked(mouseEvent -> {
            lbId.setVisible(false);
            tbName.clear();
            tbSection.clear();
            cbListProf.getSelectionModel().selectFirst();
        });

        btnAdd.setOnMouseClicked(mouseEvent -> {
            Grade myGrade= new Grade();
            myGrade.setName(tbName.getText());
            myGrade.setSection(tbSection.getText());
            //TODO récupérer le prof seclectionné
            //myGrade.setTeacher(myTeacher);
            myGrade.setSchool(SchoolManagingApplication.getMySchool());

            GluonObservableObject<Grade> PotentialConnected = HttpRequests.addGrade(myGrade);
        });

        btnSup.setOnMouseClicked(mouseEvent -> {
            String id = lbId.getText();
            HttpRequests.deleteGrade(id);

        });
    }


    private void initializeSelection() {
        this.selectedGrade.addListener((observableValue, grade, grade1)-> {
            this.lbId.setText(String.valueOf(grade1.getId()));
            lbId.setVisible(true);
            this.tbName.setText(grade1.getName());
            this.tbSection.setText(grade1.getSection());
            //TODO le mettre sur le prof seclectionné
            this.cbListProf.getSelectionModel().select(grade.getTeacher());
        });
    }

    private void setEntete(){
        Label title = (Label) enTete.getChildren().get(1);
        title.setText("  Gestion des classes  ");
    }

}
