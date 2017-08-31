package com.example.martynasb.paranoidnotes;

import org.jasypt.util.text.StrongTextEncryptor;

class NoteEncryptor implements TextEncryptor {
    private StrongTextEncryptor textEncryptor;

    NoteEncryptor() {
        textEncryptor = new StrongTextEncryptor();
    }

    public String decryptWithPassword(String encryptedString, String password) {
        textEncryptor.setPassword(password);
        return textEncryptor.decrypt(encryptedString);
    }

    public String encryptWithPassword(String contentString, String password) {
        textEncryptor.setPassword(password);
        return textEncryptor.encrypt(contentString);
    }
}
