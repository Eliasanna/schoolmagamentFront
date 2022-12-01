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

    public static GluonObservableList<User> getAllUser() {
        RestClient school = RestClient.create()
                .method("GET")
                .host("http://localhost:8080/schoolmanagement/api/schools")
                .connectTimeout(10000)
                .readTimeout(1000);
        return DataProvider.retrieveList(school.createListDataReader(User.class));
    }

    public static GluonObservableObject<User> addUser(User myUser) {
        RestClient client = RestClient.create()
                .method("POST")
                .host("http://localhost:8080/schoolmanagement/api/users/")
                .connectTimeout(10000)
                .readTimeout(1000)
                .dataString(JsonUtils.getStringJson(myUser))
                .contentType("application/json");

        return DataProvider.retrieveObject(client.createObjectDataReader(User.class));
    }

    public static void deleteUser(String id) {
        RestClient client = RestClient.create()
                .method("DELETE")
                .host("http://localhost:8080/schoolmanagement/api/users/" + String.valueOf(id))
                .connectTimeout(10000)
                .readTimeout(1000);
        DataProvider.retrieveObject(client.createObjectDataReader(User.class));
    }

    public static GluonObservableObject<School> addSchool(School mySchool) {
        RestClient client = RestClient.create()
                .method("POST")
                .host("http://localhost:8080/schoolmanagement/api/schools/")
                .connectTimeout(10000)
                .readTimeout(1000)
                .dataString(JsonUtils.getStringJson(mySchool))
                .contentType("application/json");

        return DataProvider.retrieveObject(client.createObjectDataReader(School.class));
    }

    public static GluonObservableList<Grade> getAllGrade(int schoolId) {
        RestClient grade = RestClient.create()
                .method("GET")
                .host("http://localhost:8080/schoolmanagement/api/grades/find/"+ schoolId)
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
        RestClient client = RestClient.create()
                .method("DELETE")
                .host("http://localhost:8080/schoolmanagement/api/grades/" + String.valueOf(id))
                .connectTimeout(10000)
                .readTimeout(1000);
        DataProvider.retrieveObject(client.createObjectDataReader(Grade.class));
    }

    public static GluonObservableList<Course> getAllCourse(int schoolId) {
        RestClient course = RestClient.create()
                .method("GET")
                .host("http://localhost:8080/schoolmanagement/api/courses/find/"+ schoolId)
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
        RestClient client = RestClient.create()
                .method("DELETE")
                .host("http://localhost:8080/schoolmanagement/api/courses/" + String.valueOf(id))
                .connectTimeout(10000)
                .readTimeout(1000);
        DataProvider.retrieveObject(client.createObjectDataReader(Course.class));
    }

    public static GluonObservableList<Teacher> getAllTeacher(int schoolId) {
        RestClient teacher = RestClient.create()
                .method("GET")
                .host("http://localhost:8080/schoolmanagement/api/teachers/find/"+ schoolId)
                .connectTimeout(10000)
                .readTimeout(1000);
        return DataProvider.retrieveList(teacher.createListDataReader(Teacher.class));
    }
    public static GluonObservableObject<Teacher> addTeacher(Teacher myTeacher) {
        RestClient client = RestClient.create()
                .method("POST")
                .host("http://localhost:8080/schoolmanagement/api/teachers/")
                .connectTimeout(10000)
                .readTimeout(1000)
                .dataString(JsonUtils.getStringJson(myTeacher))
                .contentType("application/json");
        return DataProvider.retrieveObject(client.createObjectDataReader(Teacher.class));
    }

    public static void deleteTeacher(String id) {
        RestClient client = RestClient.create()
                .method("DELETE")
                .host("http://localhost:8080/schoolmanagement/api/teachers/" + String.valueOf(id))
                .connectTimeout(10000)
                .readTimeout(1000);
        DataProvider.retrieveObject(client.createObjectDataReader(Teacher.class));
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
    public static void deleteClassRoom(String id) {
        RestClient client = RestClient.create()
                .method("DELETE")
                .host("http://localhost:8080/schoolmanagement/api/classrooms/" + String.valueOf(id))
                .connectTimeout(10000)
                .readTimeout(1000);
        DataProvider.retrieveObject(client.createObjectDataReader(ClassRoom.class));
    }

    public static GluonObservableObject<Address> addAddress(Address adresse) {
        RestClient client = RestClient.create()
                .method("POST")
                .host("http://localhost:8080/schoolmanagement/api/addresses/")
                .connectTimeout(10000)
                .readTimeout(1000)
                .dataString(JsonUtils.getStringJson(adresse))
                .contentType("application/json");
        return DataProvider.retrieveObject(client.createObjectDataReader(Address.class));

    }
}
