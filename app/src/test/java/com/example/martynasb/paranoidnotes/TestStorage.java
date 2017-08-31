package com.example.martynasb.paranoidnotes;

import java.util.ArrayList;
import java.util.List;

class TestStorage implements NoteStorage {
    private String password = null;
    private List<NoteItem> noteList = new ArrayList<>();

    void acceptPassword(String correctPassword) {
        password = correctPassword;
    }

    @Override
    public boolean isEmpty() {
        return noteList.isEmpty();
    }

    @Override
    public boolean canDecryptWithPassword(String password) {
        return password.equals(this.password);
    }

    @Override
    public void addNoteItem(NoteItem note, String password) {
        noteList.add(note);
    }

    public List<NoteItem> getNoteList(String password) throws Exception {
        if (this.password.equals(password)) {
            return noteList;
        }
        throw new Exception("Wrong password");
    }
}
