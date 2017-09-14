package com.example.martynasb.paranoidnotes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ViewNoteActivity extends AppCompatActivity {
    private NoteService noteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        String noteId = getIntent().getStringExtra("noteId");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupContent(noteId);
    }

    private void setupContent(String noteId) {
        final NoteService noteService = ((ParanoidNotes)getApplication()).getNoteService();

        NoteItem note = noteService.getNoteById(noteId);
        setTitle(note.getTitle());

        TextView text = (TextView) findViewById(R.id.view_note_text);
        text.setText(note.getBody());
    }

}
