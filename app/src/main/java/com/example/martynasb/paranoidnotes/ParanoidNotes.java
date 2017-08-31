package com.example.martynasb.paranoidnotes;


import android.app.Application;
import android.content.Context;

public class ParanoidNotes extends Application {
    private NoteService noteService;

    static ParanoidNotes fromContext(Context c) {
        return (ParanoidNotes) c.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.noteService = new NoteService(
            new ParanoidStorage(
                getApplicationContext(),
                new NoteEncryptor()
            )
        );
    }

    public NoteService getNoteService() {
        return this.noteService;
    }
}
