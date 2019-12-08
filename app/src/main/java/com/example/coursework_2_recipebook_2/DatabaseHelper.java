package com.example.coursework_2_recipebook_2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context){
        super(context, "recipeDB", null, 1);
    }

    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE Recipe (" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(128) NOT NULL," +
                "instructions VARCHAR(128) NOT NULL," +
                "rating INTEGER);");

        db.execSQL("CREATE TABLE allingredients (" +
                " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "ingredientname VARCHAR(128) NOT NULL);");

        db.execSQL("CREATE TABLE recipe_ingredients (" +
                " recipe_id INT NOT NULL," +
                " ingredient_id INT NOT NULL," +
                " CONSTRAINT fk1 FOREIGN KEY (recipe_id) REFERENCES recipes (_id)," +
                " CONSTRAINT fk2 FOREIGN KEY (ingredient_id) REFERENCES ingredients (_id)," +
                " CONSTRAINT _id PRIMARY KEY (recipe_id, ingredient_id) );");
    }



    /*On upgrade will be called if the verison number, is incremeneted from the previous version*/
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
