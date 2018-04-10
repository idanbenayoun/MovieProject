package com.example.idan.movieproject;

public class MovieInfo {

    private int id;
    private String subject;//title
    private String body;
    private String url;
    private int orderNumber;//the id number of the movie API


    public MovieInfo(){}

    public MovieInfo(int id, String subject , String body , String url){
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.url = url;
    }


    public MovieInfo(String subject , String body , String url){
        this.subject = subject;
        this.body = body;
        this.url = url;
    }

    public MovieInfo(String subject , String body){
        this.subject = subject;
        this.body = body;

    }

    public MovieInfo(String subject){
        this.subject = subject;
    }

    public MovieInfo(int id, String subject , String body , String url, int orderNumber){
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.url = url;
        this.orderNumber = orderNumber;
    }

    public MovieInfo(int id, String subject , String body , String url, int orderNumber, int watched){
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.url = url;
        this.orderNumber = orderNumber;
    }

    public MovieInfo(String subject , String body , String url, int orderNumber){
        this.subject = subject;
        this.body = body;
        this.url = url;
        this.orderNumber = orderNumber;
    }

    public MovieInfo(String subject , String body, int orderNumber){
        this.subject = subject;
        this.body = body;
        this.orderNumber = orderNumber;
    }

    public MovieInfo(String subject, int orderNumber){

        this.subject = subject;
        this.orderNumber = orderNumber;

    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}


