package model;

import org.eclipse.persistence.sessions.DatabaseLogin;
import org.eclipse.persistence.sessions.Session;
import org.eclipse.persistence.sessions.factories.SessionCustomizer;

/**
 * Created by Phips on 10.08.2015.
 */
public class ReconnectionCustomization implements SessionCustomizer {

    @Override
    public void customize(Session session) throws Exception {
        DatabaseLogin login = (DatabaseLogin)session.getDatasourceLogin();
        login.setConnectionHealthValidatedOnError(false);
    }
}
