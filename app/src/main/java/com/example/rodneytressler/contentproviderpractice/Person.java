package com.example.rodneytressler.contentproviderpractice;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.provider.BaseColumns;

/**
 * Created by rodneytressler on 12/27/17.
 */

@Entity(tableName = Person.TABLE_NAME)
public class Person {

    public static final String TABLE_NAME = "people";

    public static final String COLUMN_ID = BaseColumns._ID;

    public static final String COLUMN_NAME = "name";

    public static final String COLUMN_AGE = "age";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

    @ColumnInfo(name = COLUMN_NAME)
    public String name;

    @ColumnInfo(name = COLUMN_AGE)
    public int age;


    public static Person fromContentValues(ContentValues values) {
        final Person person  = new Person();
        if (values.containsKey(COLUMN_ID)) {
            person.id = values.getAsLong(COLUMN_ID);
        }
        if (values.containsKey(COLUMN_NAME)) {
            person.name = values.getAsString(COLUMN_NAME);
        }

        if(values.containsKey(COLUMN_AGE)) {
            person.age = values.getAsInteger(COLUMN_AGE);
        }
        return person;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
