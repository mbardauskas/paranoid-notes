package com.example.martynasb.paranoidnotes;

import java.util.ArrayList;

public class NoteService {
    private ArrayList<NoteItem> noteList = new ArrayList<>();
    private String potentialPassword;

    public void addNote(NoteItem note) {
        noteList.add(note);
    }

    public ArrayList<NoteItem> getNoteList() {
        noteList.add(new NoteItem("Note 11", "body 1"));
        noteList.add(new NoteItem("Note 22", "body 2"));
        return noteList;
    }

    public boolean hasPassword() {
        return false;
    }

    public boolean isPotentialPasswordValid() {
        return false;
    }

    public void setPotentialPassword(String potentialPassword) {
        this.potentialPassword = potentialPassword;
    }
}
