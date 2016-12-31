package presentation.hotelui;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import vo.OrderVO;

/**
 * @author samperson1997
 * 客户查看酒店详细信息单元格
 *
 */
public class CustomerHotelInfoCell extends GridPane {

	OrderVO orderVO;

	private Label hotelText;
	private Label roomText;
	private Label checkInText;
	private Label checkOutText;
	private Label numText;
	private Label childrenText;
	private Label totalText;
	private Label stateText;

	/**
	 * @param orderVO
	 * 客户查看酒店详细信息单元格
	 * 
	 */
	public CustomerHotelInfoCell(OrderVO orderVO) {
		
		super();
		this.orderVO = orderVO;

		hotelText = new Label(orderVO.hotelName);
		this.add(hotelText, 0, 0, 3, 1);
		stateText = new Label(orderVO.orderState);
		this.add(stateText, 4, 0, 1, 1);
		roomText = new Label(orderVO.roomName);
		this.add(roomText, 0, 1, 2, 1);
		totalText = new Label("¥" + String.valueOf(orderVO.total));
		this.add(totalText, 4, 1, 1, 1);
		checkInText = new Label("入 " + orderVO.checkInTime.toString());
		this.add(checkInText, 0, 2, 1, 1);
		checkOutText = new Label("离" + orderVO.checkOutTime.toString());
		this.add(checkOutText, 1, 2, 1, 1);
		numText = new Label(String.valueOf(orderVO.roomNum) + "间");
		this.add(numText, 1, 1, 1, 1);
		childrenText = new Label(orderVO.hasChildren ? "有" : "无" + "儿童");
		this.add(childrenText, 2, 1, 1, 1);

		this.setHgap(10);
		this.setVgap(20);

	}

}
