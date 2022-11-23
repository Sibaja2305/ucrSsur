/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucrssur;

/**
 * Esta clase contiene los atributos necesario que tiene el registro de los
 * estudiantes de la UCRSSUR(.txt)
 * @author Kevin Sibaja
 * @author Yordany Navarro Hernandez
 * @author Jonathan Alfaro
 */
public class Student {

    private String email;
    private String gender;
    private String IdStudent;
    private String name;
    private String residence;

    public Student() {
    }

    public Student(String IdStudent, String name,String gender,String residence, String email) {
        this.email = email;
        this.gender = gender;
        this.IdStudent = IdStudent;
        this.name = name;
        this.residence = residence;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the IdStudent
     */
    public String getIdStudent() {
        return IdStudent;
    }

    /**
     * @param IdStudent the IdStudent to set
     */
    public void setIdStudent(String IdStudent) {
        this.IdStudent = IdStudent;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the residence
     */
    public String getResidence() {
        return residence;
    }

    /**
     * @param residence the residence to set
     */
    public void setResidence(String residence) {
        this.residence = residence;
    }

    @Override
    public String toString() {
        return "Student{" + "email=" + email + ", gender=" + gender + ", IdStudent=" + IdStudent + ", name=" + name + ", residence=" + residence + '}';
    }
  
}