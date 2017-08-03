package com.example.martynasb.paranoidnotes;


import android.app.Application;

public class ParanoidNotes extends Application {
    private NoteService noteService;

    @Override
    public void onCreate() {
        super.onCreate();
        this.noteService = new NoteService();
    }

    public NoteService getNoteService() {
        return this.noteService;
    }
}
