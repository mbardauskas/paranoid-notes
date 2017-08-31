package com.example.martynasb.paranoidnotes;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class ParanoidStorage implements NoteStorage {
    private Context context;
    final private String FILE_NAME = "note-storage.txt";
    final private String tag = "paranoid storage";

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
            Log.e(tag, "isEmpty failed: " + e.toString());
            return true;
        }
    }

    @Override
    public boolean canDecryptWithPassword(String password) {
        return true;
    }

    @Override
    public void addNoteItem(NoteItem note) {
        try {
            List<NoteItem> notes = getNoteList();
            notes.add(note);
            storeNotesToStorage(notes);
        } catch (Exception e) {
            Log.e(tag, "Add note failed: " + e.toString());
        }
    }

    @Override
    public List<NoteItem> getNoteList() throws Exception {
        String contentString = getContentStringFromStorage();
        return getNoteListFromString(contentString);
    }

    private void storeNotesToStorage(List<NoteItem> notes) throws Exception {
        String noteJsonString = new Gson().toJson(notes);
        Log.d(tag, "store notes: " + noteJsonString);

        FileOutputStream fos = this.context.openFileOutput(
            FILE_NAME,
            Context.MODE_PRIVATE
        );

        fos.write(noteJsonString.getBytes());
        fos.close();
    }

    private List<NoteItem> getNoteListFromString(String contentString) {
        return new Gson().fromJson(
            contentString,
            new TypeToken<ArrayList<NoteItem>>() {}.getType()
        );
    }

    private String getContentStringFromStorage() throws Exception {
        FileInputStream fis = this.context.openFileInput(FILE_NAME);
        byte fileContent[] = new byte[(int)fis.available()];
        fis.read(fileContent);
        String contentString = new String(fileContent);
        Log.d(tag , "File content: " + contentString);
        return contentString;
    }
}
