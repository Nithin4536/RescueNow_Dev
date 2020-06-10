package com.example.rescuenow_dev.patient.consult_doctors;

public class DoctorObject {

    private String id;
    private String name;
    private String gender;
    private String role;
    private String email;
    private String age;
    private String hospital_name;
    private String hospital_id;
    private String speciality;

    public DoctorObject(String id, String name, String gender, String age, String email, String speciality, String hospital_id,String hospital_name) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.age = age;
        this.hospital_name = hospital_name;
        this.hospital_id = hospital_id;
        this.speciality = speciality;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }


    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public DoctorObject(String id, String name, String hospital_name, String speciality) {
        this.id = id;
        this.name = name;
        this.hospital_name = hospital_name;
        this.speciality = speciality;
    }
}
