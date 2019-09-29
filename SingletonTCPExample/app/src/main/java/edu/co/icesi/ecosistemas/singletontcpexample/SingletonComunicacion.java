package edu.co.icesi.ecosistemas.singletontcpexample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

public class SingletonComunicacion extends Observable implements Runnable{

    private static SingletonComunicacion ref;

    //Creacion de variables
    private Socket s;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private boolean conectado;

    private SingletonComunicacion(){
        conectado = false;
    }

    //Se inicializa el hilo de la comunicaion
    public static SingletonComunicacion getInstance() {
        if( ref == null ){
            ref = new SingletonComunicacion();
            new Thread(ref).start();
            System.out.println("INICIADO");
        }
        return ref;
    }

    //En este hilo se crea un la conexión  en el puerto 5000 mientras la conexion no esté habilitada
    // Una vez conectado, el hilo recibe el mensaje y descansa durante 1s para reiniciar el proceso.

    @Override
    public void run() {
        if(!conectado){
            try {
                InetAddress ip = InetAddress.getByName("10.0.2.2");
                s = new Socket(ip, 5000);
                entrada = new DataInputStream(s.getInputStream());
                salida = new DataOutputStream(s.getOutputStream());
                conectado = true;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        while(true){
            try {
                recibir();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //Si la conexión está habilitada, se guarda el mensaje, se notifica a los observadores
    // y se limpia el mmensaje
    private void recibir() throws IOException {
        if(conectado){
            String mensajeEntrada = entrada.readUTF();
            setChanged();
            notifyObservers(mensajeEntrada);
            clearChanged();
        }
    }

    //en este metodo, el servidor envia un mensaje al cliente si la conexión es aceptada mediante el UTF

    public void enviar(final String mensaje){
        if(conectado) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        salida.writeUTF(mensaje);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
