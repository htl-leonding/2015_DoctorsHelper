/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

//Steininger whole class revived

@Entity
@NamedQueries({
        @NamedQuery(name = "GetStudents", query = "select s from Student s"),
        @NamedQuery(name = "GetAllClasses", query = "select distinct s.clazz from Student s")
})
public class Student implements Serializable, Comparable<Student> {

    private static final long serialVersionUID = 1L;

    private LongProperty id = new SimpleLongProperty();
    private StringProperty firstname = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty clazz = new SimpleStringProperty();
    private StringProperty birthdate = new SimpleStringProperty();  //Steininger made type of birthdate to StringProperty
    private StringProperty sex = new SimpleStringProperty();        //Steininger made type of sex to StringProperty

    private StringProperty chronic = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty phone = new SimpleStringProperty();

    //region Constructors
    public Student() {
    }

    public Student(String firstname, String lastName, String clazz, LocalDate birthdate, String sex) {
        setFirstname(firstname);
        setLastName(lastName);
        setClazz(clazz);
        setBirthdate(birthdate);
        setSex(sex);
    }

    public Student(Long id, String firstname, String lastName, String clazz, LocalDate birthdate, String sex) {
        setId(id);
        setFirstname(firstname);
        setLastName(lastName);
        setClazz(clazz);
        setBirthdate(birthdate);
        setSex(sex);
    }

    public Student(String firstname, String lastName, String clazz, LocalDate birthdate, String sex, String address, String phone) {
        setFirstname(firstname);
        setLastName(lastName);
        setClazz(clazz);
        setBirthdate(birthdate);
        setSex(sex);
        setAddress(address);
        setPhone(phone);
    }
    //endregion

    //region Getter and Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id.get();
    }
    public LongProperty idProperty() {
        return id;
    }
    public void setId(long id) {
        this.id.set(id);
    }

    public String getFirstname() {
        return firstname.get();
    }
    public StringProperty firstnameProperty() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public String getLastName() {
        return lastName.get();
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getClazz() {
        return clazz.get();
    }
    public StringProperty clazzProperty() {
        return clazz;
    }
    public void setClazz(String clazz) {
        this.clazz.set(clazz);
    }

    public LocalDate getBirthdate() { return LocalDate.parse(birthdateProperty().get(), DateTimeFormatter.ofPattern("dd.MM.yyyy")); }
    public StringProperty birthdateProperty() {
        return birthdate;
    }
    public void setBirthdate(LocalDate birthdate) { this.birthdate.set(birthdate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))); }

    public String getSex() {
        return sex.get();
    }
    public StringProperty sexProperty() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex.set(sex);
    }



    public String getChronic() {
        return chronic.get();
    }
    public StringProperty chronicProperty() {
        return chronic;
    }
    public void setChronic(String chronic) {
        this.chronic.set(chronic);
    }

    public String getAddress() {
        return address.get();
    }
    public StringProperty addressProperty() {
        return address;
    }
    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getPhone() {
        return phone.get();
    }
    public StringProperty phoneProperty() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    //endregion

    @Override
    public String toString() {
        return getLastName() + " " + getFirstname();
    }

    @Override
    public int compareTo(Student o) {
        return getLastName().compareTo(o.getLastName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;

        Student student = (Student) o;

        if (birthdate != null ? !birthdate.equals(student.birthdate) : student.birthdate != null) return false;
        if (clazz != null ? !clazz.equals(student.clazz) : student.clazz != null) return false;
        if (firstname != null ? !firstname.equals(student.firstname) : student.firstname != null) return false;
        if (id != null ? !id.equals(student.id) : student.id != null) return false;
        if (lastName != null ? !lastName.equals(student.lastName) : student.lastName != null) return false;
        if (sex != null ? !sex.equals(student.sex) : student.sex != null) return false;
        if (address != null ? !address.equals(student.address) : student.address != null) return false;
        if (phone != null ? !phone.equals(student.phone) : student.phone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (clazz != null ? clazz.hashCode() : 0);
        result = 31 * result + (birthdate != null ? birthdate.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }
}