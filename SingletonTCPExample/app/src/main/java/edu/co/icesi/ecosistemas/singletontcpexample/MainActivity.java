package edu.co.icesi.ecosistemas.singletontcpexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private TextView numberOfRects;
    private String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se implementa la interfaz Observable
        numberOfRects = findViewById(R.id.tv_num_main);
        //Se inicializa la comunicación global para que esta clase main sea observable
        SingletonComunicacion.getInstance();
        SingletonComunicacion.getInstance().addObserver(this);

        System.out.println("INSTANCIA:" + SingletonComunicacion.getInstance());


        //Aqui se inician las variables como botones para luego darles un valor que generaran
        //Al realizar una accion, en este caso onClick
        Button btnRed = findViewById(R.id.btn_main_red);
        Button btnGreen = findViewById(R.id.btn_main_green);
        Button btnBlue = findViewById(R.id.btn_main_blue);

        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = "rojo";
            }
        });

        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = "verde";
            }
        });

        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = "azul";
            }
        });

        Button btnSend = findViewById(R.id.btn_main_go);

        //En esta sección el boton avisa sobre que fue pulsado y cambia de actividad
        // Además envía la variable string Color con un valor
        //el metodo putExtra() funciona para enviar la variable Color de esta actividad a la SecondActivity
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SecondActivity.class);
                i.putExtra("color",color);
                startActivity(i);
               /* i.putExtra("apellido",color);
                i.putExtra("nombre",color);
                i.putExtra("telefono",color);
                i.putExtra("nota",color);*/
            }
        });

    }
    //En este metodo se crea una instancia de o para pasarlo a String
    //De esta forma se separa los strings con split y se crea un hilo Runnable
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
                        numberOfRects.setText(""+value);
                    }
                });
            }
        }
    }
}
