package net.javaguides.springboot;

public class ChatMessage {

	private String roomId;

	private String type;

	private int[] deskStatus;
	private int row;
	private int col;
	private int users;

	public ChatMessage() {
	}

	public ChatMessage(String roomId, int[] deskStatus, String type, int row, int col, int users) {
		this.col = col;
		this.row = row;
		this.users = users;
		this.type = type;
		this.deskStatus = deskStatus;
		this.roomId = roomId;
	}

	
	/** 
	 * @return String
	 */
	public String getRoomId() {
		return roomId;
	}

	
	/** 
	 * @param roomId
	 */
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	
	/** 
	 * @return int[]
	 */
	public int[] getDeskStatus() {
		return deskStatus;
	}

	
	/** 
	 * @param deskStatus
	 */
	public void setDeskStatus(int[] deskStatus) {
		this.deskStatus = deskStatus;
	}

	
	/** 
	 * @return int
	 */
	public int getRow() {
		return row;
	}

	
	/** 
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}

	
	/** 
	 * @return int
	 */
	public int getCol() {
		return col;
	}

	
	/** 
	 * @param col
	 */
	public void setCol(int col) {
		this.col = col;
	}

	
	/** 
	 * @return int
	 */
	public int getUsers() {
		return users;
	}

	
	/** 
	 * @param users
	 */
	public void setUsers(int users) {
		this.users = users;
	}

	
	/** 
	 * @return String
	 */
	public String getType() {
		return type;
	}

	
	/** 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
}
