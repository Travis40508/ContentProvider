package com.example.rodneytressler.contentproviderpractice;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rodneytressler on 12/27/17.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private final Callback callback;
    private Cursor personList;

    public PersonAdapter(Callback callback) {
        this.callback = callback;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new PersonViewHolder(itemView);
    }

    public void setList(Cursor personList) {
        this.personList = personList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder holder, final int position) {
        if(personList.moveToPosition(position)) {
            holder.personName.setText(personList.getString(personList.getColumnIndexOrThrow(Person.COLUMN_NAME)));
            holder.personAge.setText(personList.getString(personList.getColumnIndexOrThrow(Person.COLUMN_AGE)));

            final int idIndex = personList.getColumnIndex(Person.COLUMN_ID);
            final int id = personList.getInt(idIndex);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    callback.itemLongPressed(id);
                    return false;
                }
            });

            final ContentValues contentValues = new ContentValues();
            contentValues.put(Person.COLUMN_NAME, holder.personName.getText().toString() + " SUCKS!");
            contentValues.put(Person.COLUMN_AGE, holder.personAge.getText().toString());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.itemPressed(id, contentValues);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return personList == null ? 0 : personList.getCount();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {

        private TextView personName;

        private TextView personAge;

        public PersonViewHolder(final View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.text_name);
            personAge = itemView.findViewById(R.id.text_age);

        }

    }

    interface Callback {
        void itemLongPressed(int tag);

        void itemPressed(int id, ContentValues contentValues);
    }
}
