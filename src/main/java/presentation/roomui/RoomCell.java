package presentation.roomui;

import javafx.beans.property.SimpleStringProperty;
import vo.RoomVO;

/**
 * @author samperson1997
 * 房间单元格
 *
 */
public class RoomCell {
	private SimpleStringProperty roomName;
	private SimpleStringProperty price;
	private SimpleStringProperty numOfRoom;
	private SimpleStringProperty service;
	private SimpleStringProperty maxNumOfPeople;

	/**
	 * @param vo
	 * 房间单元格
	 * 
	 */
	public RoomCell(RoomVO vo) {
		
		roomName = new SimpleStringProperty(String.valueOf(vo.roomName));
		price = new SimpleStringProperty(String.valueOf(vo.price));
		numOfRoom = new SimpleStringProperty(String.valueOf(vo.numOfRoom));
		service = new SimpleStringProperty(vo.introduction);
		maxNumOfPeople = new SimpleStringProperty(String.valueOf(vo.maxNumOfPeople));

	}

	public RoomCell(String roomName, String price, String numOfRoom, String service, String maxNumOfPeople) {

		this.roomName = new SimpleStringProperty(roomName);
		this.price = new SimpleStringProperty(price);
		this.numOfRoom = new SimpleStringProperty(numOfRoom);
		this.service = new SimpleStringProperty(service);
		this.maxNumOfPeople = new SimpleStringProperty(maxNumOfPeople);
	}

	public void setPrice(String s) {
		price.set(s);
	}

	public String getPrice() {
		return price.get();
	}

	public void setService(String s) {
		service.set(s);
	}

	public String getService() {
		return service.get();
	}

	public void setRoomName(String s) {
		roomName.set(s);
	}

	public String getRoomName() {
		return roomName.get();
	}

	public void setNumOfRoom(String s) {
		numOfRoom.set(s);
	}

	public String getNumOfRoom() {
		return numOfRoom.get();
	}

	public void setMaxNumOfPeople(String s) {
		maxNumOfPeople.set(s);
	}

	public String getMaxNumOfPeople() {
		return maxNumOfPeople.get();
	}

}
