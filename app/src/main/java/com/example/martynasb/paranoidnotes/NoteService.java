package com.example.martynasb.paranoidnotes;

import android.util.Log;

import java.util.Collections;
import java.util.List;

class NoteService {
    private String tag = "note service";
    private String password = null;
    private NoteStorage storage;

    NoteService(NoteStorage storage) {
        this.storage = storage;
    }

    void addNote(NoteItem note) {
        if (isLoggedIn()) {
            storage.addNoteItem(note, this.password);
        }
    }

    List<NoteItem> getNoteList() throws Exception {
        if (isLoggedIn()) {
            return storage.getNoteList(this.password);
        }
        return Collections.emptyList();
    }

    NoteItem getNoteById(String noteId) {
        try {
            List<NoteItem> list = storage.getNoteList(this.password);
            for(NoteItem noteItem : list) {
                if (noteItem.getId().equals(noteId)) {
                    return noteItem;
                }
            }
        } catch (Exception e) {
            Log.d(tag, "Failed getting note by id: " + e.toString());
        }
        return new NoteItem("", "");
    }

    boolean isLoggedIn() {
        return password != null;
    }

    boolean login(String password) {
        if (password.equals(this.password) || storage.canDecryptWithPassword(password)) {
            this.password = password;
            return true;
        }
        return false;
    }

    boolean isFirstTimeUser() {
        return !isLoggedIn() && storage.isEmpty();
    }

    void registerNewUser(String password) {
        this.password = password;
    }
}
