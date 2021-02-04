package com.example.alamacenamientobddsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etCodigo,etDescripcion,etPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCodigo=(EditText)findViewById(R.id.textCodigo);
        etDescripcion=(EditText)findViewById(R.id.textDescricion);
        etPrecio=(EditText)findViewById(R.id.textPrecio);
    }
    //REGISTRAR
    public  void registrar(View view){
        AdminSQLiteOpenHeiper admin= new AdminSQLiteOpenHeiper(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String codigo= etCodigo.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        String precio = etPrecio.getText().toString();

        if(!codigo.isEmpty()&&!descripcion.isEmpty()&&!precio.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo",codigo);
            registro.put("descripcion",descripcion);
            registro.put("precio",precio);

            bd.insert("articulos",null,registro);
            bd.close();
            etPrecio.setText("");
            etDescripcion.setText("");
            etCodigo.setText("");

            Toast.makeText(this,"Registro completo",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,"Llena todos los cambios",Toast.LENGTH_SHORT).show();
        }
    }
    //CONSULTAR PRODUCTO
    public void buscar(View v){
        AdminSQLiteOpenHeiper admin = new AdminSQLiteOpenHeiper(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String codigo=etCodigo.getText().toString();

        if(!codigo.isEmpty()){
            Cursor fila=db.rawQuery("select descripcion, precio from articulos where codigo ="+codigo,null);

            if(fila.moveToFirst()){
                etDescripcion.setText(fila.getString(0));
                etPrecio.setText(fila.getString(1));

                db.close();
            }else {
                Toast.makeText(this, "Articulo no encontrado", Toast.LENGTH_SHORT).show();
                db.close();
            }
            }else{
                Toast.makeText(this,"Introduce el codigo del articulo",Toast.LENGTH_SHORT).show();
            }
        }
    //Eliminar producto
    public void eliminar(View view){
        AdminSQLiteOpenHeiper admin = new AdminSQLiteOpenHeiper
                (this, "administracion", null, 1);

        SQLiteDatabase db= admin.getWritableDatabase();
        String codigo=etCodigo.getText().toString();

        if(!codigo.isEmpty()){
            int cantidad=db.delete("articulos","codigo="+codigo,null);
            db.close();

            etCodigo.setText("");
            etDescripcion.setText("");
            etPrecio.setText("");

            if(cantidad==1){
                Toast.makeText(this,"Articulo Eliminado", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"El articulo no existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Introduce el código del artículo", Toast.LENGTH_SHORT).show();
        }
    }

    //Modificar el producto
    public void modificar(View view){
        AdminSQLiteOpenHeiper admin=new AdminSQLiteOpenHeiper
                (this,"administracion", null, 1);

        SQLiteDatabase db =admin.getWritableDatabase();

        String codigo=etCodigo.getText().toString();
        String descripcion=etDescripcion.getText().toString();
        String precio=etPrecio.getText().toString();

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro=new ContentValues();
            registro.put("codigo",codigo);
            registro.put("descripcion",descripcion);
            registro.put("precio",precio);

            int cantidad=db.update("articulos", registro, "codigo="+codigo,null);
            db.close();

            if(cantidad==1){
                Toast.makeText(this,"Se modifico el articulo correctamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"El articulo no existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}