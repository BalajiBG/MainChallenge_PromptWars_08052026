package com.promptwars.travel.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Utility class for security-related operations.
 * Helps in maintaining high code quality by centralizing authentication logic.
 */
@Component
public class SecurityUtils {

    /**
     * Safely retrieves the current authenticated username.
     * @return The username or "anonymous" if not authenticated.
     */
    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return auth.getName();
        }
        return "anonymous";
    }
}
