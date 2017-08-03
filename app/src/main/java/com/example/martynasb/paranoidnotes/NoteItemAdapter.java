package com.example.martynasb.paranoidnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteItemAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<NoteItem> mDataSource;

    public NoteItemAdapter(Context context, ArrayList<NoteItem> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    // @TODO: optimize. inflate is costly (?)
    public View getView(int position, View convertView, ViewGroup parent) {
        mInflater = (LayoutInflater) mContext
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = mInflater.inflate(R.layout.list_item_note, parent, false);

        TextView titleTextView = (TextView) rowView.findViewById(R.id.list_item_note_title);

        NoteItem note = (NoteItem) getItem(position);

        titleTextView.setText(note.getTitle());

        return rowView;
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
 }
