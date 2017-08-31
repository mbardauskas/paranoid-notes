package com.example.martynasb.paranoidnotes;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ParanoidStorageUnitTests {
    private ParanoidStorage storage;
    private Context context;

    final private Context fileNotFountContext = new MockContext() {
        @Override
        public FileInputStream openFileInput(String name) throws FileNotFoundException {
            throw new FileNotFoundException();
        }
    };

    final private Context fileFoundAndNotEmptyContext = new MockContext() {
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

    final private Context fileFoundAndEmptyContext = new MockContext() {
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


    @Before
    public void setUp() throws Exception {
        storage = new ParanoidStorage(this.context);
    }

    @Test
    public void isEmptyByDefault() throws Exception {
        storage = new ParanoidStorage(fileNotFountContext);
        assertThat(storage.isEmpty()).isTrue();
    }

    @Test
    public void isNotEmptyWhenFileHasBeenCreatedAndContainsSomething() throws Exception {
        storage = new ParanoidStorage(fileFoundAndNotEmptyContext);
        assertThat(storage.isEmpty()).isFalse();
    }

    @Test
    public void isEmptyWhenFileHasBeenCreatedButIsEmpty() throws Exception {
        storage = new ParanoidStorage(fileFoundAndEmptyContext);
        assertThat(storage.isEmpty()).isTrue();
    }
}
