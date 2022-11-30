package com.thales.schoolmanagingjfx.utils;
import com.gluonhq.connect.GluonObservableList;
import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import com.thales.schoolmanagingjfx.model.*;


public class HttpRequests {
    public static GluonObservableObject<User> tryLogin(User login) {

        RestClient client = RestClient.create()
                .method("POST")
                .host("http://localhost:8080/schoolmanagement/api/users/connect")
                .connectTimeout(10000)
                .readTimeout(1000)
                .dataString(JsonUtils.getStringJson(login))
                .contentType("application/json");

        return DataProvider.retrieveObject(client.createObjectDataReader(User.class));
    }

    public static GluonObservableList<School> getAllSchool() {
        RestClient school = RestClient.create()
                .method("GET")
                .host("http://localhost:8080/schoolmanagement/api/schools")
                .connectTimeout(10000)
                .readTimeout(1000);
        return DataProvider.retrieveList(school.createListDataReader(School.class));
    }

    public static GluonObservableList<Grade> getAllGrade() {
        RestClient grade = RestClient.create()
                .method("GET")
                .host("http://localhost:8080/schoolmanagement/api/grades")
                .connectTimeout(10000)
                .readTimeout(1000);
        return DataProvider.retrieveList(grade.createListDataReader(Grade.class));
    }

    public static GluonObservableObject<Grade> addGrade(Grade myGrade) {
        RestClient client = RestClient.create()
                .method("POST")
                .host("http://localhost:8080/schoolmanagement/api/grades/")
                .connectTimeout(10000)
                .readTimeout(1000)
                .dataString(JsonUtils.getStringJson(myGrade))
                .contentType("application/json");

        return DataProvider.retrieveObject(client.createObjectDataReader(Grade.class));
    }

    public static void deleteGrade(String id) {
        //TODO suppression grade
    }

    public static GluonObservableList<Course> getAllCourse() {
        RestClient course = RestClient.create()
                .method("GET")
                .host("http://localhost:8080/schoolmanagement/api/courses")
                .connectTimeout(10000)
                .readTimeout(1000);
        return DataProvider.retrieveList(course.createListDataReader(Course.class));
    }
    public static GluonObservableObject<Course> addCourse(Course myCourse) {
        RestClient client = RestClient.create()
                .method("POST")
                .host("http://localhost:8080/schoolmanagement/api/courses/")
                .connectTimeout(10000)
                .readTimeout(1000)
                .dataString(JsonUtils.getStringJson(myCourse))
                .contentType("application/json");
        return DataProvider.retrieveObject(client.createObjectDataReader(Course.class));
    }

    public static void deleteCourse(String id) {
        //TODO suppression course
    }

    public static GluonObservableList<Teacher> getAllTeacher() {
        RestClient teacher = RestClient.create()
                .method("GET")
                .host("http://localhost:8080/schoolmanagement/api/grades")
                .connectTimeout(10000)
                .readTimeout(1000);
        return DataProvider.retrieveList(teacher.createListDataReader(Teacher.class));
    }

    public static GluonObservableList<ClassRoom> getAllClassRoom(int schoolId) {
        RestClient classRoom = RestClient.create()
                .method("GET")
                .host("http://localhost:8080/schoolmanagement/api/classrooms/find/"+ schoolId)
                .connectTimeout(10000)
                .readTimeout(1000);
        return DataProvider.retrieveList(classRoom.createListDataReader(ClassRoom.class));

    }

    public static GluonObservableObject<ClassRoom> addClassRoom(ClassRoom myClassRoom) {
        RestClient client = RestClient.create()
                .method("POST")
                .host("http://localhost:8080/schoolmanagement/api/classrooms/")
                .connectTimeout(10000)
                .readTimeout(1000)
                .dataString(JsonUtils.getStringJson(myClassRoom))
                .contentType("application/json");
        return DataProvider.retrieveObject(client.createObjectDataReader(ClassRoom.class));
    }
}
