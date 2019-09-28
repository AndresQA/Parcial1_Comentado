import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

public class Comunicacion extends Observable implements Runnable {
	
	private ServerSocket ss;
	private Socket s;
	private DataInputStream entrada;
	private DataOutputStream salida;
	
	//se establece la comunicacion
	public Comunicacion() {		
		try {
			//se abre el servidor en el puerto 5000
			ss = new ServerSocket(5000);
			//se usa para aceptar la solicitud entrante al socket
			s = ss.accept();
			//Se inicializa el DataInputStream para Leer datos o informacion del cliente
			entrada = new DataInputStream(s.getInputStream());
			//Se inicializa el DataOutputStream para Escribe datos o informacion desde el servidor al cliente
			salida = new DataOutputStream(s.getOutputStream());
			//Si todo esto se ha cumplido con exito imprime "conectado en consola"
			System.out.println("CONECTADO");
		} catch (IOException e) {			
			e.printStackTrace();
		}			
	}
	
	//Se crea un hilo para regular la conexion
	@Override
	public void run() {
		//Bucle para determinar si ha llegado un nuevo mensaje
		while(true) {			
			try {
				//llama a la funcion recibir
				recibir();
				//pone a mimir el hilo durante 100 milisegundos
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
	}

	private void recibir() throws IOException {
		//Almacena los datos enviado por el cliente en una variable string
		String mensaje = entrada.readUTF();
		//indica que observable ha cambiado
		setChanged();
		//notifica el cambio a la clase obserserver que seria MainApp
		notifyObservers(mensaje);
		//Borra el indicador de cambio de observable
		clearChanged();		
	}
	
	//Envia un mensaje al servidor en este caso la cantidad de cuadros que hay en el lienzo, podemos ver que el constructor recibe un string mensaje que es enviado desde el MainApp
	public void enviar(String mensaje) {
		try {
			//Envia el mensaje al cleinte mediante la variable salida que es un DataOutputStream
			salida.writeUTF(mensaje);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

}
