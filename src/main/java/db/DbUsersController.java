package db;

import entity.Member;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.EntityManager;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

public class DbUsersController {

    private static DbUsersController instance;

    private DbUsersController() {
    }

    public static DbUsersController getInstance() {
        if (instance == null) {
            instance = new DbUsersController();
        }
        return instance;
    }

    /**
     * Checks if the combination from username and password is saved in the
     * database.
     *
     * @return true if right data, false if wrong data
     */
    public int validateUser(String username, String password) {
        try {

            password = String.valueOf(password.hashCode());
            return DBUtil.getEntityManager()
                    .createNamedQuery("GetUserByUsernameAndPassword")
                    .setParameter("USERNAME", username)
                    .setParameter("PASSWORD", password)
                    .getResultList().size()
                    > 0 ? 1 : 0;
        } catch (Exception e) {
            return 2;
        }
    }

    /**
     * Adds a user to the database
     *
     * @param u: User
     * @return true if successful, false if not
     */
    public boolean addUser(Member u) {
        try {
            EntityManager em = DBUtil.getEntityManager();
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            u.setPassword(String.valueOf(u.getPassword().hashCode()));

            em.persist(u);
            em.getTransaction().commit();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Deletes a user
     *
     * @param u: User
     * @return true if successful, false if not
     */
    public boolean deleteUser(Member u) {
        try {
            DBUtil.getEntityManager().getTransaction().begin();
            DBUtil.getEntityManager().createNamedQuery("DeleteUser").setParameter("USERNAME", u.getUsername()).executeUpdate();
            DBUtil.getEntityManager().getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Deletes a user
     *
     * @return true if successful, false if not
     */
    public boolean deleteAllUsers() {
        try {
            DBUtil.getEntityManager().createNamedQuery("DeleteAllUsers").executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the user with the given username
     *
     * @param username
     * @return user if successful, null if not
     */
    public Member getUser(String username) {
        try {
            return (Member) DBUtil.getEntityManager().createNamedQuery("GetUserByUsername").setParameter("USERNAME", username).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get all users
     *
     * @return user if successful, null if not
     */
    public List<Member> getAllUsers() {
        try {
            return (List<Member>) DBUtil.getEntityManager().createNamedQuery("GetUsers").getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Changes the data of a user
     *
     * @param old: Old user
     * @param neW: New user
     * @return true if successful, false if not
     */
    public boolean changeUser(Member old, Member neW) {
        try {
            deleteUser(old);
            addUser(neW);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
