package com.example.coursework_2_recipebook_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.File;

/*TO DO List
*Refresh entries when a new recipe is added - DONE
*Entries not repeated, Generally fix persistance and for loops - DONE
*Display a recipe - DONE
*Delete Entry - DONE
*Sort Entries by rating - DONE
*Do Rating on single entry - DONE
*Ontrim for the entries of ingredients
*Content Provider
* */
public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    int _id = 0;

    final static int NEW_RECIPE_REQUEST_CODE = 2;
    final static int SINGLE_RECIPE_DELETE_REQUEST_CODE = 3;

    public ListView listView = null;

    Cursor recipeCursor;

    Boolean sortBy = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();


        presentAllRecipes("_id");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {



                _id = Integer.parseInt(((TextView) myView.findViewById(R.id._id)).getText().toString());

                Intent oneRecipeIntent = new Intent(MainActivity.this, Single_Recipe.class);

                Bundle recipeBundle = new Bundle();
                recipeBundle.putInt("recipeID", _id);

                oneRecipeIntent.putExtras(recipeBundle);

                startActivityForResult(oneRecipeIntent, NEW_RECIPE_REQUEST_CODE);
            }
        });
    }

    public void floatingActionOnClick(View v){
        Intent newRecipeIntent = new Intent(MainActivity.this, New_Recipe.class);
        startActivityForResult(newRecipeIntent, NEW_RECIPE_REQUEST_CODE);
    }

    public void sortByOnClick(View v){
        if(sortBy == false){
            sortBy = true;
            presentAllRecipes("_id");
        }else{
            sortBy = false;
            presentAllRecipes("rating desc");
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_RECIPE_REQUEST_CODE){
            if(sortBy == false){
                presentAllRecipes("_id");
            }else{
                presentAllRecipes("rating desc");
            }
        }
        else if(requestCode == SINGLE_RECIPE_DELETE_REQUEST_CODE){
            if(sortBy == false){
                presentAllRecipes("_id");
            }else{
                presentAllRecipes("rating desc");
            }
        }
    }

    public void presentAllRecipes(String order){

        recipeCursor = db.query("Recipe", new String[] { "_id", "name", "instructions", "rating" },
                null, null, null, null, order);

        if(recipeCursor.moveToFirst()) {
            do {
                int id = recipeCursor.getInt(0);
                String name = recipeCursor.getString(1);
                String instructions = recipeCursor.getString(2);
                int rating = recipeCursor.getInt(3);
            }while(recipeCursor.moveToNext());
        }

        String[] columns = new String[] {"name", "rating", "_id"};
        int[] to = new int[] {R.id.titleTextView, R.id.ratingtTextView, R.id._id};
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this, R.layout.recipe_layout, recipeCursor, columns, to, 0);

        listView = (ListView) findViewById(R.id.recipeListView);
        listView.setAdapter(dataAdapter);
    }

    public void allIngredientsButtonClick(View v){
        Intent allIngredientsIntent = new Intent(MainActivity.this, All_Ingredients.class);
        startActivity(allIngredientsIntent);
    }

}
