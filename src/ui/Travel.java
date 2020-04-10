package ui;
import java.util.Scanner;
import model.Ship;
import model.Client;
import model.Cargo;

public class Travel{
	// attributes
	Scanner sc = new Scanner(System.in);
	Ship ship = new Ship();
	boolean clientsCreated = false;
	boolean cargosCreated = false;
	int cargoQuantity = 0;
	
	// constructor
	public Travel(){
	}
	
	// methods
	
	/**
	This method is the main interface of the program. It allows the user to access the UI methods. <br>
	<b>pre:</b>
	<b>post:</b> The user will be shown a list of options. The method they choose will be executed. <br>
	*/
	public void menu(){
		System.out.println("\n Seleccione una opcion: \n 1. Ingresar la informacion de los clientes. \n 2. Cargar el barco. \n 3. Mostrar la informacion de la carga. \n 4. Mostrar la informacion del cliente. \n 5. Mostrar la informacion del barco \n 6. Hacer el viaje \n 7. Descargar el barco \n 0. Salir");
		
		switch(sc.nextInt()){
			case 1: createClients();
			break;
			
			case 2: loadShip();
			break;
			
			case 3: displayCargoMenu();
			break;
			
			case 4: displayClientMenu();
			break;
			
			case 5: displayShip();
			break;
			
			case 6: sail();
			break;
			
			case 7: unloadShip(true);
			break;
			
			case 0: System.out.println("Gracias por usar el sistema de control de 'El Pirata'.");
			break;
			
			default:
			System.out.println("Opcion no valida. \n");
			menu();
		}
	}
	
	/**
	This method allows the user to create a client by entering the respective data by console.<br>
	<b>pre:</b> <br>
	<b>post:</b> The client will have been created with the data specified by the user in each field. Then, the user is shown what rank the client entered belongs to.<br>
	*/
	public void createClients(){
		int i;
		
		if(clientsCreated == false){
			for(i=0; i<ship.getClients().length; i++){
				ship.getClients()[i] = new Client("", "", "", 0, 0, 0);
			}
		}
		
		for(i=0; i < ship.getClients().length; i++){
			System.out.println("Informacion del cliente #"+ (i+1) + ": \n");
			
			sc.nextLine();
			System.out.println("Ingrese el nombre del cliente:");
			ship.getClients()[i].setName(sc.nextLine());
			
			System.out.println("Ingrese el numero del registro mercantil para el cliente:");
			ship.getClients()[i].setRegisterNum(sc.nextLine());
			
			System.out.println("Ingrese la fecha de expedicion del registro mercantil (DD/MM/AAAA)):");
			ship.getClients()[i].setRegisterDate(sc.nextLine());
			
			System.out.println("Ingrese la cantidad de kilos transportados por el cliente:");
			ship.getClients()[i].setKilos(sc.nextInt());
			
			System.out.println("Ingrese el valor total pagado por el cliente:");
			ship.getClients()[i].setTotalValue(sc.nextInt());
			
			ship.getClients()[i].updateType();
			if(ship.getClients()[i].getType() == 1){
				System.out.println("El cliente esta en la clase Normal. \n");
			}else if(ship.getClients()[i].getType() == 2){
				System.out.println("El cliente esta en la clase Plata. \n");
			}else if(ship.getClients()[i].getType() == 3){
				System.out.println("El cliente esta en la clase Oro. \n");
			}else{
				System.out.println("El cliente esta en la clase Platino. \n");
			}	
		}
		clientsCreated = true;
		menu();
	}
	
