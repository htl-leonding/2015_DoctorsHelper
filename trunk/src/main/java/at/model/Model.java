package at.model;

import at.db.DBUtil;
import at.entity.Checkup;
import at.entity.Document;
import at.entity.Student;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import at.entity.Trip;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;

/**
 *
 * @author wicki_000
 * edited by Lukas Wickenhauser
 */
public class Model extends Observable {

    private static Model instance;

    private ObservableList<Student> studentList;
    private ObservableList<Document> studentDocuments;
    private ObservableList<Checkup> studentCheckups;
    private ObservableList<String> clazz;
    private EntityManager em;
    
    private Model(){
        studentList = FXCollections.observableArrayList();
        clazz = FXCollections.observableArrayList();
        studentDocuments = FXCollections.observableArrayList();
        studentCheckups = FXCollections.observableArrayList();
        em = DBUtil.getEntityManager();
    }

    public static Model getModel(){
        if (instance == null){
            instance = new Model();
        }
        return instance;
    }

    //region ListMethods

    public ObservableList<Student> getStudentList() {
        return studentList;
    }

    public void sortList(String filter){
        List<Student> help = em.createNamedQuery("GetStudents").getResultList();
        studentList.clear();

        for (Student i : help) {
            if (i.getFirstname().contains(filter) || i.getLastName().contains(filter)) {
                studentList.add(i);
            }
        }
    }

    public void sortList(String filter, String clazz) {
        List<Student> help = em.createNamedQuery("GetStudents").getResultList();
        studentList.clear();

        boolean allStudents = false;
        if (clazz == null)
            clazz = "Alle";
        if (clazz.equals("Alle"))
            allStudents = true;
        for (Student i : help) {
            if ((i.getFirstname().toLowerCase().contains(filter.toLowerCase()) || i.getLastName().toLowerCase().contains(filter.toLowerCase())) && (i.getClazz().equals(clazz) || allStudents)) {
                studentList.add(i);
            }
        }
    }

    public void updateList() {
        List<Student> help = em.createNamedQuery("GetStudents").getResultList();
        if (!studentList.equals(help)) {
            studentList.clear();
            studentList.addAll(help);
        }
        setChanged();
        countObservers();
        notifyObservers();
    }
    //endregion

    //region StudentMethods
    public void addStudent(Student s){
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
        updateList();
    }

    public void deleteStudent(Student s){
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();

        em.createNamedQuery("DeleteAllDocumentsByStudentID").setParameter("STUDENT_ID", s.getId()).executeUpdate();

        em.remove(s);
        em.getTransaction().commit();
        updateList();
    }

    public List<Student> getStudentByClass(String clazz){
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();

        return (List<Student>) em.createNamedQuery("GetStudentsByClass").setParameter("CLAZZ", clazz).getResultList();
    }

    public void mergeStudent(Student s){
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();

        em.merge(s);
        em.getTransaction().commit();
        updateList();
    }

    public void updateStudent(Student oldS, Student newS) {
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();

        Student student = em.find(Student.class, oldS.getId());
        student.setFirstname(newS.getFirstname());
        student.setLastName(newS.getLastName());
        student.setClazz(newS.getClazz());
        student.setBirthdate(newS.getBirthdate());
        student.setSex(newS.getSex());

        em.merge(student);
        em.getTransaction().commit();
        updateList();
    }


    public ObservableList<String> getAllClasses(){
        LinkedList<String> l = new LinkedList<>(em.createNamedQuery("GetAllClasses").getResultList());
        clazz.clear();

        l.addFirst("Alle");
        for (String s : l) {
            clazz.add(s);
        }

        return clazz;
    }

    public void insertStudentFromCSV(File f){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f.getAbsolutePath()), StandardCharsets.UTF_8));
            String[] columns;
            String line;
            final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            boolean update=false;

            while((line = br.readLine()) != null){
                columns = line.split(";");
                LocalDate birthDate;
                try{
                    birthDate = LocalDate.parse(columns[3], dtf);
                } catch (Exception e){
                    birthDate = LocalDate.parse("01.01.1970", dtf);
                }

                update=false;
                Student s = new Student(
                        columns[0],    // firstName
                        columns[1],    // givenName
                        columns[2],    // clazz
                        birthDate,
                        columns[4].equals("FEMALE") ? "FEMALE" : "MALE");
                for (Student student: getAllStudents()) {

                    if(s.getFirstname().equals(student.getFirstname())&&
                            s.getLastName().equals(student.getLastName())&&
                            s.getBirthdate().equals(student.getBirthdate())&&
                            s.getSex().equals(student.getSex()))
                    {
                        Student updateStudent=new Student(student.getFirstname(),student.getLastName(),
                                s.getClazz(),student.getBirthdate(),student.getSex(),student.getAddress(),student.getPhone());
                        update=true;
                        updateStudent(student,updateStudent);

                    }

                }
                if(!update)
                addStudent(s);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //endregion

    //region DocumentMethods
    public void addDocument(Document document){
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();

        if (document.getId() == null)
            em.persist(document);
        else
            em.merge(document);

        em.getTransaction().commit();

        updateList();
    }

    public ObservableList getStudentDocumentList() {
        return studentDocuments;
    }

    public ObservableList getStudentCheckups(){return studentCheckups;}

    public void updateStudentCheckups(){

        ObservableList<Checkup> checkups = FXCollections.observableArrayList();

        for (Document d: studentDocuments ) {
            if (d instanceof Checkup){
                checkups.add((Checkup)d);
            }
        }

        studentCheckups.clear();
        studentCheckups.addAll( checkups);
    }

    public void updateStudentDocuments(long student) {
        studentDocuments.clear();
        studentDocuments.addAll(em.createNamedQuery("getDocumentsByStudentID").setParameter("STUDENT_ID", student).getResultList());
        updateStudentCheckups();
    }
    //endregion

    public List<Trip> getAllTrips(){
        return em.createNamedQuery("GetTrips").getResultList();
    }

    public List<Trip> getTripByClass(String filter){
        return em.createNamedQuery("GetTripByClass").setParameter("c",filter).getResultList();
    }

    public void addTrip(Trip t){
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
    }

    public void updateTrip( Trip actTrip){
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(actTrip);
        em.getTransaction().commit();
    }

    public List<Student> getAllStudents(){
        return em.createNamedQuery("GetStudents",Student.class).getResultList();

    }

    public double calcBmi(Double height, Double weight) {
        if (height == 0 || weight == 0)
            return 0;
        else return Math.round(weight / ((height/100)*(height/100)) * Math.pow(10, 2)) / Math.pow(10, 2);
    }
}
