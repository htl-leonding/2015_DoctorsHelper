package entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import java.time.LocalDate;

/**
 * Created by Phips on 17.08.2015.
 */

@Entity
public class ParentMessage extends Document{

    String problem;
    String doctor;
    String doctorType;

    //region Constructor
    public ParentMessage() {

    }

    public ParentMessage(Student student, String message, LocalDate timeStamp, String problem) {
        super(student, message, timeStamp);
        this.problem = problem;
    }
    //endregion

    //region Properties
    @Basic
    public String getProblem() {
        return problem;
    }
    public void setProblem(String header) {
        this.problem = header;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    //endregion

    @Override
    public String toString() {
        return getTimeStamp() + ": Elternbrief";
    }
}
