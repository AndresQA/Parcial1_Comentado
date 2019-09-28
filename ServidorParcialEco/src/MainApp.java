import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;

public class MainApp extends PApplet implements Observer {

	public static void main(String[] args) {
		PApplet.main("MainApp");
	}
	
	@Override
	public void settings() {
		size(500,500);
	}
	
	Comunicacion com;
	//Se crea un Arraylist de la clase cuadro para ir agregando los bloques de diferentes elementos como agua, tierra, arbol
	ArrayList<Cuadro> cuadros;
	
	@Override
	public void setup() {
		
		cuadros = new ArrayList<Cuadro>();
		//Se crea una instancia de la clase comunicacion
		com = new Comunicacion();
		//añade el observer
		com.addObserver(this);
		//Hilo para la comunicacion Servidor-cliente
		new Thread(com).start();		
	}
	
	@Override
	public void draw() {
		background(255);
		//un for para ir pintando los elementos que se van agregando dentro del arraylist
		for (int i = 0; i < cuadros.size(); i++) {
			cuadros.get(i).pintar(this);
		}
	}
	//
	@Override
	//Se llama cuando se produce un cambio en el estado de observable que seria la clase de comunicacion
	public void update(Observable arg0, Object o) {	
		//si el objeto es de tipo string entonces se ejecuta la accion. Instanceof sirve para conocer si un objeto es de un tipo determinado
		if(o instanceof String) {
			//almacena y castea al objeto en una variable tipo string llamada mensaje
			String mensaje = (String)o;
			//Se hace un arreglo del mensaje en el que el separador (por eso el split) sean los : (dos puntos)
			String[] partes = mensaje.split(":");
			//se lamacena en una variable la primera posicion (0) del arreglo de string pero se pasa a entero con un parseInt ya que esta variable corresponde a la posicion X del material.
			int x = Integer.parseInt(partes[0]);
			//se lamacena en una variable la segunda posicion (1) del arreglo de string pero se pasa a entero con un parseInt ya que esta variable corresponde a la posicion Y del material.
			int y = Integer.parseInt(partes[1]);
			//Se alamcena la tercera parte (2) del arreglo de string en una variable color no es necesario hacer casteo a entero ya que es un texto
			String color = partes[2];
			//Se agregan las partes al arraylist para pintar los elementos
			cuadros.add(new Cuadro(x, y, color));
			//Se envia informacion al cliente de la cantidad de cuadros o elementos que hay pintados en el lienzo actualmente, ademas de llamar al metodo enviar de la clase comunicacion ya que la variable COM esta instanciada
			com.enviar("num:"+cuadros.size());
		}
	}

}
