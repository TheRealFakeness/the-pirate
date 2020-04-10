package model;

public class Cargo {
	
	// constants
	public static final int PERISHABLE = 1;
	public static final int NONPERISHABLE = 2;
	public static final int DANGEROUS = 3;
	public static final int PERISHABLE_COST = 250000;
	public static final int NONPERISHABLE_COST = 80000;
	public static final int DANGEROUS_COST = 390000;
	public static final double SILVER_DISCOUNT = 0.015;
	public static final double GOLD_DISCOUNT = 0.03;
	public static final double PLATINUM_DISCOUNT = 0.05;
	
	// attributes
	private int boxes;
	private double boxWeight;
	private int client;
	private int type;
	
	// getters and setters
	public int getBoxes() {
		return boxes;
	}
	
	public void setBoxes(int boxes) {
		this.boxes = boxes;
	}
		
	public double getBoxWeight() {
		return boxWeight;
	} 
	
	public void setBoxWeight(double boxWeight) {
		this.boxWeight = boxWeight;
	}
	
	public int getClient() {
		return client;
	}
	
	public void setClient(int client) {
		this.client = client;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type){
		this.type = type;
	}
	
	// constructor
	public Cargo(int boxes, int boxWeight, int client, int type){
		this.boxes = boxes;
		this.boxWeight = boxWeight;
		this.client = client;
		this.type = type;
	}
	
	//methods
	/*
	This method calculates the total weight of the cargo. <br>
	<b>pre:</b> The cargo has to have been initialized with values other than empty or null. <br>
	<b>post:</b> Returns the total weight by multiplying box weight by box quantity.<br>
	**/
	public double getTotalWeight(){
		double totalWeight = boxes*boxWeight;
		return totalWeight;
	}
	
	/*
	This method calculates the total price of the cargo. <br>
	<b>pre:</b> The cargo has to have been initialized with values other than empty or null. <br>
	<b>post:</b> Returns the total price by multiplying total weight by cargo type cost and applying the discount given by the client's type.<br>
	@param clientType This is the client's type that will define the discount applied. <br>
	**/
	public int getPrice(int clientType){
		int price = 0;
		
		if (type == DANGEROUS){
			price = ((int)Math.round(getTotalWeight()))*DANGEROUS_COST;
		}else if (type == NONPERISHABLE){
			price = ((int)Math.round(getTotalWeight()))*NONPERISHABLE_COST;
		}else if (type == PERISHABLE){
			price = ((int)Math.round(getTotalWeight()))*PERISHABLE_COST;
		}
		
		if(clientType == 4){
			price = price - (int)Math.round((price * PLATINUM_DISCOUNT));
		}else if(clientType == 3 && (type == PERISHABLE || type == NONPERISHABLE)){
			price = price - (int)Math.round((price * GOLD_DISCOUNT));
		}else if(clientType == 2 && type == PERISHABLE){
			price = price - (int)Math.round((price * SILVER_DISCOUNT));
		}
		
		return price;
	}
}