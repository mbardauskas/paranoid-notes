package com.example.martynasb.paranoidnotes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NoteService {
    private String password = null;
    private NoteStorage storage;

    public NoteService(NoteStorage storage) {
        this.storage = storage;
    }

    public void addNote(NoteItem note) {
        if (isLoggedIn()) {
            storage.addNoteItem(note);
        }
    }

    public List<NoteItem> getNoteList() {
        if (isLoggedIn()) {
            return storage.getNoteList();
        }
        return Collections.emptyList();
    }

    public boolean isLoggedIn() {
        return password != null;
    }

    public boolean login(String password) {
        if (password.equals(this.password) || storage.canDecryptWithPassword(password)) {
            this.password = password;
            return true;
        }
        return false;
    }

    public boolean isFirstTimeUser() {
        return !isLoggedIn() && storage.isEmpty();
    }

    public void registerNewUser(String password) {
        this.password = password;
    }
}
