/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.common.crypt;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;
import eu.artofcoding.bookworm.common.crypt.AnnotationProcessor.FieldAnnotationAction;
import org.jasypt.encryption.StringEncryptor;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public final class Cryptor {

    private static final Logger LOGGER = Logger.getLogger(Cryptor.class.getName());

    static {
        // Disable JCE restriction check
        try {
            Field field = Class.forName("javax.crypto.JceSecurity").getDeclaredField("isRestricted");
            field.setAccessible(true);
            field.set(null, Boolean.FALSE);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot disable JCE check", e);
        }
    }

    private Cryptor() {
        throw new AssertionError();
    }

    public static <T extends GenericEntity> void processEncryptOnPersist(final StringEncryptor stringEncryptor, final T entity) {
        final AnnotationProcessor annotationProcessor = new AnnotationProcessor();
        annotationProcessor.processFieldAnnotation(entity, EncryptOnPersist.class, new EncryptOnPersistAction(stringEncryptor));
    }

    public static <T extends GenericEntity> void processDecryptOnLoad(final StringEncryptor stringDecryptor, final T entity) {
        final AnnotationProcessor annotationProcessor = new AnnotationProcessor();
        annotationProcessor.processFieldAnnotation(entity, DecryptOnLoad.class, new DecryptOnLoadAction(stringDecryptor));
    }

    private static class EncryptOnPersistAction implements FieldAnnotationAction {

        private final StringEncryptor stringEncryptor;

        public EncryptOnPersistAction(final StringEncryptor stringEncryptor) {
            this.stringEncryptor = stringEncryptor;
        }

        @Override
        public void process(final Object object, final Field field) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            if (String.class == field.getType()) {
                try {
                    final String value = (String) field.get(object);
                    final boolean hasValue = null != value && !value.isEmpty();
                    if (hasValue) {
                        final String encryptedValue = stringEncryptor.encrypt(value);
                        field.set(object, encryptedValue);
                    }
                } catch (IllegalAccessException e) {
                    LOGGER.severe(String.format("Cannot encrypt field %s: %s", field.getName(), e.getMessage()));
                }
            }
        }

    }

    private static class DecryptOnLoadAction implements FieldAnnotationAction {

        private final StringEncryptor stringEncryptor;

        public DecryptOnLoadAction(final StringEncryptor stringEncryptor) {
            this.stringEncryptor = stringEncryptor;
        }

        @Override
        public void process(final Object object, final Field field) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            if (String.class == field.getType()) {
                try {
                    final String value = (String) field.get(object);
                    final boolean hasValue = null != value && !value.isEmpty();
                    if (hasValue) {
                        final String encryptedValue = stringEncryptor.decrypt(value);
                        field.set(object, encryptedValue);
                    }
                } catch (IllegalAccessException e) {
                    LOGGER.severe(String.format("Cannot decrypt field %s: %s", field.getName(), e.getMessage()));
                }
            }
        }

    }

}
