package model;

public class Client{
	//constants
	public static final int NORMAL = 1;
	public static final int SILVER = 2;
	public static final int GOLD = 3;
	public static final int PLATINUM = 4;
	public static final int SILVER_THRESHOLD_W = 35000;
	public static final int GOLD_THRESHOLD_W = 55000;
	public static final int GOLD_THRESHOLD_V = 2000000;
	public static final int PLATINUM_THRESHOLD_V = 5000000;
	
	//attributes
	private String name;
	private String registerNum;
	private String registerDate;
	private int type;
	private double kilos;
	private int totalValue;
	
	//getters and setters
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRegisterNum() {
		return registerNum;
	}
	
	public void setRegisterNum(String registerNum) {
		this.registerNum = registerNum;
	}
	
	public String getRegisterDate() {
		return registerDate;
	}
	
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public double getKilos() {
		return kilos;
	}
	
	public void setKilos(double kilos) {
		this.kilos = kilos;
	}
	
	public int getTotalValue() {
		return totalValue;
	}
	
	public void setTotalValue(int totalValue) {
		this.totalValue = totalValue;
	}
	
	
	// constructor
	public Client(String name, String registerNum, String registerDate, int type, int kilos, int totalValue){
		this.name = name;
		this.registerNum = registerNum;	
		this.registerDate = registerDate;
		this.type = type;
		this.kilos = kilos;
		this.totalValue = totalValue;
	}
	
	//methods
	
	/**
	This method sets the client's type depending on the conditions given by each rank.<br>
	<b>pre:</b> The client has to have been initialized with values other than empty or null.<br>
	<b>post:</b> The client's type is updated or left the same. <br>
	*/
	public void updateType(){
		if(totalValue >= PLATINUM_THRESHOLD_V){
			type = 4;
		}else if(totalValue >= GOLD_THRESHOLD_V || kilos >= GOLD_THRESHOLD_W){
			type = 3;
		}else if(kilos >= SILVER_THRESHOLD_W){
			type = 2;
		}else{
			type = 1;
		}
	}
}