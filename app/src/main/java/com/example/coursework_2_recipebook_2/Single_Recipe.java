package com.example.coursework_2_recipebook_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Single_Recipe extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    int recipeID;
    int newRatingInt;
    String newRatingIntString;

    Cursor singleRecipeCursor, c;

    TextView nameTextView, ratingTextView, ingredientstextView, instructionsTextView, newRatingTextView;
    String recipeName, recipeIDString, recipeRatingString, recipeIngredient, recipeInstructions;
    int recipeRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single__recipe);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();


        nameTextView = findViewById(R.id.recipeNameTextView);
        ratingTextView = findViewById(R.id.ratingTextView);
        ingredientstextView = findViewById(R.id.ingredientstextView);
        instructionsTextView = findViewById(R.id.instructionsTextView);

        Bundle recipeBundle = getIntent().getExtras();
        recipeID = recipeBundle.getInt("recipeID");

        Log.d("g53mdp", "Checking value of recipeID:" + recipeID);

        recipeIDString = Integer.toString(recipeID);

        String [] recipeId = { recipeIDString };

        c = db.rawQuery("select r._id as recipe_id, r.name, r.instructions, r.rating, ri.ingredient_id, i.ingredientname "+
                "from Recipe r "+
                "join recipe_ingredients ri on (r._id = ri.recipe_id)"+
                "join allingredients i on (ri.ingredient_id = i._id) where r._id == ?", recipeId);

        recipeIngredient = "";
        recipeInstructions = "";
        if(c.moveToFirst()) {
            do {
                recipeName = c.getString(1);
                recipeRating = c.getInt(3);
                recipeInstructions = c.getString(2);
                recipeIngredient += c.getString(5) + "\n";
            }while(c.moveToNext());
        }
        Log.d("g53mdp", "Count: " + c.getCount());

        Log.d("g53mdp", "Value of receipeName: " + recipeName);
        Log.d("g53mdp", "Value of recipeIngredients: " + recipeIngredient);
        Log.d("g53mdp", "Value of recipeRating: " + recipeRating);
        Log.d("g53mdp", "Value of recipeInstructions: " + recipeInstructions);

        recipeRatingString = Integer.toString(recipeRating);
        nameTextView.setText(recipeName.toString());
        ratingTextView.setText(recipeRatingString);

        ingredientstextView.setText(recipeIngredient);
        instructionsTextView.setText(recipeInstructions);
    }

    public void deleteButtonOnClick(View v){

        db.delete("Recipe","_id = " + recipeID, new String[]{});

        //Handle this delete on return
        finish();

    }

    public void saveButtonOnClick(View v){
        newRatingTextView = findViewById(R.id.updateRatingField);
        newRatingIntString = newRatingTextView.getText().toString();


        newRatingInt = Integer.parseInt(newRatingIntString);

        Log.d("g53mdp", "newRating Int: " + newRatingInt);
        Log.d("g53mdp", "nrecipeIDString: " + recipeIDString);

        ContentValues cv = new ContentValues();
        cv.put("rating", newRatingInt);

        db.update("Recipe", cv, "_id = ?", new String[]{recipeIDString});

        ratingTextView.setText(newRatingIntString);

    }
}
