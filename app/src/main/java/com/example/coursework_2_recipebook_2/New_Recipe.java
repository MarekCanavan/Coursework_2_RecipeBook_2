package com.example.coursework_2_recipebook_2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class New_Recipe extends AppCompatActivity {

    TextView nameText, ingredientsText, instructionsText, ratingsText;
    String nameTextString, ingredientsTextString, instructionsTextString, ratingsTextString;
    int ratingsTextInt;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__recipe);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
    }

    public void saveButtonOnClick(View v){

        nameText = findViewById(R.id.nameText);
        ingredientsText = findViewById(R.id.ingredientsText);
        instructionsText = findViewById(R.id.instructionsText);
        ratingsText = findViewById(R.id.ratingsText);

        nameTextString = nameText.getText().toString();
        ingredientsTextString = ingredientsText.getText().toString();
        instructionsTextString = instructionsText.getText().toString();
        ratingsTextString = ratingsText.getText().toString();

        ratingsTextInt = Integer.parseInt(ratingsTextString);


        Log.d("g53mdp", "Value of ratings int " + ratingsTextInt);
        db.execSQL("INSERT INTO Recipe (name, instructions, rating)" + "VALUES" +
                "('" + nameTextString + "','" + instructionsTextString+ "','" + ratingsTextInt + "');");

        finish();

    }
}
