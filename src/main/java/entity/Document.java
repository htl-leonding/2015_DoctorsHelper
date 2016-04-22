/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.eclipse.persistence.jpa.config.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Lukas
 * revived by Steininger
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "getDocumentsByStudentID", query = "select d from Document d where d.student.id=:STUDENT_ID ORDER BY d.timeStamp DESC"),
    @NamedQuery(name="DeleteAllDocumentsByStudentID", query="Delete From Document where student.id=:STUDENT_ID"),
        @NamedQuery(name = "GetStudentsByClass", query = "select s from Student s where s.clazz = :CLAZZ")
})
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private StringProperty information = new SimpleStringProperty();
    private StringProperty timeStamp = new SimpleStringProperty();
    private Student student;

    //region Constructor
    public Document() { }


    public Document(Student student, String information, LocalDate timeStamp) {
        this.student = student;
        this.information.set(information);
        this.timeStamp.set(timeStamp.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    public Document(Student student, String information, String timeStamp) {
        this.student = student;
        this.information.set(information);
        this.timeStamp.set(timeStamp);
    }
    //endregion

    //region Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() { return id; }
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne (cascade = CascadeType.REMOVE)
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }

    @Lob
    public String getInformation() {
        return information.get();
    }
    public StringProperty informationProperty() {
        return information;
    }
    public void setInformation(String information) {
        this.information.set(information);
    }

    @Basic
    public String getTimeStamp() {
        return timeStamp.get();
    }
    public StringProperty dateProperty() {
        return timeStamp;
    }
    public void setTimeStamp(String date) {
        this.timeStamp.set(date);
    }
    public void setTimeStamp(LocalDate date) { this.timeStamp.set(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))); }
    //endregion
}
