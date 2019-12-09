package com.example.coursework_2_recipebook_2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class All_Ingredients extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    String ingredientName = null;
    public ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__ingredients);


        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();


        Cursor ingredientCursor = db.query("allingredients", new String[] { "_id", "ingredientname" },
                null, null, null, null, null);



        String[] columns = new String[] {"ingredientname"};
        int[] to = new int[] {R.id.ingredientLayoutTextView};
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this, R.layout.ingredient_layout, ingredientCursor, columns, to, 0);

        listView = (ListView) findViewById(R.id.allIngredientsListView);
        listView.setAdapter(dataAdapter);
    }
}