	/**
	This method allows the used to load the ship with the specified number of cargos. <br>
	<b>pre:</b> The clients have to have been created beforehand. <br>
	<b>post:</b> The ship will be loaded with the cargo information entered by the user. The discounts will be applied according to the client's type and the total price for each client will be shown. The value cargosCreated will be set to true. <br>
	*/
	public void loadShip(){
		if(clientsCreated){
			System.out.println("\n Ingrese el numero de cargas que se van a transportar en el viaje. (1 a 5)");
			cargoQuantity = 0;
			
			while(cargoQuantity < 1 || cargoQuantity > 5){
				cargoQuantity = sc.nextInt();
			}
			
			int i, j;
			
			if(cargosCreated == false){	
				for(i=0; i<ship.getCargos().length; i++){
				ship.getCargos()[i] = new Cargo(0, 0, -1, 0);
				}
			}else{
				unloadShip(false);
				cargosCreated = true;
			}
			
			for(i=0; i<cargoQuantity; i++){
				System.out.println("\n Informacion de la carga #"+ (i+1) + ":");
				
				System.out.println("\n Ingrese la cantidad de cajas:");
				ship.getCargos()[i].setBoxes(sc.nextInt());
				
				while(ship.getCargos()[i].getBoxWeight() < 0.001){
					System.out.println("\n Ingrese el peso por caja: (en gramos)");
					ship.getCargos()[i].setBoxWeight((sc.nextDouble()/1000));
				}
				
				System.out.println("\n Clientes:");
				for(j=0; j<ship.getClients().length; j++){
					System.out.println((j+1) +"." + ship.getClients()[j].getName());
				}
				
				int choice = 0;
				while(choice < 1 || choice > 5){
					System.out.println("\n Ingrese el numero del cliente:");
					choice = sc.nextInt();
					ship.getCargos()[i].setClient((choice)-1);
					
					int repeatCount = 0;
					for(j=0; j<cargoQuantity; j++){
						if(ship.getCargos()[i].getClient() == ship.getCargos()[j].getClient()){
							repeatCount++;
						}
					}
					
					if(repeatCount >= 2){
						System.out.println("ERROR: El cliente ya tiene una carga asignada");
						repeatCount = 0;
						choice = 0;
						ship.getCargos()[i].setClient(0);
					}
				}
				
				System.out.println("\n Tipos de carga: \n 1. Perecedero \n 2. No perecedero \n 3. Peligroso");
				while(ship.getCargos()[i].getType() < 1 || ship.getCargos()[i].getType() > 3){
					System.out.println("Ingrese el tipo de carga");
					ship.getCargos()[i].setType(sc.nextInt());
				}
			}
			
			System.out.println("Estos son los precios totales para cada cliente:");
			for(i=0; i < cargoQuantity; i++){
				System.out.println(ship.getClients()[ship.getCargos()[i].getClient()].getName() + ": " + ship.getCargos()[i].getPrice(ship.getClients()[ship.getCargos()[i].getClient()].getType()));
			}
			cargosCreated = true;
		}else{
			System.out.print("ERROR: Debe crear los clientes primero.");
		}
		menu();
	}
	
	/**
	This method allows the user to get the information from a specific or all cargos. <br>
	<b>pre:</b> The cargo has to have been initialized with values other than empty or null. <br>
	<b>post:</b> The information of the cargo or cargos selected by the user will be displayed. <br>
	*/
	public void displayCargoMenu(){
		if(cargosCreated){
			int i;
			int j;
			int choice = 0;
			
			System.out.println("\n Que carga desea consultar?");
			
			for(i=0; i<cargoQuantity; i++){
				System.out.println((i+1)+". "+ ship.getClients()[ship.getCargos()[i].getClient()].getName());
			}
			System.out.println((i+1)+". Todas");
			
			while(choice < 1 || choice > (cargoQuantity+1)){
				choice = sc.nextInt();
			}
			
			if(choice == i+1){
				for(j=0; j<(cargoQuantity); j++){
					displayCargo(j);
				}
			}else{
				displayCargo(choice-1);
			}
		}else{
			System.out.println("ERROR: Debe crear la carga primero.");
		}
		
		menu();
	}
	
	/**
	This method displays the information of the cargo specified. <br>
	<b>pre:</b> The cargo has to have been initialized with values other than empty or null. <br>
	<b>post:</b> The information of the cargo will be displayed <br>
	@param cargo This is the position of the cargo in the array (Starts with 0). <br>
	*/
	public void displayCargo(int cargo){
		System.out.println("\n Informacion de la carga #"+ (cargo+1)+": \n");
		System.out.println("Cantidad de cajas: " + ship.getCargos()[cargo].getBoxes());
		System.out.println("Peso por caja (en kilos): " + ship.getCargos()[cargo].getBoxWeight());
		System.out.println("Cliente: " + ship.getClients()[ship.getCargos()[cargo].getClient()].getName());
		switch(ship.getCargos()[cargo].getType()){
			case 1:
			System.out.println("Tipo de carga: Perecedera");
			break;
			case 2:
			System.out.println("Tipo de carga: No Perecedera");
			break;
			case 3:
			System.out.println("Tipo de carga: Peligrosa");
			break;
		}
		System.out.println("Peso total (en kilos): "+ ship.getCargos()[cargo].getTotalWeight());
		System.out.println("Precio: "+ ship.getCargos()[cargo].getPrice(ship.getClients()[ship.getCargos()[cargo].getClient()].getType()));
	}
	
	/**
	This method allows the user to get the information from a specific or all clients. <br>
	<b>pre:</b> The client has to have been initialized with values other than empty or null. <br>
	<b>post:</b> The information of the clent or clients selected by the user will be displayed. <br>
	*/
	public void displayClientMenu(){
		if(clientsCreated){
			int i;
			int j;
			int choice = 0;
			
			System.out.println("\n Que cliente desea consultar?");
			
			for(i=0; i<ship.getClients().length; i++){
				System.out.println((i+1)+". "+ ship.getClients()[i].getName());
			}
			System.out.println((i+1)+". Todos");
			
			while(choice < 1 || choice > ((ship.getClients().length)+1)){
				choice = sc.nextInt();
			}
			
			if(choice == i+1){
				for(j=0; j<((ship.getClients().length)); j++){
					displayClient(j);
				}
			}else{
				displayClient(choice-1);
			}
		}else{
			System.out.println("ERROR: Debe crear el cliente primero.");
		}
		
		menu();
	}
	
