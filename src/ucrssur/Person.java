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
 */
public class Person {

    private String email;
    private String gender;
    private String ID;
    private String name;
    private String residence;
 /**
  * Constructor vacio 
  */
    public Person() {
    }
   /**
    * constructores contiene todos los atributos necesarios para utilizarlos en 
    * el codigo. 
    * @param email
    * @param gender
    * @param ID
    * @param name
    * @param residence 
    */
    public Person(String email, String gender, String ID, String name, String residence) {
        this.email = email;
        this.gender = gender;
        this.ID = ID;
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
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
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
        return "Person{" + "email=" + email + ", gender=" + gender + ", ID=" + ID + ", name=" + name + ", residence=" + residence + '}';
    }
    

}
