package entity;

import com.schooldoctor.Schooldoctor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lukas on 08.03.2016.
 */
@Entity
public class Checkup extends Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private AsthmaTyp asthma;
    private BCGTyp bcg;
    private AllergieTyp allergie;
    private DiabetesTyp diabetes;
    private BrillenträgerTyp brillenträger;
    private SchielenTyp schielen;
    private VisusTyp visus;
    private HörvermögenTyp hörvermögen;
    private SprachfehlerTyp sprachfehler;
    private NaseTyp nase;
    private TonsillenTyp tonsillen;
    private LinkedList<ZähneTyp> zähne;
    private GebisstellungTyp gebisstellung;
    private SchilddrüseTyp schilddrüse;
    private HautTyp haut;
    private HerzTyp herz;
    private LungTyp lunge;
    private BauchTyp bauch;
    private WirbelsäuleTyp wirbelsäule;
    private ArmeTyp arme;
    private BeineTyp beine;
    private NervenTyp nerven;
    private String otherDiagnoses;
    private specialistExaminationType specialistExamination;
    private treatmentType treatment;
    private ReadyForExcursionType readyForExcursion;
    private schoolHealthMonitoringType schoolHealthMonitoring;
    private String treatmentBecauseOf;
    private String specialistExaminationBecauseOf;
    private String readyForExcursionName;
    private Double height;
    private Double weight;

    public Checkup() {
        zähne = new LinkedList<>();
    }

    public  Checkup(Student s, LocalDate timeStamp){
        super(s, "", timeStamp);
        zähne = new LinkedList<>();
    }

    @Override
    public String toString() {
        return getTimeStamp() + ": Untersuchung";
    }

    //region getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtherDiagnoses() {
        return otherDiagnoses;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setOtherDiagnoses(String otherDiagnoses) {
        this.otherDiagnoses = otherDiagnoses;
    }

    public specialistExaminationType getSpecialistExamination() {
        return specialistExamination;
    }

    public void setSpecialistExamination(specialistExaminationType specialistExamination) {
        this.specialistExamination = specialistExamination;
    }

    public treatmentType getTreatment() {
        return treatment;
    }

    public void setTreatment(treatmentType treatment) {
        this.treatment = treatment;
    }

    public ReadyForExcursionType getReadyForExcursion() {
        return readyForExcursion;
    }

    public void setReadyForExcursion(ReadyForExcursionType readyForExcursion) {
        this.readyForExcursion = readyForExcursion;
    }

    public schoolHealthMonitoringType getSchoolHealthMonitoring() {
        return schoolHealthMonitoring;
    }

    public void setSchoolHealthMonitoring(schoolHealthMonitoringType schoolHealthMonitoring) {
        this.schoolHealthMonitoring = schoolHealthMonitoring;
    }

    public String getTreatmentBecauseOf() {
        return treatmentBecauseOf;
    }

    public void setTreatmentBecauseOf(String treatmentBecauseOf) {
        this.treatmentBecauseOf = treatmentBecauseOf;
    }

    public String getSpecialistExaminationBecauseOf() {
        return specialistExaminationBecauseOf;
    }

    public void setSpecialistExaminationBecauseOf(String specialistExaminationBecauseOf) {
        this.specialistExaminationBecauseOf = specialistExaminationBecauseOf;
    }

    public String getReadyForExcursionName() {
        return readyForExcursionName;
    }

    public void setReadyForExcursionName(String readyForExcursionName) {
        this.readyForExcursionName = readyForExcursionName;
    }

    public AsthmaTyp getAsthma() {
        return asthma;
    }

    public void setAsthma(AsthmaTyp asthma) {
        this.asthma = asthma;
    }

    public BCGTyp getBcg() {
        return bcg;
    }

    public void setBcg(BCGTyp bcg) {
        this.bcg = bcg;
    }

    public AllergieTyp getAllergie() {
        return allergie;
    }

    public void setAllergie(AllergieTyp allergie) {
        this.allergie = allergie;
    }

    public DiabetesTyp getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(DiabetesTyp diabetes) {
        this.diabetes = diabetes;
    }

    public BrillenträgerTyp getBrillenträger() {
        return brillenträger;
    }

    public void setBrillenträger(BrillenträgerTyp brillenträger) {
        this.brillenträger = brillenträger;
    }

    public SchielenTyp getSchielen() {
        return schielen;
    }

    public void setSchielen(SchielenTyp schielen) {
        this.schielen = schielen;
    }

    public VisusTyp getVisus() {
        return visus;
    }

    public void setVisus(VisusTyp visus) {
        this.visus = visus;
    }

    public HörvermögenTyp getHörvermögen() {
        return hörvermögen;
    }

    public void setHörvermögen(HörvermögenTyp hörvermögen) {
        this.hörvermögen = hörvermögen;
    }

    public SprachfehlerTyp getSprachfehler() {
        return sprachfehler;
    }

    public void setSprachfehler(SprachfehlerTyp sprachfehler) {
        this.sprachfehler = sprachfehler;
    }

    public NaseTyp getNase() {
        return nase;
    }

    public void setNase(NaseTyp nase) {
        this.nase = nase;
    }

    public TonsillenTyp getTonsillen() {
        return tonsillen;
    }

    public void setTonsillen(TonsillenTyp tonsillen) {
        this.tonsillen = tonsillen;
    }

    public LinkedList<ZähneTyp> getZähne() {
        return zähne;
    }

    public void addZähne(ZähneTyp typ){
        zähne.add(typ);
    }

    public void setZähne(LinkedList<ZähneTyp> zähne) {
        this.zähne = zähne;
    }

    public GebisstellungTyp getGebisstellung() {
        return gebisstellung;
    }

    public void setGebisstellung(GebisstellungTyp gebisstellung) {
        this.gebisstellung = gebisstellung;
    }

    public SchilddrüseTyp getSchilddrüse() {
        return schilddrüse;
    }

    public void setSchilddrüse(SchilddrüseTyp schilddrüse) {
        this.schilddrüse = schilddrüse;
    }

    public HautTyp getHaut() {
        return haut;
    }

    public void setHaut(HautTyp haut) {
        this.haut = haut;
    }

    public HerzTyp getHerz() {
        return herz;
    }

    public void setHerz(HerzTyp herz) {
        this.herz = herz;
    }

    public LungTyp getLunge() {
        return lunge;
    }

    public void setLunge(LungTyp lunge) {
        this.lunge = lunge;
    }

    public BauchTyp getBauch() {
        return bauch;
    }

    public void setBauch(BauchTyp bauch) {
        this.bauch = bauch;
    }

    public WirbelsäuleTyp getWirbelsäule() {
        return wirbelsäule;
    }

    public void setWirbelsäule(WirbelsäuleTyp wirbelsäule) {
        this.wirbelsäule = wirbelsäule;
    }

    public ArmeTyp getArme() {
        return arme;
    }

    public void setArme(ArmeTyp arme) {
        this.arme = arme;
    }

    public BeineTyp getBeine() {
        return beine;
    }

    public void setBeine(BeineTyp beine) {
        this.beine = beine;
    }

    public NervenTyp getNerven() {
        return nerven;
    }

    public void setNerven(NervenTyp nerven) {
        this.nerven = nerven;
    }

    public enum AsthmaTyp{
        Ja("Ja"),Nein("Nein");

        private String desc;

        AsthmaTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }
