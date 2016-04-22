package entity;

import db.DBUtil;
import org.junit.*;
import org.junit.runners.MethodSorters;

import javax.persistence.EntityManager;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentTest {
/*
    EntityManager em;

    @Before
    public void setUp() throws Exception {
        em = DBUtil.getCustomEntityManager("MemoryDbPU");
    }

    @After
    public void tearDown() throws Exception {
        em.close();
    }

    @AfterClass
    public static void shutdown()  {
        System.out.println("DATABASE SHUTDOWN INITIATED");
        DBUtil.shutdown();
        try {
            // Memory-Datenbank wird mit drop=true geloescht
            DriverManager.getConnection("jdbc:derby://localhost:1527/memory:testdb;drop=true").close();
        } catch (SQLException ex) {
            if (ex.getErrorCode() != 45000) {
                System.out.println("Error occured: " + ex.getErrorCode() + ": " + ex.getLocalizedMessage());
            } else {
                System.out.println("db shutdown successfull");
            }
        }
    }
    
    @Test
    public void test001sanitizeTest() throws Exception {

        for (Map.Entry<String,Object> entry : em.getProperties().entrySet() ) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        assertThat(em.getProperties().get("javax.persistence.jdbc.url")
                ,is("jdbc:derby://localhost:1527/memory:testdb;create=true"));
        assertThat(em.getProperties(), hasEntry("javax.persistence.jdbc.url"
                , "jdbc:derby://localhost:1527/memory:testdb;create=true"));
        assertThat(em.getProperties().get("javax.persistence.jdbc.url").toString()
                , containsString("testdb"));
    }

    @Test
    public void test010studentWithoutPersisting() throws Exception {
        Student student = new Student(
                "Max",
                "Mustermann",
                "3AHIF",
                LocalDate.parse("01.01.2000", DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                "männlich"
        );
        assertThat(student.toString(), is("Max Mustermann"));
        assertThat(student.getBirthdate(),is(LocalDate.of(2000, Month.JANUARY,1)));
    }

    @Test
    public void test015persistStudent() throws Exception {
        Student student = new Student(
                "Max",
                "Mustermann",
                "3AHIF",
                LocalDate.parse("01.01.2000", DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                "männlich"
        );

        em.getTransaction().begin();
        em.persist(student);
        //em.flush();  // notwendig, da sonst keine Id garantiert (bei IDENTITY)
        em.getTransaction().commit();

        student = null;

        long countRows = em
                .createQuery("select count(s) from Student s", Long.class)
                .getSingleResult();

        student = em.find(Student.class, 1L);

        System.out.println(countRows);

        assertThat("Anzahl der Datensätze sollte 1 sein",countRows, is(1L));
        assertThat("Die studentId sollte 1 sein",student.getId(),is(1L));
        assertThat(student.getFirstname(), is("Max"));
        assertThat(student.getLastName(), is("Mustermann"));
        assertThat(student.getClazz(), is("3AHIF"));
        assertThat(student.getBirthdate(),is(LocalDate.of(2000, Month.JANUARY,1)));
    }

    @Test
    public void test020persistDisease() throws Exception {

        /*Student student = em.find(Student.class, 1L);

        Disease disease = new Disease(student, "Schnupfen", LocalDate.now(), 3, LocalDate.of(2016, 1, 12) "mit Schneuzen");
        em.getTransaction().begin();
        em.persist(disease);
        em.getTransaction().commit()

        disease = null;

        disease = em.find(Disease.class, 1L);

        System.out.println(disease);
        assertThat(disease.getStudent().getLastName(), is("Mustermann"));
    }
*/
}