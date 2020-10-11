package com.example.parcialandroid.data;

import android.provider.BaseColumns;

public final class Materias {

    private Materias() {

    }

    public static final class MateriasEntry implements BaseColumns {
        public final static String TABLE_NAME = "materias";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_MATERIA_NAME = "name";
    }
}
