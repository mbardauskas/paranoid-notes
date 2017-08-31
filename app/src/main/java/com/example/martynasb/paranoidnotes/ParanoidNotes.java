package com.example.martynasb.paranoidnotes;


import android.app.Application;
import android.content.Context;

import java.util.List;

public class ParanoidNotes extends Application {
    private NoteService noteService;

    static ParanoidNotes fromContext(Context c) {
        return (ParanoidNotes) c.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.noteService = new NoteService(new ParanoidStorage(getApplicationContext()));
    }

    public NoteService getNoteService() {
        return this.noteService;
    }
}
