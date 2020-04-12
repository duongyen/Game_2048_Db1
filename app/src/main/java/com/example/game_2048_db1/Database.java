package com.example.game_2048_db1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GAME2048.db";
    private static final String TABLE_SCORE = "SCORE";
    private static final String TABLE_BOX = "BOX";

    private static final String SCORE = "SCORE";
    private static final String HIGH_SCORE = "HIGH_SCORE";
    private static final String COL_1 = "COL_1";
    private static final String COL_2 = "COL_2";
    private static final String COL_3 = "COL_3";
    private static final String COL_4 = "COL_4";
    private static final String ID = "ID";

    private static final int VERSION = 1;

    private static final String SQL_CREATE_TABLE_SCORE = "CREATE TABLE IF NOT EXISTS " + TABLE_SCORE
            + "(" + SCORE + " INTEGER, " + HIGH_SCORE + " INTEGER)";
    private static final String SQL_DELETE_TABLE_SCORE = "DROP TABLE IF EXISTS " + TABLE_SCORE;

    private static final String SQL_CREATE_TABLE_BOX = "CREATE TABLE IF NOT EXISTS " + TABLE_BOX
            + "(" + ID + "  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COL_1 + " INTEGER, "
            + COL_2 + " INTEGER, "
            + COL_3 + " INTEGER, "
            + COL_4 + " INTEGER)";
    private static final String SQL_DELETE_TABLE_BOX = "DROP TABLE IF EXISTS " + TABLE_BOX;


    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_SCORE);
        db.execSQL(SQL_CREATE_TABLE_BOX);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_SCORE);
        db.execSQL(SQL_DELETE_TABLE_BOX);
        onCreate(db);
    }


    public void saveScore(int score, int highScore) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query2 = "select * from " + TABLE_SCORE;
        Cursor c2 = db.rawQuery(query2, null);
        ContentValues contentValuesScore = new ContentValues();
        if (c2.getCount() != 0) {
            c2.moveToFirst();
            contentValuesScore.put(SCORE, score);
            contentValuesScore.put(HIGH_SCORE, highScore);

            db.update(TABLE_SCORE,contentValuesScore,null, null);
        } else {
            contentValuesScore.put(SCORE, score);
            contentValuesScore.put(HIGH_SCORE, highScore);

            db.insert(TABLE_SCORE, null, contentValuesScore);
        }


        db.close();
    }

    public void saveBox(int id, int co1, int col2, int col3, int col4) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query1 = "select * from " + TABLE_BOX + " where " + ID + " = " + id;
        Cursor c1 = db.rawQuery(query1, null);
        ContentValues contentValuesBox = new ContentValues();
        if (c1.getCount() != 0) {
            c1.moveToFirst();

            contentValuesBox.put(COL_1, co1);
            contentValuesBox.put(COL_2, col2);
            contentValuesBox.put(COL_3, col3);
            contentValuesBox.put(COL_4, col4);
            db.update(TABLE_BOX, contentValuesBox, ID + "=?", new String[]{String.valueOf(id)});
        } else {
            contentValuesBox.put(COL_1, co1);
            contentValuesBox.put(COL_2, col2);
            contentValuesBox.put(COL_3, col3);
            contentValuesBox.put(COL_4, col4);
            db.insert(TABLE_BOX, null, contentValuesBox);
        }

        db.close();
    }
    public int[] getScore() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SCORE, null);

        cursor.moveToLast();
        if (!cursor.moveToLast()) return null;

        int[] score = new int[2];
        score[0] = cursor.getInt(cursor.getColumnIndex(SCORE));
        score[1] = cursor.getInt(cursor.getColumnIndex(HIGH_SCORE));

        return score;
    }

    public int[][] getBox() {
        SQLiteDatabase db = this.getReadableDatabase();
        int[][] box = new int[4][4];
        int i = 0;
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BOX, null);
            cursor.moveToFirst();
            if(!cursor.moveToFirst())
                return null;
            if (cursor != null) {
                while (i < 4) {
                    box[i][0] = Integer.parseInt(cursor.getString(1));
                    box[i][1] = Integer.parseInt(cursor.getString(2));
                    box[i][2] = Integer.parseInt(cursor.getString(3));
                    box[i][3] = Integer.parseInt(cursor.getString(4));
                    i++;
                    cursor.moveToNext();
                }
            }

        } catch (Exception e) {

        }

        return box;
    }
}
