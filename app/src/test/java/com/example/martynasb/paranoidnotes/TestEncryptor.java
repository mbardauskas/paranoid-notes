package com.example.martynasb.paranoidnotes;

public class TestEncryptor implements TextEncryptor {
    public String decryptWithPassword(String encryptedString, String password) {
        return encryptedString;
    }

    public String encryptWithPassword(String contentString, String password) {
        return contentString;
    }
}

