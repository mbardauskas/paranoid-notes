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


class ParanoidStorage implements NoteStorage {
    private Context context;
    final private String FILE_NAME = "note-storage.txt";
    final private String tag = "paranoid storage";

    ParanoidStorage(Context context) {
        this.context = context;
    }

    @Override
    public boolean isEmpty() {
        try {
            FileInputStream fis = this.context.openFileInput(FILE_NAME);
            return fis.read() == -1;
        } catch (Exception e) {
            Log.e(tag, "isEmpty failed: " + e.toString());
            return true;
        }
    }

    @Override
    public boolean canDecryptWithPassword(String password) {
        try {
            getNoteList(password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void addNoteItem(NoteItem note, String password) {
        try {
            List<NoteItem> notes = getNoteList(password);
            notes.add(note);
            storeNotesToStorage(notes);
        } catch (Exception e) {
            Log.e(tag, "Add note failed: " + e.toString());
        }
    }

    @Override
    public List<NoteItem> getNoteList(String password) throws Exception {
        String contentString = getContentStringFromStorage();
        String decryptedString = decryptWithPassword(contentString, password);
        return getNoteListFromString(decryptedString);
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
        byte fileContent[] = new byte[fis.available()];
        fis.read(fileContent);
        String contentString = new String(fileContent);
        Log.d(tag , "File content: " + contentString);
        return contentString;
    }

    private static String decryptWithPassword(String contentString, String password) {
        if (password.equals("123")) {
            return contentString;
        }
        return "something else";
    }
}
