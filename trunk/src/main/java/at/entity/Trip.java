package at.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Lukas on 31.03.2016.
 */


@Entity
@NamedQueries({
        @NamedQuery(name = "GetTrips", query = "select t from Trip t"),
        @NamedQuery(name = "GetTripByClass", query = "select t from Trip t where t.clazz=:c")
})
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinTable(name="TRIP_ABSENTSTUDENTS")
    private List<Student> absentStudents;
    @JoinTable(name="TRIP_SELECTEDSTUDENTS")
    private List<Student> unselectedStudents;
    private List<Student> selectedStudents;

    private String name;
    private String clazz;

    public Trip (List<Student> absent, List<Student> unselected, List<Student> selected, String clazz, String name){
        absentStudents=absent;
        unselectedStudents=unselected;
        selectedStudents=selected;
        this.clazz=clazz;
        this.name = name;
    }

    public Trip() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Student> getAbsentStudents() {
        return absentStudents;
    }

    public void setAbsentStudents(List<Student> absentStudents) {
        this.absentStudents = absentStudents;
    }

    public List<Student> getUnselectedStudents() {
        return unselectedStudents;
    }

    public void setUnselectedStudents(List<Student> unselectedStudents) {
        this.unselectedStudents = unselectedStudents;
    }

    public List<Student> getSelectedStudents() {
        return selectedStudents;
    }

    public void setSelectedStudents(List<Student> snselectedStudents) {
        this.selectedStudents = snselectedStudents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return clazz+": "+name;
    }
}
