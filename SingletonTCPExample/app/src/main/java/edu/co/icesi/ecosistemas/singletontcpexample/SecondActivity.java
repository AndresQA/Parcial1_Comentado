package edu.co.icesi.ecosistemas.singletontcpexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class SecondActivity extends AppCompatActivity implements Observer {

    private String color;
    private TextView num;

    //Esta clase es observadora de la clase main, o la actividad inicial
    //Esta clase recibe el cambio de color en la clase inicial y lo guarda en la variabe color.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent i = getIntent();
        color = i.getStringExtra("color");

        num = findViewById(R.id.tv_second_num);

        //Aquí no se como explicarlo xd

        SingletonComunicacion com = SingletonComunicacion.getInstance();
        com.addObserver(this);

        //Se inicializa el boton, el cual recibe las coordenadas de valX y valY mas el color recibido por medio del singleton(esta bien?)
        Button btnSend = findViewById(R.id.btn_second_send);

        // el boton recibe las variables de texto y las convierte a strings,  para poderlas enviar por medio de la clase de comunicación global o singleton.
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edtX = findViewById(R.id.edt_second_x);
                EditText edtY = findViewById(R.id.edt_second_y);
                String valX = edtX.getText().toString();
                String valY = edtY.getText().toString();
                SingletonComunicacion.getInstance().enviar(valX+":"+valY+":"+color);
                finish();
            }
        });
    }

    //Este metodo permite que los observadores sepan acerca de un cambio y cuál será,
    //
    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof String){
            String m = (String)o;
            String[] partes = m.split(":");
            if(m.contains("num") && partes.length == 2){
                final int value = Integer.parseInt(partes[1]);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        num.setText(""+value);
                    }
                });
            }
        }
    }
}
