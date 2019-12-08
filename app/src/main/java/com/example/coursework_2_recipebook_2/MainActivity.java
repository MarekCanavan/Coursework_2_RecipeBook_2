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

import java.io.File;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    public ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();


        Cursor recipeCursor = db.query("Recipe", new String[] { "_id", "name", "instructions", "rating" },
                null, null, null, null, null);

        if(recipeCursor.moveToFirst()) {
            do {
                int id = recipeCursor.getInt(0);
                String name = recipeCursor.getString(1);
                String instructions = recipeCursor.getString(2);
                int rating = recipeCursor.getInt(3);
            }while(recipeCursor.moveToNext());
        }

        String[] columns = new String[] {"name", "rating"};
        int[] to = new int[] {R.id.titleTextView, R.id.ratingtTextView};
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this, R.layout.recipe_layout, recipeCursor, columns, to, 0);

        listView = (ListView) findViewById(R.id.recipeListView);
        listView.setAdapter(dataAdapter);

        Log.d("g53mdp", "test");
        listView.setAdapter(new ArrayAdapter<File>(this, android.R.layout.simple_list_item_1, list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {

                Log.d("g53mdp", "test");
                File selectedFromList = (File) (listView.getItemAtPosition(myItemInt));
                String selectedFromListString = selectedFromList.toString();

                Log.d("g53mdp", "Value fro, onClick: " + selectedFromListString);

                Intent oneRecipeIntent = new Intent(MainActivity.this, Single_Recipe.class);
                startActivity(oneRecipeIntent);
            }
        });
    }

    public void floatingActionOnClick(View v){
        Intent newRecipeIntent = new Intent(MainActivity.this, New_Recipe.class);
        startActivity(newRecipeIntent);
    }
}
