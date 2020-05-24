package com.example.rescuenow_dev.diseases;

public class Diseases {

    private String d_id;
    private String d_name;
    private String d_precautions;
    private String d_symptoms;
    private String d_description;

    public Diseases(String d_id, String d_name, String d_description,  String d_precautions, String d_symptoms) {
        this.d_id = d_id;
        this.d_name = d_name;
        this.d_precautions = d_precautions;
        this.d_symptoms = d_symptoms;
        this.d_description = d_description;
    }

    public Diseases() {
    }

    public String getD_description() {
        return d_description;
    }

    public void setD_description(String d_description) {
        this.d_description = d_description;
    }

    public Diseases(String d_name, String d_symptoms, String d_description) {
        this.d_name = d_name;
        this.d_symptoms = d_symptoms;
        this.d_description = d_description;
    }

    public Diseases(String d_name, String d_description, String d_symptoms, String d_precautions) {
        this.d_name = d_name;
        this.d_precautions = d_precautions;
        this.d_symptoms = d_symptoms;
        this.d_description = d_description;
    }

    public String getD_id() {
        return d_id;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public String getD_precautions() {
        return d_precautions;
    }

    public void setD_precautions(String d_precautions) {
        this.d_precautions = d_precautions;
    }

    public String getD_symptoms() {
        return d_symptoms;
    }

    public void setD_symptoms(String d_symptoms) {
        this.d_symptoms = d_symptoms;
    }
}
