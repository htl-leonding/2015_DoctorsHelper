/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.*;

/**
 *
 * @author wicki_000
 * edited by Steininger
 */

@Entity
public class Disease extends Document {
    private Integer level;
    private String beginDate;
    private String additionalInformation;

    //region Constructor
    public Disease() {

    }

    public Disease(Student student, String information, LocalDate timeStamp, Integer level, LocalDate beginDate, String additionalInformation) {
        super(student, information, timeStamp);
        this.level = level;
        this.beginDate = beginDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.additionalInformation = additionalInformation;
    }
    //endregion

    //region Properties
    @Basic
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }

    @Basic
    public String getBeginDate() {
        return beginDate;
    }
    @Transient
    public LocalDate getBeginDateAsDate() { return LocalDate.parse(beginDate, DateTimeFormatter.ofPattern("dd.MM.yyyy")); }
    public void setBeginDate(String date) {
        this.beginDate = date;
    }
    public void setBeginDateAsDate(LocalDate date) { this.beginDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")); }

    @Lob
    public String getAdditionalInformation() {
        return additionalInformation;
    }
    public void setAdditionalInformation(String additionalInformation) { this.additionalInformation = additionalInformation; }
    //endregion

    @Override
    public String toString() {
        return getTimeStamp() + ": Krankmeldung wegen " + getInformation() + " mit Schweregrad " + level;
    }
}
