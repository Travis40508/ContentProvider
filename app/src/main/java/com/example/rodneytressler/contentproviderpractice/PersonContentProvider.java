package com.example.rodneytressler.contentproviderpractice;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by rodneytressler on 12/27/17.
 */

public class PersonContentProvider extends ContentProvider {


    public static final String AUTHORITY = "com.example.rodneytressler.contentproviderpractice.provider";

    public static final Uri URI_PERSON = Uri.parse(
            "content://" + AUTHORITY + "/" + Person.TABLE_NAME
    );

    private static final int CODE_PERSON_DIR = 100;
    private static final int CODE_PERSON_ITEM = 101;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, Person.TABLE_NAME, CODE_PERSON_DIR);
        MATCHER.addURI(AUTHORITY, Person.TABLE_NAME + "/*", CODE_PERSON_ITEM);
    }



    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);
        if(code == CODE_PERSON_DIR || code == CODE_PERSON_ITEM) {
            final Context context = getContext();
            if(context == null) {
                return null;
            }

            PersonDao person = PersonDatabase.getInstance(context).personDao();
            final Cursor cursor;

            if(code == CODE_PERSON_DIR) {
                cursor = person.selectAll();
            } else {
                //THIS MIGHT BE A PROBLEM IN CASE OF PERSISTENT ERRORS
                return null;
            }

            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (MATCHER.match(uri)) {
            case CODE_PERSON_DIR :
                final Context context = getContext();
                if(context == null) {
                    return null;
                }
                final long id = PersonDatabase.getInstance(context).personDao().insert(Person.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_PERSON_ITEM :
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
