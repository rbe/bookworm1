/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.common.crypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import javax.persistence.PostLoad;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import java.util.logging.Logger;

public class CryptorEntityListener {

    private static final Logger LOGGER = Logger.getLogger(CryptorEntityListener.class.getName());

    private static final String ENC_PWD;

    private static final StandardPBEStringEncryptor stringEncryptor;

    static {
        ENC_PWD = System.getenv("CAMEL_ENCRYPTION_PASSWORD");
        if (null == ENC_PWD) {
            LOGGER.warning("There's no encryption password, please set CAMEL_ENCRYPTION_PASSWORD!");
            stringEncryptor = null;
        } else {
            try {
                Class.forName("eu.artofcoding.bookworm.common.crypt.Cryptor");
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
            stringEncryptor = new StandardPBEStringEncryptor();
            stringEncryptor.setPassword(ENC_PWD);
        }
    }

    @PrePersist
    void onPrePersist(Object o) {
        if (null != stringEncryptor) {
            Cryptor.processEncryptOnPersist(stringEncryptor, o);
        }
    }

    @PostLoad
    void onPostLoad(Object o) {
        if (null != stringEncryptor) {
            Cryptor.processDecryptOnLoad(stringEncryptor, o);
        }
    }

    @PostUpdate
    void onPostUpdate(Object o) {
        if (null != stringEncryptor) {
            Cryptor.processEncryptOnPersist(stringEncryptor, o);
        }
    }

}
