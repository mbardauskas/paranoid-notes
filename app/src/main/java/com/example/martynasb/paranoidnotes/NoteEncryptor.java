package com.example.martynasb.paranoidnotes;

import se.simbio.encryption.Encryption;

class NoteEncryptor implements TextEncryptor {
    private static String salt = "Simple salt";
    private static byte[] iv = new byte[16];

    public String decryptWithPassword(String encryptedString, String password) {
        Encryption encryption = Encryption.getDefault(password, salt, iv);
        return encryption.decryptOrNull(encryptedString);
    }

    public String encryptWithPassword(String contentString, String password) {
        Encryption encryption = Encryption.getDefault(password, salt, iv);
        return encryption.encryptOrNull(contentString);
    }
}
