package com.example.martynasb.paranoidnotes;

import org.assertj.core.util.Compatibility;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class NoteServiceUnitTests {
    private NoteService noteService;
    private TestStorage storage;
    private NoteItem note;

    @Before
    public void setUp() throws Exception {
        storage = new TestStorage();
        noteService = new NoteService(storage);
        note = new NoteItem("Title1", "Body1");
    }

    @Test
    public void isNotLoggedInByDefault() throws Exception {
        assertThat(noteService.isLoggedIn()).isFalse();
        assertThat(noteService.getNoteList()).isEmpty();
    }

    @Test
    public void loginDoesntWorkWithIncorrectPasswordAndReturnsFalseAndEmptyList() throws Exception {
        assertThat(noteService.login("IncorrectPassword")).isFalse();
        assertThat(noteService.getNoteList()).isEmpty();
    }

    @Test
    public void firstTimeOpenApp() throws Exception {
        assertThat(noteService.isFirstTimeUser()).isTrue();

        noteService.registerNewUser("CorrectNewPassword");
        assertThat(noteService.isLoggedIn()).isTrue();
        assertThat(noteService.isFirstTimeUser()).isFalse();

        assertThat(noteService.login("CorrectNewPassword")).isTrue();
    }

    @Test
    public void hasNeverLoggedInShouldWhenStorageIsEmpty() {
        storage.acceptPassword("CorPas");
        storage.addNoteItem(note, "CorPas");
        assertThat(noteService.isFirstTimeUser()).isFalse();
    }

    @Test
    public void ableToLoginWhenStorageAcceptsPassword() {
        storage.acceptPassword("CorrectPassword");
        assertThat(noteService.login("CorrectPassword")).isTrue();
    }

    @Test
    public void notSaveNotesWhenNotLoggedIn() throws Exception {
        noteService.addNote(note);
        assertThat(noteService.getNoteList()).isEmpty();
    }

    @Test
    public void getNotesWhenLoggedIn() throws Exception {
        storage.acceptPassword("CorrectPassword");
        noteService.registerNewUser("CorrectPassword");
        assertThat(noteService.isLoggedIn()).isTrue();

        noteService.addNote(note);
        assertThat(noteService.getNoteList()).isNotEmpty();
    }

    @Test
    public void loggedInUserThroughStorageIsLoggedIn() throws Exception {
        storage.acceptPassword("CorrectPassword");
        assertThat(noteService.isLoggedIn()).isFalse();
        assertThat(noteService.login("CorrectPassword")).isTrue();
        assertThat(noteService.isLoggedIn()).isTrue();
    }

    @Test
    public void addNoteAndGetListTalkDirectlyWithStorage() {
        storage.acceptPassword("CorPas");
        noteService.login("CorPas");

        noteService.addNote(note);
        try {
            assertThat(storage.getNoteList("CorPas")).containsExactly(note);
        } catch(Exception e) {
            System.err.println("Failed get not list: " + e.toString());
        }
    }

    @Test
    public void getNoteListShouldReturnEmptyListWhenNotLoggedIn() throws Exception {
        storage.acceptPassword("CorPas");
        storage.addNoteItem(note, "CorPas");
        assertThat(noteService.isLoggedIn()).isFalse();
        assertThat(noteService.getNoteList()).isEmpty();
    }

    @Test
    public void getNoteByIdReturnsNoteWhenItExists() {
        String password = "123";

        storage.acceptPassword(password);
        storage.addNoteItem(note, password);
        noteService.login(password);

        assertThat(noteService.getNoteById(note.getId())).isEqualTo(note);
    }

    @Test
    public void getNoteByIdReturnsEmptyNoteWhenOneIsNotExistant() {
        String password = "123";

        storage.acceptPassword(password);
        storage.addNoteItem(note, password);
        noteService.login(password);

        NoteItem gotNote = noteService.getNoteById("other-id");
        assertThat(gotNote.getTitle()).isEqualTo("");
        assertThat(gotNote.getBody()).isEqualTo("");
    }
}
