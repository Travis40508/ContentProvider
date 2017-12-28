package com.example.rodneytressler.contentproviderpractice;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PersonAdapter.Callback {

    private static final int LOADER_PEOPLE = 100;
    private PersonAdapter adapter;
    private RecyclerView recyclerView;
    private Button goToOtherActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        goToOtherActivity = findViewById(R.id.button_other_activity);
        adapter = new PersonAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        listenForButtonPress();

        getSupportLoaderManager().initLoader(LOADER_PEOPLE, null, mLoaderCallbacks);
    }

    private void listenForButtonPress() {
        goToOtherActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddPersonActivity.class));
            }
        });
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case LOADER_PEOPLE :
                            return new CursorLoader(getApplicationContext(),
                                    PersonContentProvider.URI_PERSON, new String[]{Person.COLUMN_NAME}, null, null, null);
                        default:
                            throw new IllegalArgumentException();
                    }
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    boolean cursorHasValidData = false;
                    if (data != null && data.moveToFirst()) {
                        cursorHasValidData = true;
                    }
                    if (!cursorHasValidData) {
                        return;
                    }
                    switch (loader.getId()) {
                        case LOADER_PEOPLE :
                            adapter.setList(data);
                            break;
                    }
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    switch (loader.getId()) {
                        case LOADER_PEOPLE :
                            adapter.setList(null);
                            break;
                    }
                }
            };

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOADER_PEOPLE, null, mLoaderCallbacks);
    }

    @Override
    public void itemLongPressed(int id) {
        String stringId = Integer.toString(id);
        Uri uri = PersonContentProvider.URI_PERSON;
        uri = uri.buildUpon().appendPath(stringId).build();

        getContentResolver().delete(uri, null, null);

        getSupportLoaderManager().restartLoader(LOADER_PEOPLE, null, mLoaderCallbacks);
        Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemPressed(int id, ContentValues contentValues) {
        String stringId = Integer.toString(id);
        Uri uri = PersonContentProvider.URI_PERSON;
        uri = uri.buildUpon().appendPath(stringId).build();

        getContentResolver().update(uri, contentValues, null, null);
        getSupportLoaderManager().restartLoader(LOADER_PEOPLE, null, mLoaderCallbacks);
        Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
    }
}
