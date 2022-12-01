package com.thales.schoolmanagingjfx.controller;

import com.gluonhq.connect.GluonObservableList;
import com.gluonhq.connect.GluonObservableObject;
import com.thales.schoolmanagingjfx.SchoolManagingApplication;
import com.thales.schoolmanagingjfx.model.Course;
import com.thales.schoolmanagingjfx.model.Grade;
import com.thales.schoolmanagingjfx.model.School;
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
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class GestionMatiereController implements Initializable {
    @FXML
    public TableView tbView;
    @FXML
    public Label lbId;
    @FXML
    public TextField tbName;
    @FXML
    public ColorPicker cpColor;
    @FXML
    public Button btnClear;
    @FXML
    public Button btnAdd;
    @FXML
    public Button btnSup;
    @FXML
    public HBox enTete;
    private School school = SchoolManagingApplication.getMySchool();

    private ObjectProperty<Course> selectedCourse = new SimpleObjectProperty<Course>();

    private ObservableList<Course> courses;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEntete();
        lbId.setVisible(false);
        initializeTableView();
        initializeButtons();
        initializeSelection();
        SchoolManagingApplication.mySchoolProperty().addListener((observableValue, school, t1) -> {
            this.school = SchoolManagingApplication.getMySchool();
            lbId.setVisible(false);
            chargeListe();
            initializeSelection();
        });
    }

    private void initializeButtons() {

        btnClear.setOnMouseClicked(mouseEvent -> {
            lbId.setVisible(false);
            tbName.clear();
            cpColor.setValue(Color.WHITE);
        });

        btnAdd.setOnMouseClicked(mouseEvent -> {
            Course myCourse = new Course();
            myCourse.setColor(String.valueOf(cpColor.getValue()));
            myCourse.setName(tbName.getText());
            myCourse.setSchool(school);

            GluonObservableObject<Course> potentialConnected = HttpRequests.addCourse(myCourse);
            potentialConnected.setOnFailed(connectStateEvent -> {
                courses.add(myCourse);
                chargeListe();
            });
            potentialConnected.setOnSucceeded(connectStateEvent -> {

            });
        });

        btnSup.setOnMouseClicked(mouseEvent -> {
            String id = lbId.getText();
            HttpRequests.deleteCourse(id);
            chargeListe();
        });
    }

    private void initializeTableView() {
        TableColumn<Course, String> idCourseCol = new TableColumn<>("Identifiant");
        TableColumn<Course, String> nameCourseCol = new TableColumn<>("Nom");
        TableColumn<Course, String> colorCourseCol = new TableColumn<>("Couleur");

        tbView.getColumns().addAll(idCourseCol,nameCourseCol,colorCourseCol);
        idCourseCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCourseCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        colorCourseCol.setCellValueFactory(new PropertyValueFactory<>("color"));

        chargeListe();

        tbView.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            selectedCourse.setValue((Course) t1);
        });
    }

    private void chargeListe() {
        GluonObservableList<Course> gotList = HttpRequests.getAllCourse(school.getId());
        gotList.setOnSucceeded(connectStateEvent -> {
            this.courses = FXCollections.observableArrayList(gotList);
            tbView.setItems(this.courses);
        });
    }

    private void initializeSelection() {
        this.selectedCourse.addListener((observableValue, course, course1)-> {
            if(course1==null){
                lbId.setText("");
                lbId.setVisible(false);
                this.tbName.clear();
                cpColor.setValue(Color.WHITE);
            }
            else{
            this.lbId.setText(String.valueOf(course1.getId()));
            lbId.setVisible(true);
            this.tbName.setText(course1.getName());
            String color = course1.getColor();
            cpColor.setValue(Color.valueOf(color));
            }
        });
    }
    private void setEntete(){
        Label title = (Label) enTete.getChildren().get(1);
        title.setText("  Gestion des matières  ");
    }
}
