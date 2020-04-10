package ui;
import ui.Travel;

public class Main {
	public static void main(String[] args) {
		System.out.println("Bienvenido al sistema de control de 'El Pirata'.");
		Travel travel = new Travel();
		travel.menu();
	}
}