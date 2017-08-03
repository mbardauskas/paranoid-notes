package com.example.martynasb.paranoidnotes;

public class NoteItem {
    private String title;
    private String body;
    private String id;

    public NoteItem(String title, String body) {
        this.id = "asd";
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
