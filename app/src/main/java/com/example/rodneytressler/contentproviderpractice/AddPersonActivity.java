package com.example.rodneytressler.contentproviderpractice;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPersonActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText ageInput;
    private Button addPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        nameInput = findViewById(R.id.input_name);
        ageInput = findViewById(R.id.input_age);
        addPerson = findViewById(R.id.button_add);
        listenForButtonPress();
    }

    private void listenForButtonPress() {
        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameInput.length() > 0 && ageInput.length() > 0) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Person.COLUMN_NAME, nameInput.getText().toString());
                    contentValues.put(Person.COLUMN_AGE, ageInput.getText().toString());

                    Uri uri = getContentResolver().insert(PersonContentProvider.URI_PERSON, contentValues);
                } else {
                    toastFailure();
                }
            }
        });
    }

    private void toastFailure() {
        Toast.makeText(this, "Please input all fields first!", Toast.LENGTH_SHORT).show();
    }
}
