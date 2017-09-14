package com.example.martynasb.paranoidnotes;

import java.util.UUID;

public class NoteItem {
    private String title;
    private String body;
    private String id;

    public NoteItem(String title, String body) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }

    public String getId() {
        return id;
    }
}
