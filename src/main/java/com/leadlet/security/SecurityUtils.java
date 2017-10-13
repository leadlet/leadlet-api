package com.leadlet.security;

import com.leadlet.domain.AppAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.EntityManager;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    private static EntityManager entityManager;

    private SecurityUtils(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }


    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static AppAccount getCurrentUserAppAccountReference() {

        Long appAccountId = getCurrentUserAppAccountId();

        AppAccount appAccountRef = null;

        if( appAccountId != null ){
            appAccountRef = entityManager.getReference(AppAccount.class, appAccountId);
        }

        return appAccountRef;
    }


    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static Long getCurrentUserAppAccountId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Long appAccountId = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                AppUserDetail springSecurityUser = (AppUserDetail) authentication.getPrincipal();
                appAccountId = springSecurityUser.getAppAccountId();
            }
        }
        return appAccountId;
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user
     */
    public static String getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String) {
            return (String) authentication.getCredentials();
        }
        return null;
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS));
        }
        return false;
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the isUserInRole() method in the Servlet API
     *
     * @param authority the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
        }
        return false;
    }
}