//endregion

    //region EnumTypes

    public enum specialistExaminationType{
        Ja("JA"),Nein("Nein");

        private String desc;

        specialistExaminationType(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }

    public enum treatmentType{
        Ja("JA"),Nein("Nein");

        private String desc;

        treatmentType(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }

    public enum ReadyForExcursionType{
        bedingtGeeignet("bedingt geeignet"),nichtGeeignet("nicht geeignet");

        private String desc;

        ReadyForExcursionType(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }

    public enum schoolHealthMonitoringType{
        Ja("JA"),Nein("Nein");

        private String desc;

        schoolHealthMonitoringType(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }


    public enum BCGTyp{
        Ja("JA"),Nein("Nein");

        private String desc;

        BCGTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }

    public enum AllergieTyp{
        Ja("JA"),Nein("Nein");

        private String desc;

        AllergieTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }

    public enum DiabetesTyp{
        Ja("JA"),Nein("Nein");

        private String desc;

        DiabetesTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }

    public enum BrillenträgerTyp{
        Ja("JA"),Nein("Nein");

        private String desc;

        BrillenträgerTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }

    public enum SchielenTyp{
        Ja("JA"),Nein("Nein");

        private String desc;

        SchielenTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }

    public enum VisusTyp{
        bds("6/6 bds."),einseitigHerabgesetzt("einseitig herabgesetzt"),
        beidseitigHerabgesetzt("beidseitig Herabgesetzt");

        private String desc;

        VisusTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }

    public enum HörvermögenTyp{
        normal("normal"),einseitigBehindert("einseitig behindert"),
        beidseitigBehindert("beidseitig behindert");

        private String desc;

        HörvermögenTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }

    public enum SprachfehlerTyp{
        Ja("JA"),Nein("Nein");

        private String desc;

        SprachfehlerTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }

    public enum NaseTyp{
        frei("frei"),AtmungchronBehindert("Atmung chron. behindert");

        private String desc;

        NaseTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }

    public enum TonsillenTyp{
        normal("normal"),krankhaftVerändert("krankhaft verändert"), entfernt("entfernt");

        private String desc;

        TonsillenTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }


    public enum ZähneTyp{
        UntersMSpatel("Unters. m. Spatel"),UntersMSpiegelUSonde("Unters. m. Spiegel u. Sonde"),
        gesund("gesund"), saniert("saniert"), kariös("kariös");

        private String desc;

        ZähneTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }


    public enum GebisstellungTyp{
        normal("normal"),fehlstellungInBehandl("Fehlstellung in Behandlung"), fehlstellungOBehandlung("Fehlstellung o. Behandlung");

        private String desc;

        GebisstellungTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }


    public enum SchilddrüseTyp{
        normal("normal"),nurTastbarVergrößert("nur tastbar vergrößert"), sichtbarVergrößert("sichtbar vergrößert");

        private String desc;

        SchilddrüseTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }

    public enum HautTyp{
        normal("normal"),chronHAutleiden("chron. Hautleiden");

        private String desc;

        HautTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }


    public enum HerzTyp{
        normal("normal"),organHerzUndGefäß("organ. Herz u. Gefäßerkrankung");

        private String desc;

        HerzTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }


    public enum LungTyp{
        normal("normal"),chronBronchitis("chron. rezid. Bronchitits"), Asthma("Asthma");

        private String desc;

        LungTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }


    public enum BauchTyp{
        normal("normal"),hernien("Hernien"), sonstiges("Sonstiges");

        private String desc;

        BauchTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }


    public enum WirbelsäuleTyp{
        normal("normal"),haltungsschwäche("Haltungsschwäche"), fehlform("Fehlform");

        private String desc;

        WirbelsäuleTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }

    public enum ArmeTyp{
        normal("normal"),fehlformOFunktionsbehinderung("Fehlform o. Funktionsbehinderung"),
        fehlformMFunktionsbehinderung("Fehlform m. Funktionsbehinderung");

        private String desc;

        ArmeTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }

    public enum BeineTyp{
        normal("normal"),fehlformOFunktionsbehinderung("Fehlform o. Funktionsbehinderung"),
        fehlformMFunktionsbehinderung("Fehlform m. Funktionsbehinderung");

        private String desc;

        BeineTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }

    public enum NervenTyp{
        normal("normal"),vegetUPsychLabil("veget. u. psych. labil"),
        organNervenleiden("organ. Nervenleiden");

        private String desc;

        NervenTyp(String description) {
            desc=description;
        }

        public String getDescription() {
            return desc;
        }
    }
//endregion
}
