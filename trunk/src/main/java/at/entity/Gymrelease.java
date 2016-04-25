package at.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Phips on 29.06.2015.
 */

@Entity
public class Gymrelease extends Document {
    private String beginDate;
    private String endDate;
    private String reason;

    //region Constructors
    public Gymrelease() {

    }

    public Gymrelease(Student student, String information, LocalDate timeStamp, String begin, String end, String reason) {
        super(student, information, timeStamp);
        this.reason = reason;
        this.beginDate = begin;
        this.endDate = end;
    }

    public Gymrelease(Student student, String information, LocalDate timeStamp, LocalDate begin, LocalDate end, String reason) {
        super(student, information, timeStamp);
        this.reason = reason;
        this.beginDate = begin.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.endDate = end.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    //endregion

    //region Properties
    @Basic
    public String getBeginDate() { return beginDate; }
    @Transient
    public LocalDate getBeginDateAsDate() { return  LocalDate.parse(beginDate, DateTimeFormatter.ofPattern("dd.MM.yyyy")); }
    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }
    public void setBeginDate(LocalDate beginDate) { this.beginDate = beginDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")); }

    @Basic
    public String getEndDate() {
        return endDate;
    }
    @Transient
    public LocalDate getEndDateAsDate() { return  LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd.MM.yyyy")); }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")); }

    @Basic
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    //endregion

    @Override
    public String toString() {
        return getTimeStamp() + ": Turnbefreiung wegen " + reason;
    }
}
