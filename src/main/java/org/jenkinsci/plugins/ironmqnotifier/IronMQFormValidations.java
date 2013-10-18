package org.jenkinsci.plugins.ironmqnotifier;

import hudson.util.FormValidation;

/**
 * <p>IronMQFormValidations class.</p>
 *
 * @author mike
 * @version $Id: $
 */
public class IronMQFormValidations {

    /**
     * <p>isValidQueueName.</p>
     *
     * @param name a {@link java.lang.String} object.
     * @return a {@link hudson.util.FormValidation} object.
     */
    public final FormValidation isValidQueueName(final String name) {

        if (isAlpha(name)) {
            return FormValidation.ok();
        } else {
            return FormValidation.warning("Check Queue Name");
        }

    }

    /**
     * <p>isValidExpirySeconds.</p>
     *
     * @param expirySeconds a long.
     * @return a {@link hudson.util.FormValidation} object.
     */
    public final FormValidation isValidExpirySeconds(final long expirySeconds) {

        if ( expirySeconds > 0) {
            return FormValidation.ok();
        } else {
            return FormValidation.warning("Expiry Should Not be Zero");
        }
    }

    private static boolean isAlpha(final String name) {
        return name.matches("[a-zA-Z]+");

    }

}
