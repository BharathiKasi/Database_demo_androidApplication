package com.myservices.databasedemo;

import java.io.Serializable;

public class DataHelperClass implements Serializable {
    String name,surname,marks,id;

    public String getId(){
        return id;
    }
    public String getName(){

        return name;
    }

    public String getSurname(){
        return surname;
    }
    public  String getMarks(){
        return marks;
    }

    public void setName(String name){
        this.name=name;
    }
    public void setSurname(String surname){
        this.surname=surname;
    }
    public void setMarks(String marks){
        this.marks=marks;
    }
    public void setId(String id){
        this.id=id;
    }
}
