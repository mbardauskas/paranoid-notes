package com.example.martynasb.paranoidnotes;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class ParanoidStorage implements NoteStorage {
    private Context context;
    final private String FILE_NAME = "note-storage.txt";

    public ParanoidStorage (Context context) {
        this.context = context;
    }

    @Override
    public boolean isEmpty() {
        try {
            FileInputStream fis = this.context.openFileInput(FILE_NAME);
            return fis.read() == -1;
        } catch (FileNotFoundException e) {
            return true;
        } catch (Exception e) {
            System.err.println("read or other exception" + e);
            return true;
        }
    }

    @Override
    public boolean canDecryptWithPassword(String password) {
        return true;
    }

    @Override
    public void addNoteItem(NoteItem note) {
        List<NoteItem> notes = getNoteList();
        notes.add(note);
        String noteString = new Gson().toJson(notes);
        Log.d("add note", noteString);
        try {
            FileOutputStream fos = this.context.openFileOutput(
                    FILE_NAME, Context.MODE_PRIVATE
            );
            fos.write(noteString.getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e("Add note", e.toString());
        }
    }

    @Override
    public List<NoteItem> getNoteList() {
        try {
            String notesJson = "";
            FileInputStream fis = this.context.openFileInput(FILE_NAME);
            byte fileContent[] = new byte[(int)fis.available()];
            fis.read(fileContent);
            String s = new String(fileContent);
            System.out.println("File content: " + s);
            List<NoteItem> notes = new Gson().fromJson(
                s,
                new TypeToken<ArrayList<NoteItem>>() {}.getType()
            );
            System.out.println("File json objects: " + notes);
            return notes;
        } catch (Exception e) {
            Log.e("Get note list failed", e.toString());
            return new ArrayList<>();
        }
    }
}
