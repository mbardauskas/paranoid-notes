package com.example.martynasb.paranoidnotes;

import android.content.Context;
import android.test.mock.MockContext;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

public class ParanoidStorageUnitTests {
    private ParanoidStorage storage;
    private TextEncryptor encryptor;

    @Before
    public void setUp() throws Exception {
        encryptor = new TestEncryptor();
    }
    @Test
    public void isEmptyByDefault() throws Exception {
        storage = new ParanoidStorage(fileNotFoundContent, encryptor);
        assertThat(storage.isEmpty()).isTrue();
    }

    @Test
    public void isNotEmptyWhenFileHasBeenCreatedAndContainsSomething() throws Exception {
        storage = new ParanoidStorage(fileFoundAndNotEmptyContent, encryptor);
        assertThat(storage.isEmpty()).isFalse();
    }

    @Test
    public void isEmptyWhenFileHasBeenCreatedButIsEmpty() throws Exception {
        storage = new ParanoidStorage(fileFoundAndEmptyContent, encryptor);
        assertThat(storage.isEmpty()).isTrue();
    }

    @Test
    public void getNoteListThrowsWhenUnableToReadNotes() throws Exception {
        storage = new ParanoidStorage(fileFoundAndNotEmptyContent, encryptor);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                storage.getNoteList("321");
            }
        });
    }

    @Test
    public void getNoteListReturnsAListOfNotes() throws Exception {
        storage = new ParanoidStorage(fileFoundWithValidNoteItemJson, encryptor);
        assertThat(storage.getNoteList("123")).isNotEmpty();
    }


    final private Context fileNotFoundContent = new MockContext() {
        @Override
        public FileInputStream openFileInput(String name) throws FileNotFoundException {
            throw new FileNotFoundException();
        }
    };

    final private Context fileFoundAndNotEmptyContent = new MockContext() {
        @Override
        public FileInputStream openFileInput(String name) throws FileNotFoundException {
            final String filename = "some-file.txt";
            File mockedFile = new File(filename);
            FileOutputStream fos = new FileOutputStream(mockedFile);
            try {
                fos.write("hello world".getBytes());
                fos.close();
            } catch (Exception e) {
                System.err.println("failed write to some file" + e);
            }
            return new FileInputStream(filename);
        }
    };

    final private Context fileFoundWithValidNoteItemJson = new MockContext() {
        @Override
        public FileInputStream openFileInput(String name) throws FileNotFoundException {
            final String filename = "some-file.txt";
            File mockedFile = new File(filename);
            FileOutputStream fos = new FileOutputStream(mockedFile);
            try {
                fos.write("[{\"id\":\"asd\",\"title\":\"asd\",\"body\":\"asd\"}]".getBytes());
                fos.close();
            } catch (Exception e) {
                System.err.println("failed write to some file" + e);
            }
            return new FileInputStream(filename);
        }
    };

    final private Context fileFoundAndEmptyContent = new MockContext() {
        @Override
        public FileInputStream openFileInput(String name) throws FileNotFoundException {
            final String filename = "some-file.txt";
            File mockedFile = new File(filename);
            FileOutputStream fos = new FileOutputStream(mockedFile);
            try {
                fos.write("".getBytes());
                fos.close();
            } catch (Exception e) {
                System.err.println("failed write to some file" + e);
            }
            return new FileInputStream(filename);
        }
    };

}
