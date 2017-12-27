package com.example.rodneytressler.contentproviderpractice;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by rodneytressler on 12/27/17.
 */

@Database(version = 1, entities = {Person.class})
public abstract class PersonDatabase extends RoomDatabase{

    public abstract PersonDao personDao();

    private static PersonDatabase INSTANCE;

    public static PersonDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PersonDatabase.class, "data-base")
                    .build();
        }
        return INSTANCE;
    }
}
