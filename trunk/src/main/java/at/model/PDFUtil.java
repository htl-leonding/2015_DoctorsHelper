package at.model;

import at.entity.*;
import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Phips on 17.08.2015.
 */
public class PDFUtil {

    private final static String PDF_ROOT_DIRECTORY = System.getProperty("user.home") + File.separator + "Desktop" + File.separator;
    private final static Font HEADER_FONT = FontFactory.getFont("Arial", 17, Font.BOLD);
    private final static Font TEXT_FONT = FontFactory.getFont("Arial", 12, Font.NORMAL);
    private final static Font CAPTION_FONT = FontFactory.getFont("Arial", 12, Font.BOLD);


    public static void createPDFLetterFromString(String text, String pdfName) throws DocumentException, FileNotFoundException {
        Document document = new Document();


        PdfWriter.getInstance(document, new FileOutputStream(PDF_ROOT_DIRECTORY + pdfName + ".pdf"));

        //PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\tob\\asd.pdf"));

        document.open();

        document.addAuthor("created with DoctorsHelper");

        document.add(new Paragraph(pdfName, HEADER_FONT));
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph(text, TEXT_FONT));



        document.close();
    }

    public static void createGeneralReleasePDF(Student student, String subject, String from,
                                               String to, String notes) throws FileNotFoundException, DocumentException {
        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream((PDF_ROOT_DIRECTORY + student.getLastName() + "_" + "Befreiung.pdf")));

        document.open();


        document.addAuthor("created with DoctorsHelper");

        Paragraph p = new Paragraph();

        p.add(new Phrase());

        p.add(new Phrase("Befreiung", HEADER_FONT));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Klasse: ", CAPTION_FONT));
        p.add(new Phrase(student.getClazz(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Name: ", CAPTION_FONT));
        p.add(new Phrase(student.getFirstname() + " " + student.getLastName(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Befreiung", CAPTION_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Gegenstand: ", CAPTION_FONT));
        p.add(new Phrase(subject, TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Von: ", CAPTION_FONT));
        p.add(new Phrase(from, TEXT_FONT));
        p.add(Chunk.TABBING);
        p.add(new Phrase("Bis: ", CAPTION_FONT));
        p.add(new Phrase(to, TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Bemerkung: ", CAPTION_FONT));
        p.add(new Phrase(notes, TEXT_FONT));


        document.add(p);

        document.close();
    }


    //checkUpDate: datum der Untersuchung als String  DD.MM.YYYY, signatureName: z.B.: Dr. M. Bittinger, irgendwo speichern?, eligible = geeignet
    public static void createSchoolTripPDF(String tripName, String checkUpDate, String clazzName, String signatureName,
                                           int eligibleStudentCount, java.util.List<Student> notEligibleStudents,
                                           java.util.List<Student> absentStudents) throws FileNotFoundException, DocumentException {
        Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream((PDF_ROOT_DIRECTORY + clazzName + "_" +
                    "Schulveranstaltung" + "_" + "tripName" + ".pdf")));


        document.open();

        document.addAuthor("created with DoctorsHelper");


        Paragraph p = new Paragraph();


        p.add(new Phrase());

        p.add(new Phrase("Eignungsuntersuchung", HEADER_FONT));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Klasse: ", TEXT_FONT));
        p.add(new Phrase(clazzName, TEXT_FONT));
        p.add(Chunk.SPACETABBING);
        p.add(new Phrase("Datum der Untersuchung: ", TEXT_FONT));
        p.add(new Phrase(checkUpDate, TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Schulveranstaltung: ", TEXT_FONT));
        p.add(new Phrase(tripName, TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Schüleranzahl laut Teilnehmerliste: ", TEXT_FONT));
        p.add(new Phrase(String.valueOf(eligibleStudentCount), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Abwesende Schüler", TEXT_FONT));

        List absentBPList = new List(List.UNORDERED);
        for (Student s : absentStudents){
            absentBPList.add(s.getFirstname() + " " + s.getLastName());
        }

        p.add(absentBPList);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Nachstehende Schüler sind aus " +
                "gesundheitlichen Gründen für eine Teilnahme an der Veranstaltung nicht geeignet:"));

        List notEligibleBPList = new List(List.UNORDERED);
        for (Student s : notEligibleStudents){
            notEligibleBPList.add(s.getFirstname() + " " + s.getLastName());
        }

        p.add(notEligibleBPList);
        p.add(Chunk.NEWLINE);

        p.add(new Phrase("Bemerkung: ", TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Bei den untersuchten Schülern besteht kein medizinischer Einwand bzgl. einer Teilnahme."));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Bei Indikation wurden entsprechende weitere Untersuchungen veranlasst."));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase(signatureName));

        document.add(p);

        document.close();


    }


    public static void createParentsPDF(Student student, String date, String doctorType, String additionalInfos, String signatureName, java.util.List<String> problems) throws DocumentException, FileNotFoundException {
        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream((PDF_ROOT_DIRECTORY + student.getLastName() + "_" +
                "Überweisung.pdf")));


        document.open();

        document.addAuthor("created with DoctorsHelper");


        Paragraph p = new Paragraph();


        p.add(new Phrase());

        p.add(new Phrase("Mitteilung an die Eltern (Erziehungsberechtigten)", HEADER_FONT));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Name des Schülers(in): "));
        p.add(new Phrase(student.getFirstname() + " " + student.getLastName()));
        p.add(Chunk.SPACETABBING);
        p.add(new Phrase("Klasse: " + student.getClazz()));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Bei der schulärztlichen Untersuchung am " + date + " wurde"));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        List probs = new List(List.ORDERED);
        for (String s : problems){
            probs.add(s);
        }
        p.add(probs);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("festgestellt."));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Ich empfehle einen praktischen " + doctorType + " "));
        if(additionalInfos != "noSpecDoc"){
            p.add(new Phrase("für " + additionalInfos));
        }

        p.add(Chunk.NEWLINE);
        p.add(new Phrase("zur weiteren Untersuchung und eventuellen Behandlung zuzuziehen."));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);

        p.add(new Phrase("SEHR GEEHRTE FRAU KOLLEGIN!"));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("SEHR GEEHRTER HERR KOLLEGE!"));

        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Ich bitte um Untersuchung und Behandlung des(r) Schülers(in). Ich wäre Ihnen"));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("für einen stichwortartigen Befund zur Eintragung in das Gesundheitsblatt"));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("des Schülers dankbar."));
        p.add(Chunk.NEWLINE);
        p.add(signatureName);


        document.add(p);

        document.close();
        

    }


    public static void createGymReleasePDF(Student student, String startDate, String endDate, String reason) throws DocumentException, FileNotFoundException{
        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(PDF_ROOT_DIRECTORY + "Turnbefreiung" + startDate + ".pdf"));

        document.open();

        document.addAuthor("created with DoctorsHelper");

        document.add(new Paragraph("Turnbefreiung", HEADER_FONT));
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph(String.format("Der Schüler %s %s ist von %s bis %s vom Turnen befreit.",
                student.getFirstname(), student.getLastName(), startDate, endDate), TEXT_FONT));
        document.add(new Paragraph(String.format("Grund: %s", reason), TEXT_FONT));

        document.close();


    }

    public static void createCheckupPdf(Checkup checkup, String signatureName) throws FileNotFoundException, DocumentException {
        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream((PDF_ROOT_DIRECTORY + "Untersuchung_" +
                checkup.getStudent().getLastName() + ".pdf")));

        document.open();
        document.addAuthor("created with DoctorsHelper");


        Paragraph p = new Paragraph();
        p.add(new Phrase());

        p.add(new Phrase("Untersuchung", HEADER_FONT));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Schüler: ", TEXT_FONT));
        p.add(new Phrase(checkup.getStudent().getFirstname() + " " + checkup.getStudent().getLastName(), TEXT_FONT));
        p.add(Chunk.SPACETABBING);
        p.add(new Phrase("Datum der Untersuchung: ", TEXT_FONT));
        p.add(new Phrase(checkup.getTimeStamp(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Bei der Untersuchung wurde Folgendes festgestellt: ", TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Gewicht(kg) / Größe(cm) / BMI: ", TEXT_FONT));
        p.add(new Phrase(checkup.getWeight() + " / " + checkup.getHeight() + " / " + Model.getModel().calcBmi(checkup.getHeight(), checkup.getWeight()), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Asthma: ", TEXT_FONT));
        p.add(new Phrase(checkup.getAsthma().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("BCG Impfung: ", TEXT_FONT));
        p.add(new Phrase(checkup.getBcg().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Allerie: ", TEXT_FONT));
        p.add(new Phrase(checkup.getAllergie().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Diabetesmellitus: ", TEXT_FONT));
        p.add(new Phrase(checkup.getDiabetes().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Brillenträger: ", TEXT_FONT));
        p.add(new Phrase(checkup.getBrillenträger().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Schielen: ", TEXT_FONT));
        p.add(new Phrase(checkup.getSchielen().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Visus: ", TEXT_FONT));
        p.add(new Phrase(checkup.getVisus().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Hörvermögen: ", TEXT_FONT));
        p.add(new Phrase(checkup.getHörvermögen().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Sprachfehler: ", TEXT_FONT));
        p.add(new Phrase(checkup.getSprachfehler().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Nase: ", TEXT_FONT));
        p.add(new Phrase(checkup.getNase().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Tonsillen: ", TEXT_FONT));
        p.add(new Phrase(checkup.getTonsillen().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);

        p.add(new Phrase("Zähne: ", TEXT_FONT));
        for (Checkup.ZähneTyp z :checkup.getZähne()) {
            p.add(new Phrase(z.getDescription() + " ,", TEXT_FONT));
        }
        p.add(Chunk.NEWLINE);




        p.add(new Phrase("Gebiss: ", TEXT_FONT));
        p.add(new Phrase(checkup.getGebisstellung().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Schilddrüse: ", TEXT_FONT));
        p.add(new Phrase(checkup.getSchilddrüse().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Haut: ", TEXT_FONT));
        p.add(new Phrase(checkup.getHaut().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Herz und Gefäße: ", TEXT_FONT));
        p.add(new Phrase(checkup.getHerz().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Lunge: ", TEXT_FONT));
        p.add(new Phrase(checkup.getLunge().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Bauch: ", TEXT_FONT));
        p.add(new Phrase(checkup.getBauch().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Wirbelsäule und Brustkorb: ", TEXT_FONT));
        p.add(new Phrase(checkup.getWirbelsäule().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Arme und Hände: ", TEXT_FONT));
        p.add(new Phrase(checkup.getArme().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Beine und Füße: ", TEXT_FONT));
        p.add(new Phrase(checkup.getBeine().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Nervensystem: ", TEXT_FONT));
        p.add(new Phrase(checkup.getNerven().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Sonstige Befunde: ", TEXT_FONT));
        p.add(new Phrase(checkup.getOtherDiagnoses().toString(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Fachärztliche Untersuchung: ", TEXT_FONT));
        p.add(new Phrase(checkup.getSpecialistExamination().getDescription() + ", " + checkup.getSpecialistExaminationBecauseOf(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Zur Behandlung: ", TEXT_FONT));
        p.add(new Phrase(checkup.getTreatment().getDescription() + ", " + checkup.getTreatmentBecauseOf(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Geeignet für " + checkup.getReadyForExcursionName() + ": " + checkup.getReadyForExcursion().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);
        p.add(new Phrase("Schulärztliche Überwachung: ", TEXT_FONT));
        p.add(new Phrase(checkup.getSchoolHealthMonitoring().getDescription(), TEXT_FONT));
        p.add(Chunk.NEWLINE);

        document.add(p);

        document.close();


    }


}
