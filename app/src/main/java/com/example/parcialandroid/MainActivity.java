package com.example.parcialandroid;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.parcialandroid.data.MateriaModel;
import com.example.parcialandroid.data.Materias;
import com.example.parcialandroid.data.MateriasDBHelper;
import com.example.parcialandroid.data.adapters.MateriasAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    LinkedList<MateriaModel> materiasList = new LinkedList<>();
    MateriasDBHelper materiasDBHelper;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        materiasDBHelper = new MateriasDBHelper(this);
        lv =  findViewById(R.id.lista_materias);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, detalleMateria.class);
                intent.putExtra("materia", materiasList.get(position).ID);
                intent.putExtra("nombre", materiasList.get(position).nombre);
                startActivity(intent);
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrarMateria.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = this.materiasDBHelper.getReadableDatabase();
        materiasList.removeAll(materiasList);

        String[] projection = {
            Materias.MateriasEntry._ID,
            Materias.MateriasEntry.COLUMN_MATERIA_NAME
        };

        Cursor cursor = db.query(
                Materias.MateriasEntry.TABLE_NAME,
                projection,            // The columns to return
                null,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int idColumnIndex = cursor.getColumnIndex(Materias.MateriasEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(Materias.MateriasEntry.COLUMN_MATERIA_NAME);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                materiasList.add(new MateriaModel(currentID, currentName));
            }
            if(materiasList.size() > 0) {
                MateriasAdapter adapter = new MateriasAdapter(this, materiasList);
                ListView lv = (ListView) findViewById(R.id.lista_materias);
                lv.setAdapter(adapter);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Ocurrio un fallo", Toast.LENGTH_SHORT).show();
        }
    }
}
