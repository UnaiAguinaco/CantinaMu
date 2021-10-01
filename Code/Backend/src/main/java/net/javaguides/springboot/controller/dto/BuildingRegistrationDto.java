package net.javaguides.springboot.controller.dto;

public class BuildingRegistrationDto {
	private int id;
	private String name;
	private String address;

	
	public BuildingRegistrationDto(){
		
	}
	
	public BuildingRegistrationDto(int id,String name, String address) {
		super();
		this.id=id;
		this.name = name;
		this.address = address;
	}
	
	/** 
	 * @return int
	 */
	public int getId() {
		return id;
	}
	
	/** 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/** 
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/** 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** 
	 * @return String
	 */
	public String getAddress() {
		return address;
	}
	
	/** 
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
}
