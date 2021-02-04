package com.example.reproductormusica;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;
    Button botonCircular;
    int posicion=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botonCircular =(Button)findViewById(R.id.buttonCircular);
    }
    //@Override
    public boolean onCreateOptionMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_main,menu);
        return true;
    }
    public void destruir() {
        if (mp != null)
            mp.release();
    }

    public void iniciar(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numeros);
        mp.start();
        String op = botonCircular.getText().toString();
        if (op.equals("No reproducir en forma circular"))
            mp.setLooping(false);
        else
            mp.setLooping(true);
    }
    public void pausar(View v) {
        if (mp != null && mp.isPlaying()) {
            posicion = mp.getCurrentPosition();
            mp.pause();
        }
    }

    public void continuar(View v) {
        if (mp != null && mp.isPlaying() == false) {
            mp.seekTo(posicion);
            mp.start();
        }
    }

    public void detener(View v) {
        if (mp != null) {
            mp.stop();
            posicion = 0;
        }
    }

    public void circular(View v) {
        detener(null);
        String op = botonCircular.getText().toString();
        if (op.equals("No reproducir en forma circular"))
            botonCircular.setText("reproducir en forma circular");
        else
            botonCircular.setText("No reproducir en forma circular");
    }
}