package com.example.user.shift.Requests;

public class Request {

    private String idClient;
    private String nameRequest;


    public String getIdClient() {
        return idClient;
    }

    public String getNameRequest() {
        return nameRequest;
    }

    public void setNameRequest(String nameRequest) {
        this.nameRequest = nameRequest;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }
}