package com.example.martynasb.paranoidnotes;

interface TextEncryptor {
    String decryptWithPassword(String content, String password);
    String encryptWithPassword(String content, String password);
}
