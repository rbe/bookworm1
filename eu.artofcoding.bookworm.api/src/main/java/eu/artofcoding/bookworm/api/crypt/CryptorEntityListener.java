/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.api.crypt;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import javax.persistence.PostLoad;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;

public class CryptorEntityListener {

    private static final String ENC_PWD = "askjf0984673hjgfjhgfgj3sdflhsdf1245";

    private static final StandardPBEStringEncryptor stringEncryptor;

    static {
        try {
            Class.forName("eu.artofcoding.bookworm.api.crypt.Cryptor");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        stringEncryptor = new StandardPBEStringEncryptor();
        stringEncryptor.setPassword(ENC_PWD);
    }

    @PrePersist
    void onPrePersist(Object o) {
        GenericEntity entity = (GenericEntity) o;
        Cryptor.processEncryptOnPersist(stringEncryptor, entity);
    }

    @PostLoad
    void onPostLoad(Object o) {
        GenericEntity entity = (GenericEntity) o;
        Cryptor.processDecryptOnLoad(stringEncryptor, entity);
    }

    @PostUpdate
    void onPostUpdate(Object o) {
        GenericEntity entity = (GenericEntity) o;
        Cryptor.processEncryptOnPersist(stringEncryptor, entity);
    }

}
