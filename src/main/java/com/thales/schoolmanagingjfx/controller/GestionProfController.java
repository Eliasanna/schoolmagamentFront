package com.thales.schoolmanagingjfx.controller;

import com.gluonhq.connect.GluonObservableList;
import com.gluonhq.connect.GluonObservableObject;
import com.thales.schoolmanagingjfx.SchoolManagingApplication;
import com.thales.schoolmanagingjfx.model.*;
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
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class GestionProfController implements Initializable {
    @FXML
    public HBox enTete;
    @FXML
    public TableView tbView;
    @FXML
    public Label lbId;
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

    private List<Course> listCourseTeacher = new ArrayList<Course>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEntete();
        initializeTableView();
        initializeButtons();
        initializecb1();
        initializecb2();
        initializeSelection();
    }


    private void initializeSelection() {
        this.selectedTeacher.addListener((observableValue, teacher, teacher1)-> {
            this.lbId.setText(String.valueOf(teacher1.getId()));
            lbId.setVisible(true);
            this.tbFName.setText(teacher1.getFirstName());
            this.tbLName.setText(teacher1.getLastName());
            //récupere date que l'on mets ne localdate
            Date date = teacher1.getBirthdate();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            this.dpBirthDate.setValue(localDate);

            //affiche les 3 matieres

        });
    }

    private void initializeButtons() {
        btnClear.setOnMouseClicked(mouseEvent -> {

        });

        btnAdd.setOnMouseClicked(mouseEvent -> {

            Teacher myTeacher = new Teacher();
            myTeacher.setFirstName(tbFName.getText());
            myTeacher.setLastName(tbLName.getText());
            //recupere une localdate et la transforme en date
            LocalDate ldate = dpBirthDate.getValue();
            Date date = Date.from(ldate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            myTeacher.setBirthdate(date);
            //ajout des cours cours
            Course c1 = (Course) cbCourse1.valueProperty().getValue();
            Course c2 = (Course) cbCourse2.valueProperty().getValue();
            Course c3 = (Course) cbCourse3.valueProperty().getValue();
            listCourseTeacher.add(c1);
            listCourseTeacher.add(c2);
            listCourseTeacher.add(c3);
            myTeacher.setCourses(listCourseTeacher);
            myTeacher.setGrade((Grade) cbMainCourse.valueProperty().getValue());
            GluonObservableObject<Teacher> potentialConnected = HttpRequests.addTeacher(myTeacher);
        });

        btnSup.setOnMouseClicked(mouseEvent -> {
            String id = lbId.getText();
            HttpRequests.deleteTeacher(id);
        });

    }

    private void initializecb1() {

        //recupere liste des courses
        GluonObservableList<Course> listCourse = HttpRequests.getAllCourse(SchoolManagingApplication.getMySchool().getId());
        listCourse.setOnSucceeded(connectStateEvent -> {
            this.cbCourse1.setItems(listCourse);
            this.cbCourse2.setItems(listCourse);
            this.cbCourse3.setItems(listCourse);
            Course courseVide = new Course();
            listCourse.add(courseVide);
        });

        Callback<ListView<Course>, ListCell<Course>> roomCellFactory = new Callback<ListView<Course>, ListCell<Course>>() {
            @Override
            public ListCell<Course> call(ListView<Course> l) {
                return new ListCell<Course>() {
                    @Override
                    protected void updateItem(Course item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getId()+" "+item.getName());
                        }
                    }
                };
            }
        };

        this.cbCourse1.setButtonCell(roomCellFactory.call(null));
        this.cbCourse1.setCellFactory(roomCellFactory);

        this.cbCourse2.setButtonCell(roomCellFactory.call(null));
        this.cbCourse2.setCellFactory(roomCellFactory);

        this.cbCourse3.setButtonCell(roomCellFactory.call(null));
        this.cbCourse3.setCellFactory(roomCellFactory);

        //recupere liste des courses
        GluonObservableList<Grade> listGrade = HttpRequests.getAllGrade(SchoolManagingApplication.getMySchool().getId());
        listCourse.setOnSucceeded(connectStateEvent -> {
            this.cbMainCourse.setItems(listGrade);
            Grade gVide = new Grade();
            listGrade.add(gVide);
        });
    }

    private void initializecb2() {
        //recupere liste des courses
        GluonObservableList<Grade> listGrade = HttpRequests.getAllGrade(SchoolManagingApplication.getMySchool().getId());
        listGrade.setOnSucceeded(connectStateEvent -> {
            this.cbMainCourse.setItems(listGrade);
            Grade gVide = new Grade();
            listGrade.add(gVide);
        });

        Callback<ListView<Grade>, ListCell<Grade>> roomCellFactory = new Callback<ListView<Grade>, ListCell<Grade>>() {
            @Override
            public ListCell<Grade> call(ListView<Grade> l) {
                return new ListCell<Grade>() {
                    @Override
                    protected void updateItem(Grade item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getId()+" "+item.getName());
                        }
                    }
                };
            }
        };

        this.cbCourse1.setButtonCell(roomCellFactory.call(null));
        this.cbCourse1.setCellFactory(roomCellFactory);



    }

    private void initializeTableView() {
        TableView<Teacher> tbView = new TableView<>();
        TableColumn<Teacher, String> idProfCol = new TableColumn<>("Identifiant");
        TableColumn<Teacher, String> fnameProfCol = new TableColumn<>("Nom");
        TableColumn<Teacher, String> lnameProfCol = new TableColumn<>("Prénom");
        // TableColumn<Teacher, Date> dateProfCol = new TableColumn<>("Date de naissance");
        //TableColumn<Teacher, String> profMatCol = new TableColumn<>("Matières");

        tbView.getColumns().addAll(idProfCol,fnameProfCol,lnameProfCol);

        idProfCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fnameProfCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lnameProfCol.setCellValueFactory(new PropertyValueFactory<>("section"));
        //dateProfCol.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        //TODO ne fonctionnera pas
        //profMatCol.setCellValueFactory(new PropertyValueFactory<>("matiere"));

        GluonObservableList<Teacher> gotList = HttpRequests.getAllTeacher(SchoolManagingApplication.getMySchool().getId());
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
