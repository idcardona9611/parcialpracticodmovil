package com.example.parcialandroid.data;

import android.provider.BaseColumns;

public final class Actividades {
    private Actividades() {

    }

    public static final class ActividadesEntry implements BaseColumns {
        public final static String TABLE_NAME = "actividades";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_ACTIVIDAD_NAME = "name";
        public final static String COLUMN_PERCENT = "percent";
        public final static String COLUMN_IDMATERIA = "materia";
        public final static String COLUMN_CORTE = "corte";
        public final static String REFERENCE = " FOREIGN KEY ("+COLUMN_IDMATERIA+") REFERENCES "+ Materias.MateriasEntry.TABLE_NAME +"("+ Materias.MateriasEntry._ID +"))";
    }
}
