package com.example.martynasb.paranoidnotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText noteInput = (EditText) findViewById(R.id.new_note_input);
                final EditText noteTitleInput = (EditText) findViewById(R.id.new_note_title_input);
                final NoteService noteService = ((ParanoidNotes)getApplication()).getNoteService();

                noteService.addNote(new NoteItem(
                        noteTitleInput.getText().toString(),
                        noteInput.getText().toString()
                ));

                Toast.makeText(NewNoteActivity.this, noteInput.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
