/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.authenticate;

import org.apache.log4j.Logger;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public abstract class OpenAMImplicitAuthentication extends OpenAMAuthentication {

    private static Logger log = Logger.getLogger(OpenAMImplicitAuthentication.class);

    protected abstract String retrieveOpenAMToken(HttpServletRequest request);

    /**
     * Registrations are nog handled by the DSpace application.
     *
     * @param context
     *            DSpace context
     * @param request
     *            HTTP request, in case it's needed. May be null.
     * @param username
     *            Username, if available.  May be null.
     * @return in any case false
     * @throws java.sql.SQLException
     */
    @Override
    public boolean canSelfRegister(Context context, HttpServletRequest request, String username) throws SQLException {
        return true;
    }

    /**
     * We are not changing user data...
     *
     * @param context
     *            DSpace context
     * @param request
     *            HTTP request, in case it's needed. May be null.
     * @param eperson
     *            newly created EPerson record - email + information from the
     * @throws java.sql.SQLException
     */
    @Override
    public void initEPerson(Context context, HttpServletRequest request, EPerson eperson) throws SQLException {
        // Nothing to do here...
    }

    /**
     * We are not changing user data...
     *
     * @param context
     *            DSpace context
     * @param request
     *            HTTP request, in case it's needed. May be null.
     * @param username
     *            Username, if available.  May be null.
     * @return in any case false
     * @throws java.sql.SQLException
     */
    @Override
    public boolean allowSetPassword(Context context, HttpServletRequest request, String username) throws SQLException {
        return false;
    }

    /**
     * This is an implicit method
     *
     * @return in any case true
     */
    @Override
    public boolean isImplicit() {
        return true;
    }


    @Override
    public int authenticate(Context context,
                            String username,
                            String password,
                            String realm,
                            HttpServletRequest request) throws SQLException {
        String ssoId = retrieveOpenAMToken(request);
        return authenticateOpenAM(context, request, ssoId);
    }

    @Override
    public String loginPageURL(Context context, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public String loginPageTitle(Context context) {
        return "org.dspace.eperson.OpenAMAuthentication.title";
    }

}
