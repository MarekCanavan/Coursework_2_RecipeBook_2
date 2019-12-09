package com.example.coursework_2_recipebook_2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class New_Recipe extends AppCompatActivity {

    TextView nameText, ingredientsText, instructionsText, ratingsText;
    String nameTextString, ingredientsTextString, instructionsTextString, ratingsTextString, ingredientInTableString;
    ArrayList<String> allIngredients;
    String currentIngredientsArray[];

    int ratingsTextInt;
    int ingredientId;
    int recipeMaxID;
    int maxIngredientId;

    Boolean exists;

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

        /*Puts all of the ingredients from this new recipe into one array*/
        currentIngredientsArray = ingredientsTextString.split("\\r?\\n");


        db.execSQL("INSERT INTO Recipe (name, instructions, rating)" + "VALUES" +
                "('" + nameTextString + "','" + instructionsTextString+ "','" + ratingsTextInt + "');");

        Log.d("g53mdp", "New_Recipe, CheckIngredients Beging Called");

        checkIngredients();

        finish();

    }


    public void checkIngredients (){

        Log.d("g53mdp", "New_Recipe, In CheckIngredients");
        getAllIngredients();

        Log.d("g53mdp","All ingredients: " + allIngredients);

        /*Getting the maximum value from the last added recipe*/
        Cursor mCursorRecipeID = db.rawQuery("SELECT MAX(_id) AS _id FROM Recipe", null);

        Log.d("g53mdp", "Number of rows returned should be one: " + mCursorRecipeID.getCount());
        if(mCursorRecipeID.moveToFirst()){
            do {
                recipeMaxID = Integer.parseInt(mCursorRecipeID.getString(0));
            } while (mCursorRecipeID.moveToNext());
        }

        Log.d("g53mdp", "New_Recipe, recipeMaxID: " + recipeMaxID);

        for(String currentIngredient : currentIngredientsArray){
            Log.d("g53mdp", "Value of Current Ingredient: " + currentIngredient);

            exists = false;
            for(String singleIngredientFromAll : allIngredients){

                Log.d("g53mdp", "Value of Single Ingredient: " + singleIngredientFromAll);

                /*If the ingredient exists add the association*/
                if(currentIngredient.equals(singleIngredientFromAll)){

                    Log.d("g53mdp", "In the ingredient exists");
                    exists = true;


                    /*Getting the id of the ingredient that is in the database*/
                    Cursor mCursorIndegredientID = db.query("allingredients",
                            new String[]{"_id"}, "ingredientname=?",
                            new String[]{currentIngredient}, null, null, null);

                    if (mCursorIndegredientID.moveToFirst()){
                        do {
                            ingredientId = mCursorIndegredientID.getInt(0);
                        }while (mCursorIndegredientID.moveToNext());
                    }
                    /*Insert both values into the association table */
                    db.execSQL("INSERT INTO recipe_ingredients (recipe_id, ingredient_id)" + "VALUES" +
                            "('" + recipeMaxID + "','" + ingredientId + "');");
                }
            }
            /*Add to the Ingredients list and do association */
            if(exists == false ){

                Log.d("g53mdp", "New_Recipe, In exists = false");

                /*Insert the current ingredient*/
                db.execSQL("INSERT INTO allingredients (ingredientname)" + "VALUES" +
                        "('" + currentIngredient + "');");

                /*Get the max value of ingredients*/
                Cursor mCursorMaxIngredientID = db.rawQuery("SELECT MAX(_id) AS _id FROM allingredients", null);
                if(mCursorMaxIngredientID.moveToFirst()){
                    do{
                        maxIngredientId = mCursorMaxIngredientID.getInt(0);
                    }while(mCursorMaxIngredientID.moveToNext());
                }
                /*Insert both values into the association table */
                db.execSQL("INSERT INTO recipe_ingredients (recipe_id, ingredient_id)" + "VALUES" +
                        "('" + recipeMaxID + "','" + maxIngredientId + "');");

            }


        }
    }



    public void getAllIngredients (){

        allIngredients = new ArrayList<>();

        Log.d("g53mdp", "New_Recipe, In getAllIngredients ");

        Cursor ingredientCursor = db.query("allingredients", new String[] {"ingredientname"},
                null, null, null, null, null);

        if(ingredientCursor.moveToFirst()) {
            do {
                allIngredients.add(ingredientCursor.getString(0));
            }while(ingredientCursor.moveToNext());
        }
    }
}

