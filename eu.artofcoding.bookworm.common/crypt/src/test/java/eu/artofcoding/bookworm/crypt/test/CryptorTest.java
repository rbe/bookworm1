/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.crypt.test;

import org.jasypt.util.text.StrongTextEncryptor;
import org.junit.Before;
import org.junit.Test;

public class CryptorTest {

    private static final String ENC_PWD = "jasypt";

    private StrongTextEncryptor textEncryptor;

    @Before
    public void initialize() {
        try {
            Class.forName("eu.artofcoding.bookworm.common.crypt.Cryptor");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(ENC_PWD);
    }

    @Test
    public void encryptAndDecrypt() {
        String s = "Hallo";
        final String encrypt = textEncryptor.encrypt(s);
        System.out.printf("%s -> %s%n", s, encrypt);
        System.out.printf("%s -> %s%n", encrypt, textEncryptor.decrypt(encrypt));
    }

}
