package com.example.martynasb.paranoidnotes;

import java.util.ArrayList;

public class NoteService {
    public ArrayList<NoteItem> getNoteList() {
        final ArrayList<NoteItem> noteList = new ArrayList<>();
        noteList.add(new NoteItem("Note 11", "body 1"));
        noteList.add(new NoteItem("Note 22", "body 2"));
        return noteList;
    }
}
