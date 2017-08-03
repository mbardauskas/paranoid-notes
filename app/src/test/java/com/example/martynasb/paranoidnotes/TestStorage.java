package com.example.martynasb.paranoidnotes;

import java.util.ArrayList;
import java.util.List;

class TestStorage implements NoteStorage {
    private String password = null;
    private List<NoteItem> noteList = new ArrayList<>();

    @Override
    public boolean isEmpty() {
        return noteList.isEmpty();
    }

    @Override
    public boolean canDecryptWithPassword(String password) {
        return password.equals(this.password);
    }

    @Override
    public void addNoteItem(NoteItem note) {
        noteList.add(note);
    }

    public void acceptPassword(String correctPassword) {
        password = correctPassword;
    }

    public List<NoteItem> getNoteList() {
        return noteList;
    }
}
