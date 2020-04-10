package model;
import model.Client;
import model.Cargo;

public class Ship{
	// constants
	public static final int MAX_WEIGHT = 28000;
	public static final int MIN_WEIGHT = 12000;
	public static final int MIN_CARGOS = 2;
	
	//attributes
	private boolean canLeave = false;
	private Client[] clients = new Client[5];
	private Cargo[] cargos = new Cargo[5];
	
	// getters and setters
	public boolean getCanLeave() {
		return canLeave;
	}
	
	public void setCanLeave(boolean canLeave) {
		this.canLeave = canLeave;
	}
	
	public Client[] getClients(){
		return clients;
	}
	
	public void setClients(Client[] clients){
		this.clients = clients;
	}

	public Cargo[] getCargos(){
		return cargos;
	}
	
	public void setCargos(Cargo[] cargos){
		this.cargos = cargos;
	}
	
	// constructor
	public Ship(){
		this.canLeave = false;
	}
	
	// methods
	
	/**
	This method calculates the total weight the ship is carrying.<br>
	<b>pre:</b> The cargo has to have been initialized with values other than empty or null. <br>
	<b>post:</b> The function returns the total weight the ship carries. <br>
	@param cargoQuantity The number or cargos currently working with. <br>
	@return Total weight of the ship. <br>
	*/
	public double getTotalWeight(int cargoQuantity){
		int i;
		double totalWeight = 0;
		for(i=0; i < cargoQuantity; i++){
			totalWeight += cargos[i].getTotalWeight();
		}
			
		return totalWeight;
	}
	
	/**
	This method calculates the total price of the ship's cargo.<br>
	<b>pre:</b> The cargo has to have been initialized with values other than empty or null. <br>
	<b>post:</b> The function returns the total price of the ship cargo. <br>
	@param cargoQuantity The number or cargos currently working with. <br>
	@return Total price of the cargo. <br>
	*/
	public int getTotalPrice(int cargoQuantity){
		int i;
		int totalPrice = 0;
		for(i=0; i < cargoQuantity; i++){
			totalPrice += cargos[i].getPrice(clients[cargos[i].getClient()].getType());
		}
		
		return totalPrice;
	}
	
	/**
	This method returns a string that states whether the ship can leave port given the conditions for that to happen. It also updates the canLeave variable which determines if the ship can leave or not.<br>
	<b>pre:</b> The cargo and clients have to have been initialized with values other than empty or null. <br>
	<b>post:</b> The string with the status of the ship will be returned. canLeave will be updated with the ship's status. <br>
	@param cargoQuantity The number or cargos currently working with. <br>
	@return String with the status of the ship. <br>
	*/
	public String checkStatus(int cargoQuantity){
		int i;
		String status;
		String errors = "";
		boolean dangerousPresent = false;
		boolean perishablePresent = false;
		
		canLeave = true;
		
		if(getTotalWeight(cargoQuantity) > MAX_WEIGHT){
			canLeave = false;
			errors = errors + "La capacidad maxima del barco es " + MAX_WEIGHT + " kilos. ";
		}
		
		if(getTotalWeight(cargoQuantity) < MIN_WEIGHT && cargos.length < 2){
			canLeave = false;
			errors = errors + "El barco debe tener al menos 2 cargas o " + MIN_WEIGHT + " kilos. ";
		}
		
		for(i=0; i<cargos.length && dangerousPresent == false; i++){
			if(cargos[i].getType() == 3 ){
				dangerousPresent = true;
			}
		}
		
		for(i=0; i<cargos.length && perishablePresent == false; i++){
			if(cargos[i].getType() == 1 ){
				perishablePresent = true;
			}
		}
		
		if(dangerousPresent && perishablePresent){
			canLeave = false;
			errors = errors + "No se pueden transportar cargas peligrosas y perecederas a la vez. ";
		}
		
		if(canLeave){
			status = "El barco puede salir del puerto.";
		}else{
			status = "El barco no puede salir del puerto debido a los siguientes errores: " + errors;
		}
		
		return status;
	}
}