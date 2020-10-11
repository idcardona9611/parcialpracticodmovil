package com.example.parcialandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parcialandroid.data.ActividadModel;
import com.example.parcialandroid.data.Actividades;
import com.example.parcialandroid.data.MateriaModel;
import com.example.parcialandroid.data.Materias;
import com.example.parcialandroid.data.MateriasDBHelper;
import com.example.parcialandroid.data.adapters.ActividadAdapter;
import com.example.parcialandroid.data.adapters.MateriasAdapter;

import java.util.LinkedList;

public class detalleMateria extends AppCompatActivity {

    int CorteSelect = 1;
    int Materia, positionEdit;
    LinkedList<ActividadModel> listaActividad = new LinkedList<>();
    TextView percent;
    TextView nombreActividad;
    ListView lv;
    MateriasDBHelper materiasDBHelper;
    int actividadSelectedId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_materia);
        nombreActividad = findViewById(R.id.nombre_actividad);
        percent =  findViewById(R.id.percent_actividad);
        materiasDBHelper = new MateriasDBHelper(this);
        Intent intent = getIntent();
        Materia = intent.getIntExtra("materia", 0);
        getSupportActionBar().setTitle(intent.getStringExtra("nombre"));
        lv = findViewById(R.id.lista_actividades);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               ActividadModel actividad = listaActividad.get(position);
                positionEdit = position;
               actividadSelectedId = actividad.ID;
               nombreActividad.setText(actividad.nombreActividad);
               percent.setText(String.valueOf(actividad.Percent));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.displayDatabaseInfo();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.segundo_corte:
                if (checked)
                   CorteSelect = 2;
                    break;
            case R.id.tercer_corte:
                if (checked)
                    CorteSelect = 3;
                    break;
            default:
                CorteSelect = 1;
                break;
        }
        this.displayDatabaseInfo();
    }

    public void guardarActividad(View view) {
        String name = nombreActividad.getText().toString().trim();
        Integer percentActividad = Integer.parseInt(percent.getText().toString().trim());
        if(percentMayorQueCien() || percentActividad > 100) {
            Toast.makeText(this, "porcentaje mayor a 100%", Toast.LENGTH_SHORT).show();
        }
        else if (name.length() == 0) {
            Toast.makeText(this, "falta el nombre de la actividad", Toast.LENGTH_SHORT).show();
        }
        else {
            guardarMateria();
            displayDatabaseInfo();
            clearView();
        }
    }

    private boolean percentMayorQueCien() {
        int total = 0;
        for(ActividadModel item: listaActividad) {
            total = total + item.Percent;
        }
        total = total + Integer.parseInt(percent.getText().toString().trim());
        return total > 100;
    }

    private void guardarMateria(){
        String name = nombreActividad.getText().toString().trim();
        Integer percentActividad = Integer.parseInt(percent.getText().toString().trim());
        SQLiteDatabase db = materiasDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Actividades.ActividadesEntry.COLUMN_ACTIVIDAD_NAME, name);
        values.put(Actividades.ActividadesEntry.COLUMN_CORTE, CorteSelect);
        values.put(Actividades.ActividadesEntry.COLUMN_PERCENT, percentActividad.toString());
        values.put(Actividades.ActividadesEntry.COLUMN_IDMATERIA, Materia);
        long id = db.insert(Actividades.ActividadesEntry.TABLE_NAME, null, values);
        if( id == -1 ) Toast.makeText(this,"Error guardando actividad",Toast.LENGTH_SHORT).show();
        else Toast.makeText(this,"Acvidad guardada", Toast.LENGTH_SHORT).show();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = this.materiasDBHelper.getReadableDatabase();
        listaActividad.removeAll(listaActividad);

        String[] projection = {
                Actividades.ActividadesEntry._ID,
                Actividades.ActividadesEntry.COLUMN_ACTIVIDAD_NAME,
                Actividades.ActividadesEntry.COLUMN_PERCENT,
                Actividades.ActividadesEntry.COLUMN_IDMATERIA,
                Actividades.ActividadesEntry.COLUMN_CORTE
        };

        Cursor cursor = db.query(
                Actividades.ActividadesEntry.TABLE_NAME,
                projection,            // The columns to return
                "materia = ? AND corte = ?",
                new String[]{ String.valueOf(Materia), String.valueOf(CorteSelect) },
                null,
                null,
                null,
                null
        );

        try {
            int idColumnIndex = cursor.getColumnIndex(Actividades.ActividadesEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(Actividades.ActividadesEntry.COLUMN_ACTIVIDAD_NAME);
            int percentColumnIndex = cursor.getColumnIndex(Actividades.ActividadesEntry.COLUMN_PERCENT);
            int materiaColumnIndex = cursor.getColumnIndex(Actividades.ActividadesEntry.COLUMN_IDMATERIA);
            int corteColumnIndex = cursor.getColumnIndex(Actividades.ActividadesEntry.COLUMN_CORTE);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentMateria = cursor.getInt(materiaColumnIndex);
                int currentPercent =  cursor.getInt(percentColumnIndex);
                int currentCorte = cursor.getInt(corteColumnIndex);

                listaActividad.add(new ActividadModel(
                        currentID,
                        currentName,
                        currentCorte,
                        currentMateria,
                        currentPercent
                ));
            }

                ActividadAdapter adapter = new ActividadAdapter(this, listaActividad);
                ListView lv = (ListView) findViewById(R.id.lista_actividades);
                lv.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this, "Ocurrio un fallo", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteActividad() {
        SQLiteDatabase db = materiasDBHelper.getWritableDatabase();
        long id = db.delete(Actividades.ActividadesEntry.TABLE_NAME,"_ID = ?", new String[]{ String.valueOf(actividadSelectedId) });
        if( id == -1 ) Toast.makeText(this,"Error borrando la actividad",Toast.LENGTH_SHORT).show();
        else Toast.makeText(this,"actividad borrada", Toast.LENGTH_SHORT).show();
        actividadSelectedId = 0;
    }

    public void borrarActividad(View view) {
        this.deleteActividad();
        this.displayDatabaseInfo();
        clearView();
    }

    public void actualizarActvidad(View view) {
        String name = nombreActividad.getText().toString().trim();
        Integer percentActividad = Integer.parseInt(percent.getText().toString().trim());
        int anterior = listaActividad.get(positionEdit).Percent;
        listaActividad.get(positionEdit).Percent = 0;
        if(percentMayorQueCien() || percentActividad > 100) {
            Toast.makeText(this, "porcentaje mayor a 100%", Toast.LENGTH_SHORT).show();
            listaActividad.get(positionEdit).Percent = anterior;
        }
        else if (name.length() == 0) {
            Toast.makeText(this, "falta el nombre de la actividad", Toast.LENGTH_SHORT).show();
            listaActividad.get(positionEdit).Percent = anterior;
        }
        else {
            actualizarActividadEnDb();
            displayDatabaseInfo();
            clearView();
        }
    }

    public void clearView() {
        percent.setText("");
        nombreActividad.setText("");
    }

    private void actualizarActividadEnDb() {
        String name = nombreActividad.getText().toString().trim();
        Integer percentActividad = Integer.parseInt(percent.getText().toString().trim());
        SQLiteDatabase db = materiasDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Actividades.ActividadesEntry.COLUMN_ACTIVIDAD_NAME, name);
        values.put(Actividades.ActividadesEntry.COLUMN_CORTE, CorteSelect);
        values.put(Actividades.ActividadesEntry.COLUMN_PERCENT, percentActividad.toString());
        values.put(Actividades.ActividadesEntry.COLUMN_IDMATERIA, Materia);
        long id = db.update(Actividades.ActividadesEntry.TABLE_NAME, values,"_ID = ?", new String[]{ String.valueOf(actividadSelectedId)});
        actividadSelectedId = 0;
    }
}