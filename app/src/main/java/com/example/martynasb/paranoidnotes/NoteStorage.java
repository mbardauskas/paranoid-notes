package com.example.martynasb.paranoidnotes;

import java.util.List;

interface NoteStorage {
    boolean isEmpty();
    boolean canDecryptWithPassword(String password);
    void addNoteItem(NoteItem note);
    List<NoteItem> getNoteList();
}
