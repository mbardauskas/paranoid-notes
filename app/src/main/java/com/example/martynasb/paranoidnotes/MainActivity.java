package com.example.martynasb.paranoidnotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ListView listview;
    private NoteItemAdapter adapter;
    private NoteService noteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listview = (ListView) findViewById(R.id.listview);
        noteService = ((ParanoidNotes) getApplication()).getNoteService();

        if (noteService.isFirstTimeUser()) {
            showFTE();
        } else if (!noteService.isLoggedIn()) {
            showLogin();
        }

        setupToolbarAndFab();
        setupActivityContent();
    }

    private void showFTE() {
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
                .setTitle("Set password. And remember it!")
                .setView(input)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("Set password", null)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                })
                ;

        final AlertDialog dialog = alertDialogBuilder.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteService.registerNewUser(input.getText().toString());
                dialog.dismiss();
            }
        });

    }

    private void showLogin() {
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
                .setTitle("Login")
                .setView(input)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("Login", null)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                })
        ;

        final AlertDialog dialog = alertDialogBuilder.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!noteService.login(input.getText().toString())) {
                    Toast.makeText(
                            MainActivity.this, "Incorrect password!", Toast.LENGTH_SHORT
                    ).show();
                } else {
                    dialog.dismiss();
                    setupActivityContent();
                }
            }
        });

    }

    private void setupToolbarAndFab() {
        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // @TODO: optimise content changes
        setupActivityContent();
    }

    private void setupActivityContent() {
        final NoteService noteService = ParanoidNotes.fromContext(this).getNoteService();
        try {
            final List<NoteItem> noteList = noteService.getNoteList();

            adapter = new NoteItemAdapter(this, noteList);
            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    NoteItem currentNote = noteList.get(position);

                    Intent detailIntent = new Intent(MainActivity.this, NewNoteActivity.class);
                    detailIntent.putExtra("title", currentNote.getId());

                    startActivity(detailIntent);
                }
            });
        } catch (Exception e) {
            Toast.makeText(
                    MainActivity.this, "Failed getting notes!", Toast.LENGTH_SHORT
            ).show();
        }
    }
}
