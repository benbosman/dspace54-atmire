/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 * <p>
 * http://www.dspace.org/license/
 */
package org.dspace.authenticate.factory;

import org.dspace.authenticate.AuthenticationServiceImpl;
import org.dspace.authenticate.service.AuthenticationService;
import org.dspace.utils.DSpace;

/**
 * Abstract factory to get services for the authenticate package, use AuthenticateServiceFactory.getInstance() to retrieve an implementation
 *
 * @author kevinvandevelde at atmire.com
 */
public abstract class AuthenticateServiceFactory {

    public abstract AuthenticationService getAuthenticationService();

    public static AuthenticateServiceFactory getInstance() {
        final boolean initInSpringDoesNotWork = true;
        if (initInSpringDoesNotWork) {
            return new AuthenticateServiceFactoryImpl() {
                @Override
                public AuthenticationService getAuthenticationService() {
                    return new AuthenticationServiceImpl(initInSpringDoesNotWork);
                }
            };
        } else {
            return new DSpace().getServiceManager().getServiceByName("authenticateServiceFactory", AuthenticateServiceFactory.class);
        }
    }
}
