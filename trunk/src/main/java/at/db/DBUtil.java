package at.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBUtil {

    private static EntityManager em;
    private static EntityManagerFactory emf;

    private DBUtil() { }
    
    public synchronized static EntityManager getEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("SchooldoctorPU");
        }

        if(em == null) {
            em = emf.createEntityManager();
        }
        return em;
    }

    public synchronized static EntityManager getCustomEntityManager(String pu) {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(pu);
        }

        if((em == null) || !em.isOpen()) {
            em = emf.createEntityManager();
        }
        return em;
    }

    public static void shutdown() {
        try {
            if ((em != null) && em.isOpen() ) {
                em.close();
                em = null;
            }
            if (emf != null) {
                emf.close();
                emf = null;
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        try {

            if(System.getProperty("os.name").contains("Windows")){
                //Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\dbStop.bat");
            }
            if(System.getProperty("os.name").contains("OSX")){
                //TODO: start os x dbStart script

            }
            if(System.getProperty("os.name").contains(("Linux"))){
                //TODO: start linux dbStart script
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void encrypt(String text){

    }
}
