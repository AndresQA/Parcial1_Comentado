import processing.core.PApplet;

public class Cuadro {
	
	private int x;
	private int y;
	private String color;
	
	//el metodo cuadro recibe los parametros de coordenadas y color enviados desde el MainApp cuando se añadieron al arraylist
	public Cuadro(int x, int y, String color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	//Da el color dependiendo de la variable string color
	public void pintar(PApplet app) {
		switch(color) {
		case "rojo":
			app.fill(255,0,0);
			break;
		case "verde":
			app.fill(0,255,0);
			break;
		case "azul":
			app.fill(0,0,255);
			break;
		}
		app.rectMode(app.CENTER);
		app.rect(x, y, 50, 50);
	}	

}