	/**
	This method displays the information of the client specified. <br>
	<b>pre:</b> The client has to have been initialized with values other than empty or null. <br>
	<b>post:</b> The information of the client will be displayed <br>
	@param client This is the position of the client in the array (Starts with 0). <br>
	*/
	public void displayClient(int client){
		System.out.println("");
		System.out.println("Nombre: " + ship.getClients()[client].getName());
		System.out.println("Numero de registro mercantil: " + ship.getClients()[client].getRegisterNum());
		System.out.println("Fecha de expedicion del registro: " + ship.getClients()[client].getRegisterDate());
		switch(ship.getClients()[client].getType()){
			case 1:
			System.out.println("Tipo de cliente: Normal");
			break;
			
			case 2:
			System.out.println("Tipo de cliente: Plata");
			break;
			
			case 3:
			System.out.println("Tipo de cliente: Oro");
			break;
			
			case 4:
			System.out.println("Tipo de cliente: Platino");
			break;
		}
		System.out.println("Kilos totales transportados: " + ship.getClients()[client].getKilos());
		System.out.println("Precio total pagado: " + ship.getClients()[client].getTotalValue());
	}
	
	/**
	This method displays the ship's information: Total weight carried, total price of the cargo and uses checkStatus() to display whether the ship can leave port or not. <br>
	<b>pre:</b> The cargo and clients have to have been initialized with values other than empty or null. <br>
	<b>post:</b> The ship's information will be displayed. <br>
	*/
	public void displayShip(){
		if(cargosCreated){
			System.out.println("Informacion del barco: \n");
			System.out.println("Peso total transportado: " + ship.getTotalWeight(cargoQuantity) +"kg");
			System.out.println("Precio total de la carga: " + ship.getTotalPrice(cargoQuantity));
			System.out.println(ship.checkStatus(cargoQuantity));
			
		}else{
			System.out.println("ERROR: Debe crear la carga primero.");
		}
		
		menu();
	}
	
	/**
	This method updates the information of the clients who currently have a cargo on the ship. This only works if the ship can leave port. <br>
	<b>pre:</b> The cargo and clients have to have been initialized with values other than empty or null. <br>
	<b>post:</b> The ship's information will be displayed.
	*/
	public void sail(){
		if(cargosCreated){
			ship.checkStatus(cargoQuantity);
			if(ship.getCanLeave()){
			int i;	
			int[] startingType = new int[ship.getCargos().length]; 
			for(i=0; i < (cargoQuantity); i++){
				startingType[i] = ship.getClients()[ship.getCargos()[i].getClient()].getType();
				
				ship.getClients()[ship.getCargos()[i].getClient()].setKilos((ship.getClients()[ship.getCargos()[i].getClient()].getKilos())+(ship.getCargos()[i].getTotalWeight()));
				
				ship.getClients()[ship.getCargos()[i].getClient()].setTotalValue((ship.getClients()[ship.getCargos()[i].getClient()].getTotalValue())+(ship.getCargos()[i].getPrice(ship.getClients()[ship.getCargos()[i].getClient()].getType())));
				
				ship.getClients()[ship.getCargos()[i].getClient()].updateType();
				
				if(startingType[i] != ship.getClients()[ship.getCargos()[i].getClient()].getType()){
					if(ship.getClients()[ship.getCargos()[i].getClient()].getType() == 2){
						System.out.println(ship.getClients()[ship.getCargos()[i].getClient()].getName() + " ahora esta en la clase Plata. \n");
					}else if(ship.getClients()[ship.getCargos()[i].getClient()].getType() == 3){
						System.out.println(ship.getClients()[ship.getCargos()[i].getClient()].getName() + " ahora esta en la clase Oro. \n");
					}else{
						System.out.println(ship.getClients()[ship.getCargos()[i].getClient()].getName() + " ahora esta en la clase Platino. \n");
					}
				}
			}
			System.out.println("Los datos de los clientes se han actualizado correctamente.");
			}else{
			System.out.println(ship.checkStatus(cargoQuantity));
			}
		}else{
			System.out.println("ERROR: Debe crear la carga primero.");
		}
		
		menu();
	}
	
	/**
	This method sets all values of the cargo to 0. It also modifies the cargosCreated flag, which indicates whether a cargo has been created. <br>
	<b>pre:</b> A cargo has to have been created. <br>
	<b>post:</b> The cargos array will be set to all zeroes. cargosCreated will be set to false. <br>
	@param goToMenu If it is given "true" the function will activate the menu after being done.
	*/
	public void unloadShip(boolean goToMenu){
		int i;
		if(cargosCreated){
			for(i=0; i<ship.getCargos().length; i++){
				ship.getCargos()[i].setBoxes(0);
				ship.getCargos()[i].setBoxWeight(0);
				ship.getCargos()[i].setClient(0);
				ship.getCargos()[i].setType(0);
				cargosCreated = false;
			}
			
			System.out.print("El barco ha sido descargado con exito. \n");
		}else{
			System.out.print("ERROR: No se ha creado una carga. \n");
		}
		
		if(goToMenu){
			menu();
		}
	}
}