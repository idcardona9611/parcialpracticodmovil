package com.example.parcialandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parcialandroid.data.Materias;
import com.example.parcialandroid.data.MateriasDBHelper;

public class RegistrarMateria extends AppCompatActivity {

    TextView materiaName;
    MateriasDBHelper materiasDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_materia);
        getSupportActionBar().setTitle("Agregar Materia");
        materiaName = findViewById(R.id.nombre_materia);
        materiasDBHelper = new MateriasDBHelper(this);
    }

    public void guardarMateria(View view) {
        String name = materiaName.getText().toString().trim();
        if(name.length() > 0) this.guardarMateria();
        else Toast.makeText(this,"Nombre de materia no valido", Toast.LENGTH_SHORT).show();
    }

    private void guardarMateria(){
        String name = materiaName.getText().toString().trim();
        SQLiteDatabase db = materiasDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Materias.MateriasEntry.COLUMN_MATERIA_NAME, name);
        long id = db.insert(Materias.MateriasEntry.TABLE_NAME, null, values);
        if( id == -1 ) Toast.makeText(this,"Error guardando la materia",Toast.LENGTH_SHORT).show();
        else Toast.makeText(this,"Materia Guardada", Toast.LENGTH_SHORT).show();
    }
}
