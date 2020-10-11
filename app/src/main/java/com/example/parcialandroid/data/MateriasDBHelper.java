package com.example.parcialandroid.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MateriasDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "materias.db";
    private static final int DATABASE_VERSION = 3;

    public MateriasDBHelper(Context context) {
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_MATERIAS_TABLE = "CREATE TABLE " + Materias.MateriasEntry.TABLE_NAME +" ("
                + Materias.MateriasEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Materias.MateriasEntry.COLUMN_MATERIA_NAME + " TEXT NOT NULL" + ")";
        String SQL_CREATE_ACTIVIDADES_TABLE = "CREATE TABLE " + Actividades.ActividadesEntry.TABLE_NAME + " ("
                + Actividades.ActividadesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Actividades.ActividadesEntry.COLUMN_ACTIVIDAD_NAME + " TEXT NOT NULL, "
                + Actividades.ActividadesEntry.COLUMN_CORTE + " INTEGER NOT NULL ,"
                + Actividades.ActividadesEntry.COLUMN_PERCENT + " INTERGER NOT NULL,"
                + Actividades.ActividadesEntry.COLUMN_IDMATERIA + " INTEGER,"
                + Actividades.ActividadesEntry.REFERENCE;
        db.execSQL(SQL_CREATE_MATERIAS_TABLE);
        db.execSQL(SQL_CREATE_ACTIVIDADES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
