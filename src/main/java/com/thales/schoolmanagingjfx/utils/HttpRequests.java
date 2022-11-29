package com.thales.reparcar4.utils;

import com.gluonhq.connect.GluonObservableList;
import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import com.thales.reparcar4.model.Individu;
import com.thales.reparcar4.model.Login;

public class HttpRequests {

    public static GluonObservableList<Individu> getAllIndividus(){

        RestClient client = RestClient.create()
                .method("GET")
                .host("http://localhost:8080/reparcar/api/individus")
                .connectTimeout(10000)
                .readTimeout(1000);

        return DataProvider.retrieveList(client.createListDataReader(Individu.class));
    }

    public static GluonObservableList<Individu> getSearchedIndividus(String nom, String role){

        String queryRole = (role ==null || role.trim().isEmpty()) ? "" : "role="+role;
        String queryNom = (nom == null || nom.trim().isEmpty()) ? "" : "nom="+nom;

        String sep = (!queryRole.isEmpty() && !queryNom.isEmpty()) ? "&": "";
        String query = (!queryRole.isEmpty() || !queryNom.isEmpty()) ? "?": "";
        query += queryRole + sep + queryNom;


        RestClient client = RestClient.create()
                .method("GET")
                .host("http://localhost:8080/reparcar/api/individus/search" + query)
                .connectTimeout(10000)
                .readTimeout(1000);

        return DataProvider.retrieveList(client.createListDataReader(Individu.class));
    }

    public static void deleteIndividu(int id){

        RestClient client = RestClient.create()
                .method("DELETE")
                .host("http://localhost:8080/reparcar/api/individus/" + String.valueOf(id))
                .connectTimeout(10000)
                .readTimeout(1000);

        DataProvider.retrieveObject(client.createObjectDataReader(Individu.class));
    }

    public static GluonObservableObject<Individu> tryLogin(Login login) {

        RestClient client = RestClient.create()
                .method("POST")
                .host("http://localhost:8080/reparcar/api/individus/connect")
                .connectTimeout(10000)
                .readTimeout(1000)
                .dataString(JsonUtils.getStringJson(login))
                .contentType("application/json");

        return DataProvider.retrieveObject(client.createObjectDataReader(Individu.class));
    }


}
