package com.example.rodneytressler.contentproviderpractice;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rodneytressler on 12/27/17.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private Cursor personList;

    public PersonAdapter() {
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
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        if(personList.moveToPosition(position)) {
            holder.personName.setText(personList.getString(personList.getColumnIndexOrThrow(Person.COLUMN_NAME)));
            holder.personAge.setText(personList.getString(personList.getColumnIndexOrThrow(Person.COLUMN_AGE)));
        }
    }

    @Override
    public int getItemCount() {
        return personList == null ? 0 : personList.getCount();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {

        private TextView personName;

        private TextView personAge;

        public PersonViewHolder(View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.text_name);
            personAge = itemView.findViewById(R.id.text_age);
        }

    }
}