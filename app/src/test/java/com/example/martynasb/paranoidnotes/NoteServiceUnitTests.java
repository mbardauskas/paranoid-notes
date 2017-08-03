package com.example.martynasb.paranoidnotes;

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
        storage.addNoteItem(note);
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
        assertThat(storage.getNoteList()).containsExactly(note);
    }

    @Test
    public void getNoteListShouldReturnEmptyListWhenNotLoggedIn() {
        storage.addNoteItem(note);
        assertThat(noteService.isLoggedIn()).isFalse();
        assertThat(noteService.getNoteList()).isEmpty();
    }
}
