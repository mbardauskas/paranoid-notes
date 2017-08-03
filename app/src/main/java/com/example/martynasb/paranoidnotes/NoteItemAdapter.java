package com.example.martynasb.paranoidnotes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<NoteItem> dataSource;

    public NoteItemAdapter(Context context, ArrayList<NoteItem> items) {
        this.context = context;
        dataSource = items;
    }

    @Override
    public int getCount() {
        return dataSource.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_note, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.list_item_note_title);

        NoteItem note = getItem(position);

        titleTextView.setText(note.getTitle());

        return convertView;
    }

    @Override
    public NoteItem getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
